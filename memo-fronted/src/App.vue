<template>
  <div style="width: 90%; max-width: 1200px; margin: 20px auto;">
    <!-- 标题 -->
    <h1 style="text-align: center; color: #333;">我的备忘录</h1>

    <!-- 新增/编辑表单 -->
    <el-card shadow="hover" style="margin-bottom: 20px;">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入备忘录标题" />
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input v-model="form.content" type="textarea" rows="4" placeholder="请输入备忘录内容" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">{{ form.id ? '修改' : '新增' }}</el-button>
          <el-button @click="resetForm">清空</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 备忘录列表 -->
    <el-card shadow="hover">
      <el-table :data="memoList" border stripe v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" align="center" />
        <el-table-column prop="title" label="标题" min-width="200" />
        <el-table-column prop="content" label="内容" min-width="400" />
        <el-table-column prop="createTime" label="创建时间" width="200">
          <template #default="scope">
            {{ new Date(scope.row.createTime).toLocaleString() }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template #default="scope">
            <el-button type="primary" size="small" @click="editMemo(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDeleteMemo(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
// 导入的 deleteMemo 保留，用于调用接口
import { getMemoList, addMemo, updateMemo, deleteMemo } from './api/memo'

// 表单引用
const formRef = ref(null)
// 加载状态
const loading = ref(false)
// 表单数据
const form = ref({
  id: '',
  title: '',
  content: ''
})
// 备忘录列表
const memoList = ref([])
// 表单校验规则（标题不能为空）
const rules = ref({
  title: [{ required: true, message: '标题不能为空', trigger: 'blur' }]
})

// 页面加载时查所有备忘录
onMounted(() => {
  fetchMemos()
})

// 查所有备忘录
const fetchMemos = async () => {
  loading.value = true
  try {
    const res = await getMemoList()
    memoList.value = res.data || []
  } catch (err) {
    console.log('查列表失败：', err)
  } finally {
    loading.value = false
  }
}

// 提交表单（新增/修改）
const submitForm = async () => {
  // 先校验表单
  const valid = await formRef.value.validate()
  if (!valid) return

  try {
    if (form.value.id) {
      // 修改
      await updateMemo(form.value.id, {
        title: form.value.title,
        content: form.value.content
      })
      ElMessage.success('修改成功！')
    } else {
      // 新增
      await addMemo({
        title: form.value.title,
        content: form.value.content
      })
      ElMessage.success('新增成功！')
    }
    // 重置表单 + 刷新列表
    resetForm()
    fetchMemos()
  } catch (err) {
    console.log('提交失败：', err)
  }
}

// 编辑备忘录（回显数据）
const editMemo = (row) => {
  form.value.id = row.id
  form.value.title = row.title
  form.value.content = row.content
}

// 关键修复：把函数名改成 handleDeleteMemo，避免和导入的 deleteMemo 重名
const handleDeleteMemo = async (id) => {
  try {
    await ElMessageBox.confirm('确定删除吗？', '提示', { type: 'warning' })
    // 调用导入的 deleteMemo 接口函数
    await deleteMemo(id)
    ElMessage.success('删除成功！')
    fetchMemos()
  } catch (err) {
    ElMessage.info('已取消删除')
  }
}

// 重置表单
const resetForm = () => {
  formRef.value?.resetFields()
  form.value = { id: '', title: '', content: '' }
}
</script>

<style scoped>
/* 简单样式，让界面不丑 */
h1 {
  font-weight: normal;
  border-bottom: 1px solid #eee;
  padding-bottom: 10px;
}
</style>