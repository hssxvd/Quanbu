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
            <router-link
              to="/home/userinfo"
              class="text-gray-800 hover:text-green-700 font-medium transition-colors"
              :class="{ 'text-green-700 border-b-2 border-green-700 pb-1': activeNav === 'userinfo' }"
            >
              个人信息
            </router-link>
          </nav>
          <div class="flex items-center space-x-4">
            <template v-if="isLoggedIn">
              <span class="text-gray-600">{{ loginUserNickname }}</span>
              <span class="text-gray-300">|</span>
              <a
                href="#"
                class="text-gray-600 hover:text-green-700 font-medium transition-colors"
                @click.prevent="logout"
              >
                退出
              </a>
            </template>
            <template v-else>
              <router-link
                to="/login"
                class="text-gray-600 hover:text-green-700 font-medium transition-colors"
              >
                登录
              </router-link>
              <span class="text-gray-300">|</span>
              <router-link
                to="/register"
                class="text-gray-600 hover:text-green-700 font-medium transition-colors"
              >
                注册
              </router-link>
            </template>
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
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

const router = useRouter();
const store = useStore();
const activeNav = ref("home");

const isLoggedIn = computed(() => {
  return store.state.token !== "";
});

const loginUserNickname = computed(() => {
  return store.state.loginUserNickname;
});

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
    
  }
};

const logout = () => {
  store.commit("updateLoginUserNickname", "");
  store.commit("removeStorage");
  router.push("/home").catch((err) => err);
};

onMounted(() => {
  const path = router.currentRoute.value.path;
  if (path.includes("/home") && !path.includes("/userinfo")) activeNav.value = "home";
  else if (path.includes("/financing")) activeNav.value = "financing";
  else if (path.includes("/expert")) activeNav.value = "expert";
  else if (path.includes("/agri")) activeNav.value = "agri";
  else if (path.includes("/userinfo")) activeNav.value = "userinfo";
});
</script>
