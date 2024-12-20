<template>
  <div>
    <!-- 筛选功能 -->
    <div style="margin-bottom: 16px; display: flex; justify-content: space-between;">
      <div style="flex: 1; display: flex; gap: 16px;">
        <!-- 筛选身份 -->
        <el-select
            v-model="selectedStatus"
            placeholder="选择身份"
            style="width: 120px;">  <!-- 设置宽度 -->
          <el-option label="全部" value="" />
          <el-option label="管理员" value="管理员" />
          <el-option label="服务员" value="服务员" />
        </el-select>


      <!-- 筛选是否启用 -->
      <el-select
          v-model="selectedEnabled"
          placeholder="是否启用"
          style="width: 120px;">
        <el-option label="全部" value="" />
        <el-option label="启用" value="true" />
        <el-option label="禁用" value="false" />
      </el-select>
      </div>

      <!-- 增加行按钮 -->
      <div style="display: flex; gap: 16px;">
        <el-button type="primary" @click="addRow">增加</el-button>
        <el-button type="success" @click="submitData">提交</el-button>  <!-- 新增提交按钮 -->
      </div>
    </div>

    <!-- 可编辑表格 -->
    <el-table :data="filteredTableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)" border style="width: 100%; height: 100%; overflow: auto;">
      <!-- 用户编号列 -->
      <el-table-column label="用户编号">
        <template #default="scope">
          <el-input
              v-model="scope.row.table_id"
              placeholder="请输入用户编号"
          ></el-input>
        </template>
      </el-table-column>

      <!-- 用户名列 -->
      <el-table-column label="用户名">
        <template #default="scope">
          <el-input
              v-model="scope.row.username"
              placeholder="请输入用户名"
          ></el-input>
        </template>
      </el-table-column>

      <!-- 用户密码列 -->
      <el-table-column label="密码">
        <template #default="scope">
          <el-input
              v-model="scope.row.password"
              type="password"
          placeholder="请输入密码"
          ></el-input>
        </template>
      </el-table-column>

      <!-- 用户身份 -->
      <el-table-column label="身份">
        <template #default="scope">
          <el-select v-model="scope.row.status" placeholder="请选择身份" style="width: 100%;">
            <el-option label="管理员" value="管理员"></el-option>
            <el-option label="服务员" value="服务员"></el-option>
          </el-select>
        </template>
      </el-table-column>

      <!-- 是否启用列 -->
      <el-table-column label="是否启用">
        <template #default="scope">
          <el-checkbox v-model="scope.row.enabled"></el-checkbox>
        </template>
      </el-table-column>

      <!-- 操作列 -->
      <el-table-column label="操作">
        <template #default="scope">
          <el-button
              type="danger"
              size="small"
              @click="deleteRow(scope.$index)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 翻页功能 -->
    <el-pagination
        :current-page="currentPage"
        :page-size="pageSize"
        :total="filteredTableData.length"
        layout="total, prev, pager, next, jumper"
        @current-change="handlePageChange"
        class="pagination" /> <!-- 添加自定义类 -->
  </div>
</template>

<script>
export default {
  name: "internal-staff",
  data() {
    return {
      selectedStatus: "",  // 当前选中的状态筛选
      selectedEnabled: "", // 当前选中的是否启用筛选
      tableData: [
        { table_id: "T001", username: "用户1", password: "123456", status: "管理员", enabled: true },
        { table_id: "T002", username: "用户2", password: "654321", status: "服务员", enabled: false },
        { table_id: "T003", username: "用户3", password: "password", status: "管理员", enabled: true },
        { table_id: "T004", username: "用户4", password: "admin", status: "服务员", enabled: false },
      ],
      currentPage: 1,  // 当前页数
      pageSize: 5,  // 每页显示数量
    };
  },
  computed: {
    // 根据状态和是否启用筛选数据
    filteredTableData() {
      return this.tableData.filter((row) => {
        const matchesStatus = this.selectedStatus ? row.status === this.selectedStatus : true;
        const matchesEnabled = this.selectedEnabled !== "" ? String(row.enabled) === this.selectedEnabled : true;  // 添加是否启用的筛选

        return matchesStatus && matchesEnabled;  // 两者都需要满足
      });
    },
  },

  methods: {
    // 增加行
    addRow() {
      const newRow = {
        table_id: "",  // 默认为空
        username: "",  // 默认为空
        password: "",  // 默认为空
        status: this.selectedStatus || "服务员",  // 默认根据筛选的状态
        enabled: true,  // 默认启用
      };
      this.tableData.push(newRow);
    },
    // 删除行
    deleteRow(index) {
      this.tableData.splice(index, 1);
    },
    // 提交数据的逻辑
    submitData() {
      // 这里可以添加提交表单的代码
      console.log("提交的数据：", this.tableData);
    },
    // 处理翻页
    handlePageChange(page) {
      this.currentPage = page;
    },
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
