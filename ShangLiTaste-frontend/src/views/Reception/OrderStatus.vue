<template>
  <div>
    <!-- 筛选功能 -->
    <div
        style="margin-bottom: 16px; display: flex; justify-content: flex-start; gap: 16px;"
    >
      <!-- 筛选订单状态 -->
      <el-select v-model="selectedStatus" placeholder="选择订单状态" style="width: 120px;">
        <el-option label="全部" value="" />
        <el-option label="进行中" value="进行中" />
        <el-option label="已完成" value="已完成" />
        <el-option label="已结账" value="已结账" />
      </el-select>
    </div>

    <!-- 表格展示 -->
    <el-table
        :data="filteredOrderData.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
        border
        style="width: 100%;"
    >
      <!-- 订单编号列 -->
      <el-table-column label="订单编号" prop="orderId"></el-table-column>

      <!-- 餐桌编号列 -->
      <el-table-column label="餐桌编号" prop="tableId"></el-table-column>

      <!-- 下单时间列 -->
      <el-table-column label="下单时间" prop="orderTime"></el-table-column>

      <!-- 订单状态 -->
      <el-table-column label="订单状态" prop="status">
        <template #default="{ row }">
          <el-tag :type="getStatusType(row.status)" size="small">{{ row.status }}</el-tag>
          <el-button
              v-if="row.status === '已完成'"
              type="primary"
              size="small"
              @click="showCheckoutDialog(row)"
              style="margin-left: 10px;"
          >
            结账
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 翻页功能 -->
    <el-pagination
        :current-page="currentPage"
        :page-size="pageSize"
        :total="filteredOrderData.length"
        layout="total, prev, pager, next, jumper"
        @current-change="handlePageChange"
        class="pagination"
    />

    <!-- 结账弹窗 -->
    <el-dialog
        title="选择支付方式"
        v-model="checkoutDialogVisible"
        width="30%"
    >
      <el-radio-group v-model="selectedPaymentMethod">
        <el-radio label="cash">现金</el-radio>
        <el-radio label="alipay">支付宝</el-radio>
        <el-radio label="wechat">微信支付</el-radio>
      </el-radio-group>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="checkoutDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="confirmCheckout">确认结账</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
import api from "@/api/api";

export default {
  name: "OrderManagement",
  data() {
    return {
      selectedStatus: "",  // 当前选中的状态筛选
      orderData: [],  // 存储从后端获取的用户数据
      currentPage: 1,  // 当前页码，初始化为第一页
      pageSize: 10,    // 每页显示的数据条数，初始化为10
      checkoutDialogVisible: false, // 控制结账弹窗的显示
      selectedPaymentMethod: '', // 选中的支付方式
      currentCheckoutOrder: null, // 当前正在结账的订单
    };
  },
  computed: {
    // 根据筛选条件过滤数据
    filteredOrderData() {
      return this.orderData.filter((item) => {
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
        case '进行中':
          return 'warning'; // 黄色
        case '已完成':
          return 'success'; // 绿色
        case '已结账':
          return 'info'; // 蓝色
        default:
          return 'default'; // 默认颜色
      }
    },
    fetchDailyData() {
      api.get('/api/orders')  // 替换为你的后端接口地址
          .then(response => {
            if (response.data.code === 1) {
              this.orderData = response.data.data.map(order => {
                return {
                  orderId: order.orderId,
                  tableId: order.tableId,
                  orderTime: order.orderTime,
                  status: order.status === 0 ? '进行中' : order.status === 1 ? '已完成' : '已结账',
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
    showCheckoutDialog(row) {
      this.currentCheckoutOrder = row;
      this.checkoutDialogVisible = true;
      this.selectedPaymentMethod = ''; // 重置选中的支付方式
    },
    confirmCheckout() {
      if (!this.selectedPaymentMethod) {
        this.$message.warning('请选择支付方式');
        return;
      }

      // 创建一个新的对象，只包含需要更新的字段
      const updatedOrder = {
        orderId: this.currentCheckoutOrder.orderId,
        status: 2  // 假设2代表"已结账"状态
      };

      api.put(`/api/orders/${this.currentCheckoutOrder.orderId}`, updatedOrder)
          .then(response => {
            if (response.data.code === 1) {
              // 更新成功，修改本地数据
              this.currentCheckoutOrder.status = '已结账';
              this.$message.success(`订单已成功结账，支付方式：${this.getPaymentMethodName(this.selectedPaymentMethod)}`);
              this.checkoutDialogVisible = false;
            } else {
              // 处理特定的错误情况
              if (response.data.code === 0 && response.data.msg) {
                this.$message.error(response.data.msg);
              } else {
                this.$message.error('结账失败: ' + (response.data.msg || '未知错误'));
              }
            }
          })
          .catch(error => {
            console.error('Error updating order:', error);
            this.$message.error('结账失败，请稍后重试');
          });
    },
    getPaymentMethodName(method) {
      switch (method) {
        case 'cash':
          return '现金';
        case 'alipay':
          return '支付宝';
        case 'wechat':
          return '微信支付';
        default:
          return '未知方式';
      }
    },
  },
  created() {
    this.fetchDailyData();  // 页面创建时加载数据
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

