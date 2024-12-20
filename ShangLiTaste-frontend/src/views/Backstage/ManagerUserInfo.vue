<template>
  <el-descriptions title="个人信息" border>
    <el-descriptions-item
        :rowspan="2"
        :width="140"
        label="头像"
        align="center"
    >
      <el-image
          style="width: 100px; height: 100px"
          src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
      />
    </el-descriptions-item>
    <el-descriptions-item label="用户编号">01</el-descriptions-item>
    <el-descriptions-item label="用户名">用户1</el-descriptions-item>
    <el-descriptions-item label="身份">管理员</el-descriptions-item>
    <el-descriptions-item label="状态">
      <el-tag size="small">启用</el-tag>
    </el-descriptions-item>
    <el-descriptions-item label="个人简介" align="center">
      你好！
    </el-descriptions-item>
  </el-descriptions>


</template>

