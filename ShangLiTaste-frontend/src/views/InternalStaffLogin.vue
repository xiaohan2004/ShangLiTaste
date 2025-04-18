<template>
  <div class="login-container">
    <div class="login-box">
      <!-- 右上角订餐系统链接 -->
      <div class="order-link">
        <el-link @click="goToOrderSystem">订餐系统</el-link>
      </div>

      <!-- 左侧走马灯 -->
      <div class="carousel-area">
        <ImageCarousel :items="carouselItems" :height="300" :width="300" />
      </div>

      <!-- 右侧登录表单 -->
      <div class="form-area">
        <h2 class="login-title">饭店管理系统</h2>

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
              <el-radio label="admin">管理员</el-radio>
              <el-radio label="waiter">服务员</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item>
            <!-- 按钮容器，登录和注册按钮并排显示 -->
            <div class="button-container">
              <!-- 登录按钮 -->
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
import {ref} from 'vue';
import {ElMessage} from 'element-plus';
import {useRouter} from 'vue-router';
import api from "@/api/api";
import ImageCarousel from "@/components/ImageCarousel.vue"; // Import the api instance

// 轮播图数据
const carouselItems = ref([
  { id: 1, imageUrl: 'https://shanglixiaohan.oss-cn-shanghai.aliyuncs.com/shanglitaste/c7598c6f-e7d3-44a7-8b1b-11ab2f4b375f.jpg', altText: 'Image 1' },
  { id: 2, imageUrl: 'https://ts1.cn.mm.bing.net/th/id/R-C.2bc9c86f9a188e5c0975bbfb94cf766b?rik=qimcHt%2bIJ6jMFw&riu=http%3a%2f%2fn.sinaimg.cn%2fsinacn10114%2f680%2fw900h580%2f20190408%2fe954-hvhrcxn1100903.jpg&ehk=wnBXHeiqmFpFY%2bL74MlA2EYHsF370Ecp05z2jXMWPbQ%3d&risl=&pid=ImgRaw&r=0', altText: 'Image 2' },
  { id: 3, imageUrl: 'https://n.sinaimg.cn/sinacn10121/77/w2048h1229/20190908/fba6-iekuaqs5277126.jpg', altText: 'Image 3' },
  { id: 4, imageUrl: 'https://ts1.cn.mm.bing.net/th/id/R-C.c1cf981cc5dcb41b7f3e1771e9a10732?rik=AVWBHaSBFQON7g&riu=http%3a%2f%2fn.sinaimg.cn%2fsinacn10119%2f0%2fw2000h1200%2f20191031%2f5275-ihqyuym9420397.jpg&ehk=Bz4Ql73GN0vDatskuqYLxyudxmSqyebuRF0iJ4JYs7E%3d&risl=&pid=ImgRaw&r=0', altText: 'Image 4' },
]);

// 表单数据
const username = ref('');
const password = ref('');
const role = ref('admin'); // 默认选择服务员

// 获取路由实例
const router = useRouter();

// 登录方法
const handleLogin = async () => {
  if (!username.value || !password.value) {
    ElMessage.error('请输入账号和密码');
    return;
  }

  let loginUrl = '/userlogin'; // Remove the base URL as it's already set in the api instance

  try {
    const response = await api.post(loginUrl, {
      username: username.value,
      password: password.value
    });

    const {code, msg, data} = response.data;

    if (code === 1) {
      // 登录成功
      ElMessage.success(`登录成功！身份：${role.value === 'admin' ? '管理员' : '服务员'}`);

      // 保存 JWT 到本地存储，data 直接就是 JWT
      localStorage.setItem('jwt', data);


      // 根据角色跳转到不同页面
      if (role.value === 'admin') {
        goToPage('/backstage/dashboard');
      } else {
        goToPage('/reception/table-status');
      }
    } else {
      // 登录失败
      ElMessage.error(msg || '登录失败，请重试');
    }
  } catch (error) {
    console.error('登录请求失败:', error);
    ElMessage.error('登录请求失败，请检查网络连接');
  }
};

// 跳转方法
const goToPage = (path) => {
  router.push(path).catch(err => {
    console.error("跳转失败:", err);
  });
};

// 注册方法
const goToRegister = () => {
  goToPage('/internalStaff-register');
};

// 跳转到订餐系统
const goToOrderSystem = () => {
  goToPage('/customer-login');
};
</script>

<style scoped>
/* 登录页面布局 */
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f1f8ff; /* 背景色改为浅蓝色 */
}

.login-box {
  display: flex;
  background: white;
  padding: 40px;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  max-width: 700px; /* 最大宽度，防止内容撑开 */
  width: 100%; /* 宽度自适应 */
  box-sizing: border-box;
}

/* 左侧走马灯区域样式 */
.carousel-area {
  width: 300px;
  margin-right: 20px;
}

.carousel-text {
  color: #ffffff; /* 文本颜色改为白色 */
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
  text-align: center;
  font-size: 20px;
}

.el-carousel__item {
  background-color: #a8c7f7; /* 使用浅蓝色作为背景 */
  border-radius: 8px; /* 圆角效果 */
  display: flex;
  justify-content: center;
  align-items: center;
  transition: background-color 0.3s ease; /* 过渡效果 */
}

.el-carousel__item:hover {
  background-color: #87b7f1; /* 悬停时变为更亮的蓝色 */
}

.el-carousel__item:nth-child(2n) {
  background-color: #b9d8ff; /* 第二项使用较浅的蓝色 */
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #a8c7f7; /* 第一项使用主色调的浅蓝色 */
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

/* 输入框样式 */
.el-input__inner {
  width: 100%; /* 保证输入框宽度适应容器 */
  max-width: 400px; /* 输入框最大宽度 */
  border-radius: 8px;
  border-color: #7a9eff; /* 更柔和的蓝色边框 */
  transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

.el-input__inner:focus {
  border-color: #87b7f1; /* 输入框聚焦时边框变亮蓝色 */
  box-shadow: 0 0 6px rgba(64, 158, 255, 0.5);
}

/* 按钮容器，居中排列 */
.button-container {
  display: flex;
  justify-content: center;
  gap: 10px; /* 按钮之间的间距 */
  margin-top: 20px;
}

.el-button--primary {
  background-color: #7a9eff; /* 更柔和的按钮蓝色 */
  border-color: #7a9eff; /* 按钮边框蓝色 */
  color: white;
  border-radius: 8px;
  padding: 12px 20px;
  font-size: 16px;
}

.el-button--primary:hover {
  background-color: #87b7f1; /* 悬停时按钮变为更亮的蓝色 */
}

.el-button--text {
  color: #7a9eff; /* 注册按钮文本蓝色 */
}

/* 保证表单项的间距一致 */
.el-form-item {
  margin-bottom: 16px;
}

.login-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
  color: #7a9eff;
}

/* 右上角订餐系统链接样式 */
.order-link {
  position: absolute;
  top: 20px;
  right: 20px;
  font-size: 16px;
  color: #7a9eff;
  cursor: pointer;
}

.order-link:hover {
  text-decoration: underline;
}
</style>



