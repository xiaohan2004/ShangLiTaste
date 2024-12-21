<script setup>
import { ArrowDown } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus' // 导入 Element Plus 的提示组件

const router = useRouter()

// 跳转到个人信息页面
const goToPage = (path) => {
  router.push(path)
}


</script>

<template>
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
</template>

<style scoped>
.header {
  height: 50px;
  border-bottom: 1px solid #ccc;
  display: flex;
  align-items: center;
  background-color: #ff9800; /* 橙黄色背景 */
  background-image: radial-gradient(circle, rgba(255, 255, 255, 0.3) 1px, transparent 1px); /* 波点背景 */
  background-size: 10px 10px; /* 控制波点大小 */
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
