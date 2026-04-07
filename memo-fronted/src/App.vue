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

      <!-- 分页组件 -->
      <div style="text-align: center; margin-top: 20px;">
        <el-pagination
          v-model:current-page="pageNum"
          v-model:page-size="pageSize"
          :total="total"
          layout="total, prev, pager, next, jumper"
          @current-change="fetchMemos"
          @size-change="fetchMemos"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getMemoList, addMemo, updateMemo, deleteMemo } from './api/memo'

// 表单引用
const formRef = ref(null)
const loading = ref(false)
const form = ref({ id: '', title: '', content: '' })
const memoList = ref([])
const rules = ref({
  title: [{ required: true, message: '标题不能为空', trigger: 'blur' }]
})

// 分页变量
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)

// 页面加载时查询
onMounted(() => {
  fetchMemos()
})

// ======================
// 关键修改：JPA 分页取值
// ======================
const fetchMemos = async () => {
  loading.value = true
  try {
    const res = await getMemoList(pageNum.value, pageSize.value)
    
    // Spring Data JPA 返回格式：content + totalElements
    memoList.value = res.data.content
    total.value = res.data.totalElements
    
  } catch (err) {
    console.log('查列表失败：', err)
  } finally {
    loading.value = false
  }
}

// 提交表单（新增/修改）
const submitForm = async () => {
  const valid = await formRef.value.validate()
  if (!valid) return

  try {
    if (form.value.id) {
      await updateMemo(form.value.id, {
        title: form.value.title,
        content: form.value.content
      })
      ElMessage.success('修改成功！')
    } else {
      await addMemo({
        title: form.value.title,
        content: form.value.content
      })
      ElMessage.success('新增成功！')
    }
    resetForm()
    fetchMemos()
  } catch (err) {
    console.log('提交失败：', err)
  }
}

// 编辑
const editMemo = (row) => {
  form.value.id = row.id
  form.value.title = row.title
  form.value.content = row.content
}

// 删除
const handleDeleteMemo = async (id) => {
  try {
    await ElMessageBox.confirm('确定删除吗？', '提示', { type: 'warning' })
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
h1 {
  font-weight: normal;
  border-bottom: 1px solid #eee;
  padding-bottom: 10px;
}
</style>