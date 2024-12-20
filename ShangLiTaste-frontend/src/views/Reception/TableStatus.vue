<script setup>
import { ref, computed } from 'vue';
import { ElDrawer, ElButton } from 'element-plus';

// 餐桌数据（模拟数据，实际可从后端接口加载）
const tables = ref([
  { id: 1, status: 'empty', type: 'hall', orders: null },
  { id: 2, status: 'occupied', type: 'hall', orders: { customer: '张三', total: 120, items: [{ name: '炒饭', price: 50 }, { name: '红烧肉', price: 70 },{ name: '红烧肉', price: 70 }
        ,{ name: '红烧肉', price: 70 },{ name: '红烧肉', price: 70 },
        { name: '红烧肉', price: 70 },{ name: '红烧肉', price: 70 },
        { name: '红烧肉', price: 70 },{ name: '红烧肉', price: 70 },
        { name: '红烧肉', price: 70 },{ name: '红烧肉', price: 70 },
        { name: '红烧肉', price: 70 },{ name: '红烧肉', price: 70 },
        { name: '红烧肉', price: 70 },{ name: '红烧肉', price: 70 }] } },
  { id: 3, status: 'reserved', type: 'private', orders: null },
  { id: 4, status: 'empty', type: 'private', orders: null },
  { id: 5, status: 'occupied', type: 'hall', orders: { customer: '李四', total: 200, items: [{ name: '水饺', price: 100 }, { name: '小炒', price: 100 }] } },
  { id: 6, status: 'empty', type: 'hall', orders: null },
  { id: 7, status: 'reserved', type: 'private', orders: null },
  { id: 8, status: 'occupied', type: 'hall', orders: { customer: '王五', total: 300, items: [{ name: '烧烤', price: 150 }, { name: '啤酒', price: 150 }] } },
  { id: 9, status: 'empty', type: 'hall', orders: null },
  { id: 10, status: 'reserved', type: 'private', orders: null },
  { id: 11, status: 'occupied', type: 'hall', orders: { customer: '赵六', total: 400, items: [{ name: '麻辣烫', price: 200 }, { name: '炸鸡', price: 200 }] } },
  { id: 12, status: 'empty', type: 'hall', orders: null },
  { id: 13, status: 'occupied', type: 'hall', orders: { customer: '张三', total: 120, items: [{ name: '炒饭', price: 50 }, { name: '红烧肉', price: 70 },{ name: '红烧肉', price: 70 }
        ,{ name: '红烧肉', price: 70 },{ name: '红烧肉', price: 70 },
        { name: '红烧肉', price: 70 },{ name: '红烧肉', price: 70 },
        { name: '红烧肉', price: 70 },{ name: '红烧肉', price: 70 },
        { name: '红烧肉', price: 70 },{ name: '红烧肉', price: 70 },
        { name: '红烧肉', price: 70 },{ name: '红烧肉', price: 70 },
        { name: '红烧肉', price: 70 },{ name: '红烧肉', price: 70 }] } },
  { id: 14, status: 'reserved', type: 'private', orders: null },
  { id: 15, status: 'empty', type: 'private', orders: null },
  { id: 16, status: 'occupied', type: 'hall', orders: { customer: '李四', total: 200, items: [{ name: '水饺', price: 100 }, { name: '小炒', price: 100 }] } },
  { id: 17, status: 'empty', type: 'hall', orders: null },
  { id: 18, status: 'reserved', type: 'private', orders: null },
  { id: 19, status: 'occupied', type: 'hall', orders: { customer: '王五', total: 300, items: [{ name: '烧烤', price: 150 }, { name: '啤酒', price: 150 }] } },
  { id: 20, status: 'empty', type: 'hall', orders: null },
  { id: 21, status: 'reserved', type: 'private', orders: null },
  { id: 22, status: 'occupied', type: 'hall', orders: { customer: '赵六', total: 400, items: [{ name: '麻辣烫', price: 200 }, { name: '炸鸡', price: 200 }] } },
]);

// 当前筛选条件
const filterStatus = ref('all'); // 状态筛选：'all', 'empty', 'occupied', 'reserved'
const filterType = ref('all'); // 位置筛选：'all', 'hall', 'private'

// 分页设置
const currentPage = ref(1); // 当前页码
const pageSize = ref(40); // 每页显示的餐桌数量

// 筛选后的餐桌数据
const filteredTables = computed(() => {
  return tables.value.filter((table) => {
    const matchStatus = filterStatus.value === 'all' || table.status === filterStatus.value;
    const matchType = filterType.value === 'all' || table.type === filterType.value;
    return matchStatus && matchType;
  });
});

// 当前页显示的餐桌数据
const paginatedTables = computed(() => {
  const startIndex = (currentPage.value - 1) * pageSize.value;
  return filteredTables.value.slice(startIndex, startIndex + pageSize.value);
});

// 控制抽屉的显示
const drawer = ref(false);
const selectedTable = ref(null); // 当前选中的餐桌

// 点击餐桌时展示订单信息
const showTableDetails = (table) => {
  selectedTable.value = table; // 更新选中的餐桌
  drawer.value = true; // 显示抽屉
};

// 模拟实时更新（轮询或 WebSocket）
setInterval(() => {
  const randomTable = tables.value[Math.floor(Math.random() * tables.value.length)];
  randomTable.status = ['empty', 'occupied', 'reserved'][Math.floor(Math.random() * 3)];
}, 2000);

const checkout = () => {
  if (selectedTable.value && selectedTable.value.orders) {
    // 结账逻辑
    alert(`餐桌 ${selectedTable.value.id} 的订单已结账，总金额 ¥${selectedTable.value.orders.total}`);

    // 将餐桌状态设置为空闲
    selectedTable.value.status = 'empty';
    // 清空订单信息
    selectedTable.value.orders = null;
    // 关闭抽屉
    drawer.value = false;
  } else {
    alert('没有订单信息，无法结账！');
  }
};



</script>

<template>
  <div class="filters">
    <!-- 筛选条件 -->
    <el-select v-model="filterType" placeholder="按位置筛选" class="filter-select">
      <el-option value="all" label="全部位置" />
      <el-option value="hall" label="大厅" />
      <el-option value="private" label="包厢" />
    </el-select>
    <el-select v-model="filterStatus" placeholder="按状态筛选" class="filter-select">
      <el-option value="all" label="全部状态" />
      <el-option value="empty" label="空闲" />
      <el-option value="occupied" label="已占用" />
      <el-option value="reserved" label="已预订" />
    </el-select>
  </div>

  <!-- 餐桌网格 -->
  <div class="grid-container">
    <div
        v-for="table in paginatedTables"
        :key="table.id"
        class="table-item"
        :class="table.status"
        @click="showTableDetails(table)"
    >
      <p>餐桌 {{ table.id }}</p>
      <p>{{ table.type === 'hall' ? '大厅' : '包厢' }}</p>
      <p class="status">
        {{
          table.status === 'empty'
              ? '空闲'
              : table.status === 'occupied'
                  ? '已占用'
                  : '已预订'
        }}
      </p>
    </div>
  </div>

  <!-- 侧拉抽屉展示订单信息 -->
  <el-drawer v-model="drawer" title="订单信息" :with-header="false" size="20%">
    <div v-if="selectedTable && selectedTable.orders" class="drawer-content">
      <p class="order-info">订单编号: {{ selectedTable.id }}</p>
      <p class="order-info">餐桌编号: {{ selectedTable.id }}</p>
      <p class="order-info">下单时间: {{ new Date().toLocaleString() }}</p>
      <div class="order-items">
        <p>菜品:</p>
        <ul>
          <li v-for="(item, index) in selectedTable.orders.items" :key="index" class="order-item">
            <span class="item-name">{{ item.name }}</span> ..... <span class="item-price">¥{{ item.price }}</span>
          </li>
        </ul>
      </div>
      <p class="order-info total">总金额: ¥{{ selectedTable.orders.total }}</p>
      <!-- 添加结账按钮 -->
      <el-button type="primary" @click="checkout" class="checkout-btn">结账</el-button>
    </div>
    <div v-else class="drawer-content">
      <p>该餐桌没有订单信息。</p>
    </div>
  </el-drawer>




  <!-- 分页组件 -->
  <el-pagination
      v-model:current-page="currentPage"
      :page-size="pageSize"
      :total="filteredTables.length"
      layout="prev, pager, next"
      class="pagination"
  ></el-pagination>
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

/* 抽屉样式 */
.el-drawer__body {
  padding: 20px;
}

/* 美化抽屉内容 */
.drawer-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  font-family: Arial, sans-serif;
}

.order-info {
  margin-bottom: 10px;
  font-size: 16px;
  font-weight: bold;
}

/* 菜品区域 */
.order-items {
  margin-top: 10px;
  width: 100%;
  text-align: left;
  background-color: #f9f9f9;
  border-radius: 8px;
  padding: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  max-height: 400px;  /* 设置最大高度，根据需要调整 */
  overflow-y: auto;   /* 超过最大高度时，启用垂直滚动 */
}

/* 自定义滚动条 */
.order-items::-webkit-scrollbar {
  width: 6px; /* 设置滚动条的宽度 */
}

.order-items::-webkit-scrollbar-track {
  background: #f1f1f1; /* 滚动条轨道的背景 */
  border-radius: 10px;  /* 轨道的圆角 */
}

.order-items::-webkit-scrollbar-thumb {
  background-color: #888; /* 滚动条的颜色 */
  border-radius: 10px;     /* 滚动条的圆角 */
}

.order-items::-webkit-scrollbar-thumb:hover {
  background-color: #555; /* 滚动条在悬停时的颜色 */
}

/* 菜品项 */
.order-item {
  margin-bottom: 8px;
  display: flex;
  justify-content: space-between;
  padding: 5px 0;
  border-bottom: 1px solid #ddd;
}

.order-item:last-child {
  border-bottom: none;
}

.item-name {
  font-weight: bold;
  flex-grow: 1;
}

.item-price {
  color: #f56c6c;
  font-weight: bold;
  text-align: right;
}


.order-item {
  margin-bottom: 8px;
  display: flex;
  justify-content: space-between;
  padding: 5px 0;
  border-bottom: 1px solid #ddd;
}

.order-item:last-child {
  border-bottom: none;
}

.item-name {
  font-weight: bold;
  flex-grow: 1;  /* 确保名称部分占据剩余空间 */
}

.item-price {
  color: #f56c6c;
  font-weight: bold;
  text-align: right;  /* 确保价格靠右显示 */
}

/* 总金额 */
.total {
  margin-top: 20px;
  font-size: 18px;
  color: #f56c6c;
  font-weight: bold;
}

/* 结账按钮 */
.checkout-btn {
  margin-top: 20px; /* 距离总金额一定距离 */
  width: 100%;      /* 宽度占满 */
  padding: 10px;
  font-size: 16px;
  border-radius: 6px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}



.el-drawer__header {
  background-color: #f7f7f7;
  color: #333;
  font-size: 18px;
  font-weight: bold;
  text-align: center;
}

.el-drawer {
  border-radius: 8px;
  overflow: hidden;
}

/* 分页组件样式 */
.pagination {
  margin-top: 16px;
  text-align: center;
}
</style>

