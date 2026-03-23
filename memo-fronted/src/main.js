import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
// 引入 Element Plus 组件库
import ElementPlus from 'element-plus'
import 'element-plus/theme-chalk/index.css'
// 引入中文语言包（界面文字变成中文）
import zhCn from 'element-plus/es/locale/lang/zh-cn'

// 创建 Vue 实例
const app = createApp(App)
// 注册 Element Plus
app.use(ElementPlus, { locale: zhCn })
// 挂载到页面
app.mount('#app')