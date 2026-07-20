<template>

      <!-- 侧边栏 -->
      <div class="p-4 h-full">
        <!-- 我的信息 -->
        <div 
          @click="toggleMenu('myInfo')" 
          class="flex items-center justify-between text-gray-700 font-medium p-2 cursor-pointer"
        >
          <div class="flex items-center space-x-2">
            <div class="w-6 h-6 flex items-center justify-center">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-user"><path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
            </div>
            <span>我的信息</span>
          </div>
          <svg 
            xmlns="http://www.w3.org/2000/svg" 
            width="16" 
            height="16" 
            viewBox="0 0 24 24" 
            fill="none" 
            stroke="currentColor" 
            stroke-width="2" 
            stroke-linecap="round" 
            stroke-linejoin="round" 
            :class="expandedMenus.myInfo ? 'lucide lucide-chevron-up' : 'lucide lucide-chevron-down'"
          >
            <path :d="expandedMenus.myInfo ? 'm18 15-6-6-6 6' : 'm6 9 6 6 6-6'"/>
          </svg>
        </div>
        
        <!-- 我的信息子菜单 -->
        <div v-if="expandedMenus.myInfo" class="ml-8 space-y-2 mt-2">
          <div
            @click="UserInfoClick(); activeSection = 'basic'"
            class="flex items-center space-x-2 font-medium p-2 rounded-md cursor-pointer"
            :class="activeSection === 'basic' ? 'text-[#007029] bg-green-50' : 'text-gray-500 hover:bg-gray-100'"
          >
            <div class="w-2 h-2 rounded-full" :class="activeSection === 'basic' ? 'bg-[#007029]' : 'bg-gray-300'"></div>
            <span>基本信息</span>
          </div>
          <!--v-if="$store.getters.isExpert" -->
          <div v-if="$store.getters.isExpert"
            @click="ExpertInfoClick(); activeSection = 'expertinfo'"
            class="flex items-center space-x-2 font-medium p-2 rounded-md cursor-pointer"
            :class="activeSection === 'expertinfo' ? 'text-[#007029] bg-green-50' : 'text-gray-500 hover:bg-gray-100'"
          >
            <div class="w-2 h-2 rounded-full" :class="activeSection === 'expertinfo' ? 'bg-[#007029]' : 'bg-gray-300'"></div>
            <span>专家信息</span>
          </div>
          <div v-if="!$store.getters.isExpert && !$store.getters.isAdmin"
            @click="UserAddressClick();activeSection = 'address'"
            class="flex items-center space-x-2 font-medium p-2 rounded-md cursor-pointer"
            :class="activeSection === 'address' ? 'text-[#007029] bg-green-50' : 'text-gray-500 hover:bg-gray-100'"
          >
            <div class="w-2 h-2 rounded-full" :class="activeSection === 'address' ? 'bg-[#007029]' : 'bg-gray-300'"></div>
            <span>收货地址</span>
          </div>
          <div v-if="!$store.getters.isAdmin"
            @click="UserPasswordClick();activeSection = 'password'"
            class="flex items-center space-x-2 font-medium p-2 rounded-md cursor-pointer"
            :class="activeSection === 'password' ? 'text-[#007029] bg-green-50' : 'text-gray-500 hover:bg-gray-100'"
          >
            <div class="w-2 h-2 rounded-full" :class="activeSection === 'password' ? 'bg-[#007029]' : 'bg-gray-300'"></div>
            <span>修改密码</span>
          </div>
        </div>
        
        <!-- 我的发布 -->
        <div v-if="!$store.getters.isAdmin && !$store.getters.isExpert"
          @click="toggleMenu('myPosts')" 
          class="flex items-center justify-between text-gray-700 font-medium p-2 mt-4 cursor-pointer"
          :class="expandedMenus.myPosts ? 'text-[#007029]' : 'text-gray-700'"
        >
          <div class="flex items-center space-x-2">
            <div class="w-6 h-6 flex items-center justify-center">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-send"><path d="m22 2-7 20-4-9-9-4Z"/><path d="M22 2 11 13"/></svg>
            </div>
            <span>我的发布</span>
          </div>
          <svg 
            xmlns="http://www.w3.org/2000/svg" 
            width="16" 
            height="16" 
            viewBox="0 0 24 24" 
            fill="none" 
            stroke="currentColor" 
            stroke-width="2" 
            stroke-linecap="round" 
            stroke-linejoin="round" 
            :class="expandedMenus.myPosts ? 'lucide lucide-chevron-up' : 'lucide lucide-chevron-down'"
          >
            <path :d="expandedMenus.myPosts ? 'm18 15-6-6-6 6' : 'm6 9 6 6 6-6'"/>
          </svg>
        </div>
        
        <!-- 我的发布子菜单 -->
        <div v-if="expandedMenus.myPosts && !$store.getters.isAdmin && !$store.getters.isExpert" class="ml-8 space-y-2 mt-2">
          <div 
            @click="ProductsClick();activeSection = 'products'" 
            class="flex items-center space-x-2 font-medium p-2 rounded-md cursor-pointer"
            :class="activeSection === 'products' ? 'text-[#007029] bg-green-50' : 'text-gray-500 hover:bg-gray-100'"
          >
            <div class="w-2 h-2 rounded-full" :class="activeSection === 'products' ? 'bg-[#007029]' : 'bg-gray-300'"></div>
            <span>商品货源</span>
          </div>
          <div 
            @click="RequestsClick();activeSection = 'requests'" 
            class="flex items-center space-x-2 font-medium p-2 rounded-md cursor-pointer"
            :class="activeSection === 'requests' ? 'text-[#007029] bg-green-50' : 'text-gray-500 hover:bg-gray-100'"
          >
            <div class="w-2 h-2 rounded-full" :class="activeSection === 'requests' ? 'bg-[#007029]' : 'bg-gray-300'"></div>
            <span>求购需求</span>
          </div>
        </div>

        <!-- 专家管理 -->
        <div v-if="!$store.getters.isAdmin"
          @click="toggleMenu('expert')" 
          class="flex items-center justify-between text-gray-700 font-medium p-2 mt-4 cursor-pointer"
        >
          <div class="flex items-center space-x-2">
            <div class="w-6 h-6 flex items-center justify-center">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-graduation-cap"><path d="M22 10v6M2 10l10-5 10 5-10 5z"/><path d="M6 12v5c0 2 2 3 6 3s6-1 6-3v-5"/></svg>
            </div>
            <span>专家管理</span>
          </div>
          <svg 
            xmlns="http://www.w3.org/2000/svg" 
            width="16" 
            height="16" 
            viewBox="0 0 24 24" 
            fill="none" 
            stroke="currentColor" 
            stroke-width="2" 
            stroke-linecap="round" 
            stroke-linejoin="round" 
            :class="expandedMenus.expert ? 'lucide lucide-chevron-up' : 'lucide lucide-chevron-down'"
          >
            <path :d="expandedMenus.expert ? 'm18 15-6-6-6 6' : 'm6 9 6 6 6-6'"/>
          </svg>
        </div>
        <!-- 专家管理子菜单 -->
        <div v-if="expandedMenus.expert && !$store.getters.isAdmin" class="ml-8 space-y-2 mt-2">
          <div 
            @click="QuestionClick(); activeSection = 'question'"
            class="flex items-center space-x-2 font-medium p-2 rounded-md cursor-pointer"
            :class="activeSection === 'question' ? 'text-[#007029] bg-green-50' : 'text-gray-500 hover:bg-gray-100'"
          >
            <div class="w-2 h-2 rounded-full" :class="activeSection === 'question' ? 'bg-[#007029]' : 'bg-gray-300'"></div>
            <span>问答管理</span>
          </div>
          <div 
            @click="AppointmentClick();activeSection = 'appointment'"
            class="flex items-center space-x-2 font-medium p-2 rounded-md cursor-pointer"
            :class="activeSection === 'appointment' ? 'text-[#007029] bg-green-50' : 'text-gray-500 hover:bg-gray-100'"
          >
            <div class="w-2 h-2 rounded-full" :class="activeSection === 'appointment' ? 'bg-[#007029]' : 'bg-gray-300'"></div>
            <span>预约管理</span>
          </div>
          <div v-if="$store.getters.isExpert"
            @click="KnowledgeClick();activeSection = 'knowledge'"
            class="flex items-center space-x-2 font-medium p-2 rounded-md cursor-pointer"
            :class="activeSection === 'knowledge' ? 'text-[#007029] bg-green-50' : 'text-gray-500 hover:bg-gray-100'"
          >
            <div class="w-2 h-2 rounded-full" :class="activeSection === 'knowledge' ? 'bg-[#007029]' : 'bg-gray-300'"></div>
            <span>知识管理</span>
          </div>
        </div>
        <!-- 我的订单 -->
        <div v-if="!$store.getters.isAdmin && !$store.getters.isExpert"
          @click="toggleMenu('order')" 
          class="flex items-center justify-between text-gray-700 font-medium p-2 mt-4 cursor-pointer"
        >
          <div class="flex items-center space-x-2">
            <div class="w-6 h-6 flex items-center justify-center">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-clipboard-list"><path d="M16 4h2a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h2"/><path d="M15 2H9a1 1 0 0 0-1 1v2a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V3a1 1 0 0 0-1-1z"/><path d="M12 11h4"/><path d="M12 16h4"/><path d="M8 11h.01"/><path d="M8 16h.01"/></svg>
            </div>
            <span>我的订单</span>
          </div>
          <svg 
            xmlns="http://www.w3.org/2000/svg" 
            width="16" 
            height="16" 
            viewBox="0 0 24 24" 
            fill="none" 
            stroke="currentColor" 
            stroke-width="2" 
            stroke-linecap="round" 
            stroke-linejoin="round" 
            :class="expandedMenus.order ? 'lucide lucide-chevron-up' : 'lucide lucide-chevron-down'"
          >
            <path :d="expandedMenus.order ? 'm18 15-6-6-6 6' : 'm6 9 6 6 6-6'"/>
          </svg>
        </div>
                <!-- 专家管理子菜单 -->
        <div v-if="expandedMenus.order && !$store.getters.isAdmin && !$store.getters.isExpert" class="ml-8 space-y-2 mt-2">
          <div 
            @click="SellClick(); activeSection = 'sell'"
            class="flex items-center space-x-2 font-medium p-2 rounded-md cursor-pointer"
            :class="activeSection === 'sell' ? 'text-[#007029] bg-green-50' : 'text-gray-500 hover:bg-gray-100'"
          >
            <div class="w-2 h-2 rounded-full" :class="activeSection === 'sell' ? 'bg-[#007029]' : 'bg-gray-300'"></div>
            <span>我卖出的</span>
          </div>
          <div 
            @click="BuyClick();activeSection = 'buy'"
            class="flex items-center space-x-2 font-medium p-2 rounded-md cursor-pointer"
            :class="activeSection === 'buy' ? 'text-[#007029] bg-green-50' : 'text-gray-500 hover:bg-gray-100'"
          >
            <div class="w-2 h-2 rounded-full" :class="activeSection === 'buy' ? 'bg-[#007029]' : 'bg-gray-300'"></div>
            <span>我买到的</span>
          </div>
        </div>
        
        <!-- 我的融资 -->
        <div @click="FinancingClick();activeSection = 'financing'" v-if="!$store.getters.isAdmin && !$store.getters.isExpert"
        class="flex items-center space-x-2 text-gray-700 font-medium p-2 mt-4"
        :class="activeSection === 'financing' ? 'text-[#007029] bg-green-50' : 'text-gray-500 hover:bg-gray-100'">
          <div class="w-6 h-6 flex items-center justify-center">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-wallet"><path d="M21 12V7H5a2 2 0 0 1 0-4h14v4"/><path d="M3 5v14a2 2 0 0 0 2 2h16v-5"/><path d="M18 12a2 2 0 0 0 0 4h4v-4Z"/></svg>
          </div>
          <span :class="activeSection === 'financing' ? 'text-[#007029] bg-green-50' : 'text-gray-800 hover:bg-gray-100'">我的融资</span>
        </div>

        <!-- 用户管理 -->
        <div @click="UserMngClick();activeSection = 'usermng'" v-if="$store.getters.isAdmin"
        class="flex items-center space-x-2 text-gray-700 font-medium p-2 mt-4"
        :class="activeSection === 'usermng' ? 'text-[#007029] bg-green-50' : 'text-gray-500 hover:bg-gray-100'">
          <div class="w-6 h-6 flex items-center justify-center">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-user"><path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
          </div>
          <span :class="activeSection === 'usermng' ? 'text-[#007029] bg-green-50' : 'text-gray-800 hover:bg-gray-100'">用户管理</span>
        </div>
        <!-- 商品管理 -->
        <div @click="GoodsMngClick();activeSection = 'goodsmng'" v-if="$store.getters.isAdmin"
        class="flex items-center space-x-2 text-gray-700 font-medium p-2 mt-4"
        :class="activeSection === 'goodsmng' ? 'text-[#007029] bg-green-50' : 'text-gray-500 hover:bg-gray-100'">
          <div class="w-6 h-6 flex items-center justify-center">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-user"><path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
          </div>
          <span :class="activeSection === 'goodsmng' ? 'text-[#007029] bg-green-50' : 'text-gray-800 hover:bg-gray-100'">商品管理</span>
        </div>
      </div>

</template>

<script setup>
import { ref, reactive } from 'vue';
import router from '@/router/index';

// 菜单展开状态
const expandedMenus = reactive({
  myInfo: true,
  myPosts: false
});

// 切换菜单展开状态
const toggleMenu = (menu) => {
  expandedMenus[menu] = !expandedMenus[menu];
};

// 当前激活的部分
const activeSection = ref('basic');
router.push("/personal/mymsg").catch((err) => err);

const UserInfoClick = () => {
  router.push("/personal/mymsg").catch((err) => err);
};
const ExpertInfoClick = () => {
  router.push("/personal/myexpertmsg").catch((err) => err);
};
const UserAddressClick = () => {
  router.push("/personal/myadd").catch((err) => err);
};
const UserPasswordClick = () => {
  router.push("/personal/mypwd").catch((err) => err);
};
const ProductsClick = () => {
  router.push("/personal/products").catch((err) => err);
};
const RequestsClick = () => {
  router.push("/personal/requests").catch((err) => err);
};
const QuestionClick = () => {
  router.push("/personal/myquestion").catch((err) => err);
};
const AppointmentClick = () => {
  router.push("/personal/myappointment").catch((err) => err);
};
const KnowledgeClick = () => {
  router.push("/personal/myknowledge").catch((err) => err);
};
const SellClick = () => {
  router.push("/personal/mysellorder").catch((err) => err);
};
const BuyClick = () => {
  router.push("/personal/mybuyorder").catch((err) => err);
};
const FinancingClick = () => {
  router.push("/personal/myfinancing").catch((err) => err);
};
const UserMngClick = () => { 
  router.push("/personal/myusermng").catch((err) => err);
};
const GoodsMngClick = () => { 
  router.push("/personal/mygoodsmng").catch((err) => err);
};
</script>

<style scoped>
/* 可以添加额外的样式 */
</style>