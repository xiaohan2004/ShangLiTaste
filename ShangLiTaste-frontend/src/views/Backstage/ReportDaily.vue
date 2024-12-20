<template>
  <div>
    <!-- 筛选功能 -->
    <div style="margin-bottom: 16px; display: flex; justify-content: space-between;">
      <!-- 日期选择器 -->
      <div style="flex: 1; display: flex; gap: 16px;">
        <el-date-picker
            v-model="selectedDate"
            type="month"
            placeholder="选择年月"
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
      <!-- 列：日期 -->
      <el-table-column label="日期" prop="date" />
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
  name: "Customers",
  data() {
    return {
      selectedDate: null, // 选择的年月（日期对象）
      tableData: [
        { report_id: 1, date: "2024-01-01", total_sales: 1000, total_orders: 10, total_bills: 8 },
        { report_id: 2, date: "2024-02-15", total_sales: 1200, total_orders: 15, total_bills: 10 },
        { report_id: 3, date: "2024-03-10", total_sales: 1300, total_orders: 12, total_bills: 9 },
        { report_id: 4, date: "2024-02-25", total_sales: 1400, total_orders: 18, total_bills: 14 },
        { report_id: 5, date: "2024-02-28", total_sales: 1500, total_orders: 20, total_bills: 18 },
        { report_id: 6, date: "2024-03-02", total_sales: 1600, total_orders: 22, total_bills: 20 },
      ],
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页显示的数量
    };
  },
  computed: {
    // 根据选择的年月过滤数据
    filteredTableData() {
      if (!this.selectedDate) return this.tableData; // 如果未选择日期，显示全部数据

      // 将 selectedDate 转换为 yyyy-MM 格式，并确保是本地时间
      const year = this.selectedDate.getFullYear();
      const month = String(this.selectedDate.getMonth() + 1).padStart(2, "0"); // 月份需要 +1，因为 getMonth() 从 0 开始
      const selectedYearMonth = `${year}-${month}`;

      return this.tableData.filter(row => {
        const rowYearMonth = row.date.slice(0, 7); // 提取数据的年月
        return rowYearMonth === selectedYearMonth; // 比较
      });
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
    // 当日期选择器变化时的逻辑
    filterData() {
      this.currentPage = 1; // 切换日期后，将页码重置为第一页
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
