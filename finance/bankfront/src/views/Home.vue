<template>
  <div>
    <div v-if="!store.state.token" class="text-center mt-20">
      <h1 class="text-2xl text-gray-600 mb-4">欢迎来到融资管理系统</h1>
      <p class="text-gray-400">请先登录</p>
      <router-link to="/login" class="inline-block mt-4 px-6 py-2 bg-[#007029] text-white rounded-md hover:bg-[#005d22] transition">
        立即登录
      </router-link>
    </div>
    <div v-else class="text-center mt-20">
      <h1 class="text-2xl text-gray-600 mb-4">欢迎, {{ store.state.loginUserNickname }}</h1>
      <p class="text-gray-400">您已成功登录融资管理系统</p>
      <button @click="handleLogout" class="inline-block mt-4 px-6 py-2 bg-red-500 text-white rounded-md hover:bg-red-600 transition">
        退出登录
      </button>
    </div>
  </div>
</template>

<script setup>
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

const store = useStore();
const router = useRouter();

const handleLogout = () => {
  store.commit('removeStorage');
  router.push('/login');
};
</script>