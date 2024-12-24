<template>
  <div>
    <!-- 筛选条件和增加按钮容器 -->
    <div style="display: flex; justify-content: space-between; align-items: center;">

      <!-- 增加按钮，使用 flex-end 来让按钮右对齐 -->
      <el-button type="primary" @click="addRow" size="small" style="margin-left: auto;">增加</el-button>
    </div>


    <!-- 顾客信息列表 -->
    <el-table :data="filteredCustomerData" style="width: 100%" stripe>
      <el-table-column label="顾客编号" prop="customerId">
        <template #default="{ row }">
          <el-input
              v-if="row.isEditing"
              v-model="row.customerId"
              size="small"
              disabled
          ></el-input>
          <span v-else>{{ row.customerId }}</span>
        </template>
      </el-table-column>

      <el-table-column label="姓名" prop="name">
        <template #default="{ row }">
          <el-input
              v-if="row.isEditing"
              v-model="row.name"
              size="small"
              placeholder="请输入姓名"
          ></el-input>
          <span v-else>{{ row.name }}</span>
        </template>
      </el-table-column>

      <el-table-column label="电话" prop="phone">
        <template #default="{ row }">
          <el-input
              v-if="row.isEditing"
              v-model="row.phone"
              size="small"
              placeholder="请输入电话"
          ></el-input>
          <span v-else>{{ row.phone }}</span>
        </template>
      </el-table-column>

      <el-table-column label="邮箱" prop="email">
        <template #default="{ row }">
          <el-input
              v-if="row.isEditing"
              v-model="row.email"
              size="small"
              placeholder="请输入邮箱"
          ></el-input>
          <span v-else>{{ row.email }}</span>
        </template>
      </el-table-column>

      <el-table-column label="地址" prop="address">
        <template #default="{ row }">
          <el-input
              v-if="row.isEditing"
              v-model="row.address"
              size="small"
              placeholder="请输入地址"
          ></el-input>
          <span v-else>{{ row.address }}</span>
        </template>
      </el-table-column>

      <el-table-column label="注册日期" prop="registrationDate">
        <template #default="{ row }">
          <el-input
              v-if="row.isEditing"
              v-model="row.registrationDate"
              size="small"
              placeholder="请输入注册日期"
          ></el-input>
          <span v-else>{{ row.registrationDate }}</span>
        </template>
      </el-table-column>

      <el-table-column label="生日" prop="birthday">
        <template #default="{ row }">
          <el-input
              v-if="row.isEditing"
              v-model="row.birthday"
              size="small"
              placeholder="请输入注册生日"
          ></el-input>
          <span v-else>{{ row.birthday }}</span>
        </template>
      </el-table-column>

      <el-table-column label="累计消费金额" prop="totalSpent">
        <template #default="{ row }">
          <el-input
              v-if="row.isEditing"
              v-model="row.totalSpent"
              size="small"
              placeholder="请输入累计消费"
          ></el-input>
          <span v-else>{{ row.totalSpent }}</span>
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
import axios from "axios";

export default {
  name: "customers",
  data() {
    return {
      selectedEnabled: "",  // 当前选中的是否启用筛选
      customerData: [],  // 存储从后端获取的顾客数据
    };
  },
  computed: {
    // 过滤后的数据
    filteredCustomerData() {
      return this.customerData;  // 如果没有其他筛选条件，直接返回所有数据
    },
  },

  methods: {
    // 获取顾客数据
    fetchCustomerData() {
      axios.get('http://10.100.164.44:8080/api/customers')  // 替换为你的后端接口地址
          .then(response => {
            if (response.data.code === 1) {
              this.customerData = response.data.data.map(user => {
                return {
                  customerId: user.customerId,  // 顾客编号
                  name: user.name,  // 顾客姓名
                  phone: user.phone,  // 顾客电话
                  email: user.email,  // 顾客邮箱
                  address: user.address,  // 顾客地址
                  registrationDate: user.registrationDate,  // 注册日期
                  birthday: user.birthday,  // 生日
                  totalSpent: user.totalSpent,  // 累计消费金额
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
      axios.put(`http://10.100.164.44:8080/api/customers/${row.customerId}`, {
        customerId: row.customerId,  // 顾客编号
        name: row.name,  // 顾客姓名
        phone: row.phone,  // 顾客电话
        email: row.email,  // 顾客邮箱
        address: row.address,  // 顾客地址
        registrationDate: row.registrationDate,  // 注册日期
        birthday: row.birthday,  // 生日
        totalSpent: row.totalSpent,  // 累计消费金额
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
      axios.delete(`http://10.100.164.44:8080/api/customers/${row.customerId}`)
          .then(response => {
            if (response.data.code === 1) {
              // 删除成功，移除本地数据中的该行
              const index = this.customerData.indexOf(row);
              if (index !== -1) {
                this.customerData.splice(index, 1);
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
        customerId:"" , // 顾客编号
        name: "",  // 顾客姓名
        phone: "",  // 顾客电话
        email: "",  // 顾客邮箱
        address: "",  // 顾客地址
        registrationDate: "",  // 注册日期
        birthday: "",  // 生日
        totalSpent: "",  // 累计消费金额
        isEditing: false,  // 默认是编辑状态
      };

      // 将新增的用户数据推送到表格
      this.customerData.push(newRow);

      // 向后端发送创建用户请求
      axios.post('http://10.100.164.44:8080/api/customers', {
        customerId: newRow.customerId,
        name: newRow.name,
        phone: newRow.phone,
        email: newRow.email,
        address: newRow.address,
        registrationDate: newRow.registrationDate,
        birthday: newRow.birthday,
        totalSpent: newRow.totalSpent,
      })
          .then(response => {
            if (response.data.code === 1) {
              // 创建用户成功，可以在此处更新返回的数据（如用户ID）
              newRow.customerId = response.data.data.customerId;  // 假设返回数据中有用户ID
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
    // 组件加载时调用获取顾客数据的函数
    this.fetchCustomerData();
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
