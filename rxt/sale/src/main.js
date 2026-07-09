import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './assets/main.css'
import 'element-plus/dist/index.css'
import store from './store'
import ElementPlus from 'element-plus';

const app = createApp(App)
app.use(router)
app.use(store)
app.use(ElementPlus);
app.mount('#app')
