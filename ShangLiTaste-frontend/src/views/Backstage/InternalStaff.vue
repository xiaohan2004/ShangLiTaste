<template>
  <div>
    <!-- 筛选条件 -->
    <div style="display: flex; justify-content: space-between; align-items: center;">
      <div>
        <el-select v-model="selectedStatus" placeholder="身份">
          <el-option label="全部" value=""></el-option>  <!-- 添加“全部”选项 -->
          <el-option label="管理员" value="管理员"></el-option>
          <el-option label="服务员" value="服务员"></el-option>
        </el-select>
        <el-select v-model="selectedEnabled" placeholder="是否启用">
          <el-option label="全部" value=""></el-option>  <!-- 添加“全部”选项 -->
          <el-option label="启用" value="true"></el-option>
          <el-option label="禁用" value="false"></el-option>
        </el-select>
      </div>

      <!-- 增加按钮 -->
      <el-button type="primary" @click="addRow" size="small">增加</el-button>
    </div>

    <!-- 用户列表 -->
    <el-table :data="filteredTableData" style="width: 100%" stripe>
      <el-table-column label="用户编号" prop="userId">
        <template #default="{ row }">
          <el-input
              v-if="row.isEditing"
              v-model="row.userId"
              size="small"
              disabled
          ></el-input>
          <span v-else>{{ row.userId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户名" prop="username">
        <template #default="{ row }">
          <el-input
              v-if="row.isEditing"
              v-model="row.username"
              size="small"
              placeholder="请输入用户名"
          ></el-input>
          <span v-else>{{ row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column label="密码" prop="password">
        <template #default="{ row }">
          <el-input
              v-if="row.isEditing"
              v-model="row.password"
              size="small"
              placeholder="请输入密码"
              type="password" >
          </el-input>
          <span v-else>{{ row.password }}</span>
        </template>
      </el-table-column>

      <el-table-column label="身份" prop="status">
        <template #default="{ row }">
          <el-select
              v-if="row.isEditing"
              v-model="row.status"
              size="small"
              placeholder="选择身份"
          >
            <el-option label="管理员" value="管理员"></el-option>
            <el-option label="服务员" value="服务员"></el-option>
          </el-select>
          <span v-else>{{ row.status }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否启用" prop="enabled">
        <template #default="{ row }">
          <el-switch
              v-if="row.isEditing"
              v-model="row.enabled"
              size="small"
          ></el-switch>
          <span v-else>{{ row.enabled ? '启用' : '禁用' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button
              type="warning"
              size="small"
              @click="editRow(scope.row)"
              v-if="!scope.row.isEditing"
          >
            编辑
          </el-button>
          <el-button
              type="success"
              size="small"
              @click="saveRow(scope.row)"
              v-if="scope.row.isEditing"
          >
            保存
          </el-button>
          <el-button
              type="danger"
              size="small"
              @click="deleteRow(scope.row)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: "internal-staff",
  data() {
    return {
      selectedStatus: "",  // 当前选中的状态筛选
      selectedEnabled: "", // 当前选中的是否启用筛选
      userData: [],  // 存储从后端获取的用户数据
    };
  },
  computed: {
    // 根据状态和是否启用筛选数据
    filteredTableData() {
      return this.userData.filter((row) => {
        const matchesStatus = this.selectedStatus ? row.status === this.selectedStatus : true;
        const matchesEnabled = this.selectedEnabled !== "" ? String(row.enabled) === this.selectedEnabled : true;
        return matchesStatus && matchesEnabled;
      });
    },
  },
  methods: {
    // 获取用户数据
    fetchUserData() {
      axios.get('http://10.100.164.44:8080/api/users')  // 替换为你的后端接口地址
          .then(response => {
            if (response.data.code === 1) {
              this.userData = response.data.data.map(user => {
                return {
                  userId: user.userId,  // 用户编号
                  username: user.username,  // 用户名
                  password: user.password,  // 密码
                  status: user.role === 0 ? '管理员' : '服务员',  // 根据角色映射身份
                  enabled: user.status === 1,  // 根据状态设置是否启用
                  isEditing: false, // 默认不编辑
                };
              });
            } else {
              console.error('Failed to fetch data');
            }
          })
          .catch(error => {
            console.error('Error fetching data:', error);
          });
    },

    // 编辑行
    editRow(row) {
      row.isEditing = true;  // 设置当前行为可编辑
    },

    // 保存行
    saveRow(row) {
      axios.put(`http://10.100.164.44:8080/api/users/${row.userId}`, {
        userId: row.userId,
        username: row.username,
        password: row.password,
        role: row.status === '管理员' ? 0 : 1,  // 根据身份选择角色
        status: row.enabled ? 1 : 0,  // 启用状态
      })
          .then(response => {
            if (response.data.code === 1) {
              row.isEditing = false;  // 保存成功后，取消编辑状态
            } else {
              console.error('Failed to update user');
            }
          })
          .catch(error => {
            console.error('Error updating user:', error);
          });
    },

    // 删除行
    deleteRow(row) {
      // 向后端发送删除请求
      axios.delete(`http://10.100.164.44:8080/api/users/${row.userId}`)
          .then(response => {
            if (response.data.code === 1) {
              // 删除成功，移除本地数据中的该行
              const index = this.tableData.indexOf(row);
              if (index !== -1) {
                this.tableData.splice(index, 1);
              }
            } else {
              console.error('Failed to delete user');
            }
          })
          .catch(error => {
            console.error('Error deleting user:', error);
          });
    },

    addRow() {
      const newRow = {
        userId: "",  // 默认为空
        username: "",  // 默认为空
        password: "",  // 默认为空
        status: "服务员",  // 默认是“服务员”
        enabled: true,  // 默认启用
        isEditing: false,  // 默认是编辑状态
      };

      // 将新增的用户数据推送到表格
      this.userData.push(newRow);

      // 向后端发送创建用户请求
      axios.post('http://10.100.164.44:8080/api/users', {
        username: newRow.username,
        password: newRow.password,
        role: newRow.status === '管理员' ? 0 : 1,  // 根据身份选择角色
        status: newRow.enabled ? 1 : 0,  // 启用状态
      })
          .then(response => {
            if (response.data.code === 1) {
              // 创建用户成功，可以在此处更新返回的数据（如用户ID）
              newRow.userId = response.data.data.userId;  // 假设返回数据中有用户ID
              newRow.isEditing = false;  // 取消编辑状态
            } else {
              console.error('Failed to create user');
              this.userData.pop();  // 如果创建失败，移除新增的空行
            }
          })
          .catch(error => {
            console.error('Error creating user:', error);
            this.userData.pop();  // 如果请求失败，移除新增的空行
          });
    },


    // 处理翻页
    handlePageChange(page) {
      this.currentPage = page;
    },
  },
  created() {
    this.fetchUserData();
  },
};
</script>



<style scoped>
/* 自定义样式 */
.el-table {
  width: 100% !important; /* 确保表格宽度适应容器 */
  height: 100% !important; /* 确保表格高度适应容器 */
}

.el-table__body-wrapper {
  max-height: calc(100vh - 150px); /* 限制表格的最大高度，避免超出屏幕 */
  overflow: auto;
}

/* 移除列宽度限制，确保表格填充所有区域 */
.el-table-column {
  width: auto; /* 让列宽度自动调整 */
}

.el-table__header,
.el-table__body {
  table-layout: fixed; /* 使用固定布局，使列宽平分 */
}

/* 给筛选框增加一些间距 */
.el-select {
  width: 120px;  /* 设置固定宽度 */
}

.el-button {
  height: 36px; /* 设置按钮高度 */
}

/* 给翻页组件增加间距 */
.el-pagination {
  margin-top: 20px; /* 设置翻页与表格的间距 */
}
</style>
