import { createRouter, createWebHistory } from "vue-router";
import BackstageLayout from "../layout/BackstageLayout.vue";

// 页面组件
import TableManagement from "../views/Backstage/TableManagement.vue"; // 餐桌管理页面
import DishManagement from "../views/Backstage/DishManagement.vue";   // 菜品管理页面
import InternalStaff from "../views/Backstage/InternalStaff.vue";     // 内部员工管理
import Customers from "../views/Backstage/Customers.vue";             // 客户管理
import ReportDaily from "../views/Backstage/ReportDaily.vue";         // 每日报表
import ReportMonthly from "../views/Backstage/ReportMonthly.vue";     // 每月报表
import ReportYearly from "../views/Backstage/ReportYearly.vue";// 每年报表
import ManagerUserInfo from "../views/Backstage/ManagerUserInfo.vue"; // 用户信息页面
import InternalStaffLogin from "../views/InternalStaffLogin.vue";
import ReceptionLayout from "../layout/ReceptionLayout.vue";
import TableStatus from "../views/Reception/TableStatus.vue";
import DishStatus from "../views/Reception/DishStatus.vue";
import OrderStatus from "../views/Reception/OrderStatus.vue";
import WaiterUserInfo from "../views/Reception/WaiterUserInfo.vue";


// 定义路由规则
const routes = [
  // 默认重定向
  {
    path: "/",
    redirect: "/internalStaff-login", // 默认跳转到登录
  },

  // 带导航栏和侧边栏的页面
  {
    path: "/backstage",
    component: BackstageLayout, // 带导航栏和侧边栏的布局
    children: [
      { path: "table-management", name: "TableManagement", component: TableManagement },
      { path: "dish-management", name: "DishManagement", component: DishManagement },
      { path: "user-management-internalStaff", name: "InternalStaff", component: InternalStaff },
      { path: "user-management-customers", name: "Customers", component: Customers },
      { path: "report-generation-daily", name: "ReportDaily", component: ReportDaily },
      { path: "report-generation-monthly", name: "ReportMonthly", component: ReportMonthly },
      { path: "report-generation-yearly", name: "ReportYearly", component: ReportYearly },
      { path: "manager-user", name: "ManagerUserInfo", component: ManagerUserInfo },
    ],
  },

  {
    path: "/reception",
    component: ReceptionLayout, // 带导航栏和侧边栏的布局
    children: [
      { path: "table-status", name: "TableStatus", component: TableStatus },
      { path: "dish-status", name: "DishStatus", component: DishStatus },
      { path: "order-status", name: "OrderStatus", component: OrderStatus },
      { path: "waiter-user", name: "WaiterUserInfo", component: WaiterUserInfo },
      // { path: "user-management-customers", name: "Customers", component: Customers },
      // { path: "report-generation-daily", name: "ReportDaily", component: ReportDaily },
      // { path: "report-generation-monthly", name: "ReportMonthly", component: ReportMonthly },
      // { path: "report-generation-yearly", name: "ReportYearly", component: ReportYearly },
    ],
  },

  // 不需要导航栏和侧边栏的页面
  {
    path: "/internalStaff-login",
    name: "InternalStaffLogin",
    component: InternalStaffLogin,
  },


  // {
  //   path: "/other",
  //   name: "OtherPage",
  //   component: OtherPage,
  // },

  // // 404 页面
  // {
  //   path: "/:catchAll(.*)", // 捕获所有未定义的路径
  //   name: "NotFound",
  //   component: () => import("@/views/NotFound.vue"), // 需要创建 NotFound.vue 页面
  // },
];

// 创建路由实例
const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;

