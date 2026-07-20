import axios from 'axios'

const apiClient = axios.create({
  baseURL: '/api',
  timeout: 100000,
  headers: {
    'Content-Type': 'application/json',
  }
})

apiClient.interceptors.request.use(config => {
    const token = window.localStorage.token;
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, err => {
    return Promise.reject(err);
});
//响应拦截
apiClient.interceptors.response.use(res => {
    return res.data
}, err => {
    console.log(err);
})

// 导出 apiClient
export { apiClient };