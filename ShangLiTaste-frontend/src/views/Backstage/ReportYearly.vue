<template>
  <div>

    <div style="margin-bottom: 16px; display: flex; justify-content: flex-end;">
      <!-- 导出按钮 -->
      <el-button type="primary" @click="submitData">导出</el-button>
    </div>


    <!-- 表格 -->
    <el-table
        :data="filteredTableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
        border
        style="width: 100%; height: 100%; overflow: auto;"
    >
      <!-- 列：报表编号 -->
      <el-table-column label="报表编号" prop="report_id" />
      <!-- 列：年份 -->
      <el-table-column label="年份" prop="year" />
      <!-- 列：总销售额 -->
      <el-table-column label="总销售额" prop="total_sales" />
      <!-- 列：总订单数 -->
      <el-table-column label="总订单数" prop="total_orders" />
      <!-- 列：总账单数 -->
      <el-table-column label="总账单数" prop="total_bills" />
    </el-table>

    <!-- 分页 -->
    <el-pagination
        :current-page="currentPage"
        :page-size="pageSize"
        :total="filteredTableData.length"
        layout="total, prev, pager, next, jumper"
        @current-change="handlePageChange"
        class="pagination"
    />
  </div>
</template>

<script>
export default {
  name: "ReportMonthly",
  data() {
    return {
      tableData: [
        { report_id: 1, year: 2024, total_sales: 1000, total_orders: 10, total_bills: 8 },
        { report_id: 2, year: 2023, total_sales: 1600, total_orders: 22, total_bills: 20 },
      ],
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页显示的数量
    };
  },
  computed: {
    // 根据选择的年份过滤数据
    filteredTableData() {
      return this.tableData; // 不再需要根据年份进行筛选
    },
  },

  methods: {
    // 导出数据逻辑
    submitData() {
      console.log("导出的数据：", this.filteredTableData);
    },
    // 翻页逻辑
    handlePageChange(page) {
      this.currentPage = page; // 更新当前页
    },
  },
};
</script>

<style scoped>
/* 表格样式 */
.el-table {
  width: 100% !important;
  height: 100% !important;
}

/* 表格内容最大高度，支持滚动 */
.el-table__body-wrapper {
  max-height: calc(100vh - 150px);
  overflow: auto;
}

/* 按钮样式 */
.el-button {
  height: 36px;
}

/* 分页样式 */
.el-pagination {
  margin-top: 20px;
}
</style>


