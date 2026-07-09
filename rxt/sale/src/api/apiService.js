import axios from 'axios'

const apiClient = axios.create({
  baseURL: import.meta.env.VITE_APP_Address,
  timeout: 100000,
  headers: {
    'Content-Type': 'application/json',
  }
})

//请求拦截
apiClient.interceptors.request.use(config => { //拦截请求
    return config //若拦截到需要原封不动的返回
}, err => { })
//响应拦截
apiClient.interceptors.response.use(res => {
    return res.data
}, err => {
    console.log(err);
})

// 导出 apiClient
export { apiClient };