<template>
  <div class="min-h-screen flex flex-col">
    <header class="bg-white shadow-sm sticky top-0 z-50">
      <div class="container mx-auto px-6">
        <div class="flex items-center justify-between h-16">
          <div class="flex items-center">
            <img src="../assets/img/logo.png" alt="融销通" class="h-10 w-10" />
            <span class="text-xl font-bold text-green-800 ml-2">融销通</span>
          </div>
          <nav class="flex items-center space-x-8">
            <a
              href="/home"
              class="text-gray-800 hover:text-green-700 font-medium transition-colors"
              :class="{ 'text-green-700 border-b-2 border-green-700 pb-1': activeNav === 'home' }"
              @click.prevent="navigate('home')"
            >
              首页
            </a>
            <a
              href="/home/financinglist"
              class="text-gray-800 hover:text-green-700 font-medium transition-colors"
              :class="{ 'text-green-700 border-b-2 border-green-700 pb-1': activeNav === 'financing' }"
              @click.prevent="navigate('financing')"
            >
              融资服务
            </a>
            <a
              href="/home/expertassist?activeSection=experts"
              class="text-gray-800 hover:text-green-700 font-medium transition-colors"
              :class="{ 'text-green-700 border-b-2 border-green-700 pb-1': activeNav === 'expert' }"
              @click.prevent="navigate('expert')"
            >
              专家助力
            </a>
            <a
              href="/home/agripro"
              class="text-gray-800 hover:text-green-700 font-medium transition-colors"
              :class="{ 'text-green-700 border-b-2 border-green-700 pb-1': activeNav === 'agri' }"
              @click.prevent="navigate('agri')"
            >
              农产品交易
            </a>
          </nav>
          <div class="flex items-center space-x-4">
            <a
              href="/home/login"
              class="text-gray-600 hover:text-green-700 font-medium transition-colors"
              @click.prevent="navigate('login')"
            >
              登录
            </a>
            <span class="text-gray-300">|</span>
            <a
              href="/home/register"
              class="text-gray-600 hover:text-green-700 font-medium transition-colors"
              @click.prevent="navigate('register')"
            >
              注册
            </a>
          </div>
        </div>
      </div>
    </header>

    <main class="flex-grow">
      <router-view></router-view>
    </main>

    <footer class="bg-green-800 py-6">
      <div class="container mx-auto px-6">
        <div class="flex items-center justify-between">
          <div class="flex items-center">
            <img src="../assets/img/logobtm.png" alt="融销通" class="h-8 w-8" />
            <span class="text-lg font-bold text-white ml-2">融销通</span>
          </div>
          <nav class="flex items-center space-x-6">
            <a href="#" class="text-white/80 hover:text-white font-medium transition-colors text-sm">
              关于我们
            </a>
            <a href="#" class="text-white/80 hover:text-white font-medium transition-colors text-sm">
              联系方式
            </a>
            <a href="#" class="text-white/80 hover:text-white font-medium transition-colors text-sm">
              服务条款
            </a>
            <a href="#" class="text-white/80 hover:text-white font-medium transition-colors text-sm">
              隐私政策
            </a>
          </nav>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const activeNav = ref("home");

const navigate = (page) => {
  activeNav.value = page;
  switch (page) {
    case "home":
      router.push("/home");
      break;
    case "financing":
      router.push("/home/financinglist");
      break;
    case "expert":
      router.push("/home/expertassist?activeSection=experts");
      break;
    case "agri":
      router.push("/home/agripro");
      break;
    case "login":
      router.push("/home/login");
      break;
    case "register":
      router.push("/home/register");
      break;
  }
};

onMounted(() => {
  const path = router.currentRoute.value.path;
  if (path.includes("/home")) activeNav.value = "home";
  else if (path.includes("/financing")) activeNav.value = "financing";
  else if (path.includes("/expert")) activeNav.value = "expert";
  else if (path.includes("/agri")) activeNav.value = "agri";
});
</script>
