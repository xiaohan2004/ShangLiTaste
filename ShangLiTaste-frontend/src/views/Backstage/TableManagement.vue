<template>
  <div>
    <!-- 筛选功能 -->
    <div style="margin-bottom: 16px; display: flex; justify-content: space-between;">
      <div style="flex: 1; display: flex; gap: 16px;">
        <!-- 筛选状态 -->
        <el-select
            v-model="selectedStatus"
            placeholder="选择状态"
            style="width: 120px;">  <!-- 设置宽度 -->

          <el-option label="全部" value="" />
          <el-option label="空闲" value="空闲" />
          <el-option label="使用中" value="使用中" />
          <el-option label="预订" value="预订" />
        </el-select>

        <!-- 筛选区域 -->
        <el-select
            v-model="selectedLocation"
            placeholder="选择区域"
            style="width: 120px;">  <!-- 设置宽度 -->

          <el-option label="全部" value="" />
          <el-option label="大厅" value="大厅" />
          <el-option label="包厢" value="包厢" />
        </el-select>
      </div>

      <!-- 增加行按钮 -->
      <div style="display: flex; gap: 16px;">
        <el-button type="primary" @click="addRow">增加</el-button>
        <el-button type="success" @click="submitData">提交</el-button>  <!-- 新增提交按钮 -->
      </div>
    </div>

    <!-- 可编辑表格 -->
    <el-table :data="filteredTableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)" border style="width: 100%; height: 100%; overflow: auto;">
      <!-- 餐桌编号列 -->
      <el-table-column label="餐桌编号">
        <template #default="scope">
          <el-input
              v-model="scope.row.table_id"
              placeholder="请输入餐桌编号"
          ></el-input>
        </template>
      </el-table-column>

      <!-- 状态列（复选框） -->
      <el-table-column label="状态">
        <template #default="scope">
          <el-select v-model="scope.row.status" placeholder="请选择状态" style="width: 100%;">
            <el-option label="空闲" value="空闲"></el-option>
            <el-option label="使用中" value="使用中"></el-option>
            <el-option label="预订" value="预订"></el-option>
          </el-select>
        </template>
      </el-table-column>

      <!-- 区域列（复选框） -->
      <el-table-column label="区域">
        <template #default="scope">
          <el-select v-model="scope.row.location" placeholder="请选择区域" style="width: 100%;">
            <el-option label="大厅" value="大厅"></el-option>
            <el-option label="包厢" value="包厢"></el-option>
          </el-select>
        </template>
      </el-table-column>

      <!-- 操作列 -->
      <el-table-column label="操作">
        <template #default="scope">
          <el-button
              type="danger"
              size="small"
              @click="deleteRow(scope.$index)"
          >
            删除
          </el-button>
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
        class="pagination" /> <!-- 添加自定义类 -->

  </div>
</template>

<script>
export default {
  name: "TableManagement",
  data() {
    return {
      selectedStatus: "",  // 当前选中的状态筛选
      selectedLocation: "",  // 当前选中的区域筛选
      tableData: [
        { table_id: "T001", status: "空闲", location: "大厅" },
        { table_id: "T002", status: "使用中", location: "包厢" },
        { table_id: "T003", status: "预订", location: "大厅" },
        { table_id: "T004", status: "空闲", location: "包厢" },
      ],
      currentPage: 1,  // 当前页数
      pageSize: 5,  // 每页显示数量
    };
  },
  computed: {
    filteredTableData() {
      return this.tableData.filter((row) => {
        // 根据状态和区域进行筛选
        const matchesStatus = this.selectedStatus
            ? row.status === this.selectedStatus
            : true;
        const matchesLocation = this.selectedLocation
            ? row.location === this.selectedLocation
            : true;

        return matchesStatus && matchesLocation;
      });
    },
  },
  methods: {
    // 增加行
    addRow() {
      const newRow = {
        table_id: "",  // 餐桌编号留空
        status: this.selectedStatus || "空闲",  // 默认状态为筛选中的状态，若为空则设为“空闲”
        location: this.selectedLocation || "大厅",  // 默认区域为筛选中的区域，若为空则设为“大厅”
      };
      this.tableData.push(newRow);
    },
    // 删除行
    deleteRow(index) {
      this.tableData.splice(index, 1);
    },
    // 提交数据的逻辑
    submitData() {
      // 这里可以添加提交表单的代码
      console.log("提交的数据：", this.tableData);
    },
    // 处理翻页
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
