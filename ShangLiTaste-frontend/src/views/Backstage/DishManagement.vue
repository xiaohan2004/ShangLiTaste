<template>
  <div>
    <!-- 筛选功能 -->
    <div
        style="margin-bottom: 16px; display: flex; justify-content: space-between; align-items: center;"
    >
      <div style="flex: 1; display: flex; gap: 16px;">
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

      <!-- 增加和提交按钮 -->
      <div style="display: flex; gap: 16px;">
        <el-button type="primary" @click="addRow">增加</el-button>
        <el-button type="success" @click="submitData">提交</el-button>
      </div>
    </div>

    <!-- 表格展示 -->
    <el-table
        :data="filteredTableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
        border
        style="width: 100%;"
    >
      <!-- 菜品编号列 -->
      <el-table-column label="菜品编号">
        <template #default="scope">
          <el-input v-model="scope.row.dish_id" placeholder="请输入菜品编号"></el-input>
        </template>
      </el-table-column>

      <!-- 菜品编号列 -->
      <el-table-column label="图片">

      </el-table-column>

      <!-- 名称列 -->
      <el-table-column label="名称">
        <template #default="scope">
          <el-input v-model="scope.row.name" placeholder="请输入名称"></el-input>
        </template>
      </el-table-column>

      <!-- 类别列 -->
      <el-table-column label="类别">
        <template #default="scope">
          <el-select v-model="scope.row.category" placeholder="请选择类别" style="width: 100%;">
            <el-option label="类别1" value="类别1"></el-option>
            <el-option label="类别2" value="类别2"></el-option>
            <el-option label="类别3" value="类别3"></el-option>
          </el-select>
        </template>
      </el-table-column>

      <!-- 价格列 -->
      <el-table-column label="价格">
        <template #default="scope">
          <el-input v-model="scope.row.price" placeholder="请输入单价"></el-input>
        </template>
      </el-table-column>


        <!-- 描述列 -->
        <el-table-column label="描述">
          <template #default="scope">
<!--            <el-tooltip-->
<!--                class="custom-tooltip"-->
<!--                effect="dark"-->
<!--                :content="scope.row.description"-->
<!--                placement="top-start"-->
<!--            >-->
              <el-button type="text" @click="openDescriptionDialog(scope.row)">
                {{ scope.row.description || "点击编辑描述" }}
              </el-button>
<!--            </el-tooltip>-->
          </template>
        </el-table-column>

      <!-- 是否启用列 -->
      <el-table-column label="是否启用">
        <template #default="scope">
          <el-checkbox v-model="scope.row.enabled"></el-checkbox>
        </template>
      </el-table-column>

      <!-- 操作列 -->
      <el-table-column label="操作">
        <template #default="scope">
          <el-button type="danger" size="small" @click="deleteRow(scope.$index)">删除</el-button>
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

    <!-- 描述弹出框 -->
    <el-dialog
        title="编辑描述"
        v-model="descriptionDialogVisible"
        width="500px"
        :before-close="handleDialogClose"
    >
      <el-input
          type="textarea"
          v-model="currentRowDescription"
          placeholder="请输入描述"
          :rows="10"
          style="width: 100%;"
      ></el-input>
      <template #footer>
        <el-button @click="descriptionDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveDescription">保存</el-button>
      </template>
    </el-dialog>
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
          imageUrl: "",
          price: 28.5,
          description: "经典川菜，麻辣可口",
          enabled: true,
        },
        {
          dish_id: "D002",
          category: "类别2",
          name: "炸春卷",
          imageUrl: "",
          price: 12.0,
          description: "外脆内嫩的炸春卷",
          enabled: false,
        },
      ],
      currentPage: 1, // 当前页数
      pageSize: 5, // 每页显示数量
      descriptionDialogVisible: false, // 控制描述对话框显示隐藏
      currentRow: null, // 当前正在编辑的行
      currentRowDescription: "", // 当前描述内容
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
    addRow() {
      const newRow = {
        dish_id: "",
        category: "",
        name: "",
        imageUrl: "",
        price: 0,
        description: "",
        enabled: true,
      };
      this.tableData.push(newRow);
    },
    deleteRow(index) {
      this.tableData.splice(index, 1);
    },
    submitData() {
      console.log("提交的数据：", this.tableData);
    },
    handlePageChange(page) {
      this.currentPage = page;
    },
    openDescriptionDialog(row) {
      this.currentRow = row;
      this.currentRowDescription = row.description;
      this.descriptionDialogVisible = true;
    },
    saveDescription() {
      if (this.currentRow) {
        this.currentRow.description = this.currentRowDescription;
      }
      this.descriptionDialogVisible = false;
    },
    handleDialogClose() {
      this.descriptionDialogVisible = false;
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
  width: 120px;  /* 设置固定宽度 */
}

.el-button {
  height: 36px; /* 设置按钮高度 */
}

/* 给翻页组件增加间距 */
.el-pagination {
  margin-top: 20px; /* 设置翻页与表格的间距 */
}


</style>
