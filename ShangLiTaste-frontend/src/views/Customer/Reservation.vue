<template>
  <div class="reservation-container">
    <!-- 头部 -->
    <div class="header">
      <div style="width: 150px; text-align: center; font-weight: bold; color: dodgerblue;"></div>
      <div style="flex: 1;"></div>
      <div style="width: 100px;">
        <el-dropdown>
          <span class="el-dropdown-link">
            用户
            <el-icon class="el-icon--right">
              <arrow-down />
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="goToPage('/customer/customer-user')">个人信息</el-dropdown-item>
              <el-dropdown-item @click="goToPage('/customer-login')">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>

    <!-- 标题 -->
    <h2 class="reservation-title">在线预定~o( =∩ω∩= )m</h2>

    <div class="reservation-form">
      <!-- 选择区域 -->
      <el-form-item label="选择区域">
        <el-select v-model="reservation.area" placeholder="请选择预定区域" @change="filterTables">
          <el-option label="大厅" value="hall" />
          <el-option label="包厢" value="private" />
        </el-select>
      </el-form-item>

      <!-- 餐桌选择 -->
      <el-form-item label="选择餐桌">
        <el-select v-model="reservation.tableNumber" placeholder="请选择餐桌" :disabled="!reservation.area">
          <el-option v-for="table in filteredTables" :key="table.id" :label="table.name" :value="table.id" />
        </el-select>
      </el-form-item>

      <!-- 预定时间 -->
      <el-form-item label="预定时间">
        <el-date-picker
            v-model="reservation.reservationTime"
            type="datetime"
            placeholder="选择预定时间"
            :disabled-date="disabledDate"
        />
      </el-form-item>

      <!-- 预定需求 -->
      <el-form-item label="预定需求">
        <el-input v-model="reservation.specialRequest" placeholder="请输入您的特殊需求（例如，过敏信息、座位要求等）" />
      </el-form-item>

      <!-- 提交按钮 -->
      <el-form-item>
        <el-button type="primary" @click="submitReservation">提交预定</el-button>
      </el-form-item>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { ArrowDown } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus' // 导入 Element Plus 的提示组件

const router = useRouter()

// 跳转到个人信息页面
const goToPage = (path) => {
  router.push(path)
}

// 预定数据
const reservation = ref({
  area: '', // 区域（大厅/包厢）
  tableNumber: '', // 桌号
  reservationTime: '', // 预定时间
  specialRequest: '', // 特殊需求
});

// 模拟餐桌数据
const tables = ref([
  { id: 1, name: '桌子1', area: 'hall' },
  { id: 2, name: '桌子2', area: 'hall' },
  { id: 3, name: '桌子3', area: 'private' },
  { id: 4, name: '桌子4', area: 'private' },
]);

// 当前选择的区域对应的桌号
const filteredTables = ref([]);

// 根据区域筛选桌号
const filterTables = () => {
  if (reservation.value.area) {
    filteredTables.value = tables.value.filter(table => table.area === reservation.value.area);
  } else {
    filteredTables.value = [];
  }
};

// 禁止选择过去的日期
const disabledDate = (date) => {
  return date.getTime() < Date.now();
};

// 手动格式化日期为 yyyy-MM-dd HH:mm:ss
const formatDate = (date) => {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  const seconds = String(date.getSeconds()).padStart(2, '0');

  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
};

// 提交预定
const submitReservation = () => {
  if (!reservation.value.tableNumber || !reservation.value.reservationTime) {
    return ElMessage.error('请填写完整的预定信息');
  }

  // 手动格式化时间
  const formattedTime = formatDate(reservation.value.reservationTime);

  // 将格式化后的时间更新到reservation对象中
  reservation.value.reservationTime = formattedTime;

  // 提交逻辑，例如发请求保存预定信息
  console.log('预定信息:', reservation.value);
  ElMessage.success('预定成功！');
};
</script>

<style scoped>
/* 页面布局 */
.reservation-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  background-color: #fff3e0; /* 橙黄色背景 */
  min-height: 100vh;
  height: 100% !important;
}


.reservation-title {
  font-size: 32px;
  color: #f57c00;
  margin-bottom: 30px;
  margin-top: 80px;
}

.reservation-form {
  width: 100%;
  max-width: 600px;
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

/* 表单项样式 */
.el-form-item {
  margin-bottom: 20px;
}

.el-button {
  width: 100%;
  background-color: #f57c00;
  border-color: #f57c00;
}

.el-button:hover {
  background-color: #e65100;
}

.el-select, .el-date-picker, .el-input {
  width: 100%;
}

.header {
  width: 100%;  /* 占满整行 */
  height: 60px; /* 调整头栏高度，增加一些空间 */
  position: fixed; /* 固定在页面顶部 */
  top: 0;  /* 置顶 */
  left: 0; /* 确保从左侧开始 */
  border-bottom: 1px solid #ccc;
  display: flex;
  align-items: center;
  justify-content: space-between; /* 左右对齐内容 */
  padding: 0 20px; /* 左右内边距，保持内容不贴边 */
  background-color: #ff9800; /* 橙黄色背景 */
  background-image: radial-gradient(circle, rgba(255, 255, 255, 0.3) 1px, transparent 1px); /* 波点背景 */
  background-size: 10px 10px; /* 控制波点大小 */
  z-index: 1000; /* 保证头部在最上层 */
}

.el-dropdown-link:focus {
  outline: none;
}

.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}


.el-dropdown-link:focus {
  outline: none;
}

.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}

/* 按钮样式 */
.buttons {
  display: flex;
  justify-content: flex-start; /* 更紧凑的布局 */
  gap: 10px; /* 设置按钮之间的间距 */
  width: 130px; /* 可以适当缩小容器宽度 */
  margin-left: auto; /* 将按钮放置到右边 */
}

.circle-btn {
  width: 40px;
  height: 40px;
  border-radius: 50%; /* 圆形按钮 */
  background-color: #fff3e0; /* 淡黄色背景 */
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  color: #ff9800;
  font-weight: bold;
  transition: background-color 0.3s ease;
}

.circle-btn:hover {
  background-color: #ffe0b2; /* 悬停时稍微深一些的黄色 */
}
</style>

