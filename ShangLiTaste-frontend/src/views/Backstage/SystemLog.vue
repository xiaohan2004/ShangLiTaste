<template>
  <div class="system-log">
      <el-table
          v-if="logData.length > 0"
          :data="paginatedLogData"
          style="width: 100%"
          :border="false"
          :stripe="true"
          :highlight-current-row="true"
      >
        <el-table-column prop="id" label="ID" width="80" align="center"/>
        <el-table-column prop="operateUser" label="操作人" min-width="120" align="center"/>
        <el-table-column prop="operateTime" label="操作时间" min-width="180" align="center">
          <template #default="scope">
            <el-tag size="small" effect="plain">
              {{ formatDate(scope.row.operateTime) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="className" label="操作类名" min-width="200" show-overflow-tooltip/>
        <el-table-column prop="methodName" label="操作方法名" min-width="200" show-overflow-tooltip/>
        <el-table-column prop="methodParams" label="操作方法参数" min-width="250" show-overflow-tooltip/>
        <el-table-column prop="costTime" label="操作耗时(ms)" min-width="120" align="center">
          <template #default="scope">
            <el-tag :type="getTimeType(scope.row.costTime)" size="small">
              {{ scope.row.costTime }}
            </el-tag>
          </template>
        </el-table-column>
      </el-table>
      <el-empty v-else description="暂无数据"></el-empty>

    <div class="pagination-container">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[15, 20, 50, 100]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          background
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { ElMessage, ElTable, ElTableColumn, ElCard, ElTag, ElEmpty, ElPagination } from 'element-plus'
import api from "@/api/api";

export default {
  name: 'SystemLog',
  components: {
    ElTable,
    ElTableColumn,
    ElCard,
    ElTag,
    ElEmpty,
    ElPagination
  },
  data() {
    return {
      logData: [],
      currentPage: 1,
      pageSize: 15,
      total: 0
    }
  },
  computed: {
    paginatedLogData() {
      if (!this.logData || this.logData.length === 0) {
        return [];
      }
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.logData.slice(start, end);
    },
  },
  methods: {
    async fetchLogData() {
      try {
        const response = await api.get('/log', {
          params: {
            page: this.currentPage,
            size: this.pageSize
          }
        });
        if (response.data.code === 1) {
          this.logData = response.data.data || [];
          this.total = this.logData.length || 0;
        } else {
          this.logData = [];
          this.total = 0;
          ElMessage.error('获取系统日志失败');
        }
      } catch (error) {
        console.error('获取系统日志时出错:', error);
        this.logData = [];
        this.total = 0;
        ElMessage.error('获取系统日志失败');
      }
    },
    formatDate(dateString) {
      const date = new Date(dateString)
      return date.toLocaleString('zh-CN', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
      });
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.currentPage = 1;
      this.fetchLogData();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.fetchLogData();
    },
    getTimeType(time) {
      if (time < 100) return 'success'
      if (time < 500) return 'warning'
      return 'danger'
    }
  },
  mounted() {
    this.fetchLogData()
  }
}
</script>

<style scoped>
.system-log {
  padding: 16px;
}

.el-card {
  border-radius: 8px;
}

.el-table {
  width: 100%;
}

.el-table th {
  background-color: #f5f7fa;
  color: #606266;
  font-weight: bold;
}

.el-table .el-table__row:hover {
  background-color: #f5f7fa;
}

.pagination-container {
  margin-top: 16px;
  text-align: right;
}

.el-pagination {
  justify-content: flex-end;
}
</style>