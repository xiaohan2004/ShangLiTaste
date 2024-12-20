<template>
  <div>
    <!-- 筛选功能 -->
    <div style="margin-bottom: 16px; display: flex; justify-content: space-between;">
      <!-- 日期选择器 -->
      <div style="flex: 1; display: flex; gap: 16px;">
        <el-date-picker
            v-model="selectedDate"
            type="year"
            placeholder="选择年份"
            style="width: 200px;"
            @change="filterData"
        />
      </div>

      <!-- 导出按钮 -->
      <div style="display: flex; gap: 16px;">
        <el-button type="primary" @click="submitData">导出</el-button>
      </div>
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
      <!-- 列：月份 -->
      <el-table-column label="月份" prop="month" />
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
      selectedDate: null, // 选择的年份（日期对象）
      tableData: [
        { report_id: 1, year: 2024, month: 1, total_sales: 1000, total_orders: 10, total_bills: 8 },
        { report_id: 2, year: 2024, month: 2, total_sales: 1200, total_orders: 15, total_bills: 10 },
        { report_id: 3, year: 2024, month: 3, total_sales: 1300, total_orders: 12, total_bills: 9 },
        { report_id: 4, year: 2023, month: 11, total_sales: 1400, total_orders: 18, total_bills: 14 },
        { report_id: 5, year: 2023, month: 12, total_sales: 1500, total_orders: 20, total_bills: 18 },
        { report_id: 6, year: 2023, month: 1, total_sales: 1600, total_orders: 22, total_bills: 20 },
      ],
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页显示的数量
    };
  },
  computed: {
    // 根据选择的年份过滤数据
    filteredTableData() {
      if (!this.selectedDate) return this.tableData; // 如果未选择年份，显示全部数据

      const selectedYear = this.selectedDate.getFullYear(); // 获取选择的年份
      return this.tableData.filter(row => row.year === selectedYear); // 根据年份筛选
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
    // 当年份选择器变化时的逻辑
    filterData() {
      this.currentPage = 1; // 切换年份后，将页码重置为第一页
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

/* 日期选择器样式 */
.el-date-picker {
  width: 200px !important;
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

