<template>
  <div>
    <!-- 筛选功能 -->
    <div
        style="margin-bottom: 16px; display: flex; justify-content: flex-start; gap: 16px;"
    >
      <!-- 筛选订单状态 -->
      <el-select v-model="selectedStatus" placeholder="选择订单状态" style="width: 120px;">
        <el-option label="全部" value="" />
        <el-option label="待支付" value="待支付" />
        <el-option label="已支付" value="已支付" />
        <el-option label="已完成" value="已完成" />
        <el-option label="已取消" value="已取消" />
      </el-select>
    </div>

    <!-- 表格展示 -->
    <el-table
        :data="filteredTableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
        border
        style="width: 100%;"
    >
      <!-- 订单编号列 -->
      <el-table-column label="订单编号" prop="order_id"></el-table-column>

      <!-- 餐桌编号列 -->
      <el-table-column label="餐桌编号" prop="table_id"></el-table-column>

      <!-- 下单时间列 -->
      <el-table-column label="下单时间" prop="order_time"></el-table-column>

      <!-- 订单状态 -->
      <el-table-column label="订单状态" prop="status">
        <template #default="{ row }">
          <el-tag :type="getStatusType(row.status)" size="small">{{ row.status }}</el-tag>
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
        class="pagination"
    />
  </div>
</template>

<script>
export default {
  name: "OrderManagement",
  data() {
    return {
      selectedStatus: "", // 当前选中的订单状态筛选
      tableData: [
        {
          order_id: "O001",
          table_id: "T01",
          order_time: "2024-12-01 12:00",
          status: "待支付",
        },
        {
          order_id: "O002",
          table_id: "T02",
          order_time: "2024-12-01 13:00",
          status: "已支付",
        },
        {
          order_id: "O003",
          table_id: "T03",
          order_time: "2024-12-01 14:00",
          status: "已完成",
        },
        {
          order_id: "O004",
          table_id: "T04",
          order_time: "2024-12-01 15:00",
          status: "已取消",
        },
      ],
      currentPage: 1, // 当前页数
      pageSize: 5, // 每页显示数量
    };
  },
  computed: {
    // 根据筛选条件过滤数据
    filteredTableData() {
      return this.tableData.filter((item) => {
        const statusMatch = this.selectedStatus ? item.status === this.selectedStatus : true;
        return statusMatch;
      });
    },
  },
  methods: {
    handlePageChange(page) {
      this.currentPage = page;
    },
    // 根据订单状态返回对应的颜色
    getStatusType(status) {
      switch (status) {
        case '待支付':
          return 'warning'; // 黄色
        case '已支付':
          return 'success'; // 绿色
        case '已完成':
          return 'info'; // 蓝色
        case '已取消':
          return 'danger'; // 红色
        default:
          return 'default'; // 默认颜色
      }
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
  width: 120px; /* 设置固定宽度 */
}

.el-pagination {
  margin-top: 20px; /* 设置翻页与表格的间距 */
}
</style>

