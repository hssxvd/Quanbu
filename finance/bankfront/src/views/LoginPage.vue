<template>
  <div class="min-h-screen flex items-center justify-center bg-cover bg-center relative" :style="{ backgroundImage: `url(${backgroundImage})` }">
    <div class="absolute inset-0 bg-black bg-opacity-40"></div>
    
    <div class="bg-white rounded-lg shadow-xl p-8 w-full max-w-md relative z-10">
      <div class="text-center mb-8">
        <h1 class="text-2xl font-bold text-[#007029]">融资管理系统</h1>
        <p class="text-gray-600 mt-2">银行端管理平台</p>
      </div>
      
        <div class="mb-4">
          <label for="username" class="block text-sm font-medium text-gray-700 mb-1">账号</label>
          <div class="relative">
            <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-500">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                <path fill-rule="evenodd" d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z" clip-rule="evenodd" />
              </svg>
            </span>
            <input 
              id="username" 
              v-model="username" 
              type="text" 
              class="pl-10 w-full py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-[#007029] focus:border-transparent"
              placeholder="请输入账号"
              required
            />
          </div>
        </div>
        
        <div class="mb-4">
          <label for="password" class="block text-sm font-medium text-gray-700 mb-1">密码</label>
          <div class="relative">
            <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-500">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                <path fill-rule="evenodd" d="M5 9V7a5 5 0 0110 0v2a2 2 0 012 2v5a2 2 0 01-2 2H5a2 2 0 01-2-2v-5a2 2 0 012-2zm8-2v2H7V7a3 3 0 016 0z" clip-rule="evenodd" />
              </svg>
            </span>
            <input 
              id="password" 
              v-model="password" 
              :type="showPassword ? 'text' : 'password'" 
              class="pl-10 w-full py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-[#007029] focus:border-transparent"
              placeholder="请输入密码"
              required
            />
            <button 
              type="button" 
              @click="togglePasswordVisibility" 
              class="absolute inset-y-0 right-0 flex items-center pr-3 text-gray-500"
            >
              <svg v-if="showPassword" xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                <path d="M10 12a2 2 0 100-4 2 2 0 000 4z" />
                <path fill-rule="evenodd" d="M.458 10C1.732 5.943 5.522 3 10 3s8.268 2.943 9.542 7c-1.274 4.057-5.064 7-9.542 7S1.732 14.057.458 10zM14 10a4 4 0 11-8 0 4 4 0 018 0z" clip-rule="evenodd" />
              </svg>
              <svg v-else xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                <path fill-rule="evenodd" d="M3.707 2.293a1 1 0 00-1.414 1.414l14 14a1 1 0 001.414-1.414l-1.473-1.473A10.014 10.014 0 0019.542 10C18.268 5.943 14.478 3 10 3a9.958 9.958 0 00-4.512 1.074l-1.78-1.781zm4.261 4.26l1.514 1.515a2.003 2.003 0 012.45 2.45l1.514 1.514a4 4 0 00-5.478-5.478z" clip-rule="evenodd" />
                <path d="M12.454 16.697L9.75 13.992a4 4 0 01-3.742-3.741L2.335 6.578A9.98 9.98 0 00.458 10c1.274 4.057 5.065 7 9.542 7 .847 0 1.669-.105 2.454-.303z" />
              </svg>
            </button>
          </div>
        </div>
        
        <div class="flex items-center mb-6">
          <input 
            id="remember" 
            v-model="rememberMe" 
            type="checkbox" 
            class="h-4 w-4 text-[#007029] focus:ring-[#007029] border-gray-300 rounded"
          />
          <label for="remember" class="ml-2 block text-sm text-gray-700">记住密码</label>
        </div>
        
        <button @click="userLogin()"
          class="w-full bg-[#007029] hover:bg-[#005d22] text-white py-2 px-4 rounded-md transition duration-300 flex items-center justify-center"
        >
          <span>登录</span>
        </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import riceImg from "@/assets/img/rice.png";
import { apiClient } from "../api/apiService.js";
import { ElMessage } from 'element-plus';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { Base64 } from 'js-base64';

const store = useStore();
const router = useRouter();

const backgroundImage = riceImg;

const username = ref('');
const password = ref('');
const rememberMe = ref(false);
const showPassword = ref(false);

const togglePasswordVisibility = () => {
  showPassword.value = !showPassword.value;
};

const param = ref({
  username: username.value,
  password: password.value
});

const userLogin = async () => {
  try {
    param.value = {
    username: username.value,
    password: password.value
    };
    const response = await apiClient.post(`bank/user/login`, param.value, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    if (response.flag == true) {
        store.commit('setToken', response.data);

        const [, payload] = response.data.split('.');
        const decoded = Base64.decode(payload);
        const { nickname, role } = JSON.parse(decoded);
    
       store.commit('updateLoginUserNickname', nickname);
       store.commit('updateRole', role);

        router.push("/home").catch((err) => err);
    } else {
        ElMessage.error(response.message);
    }
  } catch (error) {
    console.error("请求失败", error);
    throw error;
  }
};

</script>

<style>
body {
  font-family: 'PingFang SC', 'Microsoft YaHei', sans-serif;
}
</style>