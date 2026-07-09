//定义store
import {defineStore} from 'pinia'
import {ref} from 'vue'
/* 
    第一个参数:名字,唯一性
    第二个参数:函数,函数的内部可以定义状态的所有内容

    返回值: 函数
*/
export const useTokenStore = defineStore('token',()=>{
    //定义状态的内容

    //1.响应式变量 - 初始化时从localStorage读取
    const token = ref(localStorage.getItem('token') || '')

    //2.定义一个函数,修改token的值
    const setToken = (newToken)=>{
        token.value = newToken
        localStorage.setItem('token', newToken)
    }

    //3.函数,移除token的值
    const removeToken = ()=>{
        token.value = ''
        localStorage.removeItem('token')
    }

    return {
        token,setToken,removeToken
    }
}
);
