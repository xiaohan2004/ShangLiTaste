<template>
  <div>
    <!-- 筛选功能 -->
    <div
        style="margin-bottom: 16px; display: flex; justify-content: flex-start; gap: 16px;"
    >
      <!-- 筛选类别 -->
      <el-select v-model="selectedCategory" placeholder="选择类别" style="width: 120px;">
        <el-option label="全部" value="" />
        <el-option label="类别1" value="类别1" />
        <el-option label="类别2" value="类别2" />
        <el-option label="类别3" value="类别3" />
      </el-select>

      <!-- 筛选是否启用 -->
      <el-select v-model="selectedEnabled" placeholder="是否启用" style="width: 120px;">
        <el-option label="全部" value="" />
        <el-option label="启用" value="true" />
        <el-option label="禁用" value="false" />
      </el-select>
    </div>

    <!-- 表格展示 -->
    <el-table
        :data="filteredTableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
        border
        style="width: 100%;"
    >
      <!-- 菜品编号列 -->
      <el-table-column label="菜品编号" prop="dish_id"></el-table-column>

      <!-- 图片列 -->
      <el-table-column label="图片" prop="image"></el-table-column>

      <!-- 名称列 -->
      <el-table-column label="名称" prop="name"></el-table-column>

      <!-- 类别列 -->
      <el-table-column label="类别" prop="category"></el-table-column>

      <!-- 价格列 -->
      <el-table-column label="价格" prop="price"></el-table-column>

      <!-- 是否启用列 -->
      <el-table-column label="是否启用">
        <template #default="scope">
          <el-checkbox v-model="scope.row.enabled"></el-checkbox>
        </template>
      </el-table-column>
    </el-table>

    <!-- 翻页功能 -->
    <el-pagination
        :current-page="currentPage"
        :page-size="pageSize"
        :total="filteredTableData.length"
        layout="total, prev, pager, next, jumper"
        @current-change="handlePageChange"
        class="pagination"
    />
  </div>
</template>

<script>
export default {
  name: "DishManagement",
  data() {
    return {
      selectedCategory: "", // 当前选中的类别筛选
      selectedEnabled: "", // 当前选中的是否启用筛选
      tableData: [
        {
          dish_id: "D001",
          category: "类别1",
          name: "宫保鸡丁",
          price: 28.5,
          enabled: true,
        },
        {
          dish_id: "D002",
          category: "类别2",
          name: "炸春卷",
          price: 12.0,
          enabled: false,
        },
      ],
      currentPage: 1, // 当前页数
      pageSize: 5, // 每页显示数量
    };
  },
  computed: {
    // 根据筛选条件过滤数据
    filteredTableData() {
      return this.tableData.filter((item) => {
        const categoryMatch = this.selectedCategory ? item.category === this.selectedCategory : true;
        const enabledMatch = this.selectedEnabled ? String(item.enabled) === this.selectedEnabled : true;
        return categoryMatch && enabledMatch;
      });
    },
  },
  methods: {
    handlePageChange(page) {
      this.currentPage = page;
    },
  },
};
</script>

<style scoped>
/* 自定义样式 */
.el-table {
  width: 100% !important; /* 确保表格宽度适应容器 */
  height: 100% !important; /* 确保表格高度适应容器 */
}

.el-table__body-wrapper {
  max-height: calc(100vh - 150px); /* 限制表格的最大高度，避免超出屏幕 */
  overflow: auto;
}

/* 移除列宽度限制，确保表格填充所有区域 */
.el-table-column {
  width: auto; /* 让列宽度自动调整 */
}

.el-table__header,
.el-table__body {
  table-layout: fixed; /* 使用固定布局，使列宽平分 */
}

/* 给筛选框增加一些间距 */
.el-select {
  width: 120px; /* 设置固定宽度 */
}

.el-pagination {
  margin-top: 20px; /* 设置翻页与表格的间距 */
}
</style>
