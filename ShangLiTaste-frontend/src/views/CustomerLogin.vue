<template>
  <div class="login-container">
    <div class="login-box">
      <!-- 右上角管理系统链接 -->
      <div class="admin-link">
        <el-link @click="goToAdminSystem">管理系统</el-link>
      </div>

      <!-- 左侧走马灯 -->
      <div class="carousel-area">
        <el-carousel height="300px" width="300px" motion-blur>
          <el-carousel-item v-for="item in 4" :key="item">
            <h3 class="carousel-text">{{ item }}</h3>
          </el-carousel-item>
        </el-carousel>
      </div>

      <!-- 右侧登录表单 -->
      <div class="form-area">
        <h2 class="login-title">欢迎来到订餐系统<br>━(*｀∀´*)ノ亻!</h2>

        <el-form :model="{ username, password }" class="login-form">
          <el-form-item label="账号" required>
            <el-input
                v-model="username"
                placeholder="请输入账号"
                clearable
                prefix-icon="el-icon-user"
            />
          </el-form-item>

          <el-form-item label="密码" required>
            <el-input
                v-model="password"
                placeholder="请输入密码"
                type="password"
                show-password
                clearable
                prefix-icon="el-icon-lock"
            />
          </el-form-item>

          <el-form-item>
            <!-- 登录按钮 -->
            <div class="button-container">
              <el-button type="primary" block @click="handleLogin">登录</el-button>
              <!-- 注册按钮 -->
              <el-button type="text" @click="goToRegister">注册</el-button>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';

// 表单数据
const username = ref('');
const password = ref('');

// 获取路由实例
const router = useRouter();

// 登录方法
const handleLogin = () => {
  if (!username.value || !password.value) {
    ElMessage.error('请输入账号和密码');
    return;
  }

  ElMessage.success('登录成功！');
  goToPage('/customer-selection'); // 登录成功跳转到对应页面

  // 打印登录信息
  console.log('登录信息：', { username: username.value, password: password.value });
};

// 跳转方法
const goToPage = (path) => {
  router.push(path).catch(err => {
    console.error("跳转失败:", err);
  });
};

// 注册方法
const goToRegister = () => {
  goToPage('/customer-register'); // 假设注册页面路径为 /register
};

// 跳转到管理系统页面
const goToAdminSystem = () => {
  goToPage('/internalStaff-login');
};
</script>

<style scoped>
/* 登录页面布局 */
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #fff3e0; /* 橙黄色背景 */
}

.login-box {
  display: flex;
  background: white;
  padding: 40px;
  border-radius: 8px;
  max-width: 700px; /* 最大宽度，防止内容撑开 */
  width: 100%; /* 宽度自适应 */
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

/* 左侧走马灯区域样式 */
.carousel-area {
  width: 300px;
  margin-right: 20px;
}

.carousel-text {
  color: #f57c00; /* 橙色字体 */
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #ffb74d; /* 橙色背景 */
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #ffcc80; /* 更柔和的橙色背景 */
}

/* 右侧表单区域样式 */
.form-area {
  flex: 1;
  text-align: center;
}

.login-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
  color: #f57c00; /* 橙色标题 */
}

/* 按钮容器，居中排列 */
.button-container {
  display: flex;
  justify-content: center;
  gap: 10px; /* 按钮之间的间距 */
  margin-top: 20px;
}

.el-button--primary {
  background-color: #f57c00; /* 按钮橙色 */
  border-color: #f57c00; /* 按钮边框橙色 */
}

.el-button--primary:hover {
  background-color: #e65100; /* 按钮悬停时的深橙色 */
}

/* 注册按钮样式 */
.el-button--text {
  color: #f57c00; /* 注册按钮文本橙色 */
}

/* 右上角管理系统链接样式 */
.admin-link {
  position: absolute;
  top: 20px;
  right: 20px;
  font-size: 16px;
  color: #f57c00;
  cursor: pointer;
}

.admin-link:hover {
  text-decoration: underline;
}
</style>

