import axios from 'axios'
import { ElMessage, ElLoading } from 'element-plus'

// 创建 Axios 实例，连接你的后端
const service = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL, // 从环境变量拿地址
  timeout: 5000, // 5秒没响应就提示超时
  headers: { 'Content-Type': 'application/json;charset=utf-8' }
})

// 请求拦截器：请求发出去前显示“加载中”
let loadingInstance = null
service.interceptors.request.use(
  (config) => {
    loadingInstance = ElLoading.service({
      lock: true,
      text: '正在处理...',
      background: 'rgba(0, 0, 0, 0.1)'
    })
    return config
  },
  (error) => {
    loadingInstance?.close()
    ElMessage.error('请求出错了：' + error.message)
    return Promise.reject(error)
  }
)

// 响应拦截器：适配后端直接返回数组/对象的格式
service.interceptors.response.use(
  (response) => {
    loadingInstance?.close() // 关闭加载中
    const res = response.data
    // 后端直接返回数据，前端包装成 {data: 数据}，适配页面里的 res.data
    return { data: res }
  },
  (error) => {
    loadingInstance?.close() // 关闭加载中
    // 区分不同错误类型，提示更精准
    let msg = '网络出错，请检查后端是否运行'
    if (error.response) {
      const status = error.response.status
      switch (status) {
        case 404: msg = '接口不存在'; break
        case 500: msg = '服务器内部错误'; break
        default: msg = `请求失败：${status}`
      }
    }
    ElMessage.error(msg)
    return Promise.reject(error)
  }
)

export default service