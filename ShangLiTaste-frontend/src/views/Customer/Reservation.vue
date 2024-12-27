<template>
  <div class="reservation-container">
    <!-- 头部 -->
    <div class="header">
      <div style="width: 150px; text-align: center; font-weight: bold; color: dodgerblue;"></div>
      <div style="flex: 1;"></div>
      <div style="width: 100px;">
        <el-dropdown>
          <span class="el-dropdown-link">
            {{ username }}
            <el-icon class="el-icon--right">
              <arrow-down/>
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="goToPage('/customer/customer-user')">个人信息</el-dropdown-item>
              <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>

    <!-- 标题 -->
    <h2 class="reservation-title">在线预定~o( =∩ω∩= )m</h2>

    <div v-if="hasPendingReservation" class="pending-reservation-warning">
      您已有一个待确认的预订。在此预订完成之前，无法进行新的预订。
      <div class="pending-reservation-details">
        预订时间: {{ formatDateTime(pendingReservation.reservationTime) }}
        <br>
        特殊要求: {{ pendingReservation.specialRequests }}
      </div>
    </div>

    <div v-else class="reservation-form">
      <!-- 选择区域 -->
      <el-form-item label="选择区域">
        <el-select v-model="reservation.area" placeholder="请选择预定区域" @change="handleAreaChange">
          <el-option label="大厅" value="0"/>
          <el-option label="包间" value="1"/>
          <el-option label="私密房间" value="2"/>
        </el-select>
      </el-form-item>

      <!-- 餐桌选择 -->
      <el-form-item label="选择餐桌">
        <el-select v-model="reservation.tableId" placeholder="请选择餐桌" :disabled="!reservation.area">
          <el-option v-for="table in filteredTables" :key="table.tableId" :label="table.tableId.toString()"
                     :value="table.tableId"/>
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
        <el-input v-model="reservation.specialRequests" placeholder="请输入您的特殊需求（例如，过敏信息、座位要求等）"/>
      </el-form-item>

      <!-- 提交按钮 -->
      <el-form-item>
        <el-button type="primary" @click="submitReservation">提交预定</el-button>
      </el-form-item>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted, computed, watch} from 'vue';
import {ArrowDown} from '@element-plus/icons-vue'
import {useRouter} from 'vue-router'
import {ElMessage} from 'element-plus'
import api from '@/api/api'

const router = useRouter()
const username = ref('用户') // Default value
const hasPendingReservation = ref(false)
const pendingReservation = ref(null)

// 跳转到个人信息页面
const goToPage = (path) => {
  router.push(path)
}

// 登出功能
const handleLogout = () => {
  localStorage.removeItem('jwt');
  // 重定向到登录页面
  router.push('/customer-login');
};

// JWT解析函数
function parseJWT(token) {
  const parts = token.split('.');
  if (parts.length !== 3) {
    throw new Error('Invalid JWT format');
  }

  const header = JSON.parse(atob(parts[0]));
  const payload = JSON.parse(atob(parts[1]));
  return {header, payload};
}

const fetchUserInfo = async () => {
  try {
    const token = localStorage.getItem('jwt')
    if (!token) {
      throw new Error('No token found')
    }

    const {payload} = parseJWT(token)
    const customerId = payload.customerId

    const response = await api.get(`/api/customers/${customerId}`)
    if (response.data.code === 1) {
      const userData = response.data.data
      username.value = userData.name
    } else {
      throw new Error(response.data.msg || 'Failed to fetch user info')
    }
  } catch (err) {
    console.error('Error fetching user info:', err)
    ElMessage.error('获取用户信息失败')
  }
}

const checkPendingReservations = async () => {
  try {
    const token = localStorage.getItem('jwt')
    if (!token) {
      throw new Error('No token found')
    }

    const {payload} = parseJWT(token)
    const customerId = payload.customerId

    const response = await api.get(`/api/reservations/customer/${customerId}`)
    console.log('API response:', response.data)
    if (response.data.code === 1) {
      // 检查 response.data.data 是否为对象（单个预订）
      if (typeof response.data.data === 'object' && response.data.data !== null) {
        hasPendingReservation.value = true
        pendingReservation.value = response.data.data
      } else {
        hasPendingReservation.value = false
        pendingReservation.value = null
      }
      console.log('hasPendingReservation:', hasPendingReservation.value)
      console.log('pendingReservation:', pendingReservation.value)
    } else {
      console.error('Unexpected API response:', response.data)
      ElMessage.error(`检查待处理预订时出错: ${response.data.msg || '未知错误'}`)
    }
  } catch (err) {
    console.error('Error checking pending reservations:', err)
    ElMessage.error('检查待处理预订时出错')
  }
}

onMounted(() => {
  setTimeout(() => {
    fetchUserInfo();
    checkPendingReservations();
    fetchTableData();
  }, 300);  // 2000ms（2秒）后执行回调函数
})

watch(() => localStorage.getItem('jwt'), () => {
  checkPendingReservations()
})

// 预定数据
const reservation = ref({
  customerId: null,
  area: '',
  tableId: null,
  reservationTime: '',
  specialRequests: '',
  status: 0 // 默认状态为0（待确认）
});

// 餐桌数据
const tables = ref([]);

// 当前选择的区域对应的桌号
const filteredTables = ref([]);

// 获取餐桌数据
const fetchTableData = async () => {
  try {
    const response = await api.get('/api/tables');
    if (response.data.code === 1) {
      tables.value = response.data.data.map(table => ({
        tableId: table.tableId,
        status: table.status,
        location: table.location
      }));
    } else {
      console.error('Failed to fetch table data');
      ElMessage.error('获取餐桌数据失败');
    }
  } catch (error) {
    console.error('Error fetching table data:', error);
    ElMessage.error('获取餐桌数据失败');
  }
};

// 根据区域筛选桌号
const filterTables = () => {
  if (reservation.value.area !== '') {
    filteredTables.value = tables.value.filter(table =>
        table.location.toString() === reservation.value.area && table.status === 0
    );
  } else {
    filteredTables.value = [];
  }
};

// 处理区域变化
const handleAreaChange = () => {
  reservation.value.tableId = null; // 重置选中的餐桌
  filterTables();
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

  return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
};

// 格式化日期时间显示
const formatDateTime = (dateTimeString) => {
  const date = new Date(dateTimeString);
  return date.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  });
};

// 提交预定
const submitReservation = async () => {
  if (!reservation.value.tableId || !reservation.value.reservationTime) {
    return ElMessage.error('请填写完整的预定信息');
  }

  // 手动格式化时间
  const formattedTime = formatDate(reservation.value.reservationTime);

  // 将格式化后的时间更新到reservation对象中
  reservation.value.reservationTime = formattedTime;

  try {
    const token = localStorage.getItem('jwt');
    if (!token) {
      throw new Error('No token found');
    }

    const {payload} = parseJWT(token);
    reservation.value.customerId = payload.customerId;
    console.log('Submitting reservation:', payload.customerId);
    console.log('Submitting reservation:', payload.name);
    console.log('Submitting reservation:', payload.username);
    console.log('Submitting reservation:', reservation.value);

    const response = await api.post('/api/reservations', reservation.value);
    if (response.data.code === 1) {
      ElMessage.success('预定成功！');
      // 重置表单
      reservation.value = {
        customerId: null,
        area: '',
        tableId: null,
        reservationTime: '',
        specialRequests: '',
        status: 0
      };
      // 刷新页面
      window.location.reload();
    } else {
      throw new Error(response.data.msg || 'Failed to submit reservation');
    }
  } catch (error) {
    console.error('Error submitting reservation:', error);
    ElMessage.error('预定失败，请稍后重试');
  }
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
  width: 100%; /* 占满整行 */
  height: 60px; /* 调整头栏高度，增加一些空间 */
  position: fixed; /* 固定在页面顶部 */
  top: 0; /* 置顶 */
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

.pending-reservation-warning {
  background-color: #fff3cd;
  border: 1px solid #ffeeba;
  color: #856404;
  padding: 15px;
  margin-bottom: 20px;
  border-radius: 4px;
  width: 100%;
  max-width: 600px;
}

.pending-reservation-details {
  margin-top: 10px;
  font-size: 0.9em;
  color: #666;
}
</style>