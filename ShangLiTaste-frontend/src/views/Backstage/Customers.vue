<template>
  <div>
    <!-- 筛选功能 -->
    <div style="margin-bottom: 16px; display: flex; justify-content: space-between;">
      <div style="flex: 1; display: flex; gap: 16px;">
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

      <!-- 提交按钮 -->
      <div style="display: flex; gap: 16px;">
        <el-button type="primary" @click="submitData">提交</el-button>  <!-- 提交按钮 -->
      </div>
    </div>

    <!-- 普通表格 -->
    <el-table :data="filteredTableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)" border style="width: 100%; height: 100%; overflow: auto;">
      <!-- 用户编号列 -->
      <el-table-column label="用户编号" prop="table_id" />

      <!-- 用户名列 -->
      <el-table-column label="用户名" prop="username" />

      <!-- 用户密码列 (密码不显示为明文) -->
      <el-table-column label="密码" prop="password">
        <template #default="scope">
          <el-input
              v-model="scope.row.password"
              type="password"
              placeholder="请输入密码"
              disabled
          ></el-input>
        </template>
      </el-table-column>

      <!-- 用户身份列 -->
      <el-table-column label="身份" prop="status" />

      <!-- 是否启用列 -->
      <el-table-column label="是否启用">
        <template #default="scope">
          <el-checkbox v-model="scope.row.enabled"></el-checkbox>
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
        class="pagination" />
  </div>
</template>

<script>
export default {
  name: "customers",
  data() {
    return {
      selectedEnabled: "", // 当前选中的是否启用筛选
      tableData: [
        { table_id: "T001", username: "用户1", password: "123456", status: "顾客", enabled: true },
        { table_id: "T002", username: "用户2", password: "654321", status: "顾客", enabled: false },
        { table_id: "T003", username: "用户3", password: "password", status: "顾客", enabled: true },
        { table_id: "T004", username: "用户4", password: "admin", status: "顾客", enabled: false },
      ],
      currentPage: 1,  // 当前页数
      pageSize: 5,  // 每页显示数量
    };
  },
  computed: {
    // 根据是否启用筛选数据
    filteredTableData() {
      return this.tableData.filter((row) => {
        if (this.selectedEnabled === "") {
          return true;  // 如果没有选择启用状态，则返回所有数据
        }
        return row.enabled === (this.selectedEnabled === "true");  // 比较布尔值
      });
    },
  },

  methods: {
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
