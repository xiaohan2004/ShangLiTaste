<script>
import axios from 'axios';
import api from "@/api/api";
import { ref } from 'vue';

export default {
  name: 'TableManagement',
  data() {
    return {
      tables: [],
      filterStatus: 'all',
      filterType: 'all',
      currentPage: 1,
      pageSize: 40,
      dialogVisible: false,
      currentOrder: null,
      orderItems: [],
      isLoading: false,
    };
  },
  computed: {
    filteredTables() {
      return this.tables.filter((table) => {
        const matchStatus = this.filterStatus === 'all' || table.status === this.filterStatus;
        const matchType = this.filterType === 'all' || table.location === this.filterType;
        return matchStatus && matchType;
      });
    },
    paginatedTables() {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      return this.filteredTables.slice(startIndex, startIndex + this.pageSize);
    }
  },
  methods: {
    fetchTables() {
      api.get('/api/tables')
          .then(response => {
            if (response.data.code === 1) {
              this.tables = response.data.data.map(table => ({
                tableId: table.tableId,
                status: table.status === 0 ? '空闲' : table.status === 1 ? '已预订' : '已占用',
                location: table.location === 0 ? '大厅' : table.location === 1 ? '包间' : '私密房间',
              }));
            } else {
              console.error('Failed to fetch table data');
            }
          })
          .catch(error => {
            console.error('Error fetching table data:', error);
          });
    },
    handlePageChange(page) {
      this.currentPage = page;
    },
    async handleTableClick(tableId) {
      this.isLoading = true;
      try {
        const orderResponse = await api.get(`/api/orders/table/${tableId}`);
        if (orderResponse.data.code === 1) {
          const order = orderResponse.data.data;
          this.currentOrder = {
            orderId: order.orderId,
            tableId: order.tableId,
            orderTime: order.orderTime,
            status: order.status === 0 ? '进行中' : order.status === 1 ? '已完成' : '已结账',
          };
          await this.fetchOrderItems(order.orderId);
          this.dialogVisible = true;
        } else {
          console.error('Failed to fetch order data');
        }
      } catch (error) {
        console.error('Error fetching order data:', error);
      } finally {
        this.isLoading = false;
      }
    },
    async fetchOrderItems(orderId) {
      try {
        const response = await api.get(`/api/order-items/order/${orderId}`);
        if (response.data.code === 1) {
          this.orderItems = await Promise.all(response.data.data.map(async (item) => {
            const dishResponse = await api.get(`/api/dishes/${item.dishId}`);
            const dishName = dishResponse.data.code === 1 ? dishResponse.data.data.name : 'Unknown';
            return { ...item, dishName };
          }));
        } else {
          console.error('Failed to fetch order items');
        }
      } catch (error) {
        console.error('Error fetching order items:', error);
      }
    },
    handleCloseDialog() {
      this.dialogVisible = false;
      this.currentOrder = null;
      this.orderItems = [];
    },
    async deleteOrderItem(id) {
      try {
        const response = await api.delete(`/api/order-items/${id}`);
        if (response.data.code === 1) {
          this.orderItems = this.orderItems.filter(item => item.orderItemId !== id);
        } else {
          console.error('Failed to delete order item');
        }
      } catch (error) {
        console.error('Error deleting order item:', error);
      }
    },
  },
  created() {
    this.fetchTables();
  },
};
</script>

<template>
  <div class="filters">
    <el-select v-model="filterType" placeholder="按位置筛选" class="filter-select">
      <el-option value="all" label="全部位置" />
      <el-option value="hall" label="大厅" />
      <el-option value="private" label="包厢" />
      <el-option value="privateRoom" label="私密房间" />
    </el-select>
    <el-select v-model="filterStatus" placeholder="按状态筛选" class="filter-select">
      <el-option value="all" label="全部状态" />
      <el-option value="empty" label="空闲" />
      <el-option value="reserved" label="已预订" />
      <el-option value="occupied" label="已占用" />
    </el-select>
  </div>

  <div class="grid-container">
    <div
        v-for="table in paginatedTables"
        :key="table.tableId"
        class="table-item"
        :class="{
          'empty': table.status === '空闲',
          'reserved': table.status === '已预订',
          'occupied': table.status === '已占用'
        }"
        @click="handleTableClick(table.tableId)"
    >
      <p>餐桌 {{ table.tableId }}</p>
      <p>{{ table.location === '大厅' ? '大厅' : table.location === '包间' ? '包间' : '私密房间' }}</p>
      <p class="status">
        {{
          table.status === '空闲'
              ? '空闲'
              : table.status === '已占用'
                  ? '已占用'
                  : '已预订'
        }}
      </p>
    </div>
  </div>

  <el-pagination
      v-model:current-page="currentPage"
      :page-size="pageSize"
      :total="filteredTables.length"
      layout="prev, pager, next"
      class="pagination"
  ></el-pagination>

  <el-dialog
      v-model="dialogVisible"
      title="订单详情"
      width="60%"
      @close="handleCloseDialog"
      custom-class="order-details-dialog"
  >
    <el-loading :visible="isLoading" />
    <div v-if="currentOrder" class="order-details">
      <h3 class="section-title">订单信息</h3>
      <div class="order-info">
        <p><strong>订单ID:</strong> {{ currentOrder.orderId }}</p>
        <p><strong>餐桌号:</strong> {{ currentOrder.tableId }}</p>
        <p><strong>下单时间:</strong> {{ currentOrder.orderTime }}</p>
        <p><strong>订单状态:</strong> <span :class="['status', currentOrder.status]">{{ currentOrder.status }}</span></p>
      </div>

      <h3 class="section-title">订单项目</h3>
      <el-table :data="orderItems" style="width: 100%" v-loading="isLoading" class="order-items-table">
        <el-table-column prop="orderItemId" label="项目ID" width="80"></el-table-column>
        <el-table-column prop="dishId" label="菜品ID" width="80"></el-table-column>
        <el-table-column prop="dishName" label="菜品名称"></el-table-column>
        <el-table-column prop="quantity" label="数量" width="80"></el-table-column>
        <el-table-column prop="price" label="单价"></el-table-column>
        <el-table-column prop="totalPrice" label="总价"></el-table-column>
        <el-table-column label="操作" width="120">
          <template #default="scope">
            <el-button type="danger" size="small" @click="deleteOrderItem(scope.row.orderItemId)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
/* 筛选区域样式 */
.filters {
  display: flex;
  justify-content: flex-start;
  gap: 16px;
  margin-bottom: 16px;
}

/* 设置筛选框的宽度 */
.filter-select {
  width: 150px;
}

/* 网格容器 */
.grid-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
  gap: 16px;
  padding: 16px;
  background-color: #f5f5f5;
  border: 1px solid #ccc;
  border-radius: 8px;
}

/* 餐桌项样式 */
.table-item {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #fff;
  cursor: pointer;
  text-align: center;
  transition: transform 0.3s, box-shadow 0.3s;
}

.table-item.empty {
  background-color: #e0f7fa;
}

.table-item.occupied {
  background-color: #ffcdd2;
}

.table-item.reserved {
  background-color: #fff9c4;
}

.table-item:hover {
  transform: scale(1.05);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

/* 分页组件样式 */
.pagination {
  margin-top: 16px;
  text-align: center;
}

.order-details-dialog {
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.order-details-dialog :deep(.el-dialog__header) {
  background-color: #f5f7fa;
  padding: 20px;
  border-bottom: 1px solid #e4e7ed;
}

.order-details-dialog :deep(.el-dialog__title) {
  font-size: 18px;
  font-weight: bold;
  color: #303133;
}

.order-details-dialog :deep(.el-dialog__body) {
  padding: 30px;
}

.order-details {
  font-family: 'Arial', sans-serif;
}

.section-title {
  font-size: 16px;
  color: #409EFF;
  margin-bottom: 15px;
  border-bottom: 2px solid #409EFF;
  padding-bottom: 5px;
}

.order-info {
  background-color: #f5f7fa;
  padding: 15px;
  border-radius: 4px;
  margin-bottom: 20px;
}

.order-info p {
  margin: 8px 0;
  color: #606266;
}

.status {
  padding: 2px 8px;
  border-radius: 4px;
  font-weight: bold;
}

.status.pending {
  background-color: #E6A23C;
  color: white;
}

.status.completed {
  background-color: #67C23A;
  color: white;
}

.status.cancelled {
  background-color: #F56C6C;
  color: white;
}

.order-items-table {
  margin-top: 20px;
}

.order-items-table :deep(.el-table__header) {
  background-color: #f5f7fa;
}

.order-items-table :deep(.el-table__row:hover) {
  background-color: #f0f9ff;
}

.dialog-footer {
  text-align: right;
  margin-top: 20px;
}

.dialog-footer .el-button {
  padding: 10px 20px;
}

.order-details-dialog {
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.order-details-dialog :deep(.el-dialog__header) {
  background-color: #f5f7fa;
  padding: 20px;
  border-bottom: 1px solid #e4e7ed;
}

.order-details-dialog :deep(.el-dialog__title) {
  font-size: 18px;
  font-weight: bold;
  color: #303133;
}

.order-details-dialog :deep(.el-dialog__body) {
  padding: 30px;
}

.order-details {
  font-family: 'Arial', sans-serif;
}

.section-title {
  font-size: 16px;
  color: #409EFF;
  margin-bottom: 15px;
  border-bottom: 2px solid #409EFF;
  padding-bottom: 5px;
}

.order-info {
  background-color: #f5f7fa;
  padding: 15px;
  border-radius: 4px;
  margin-bottom: 20px;
}

.order-info p {
  margin: 8px 0;
  color: #606266;
}

.status {
  padding: 2px 8px;
  border-radius: 4px;
  font-weight: bold;
}

.status.pending {
  background-color: #E6A23C;
  color: white;
}

.status.completed {
  background-color: #67C23A;
  color: white;
}

.status.cancelled {
  background-color: #F56C6C;
  color: white;
}

.order-items-table {
  margin-top: 20px;
}

.order-items-table :deep(.el-table__header) {
  background-color: #f5f7fa;
}

.order-items-table :deep(.el-table__row:hover) {
  background-color: #f0f9ff;
}

.dialog-footer {
  text-align: right;
  margin-top: 20px;
}

.dialog-footer .el-button {
  padding: 10px 20px;
}

</style>

