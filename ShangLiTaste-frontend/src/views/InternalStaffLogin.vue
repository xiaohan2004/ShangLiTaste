<script setup>
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';

// 表单数据
const username = ref('');
const password = ref('');
const role = ref('waiter'); // 默认选择服务员

// 获取路由实例
const router = useRouter();

// 登录方法
const handleLogin = () => {
  if (!username.value || !password.value) {
    ElMessage.error('请输入账号和密码');
    return;
  }

  if (role.value === 'admin') {
    ElMessage.success(`登录成功！身份：管理员`);
    // 使用 goToPage 方法进行跳转
    goToPage('/backstage/table-management');
  } else {
    ElMessage.success(`登录成功！身份：服务员`);
    goToPage('/reception/table-status');
  }

  // 打印登录信息
  console.log('登录信息：', { username: username.value, password: password.value, role: role.value });
};

// 跳转方法
const goToPage = (path) => {
  router.push(path).catch(err => {
    console.error("跳转失败:", err);
  });
};
</script>

<template>
  <div class="login-container">
    <div class="login-box">
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
        <h2 class="login-title">登录</h2>

        <el-form :model="{ username, password, role }" class="login-form">
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

          <el-form-item label="身份">
            <el-radio-group v-model="role">
              <el-radio label="waiter">服务员</el-radio>
              <el-radio label="admin">管理员</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item>
            <!-- 使用 @click 触发跳转 -->
            <el-button type="primary" block @click="handleLogin">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 登录页面布局 */
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
}

.login-box {
  display: flex;
  background: white;
  padding: 40px;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

/* 左侧走马灯区域样式 */
.carousel-area {
  width: 300px;
  margin-right: 20px;
}

.carousel-text {
  color: #475669;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
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
}
</style>



