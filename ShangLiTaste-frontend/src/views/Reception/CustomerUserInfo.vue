<template>
  <div class="profile-container">
    <!-- 侧边栏 -->
    <div class="sidebar">
      <h3>个人信息</h3>
      <el-menu :default-active="activeTab" class="profile-menu">
        <el-menu-item index="basic" @click="setActiveTab('basic')">基础信息</el-menu-item>
        <el-menu-item index="history" @click="setActiveTab('history')">消费历史</el-menu-item>
      </el-menu>
    </div>

    <!-- 内容区域 -->
    <div class="content-area">
      <!-- 基础信息 -->
      <div v-if="activeTab === 'basic'" class="basic-info">
        <form @submit.prevent="updateInfo" class="profile-form">
          <h2 class="section-title">基础信息</h2>
          <div class="form-row">
            <div class="form-item">
              <label for="customerId">客户编号</label>
              <input id="customerId" v-model="formData.customerId" disabled />
            </div>
            <div class="form-item">
              <label for="name">客户姓名</label>
              <input id="name" v-model="formData.name" />
            </div>
          </div>
          <div class="form-row">
            <div class="form-item">
              <label for="phone">客户电话</label>
              <input id="phone" v-model="formData.phone" />
            </div>
            <div class="form-item">
              <label for="email">客户电子邮件</label>
              <input id="email" v-model="formData.email" />
            </div>
          </div>
          <div class="form-row">
            <div class="form-item">
              <label for="address">客户地址</label>
              <input id="address" v-model="formData.address" />
            </div>
            <div class="form-item">
              <label for="registrationDate">注册日期</label>
              <input id="registrationDate" v-model="formData.registrationDate" disabled />
            </div>
          </div>
          <div class="form-row">
            <div class="form-item">
              <label for="birthday">客户生日</label>
              <input id="birthday" v-model="formData.birthday" />
            </div>
            <div class="form-item">
              <label for="totalSpent">累计消费金额</label>
              <input id="totalSpent" v-model="formData.totalSpent" disabled />
            </div>
          </div>
          <button type="submit" class="update-btn">修改并提交</button>
        </form>
      </div>

      <!-- 消费历史 -->
      <div v-if="activeTab === 'history'" class="purchase-history">
        <h2 class="section-title">消费历史</h2>
        <el-table :data="purchaseHistory" style="width: 100%">
          <el-table-column prop="recordId" label="消费记录编号" width="180"></el-table-column>
          <el-table-column prop="orderId" label="订单编号" width="180"></el-table-column>
          <el-table-column prop="purchaseDate" label="消费日期"></el-table-column>
          <el-table-column prop="totalAmount" label="总金额" width="150"></el-table-column>
          <el-table-column prop="paymentMethod" label="支付方式" width="150"></el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

// 基本信息数据
const formData = ref({
  customerId: 'C123456',
  name: '张三',
  phone: '12345678901',
  email: 'zhangsan@example.com',
  address: '北京市朝阳区',
  registrationDate: '2024-01-01',
  birthday: '1990-01-01',
  totalSpent: 1200.50
});

// 消费历史数据
const purchaseHistory = ref([
  { recordId: 'R001', orderId: 'O12345', purchaseDate: '2024-05-10', totalAmount: 200.00, paymentMethod: '信用卡' },
  { recordId: 'R002', orderId: 'O12346', purchaseDate: '2024-06-12', totalAmount: 350.00, paymentMethod: '支付宝' },
  { recordId: 'R003', orderId: 'O12347', purchaseDate: '2024-07-05', totalAmount: 100.00, paymentMethod: '现金' },
]);

// 当前选中的tab
const activeTab = ref('basic');

// 设置当前tab
const setActiveTab = (tab) => {
  activeTab.value = tab;
};

// 更新用户信息
const updateInfo = () => {
  alert('信息已更新!');
};
</script>

<style scoped>
.profile-container {
  display: flex;
  height: 100%;
  background-color: #fff3e0;
}

.sidebar {
  width: 250px;
  padding: 20px;
  background-color: #f57c00;
  color: white;
}

.sidebar h3 {
  margin-bottom: 10px;  /* 增加标题底部的外边距 */
  padding-bottom: 15px; /* 增加标题底部的内边距 */
}


.profile-menu {
  background-color: transparent;
  border: none;
}

.profile-menu .el-menu-item {
  color: white;
  font-size: 16px;
}

.profile-menu .el-menu-item:hover,
.profile-menu .el-menu-item.is-active {
  background-color: #e65100;
  color: white;
}

.section-title {
  font-size: 24px;
  font-weight: bold;
  color: #f57c00;
  margin-bottom: 30px; /* 增加底部间距 */
  text-align: left;
}

/* 让内容区域填满剩余空间 */
.content-area {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  height: 100%; /* 使内容区域填满整个高度 */
}

.basic-info {
  margin-left: 20px;
}

.profile-form {
  max-width: 700px;
  margin: 0;
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.form-row {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 20px;
}

.form-item {
  flex: 1 1 calc(50% - 10px);
}

.form-item label {
  font-size: 16px;
  color: #f57c00;
  display: block;
  margin-bottom: 8px;
}

/* 让基础信息部分填满整个页面 */
.basic-info {
  margin-left: 20px;
  display: flex;
  flex-direction: column;
  justify-content: space-between; /* 保证表单内容垂直分布 */
  height: 100%; /* 让它填满剩余的高度 */
}
/* 设置表单宽度和高度 */
.profile-form {
  max-width: 100%; /* 让表单宽度占满 */
  width: 100%;
  height: 100%; /* 使表单高度填满父元素 */
  margin: 0;
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  box-sizing: border-box; /* 处理内外边距的计算 */
  overflow-y: auto; /* 如果表单内容太多，启用滚动条 */
}

.form-item input {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #f57c00;
  border-radius: 5px;
  box-sizing: border-box;
}

/* 增加间距和对齐方式 */
.form-row {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 20px;
}

/* 增加更多的空间处理，确保表单项均匀分布 */
.form-item {
  flex: 1 1 calc(50% - 10px);
}

.form-item input:disabled {
  background-color: #f1f1f1;
}

.update-btn {
  margin-top: 20px;
  background-color: #f57c00;
  color: white;
  border: none;
  padding: 12px;
  font-size: 18px;
  width: 100%;
  border-radius: 5px;
  cursor: pointer;
}

.update-btn:hover {
  background-color: #e65100;
}

.purchase-history {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.purchase-history .el-table th {
  background-color: #f57c00;
  color: white;
}

.purchase-history .el-table td {
  color: #333;
}
</style>

