import axios from 'axios'

const apiClient = axios.create({
  baseURL: import.meta.env.VITE_APP_Address,
  timeout: 100000,
  headers: {
    'Content-Type': 'application/json',
  }
})

apiClient.interceptors.request.use(config => {
    const token = localStorage.getItem('token')
    if (token) {
        config.headers['Authorization'] = 'Bearer ' + token
    }
    return config
}, err => { })

apiClient.interceptors.response.use(res => {
    return res.data
}, err => {
    console.log(err);
})

export { apiClient };