<template>
  <!-- Hero Section -->
  <section class="hero-section relative h-[500px] w-full">
    <!-- 轮播容器 -->
    <div class="carousel-container w-full h-full relative overflow-hidden">
      <!-- 第一页 -->
      <div
        class="carousel-item absolute inset-0 w-full h-full opacity-0 transition-opacity duration-500 bg-[url('../assets/img/banner01.png')] bg-cover bg-center"
        id="carousel-item01"
      >
        <div class="container mx-auto px-6 h-full flex items-center">
          <div class="max-w-2xl">
            <h1 class="text-5xl font-bold text-white mb-6">
              助力农业发展，普惠金融服务
            </h1>
             <div
              class="bg-gradient-to-r from-[#007029] to-transparent p-4 rounded-lg inline-block"
            >
              <p class="text-xl text-white mb-0">
                运用科技手段，提供智能化金融服务，助力农业现代化
              </p>
            </div>
          </div>
        </div>
      </div>

      <!-- 第二页 -->
      <div
        class="carousel-item absolute inset-0 w-full h-full opacity-0 transition-opacity duration-500 bg-[url('../assets/img/banner02.png')] bg-cover bg-center"
        id="carousel-item02"
      >
        <div class="container mx-auto px-6 h-full flex items-center">
          <div class="max-w-2xl">
            <h1 class="text-5xl font-bold text-white mb-6">
              创新金融科技，服务三农发展
            </h1>
            <div
              class="bg-gradient-to-r from-[#007029] to-transparent p-4 rounded-lg inline-block"
            >
              <p class="text-xl text-white mb-0">
                运用科技手段，提供智能化金融服务，助力农业现代化
              </p>
            </div>
          </div>
        </div>
      </div>

      <!-- 第三页 -->
      <div
        class="carousel-item absolute inset-0 w-full h-full opacity-0 transition-opacity duration-500 bg-[url('../assets/img/banner03.png')] bg-cover bg-center"
        id="carousel-item03"
      >
        <div class="container mx-auto px-6 h-full flex items-center">
          <div class="max-w-2xl">
            <h1 class="text-5xl font-bold text-white mb-6">
              专业农业金融，助力乡村振兴
            </h1>
            <div
              class="bg-gradient-to-r from-[#007029] to-transparent p-4 rounded-lg inline-block"
            >
              <p class="text-xl text-white mb-0">
                提供专业的农业金融解决方案，支持农村经济发展
              </p>
            </div>
          </div>
        </div>
      </div>

      <!-- 轮播指示器 -->
      <div
        class="absolute bottom-4 left-1/2 transform -translate-x-1/2 flex space-x-2"
      >
        <button
          class="carousel-dot w-3 h-3 rounded-full bg-white opacity-50 transition-opacity duration-300"
        ></button>
        <button
          class="carousel-dot w-3 h-3 rounded-full bg-white opacity-50 transition-opacity duration-300"
        ></button>
        <button
          class="carousel-dot w-3 h-3 rounded-full bg-white opacity-50 transition-opacity duration-300"
        ></button>
      </div>
    </div>
  </section>
 <section class="py-4 bg-white border-t-2 border-gray-300">
<div class="container mx-auto px-4 py-2 max-w-6xl">
    <!-- 智能匹配按钮 -->
    <div class="flex justify-start mb-8">
      <button
          class="px-6 py-2 bg-green-800 text-white rounded-full text-sm hover:bg-green-900 transition-colors flex items-center gap-2" @click="aimatch()"
        >
          <img src="../assets/img/icon_match.png" class="w-4 h-4" />
          智能匹配
        </button>
    </div>

    <!-- 贷款产品列表 -->
    <div class="space-y-6">
      <div v-for="(product, index) in paginatedBankList" :key="index" 
           class="bg-white rounded-lg shadow-sm p-4 grid grid-cols-1 md:grid-cols-7 gap-4 border border-gray-100">
        <!-- 产品名称和标签 -->
        <div class="md:col-span-2">
          <h3 class="text-lg font-medium text-gray-900 mb-2">{{ product.name }}</h3>
          <p class="text-sx text-gray-700 mb-2">{{ product.bankName }}</p>
          <div class="flex flex-wrap gap-2">
            <span v-for="(tag, tagIndex) in product.tags" :key="tagIndex" 
                  :class="[
                    'text-xs px-2 py-1 rounded-full text-white',
                    tag.type === 'primary' ? 'bg-green-700' : 
                    tag.type === 'secondary' ? 'bg-green-600' : 'bg-green-500'
                  ]">
              {{ tag.text }}
            </span>
          </div>
        </div>

        <!-- 放款速度 -->
        <div class="md:col-span-1">
          <div class="text-sm text-gray-500 mb-1">最快放款</div>
          <div class="text-base font-medium">3个工作日</div>
        </div>

        <!-- 申请额度 -->
        <div class="md:col-span-1">
          <div class="text-sm text-gray-500 mb-1">申请额度（元）</div>
          <div class="text-base font-medium">{{ product.money }}</div>
        </div>

        <!-- 申请期限 -->
        <div class="md:col-span-1">
          <div class="text-sm text-gray-500 mb-1">贷款期限（月）</div>
          <div class="text-base font-medium">{{ product.repayment }}</div>
        </div>

        <!-- 参考利率 -->
        <div class="md:col-span-1">
          <div class="text-sm text-gray-500 mb-1">参考利率</div>
          <div class="text-base font-medium">年利率{{ product.rate }}%</div>
        </div>

        <!-- 申请按钮 -->
        <div class="md:col-span-1 flex items-center justify-end">
          <button class="bg-green-700 hover:bg-green-800 text-white px-6 py-2 rounded-md transition duration-200" @click="finanProDetail(product)">
            我要贷款
          </button>
        </div>
      </div>
    </div>

    <!-- 分页控件 -->
    <div class="mt-8 flex justify-center">
      <nav class="relative z-0 inline-flex rounded-md shadow-sm -space-x-px" aria-label="Pagination">
        <button 
          @click="prevPage" 
          :disabled="currentPage === 1"
          :class="[
            'relative inline-flex items-center px-2 py-2 rounded-l-md border border-gray-300 bg-white text-sm font-medium',
            currentPage === 1 ? 'text-gray-300 cursor-not-allowed' : 'text-gray-500 hover:bg-gray-50'
          ]"
        >
          <span class="sr-only">上一页</span>
          <svg class="h-5 w-5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
            <path fill-rule="evenodd" d="M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z" clip-rule="evenodd" />
          </svg>
        </button>
        
        <button
          v-for="page in totalPages"
          :key="page"
          @click="goToPage(page)"
          :class="[
            'relative inline-flex items-center px-4 py-2 border border-gray-300 bg-white text-sm font-medium',
            currentPage === page ? 'z-10 bg-green-50 border-green-500 text-green-600' : 'text-gray-500 hover:bg-gray-50'
          ]"
        >
          {{ page }}
        </button>
        
        <button 
          @click="nextPage" 
          :disabled="currentPage === totalPages"
          :class="[
            'relative inline-flex items-center px-2 py-2 rounded-r-md border border-gray-300 bg-white text-sm font-medium',
            currentPage === totalPages ? 'text-gray-300 cursor-not-allowed' : 'text-gray-500 hover:bg-gray-50'
          ]"
        >
          <span class="sr-only">下一页</span>
          <svg class="h-5 w-5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
            <path fill-rule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clip-rule="evenodd" />
          </svg>
        </button>
      </nav>
    </div>
  </div>
 </section>
</template>

<script setup>
import { ref,computed } from 'vue';
import { onMounted } from "vue";
import { UserIcon } from "lucide-vue-next";
import { defineComponent } from "vue";
import { apiClient } from "../api/apiService.js";
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';

import bank01 from "@/assets/img/zgnyyh.png";
import bank02 from "@/assets/img/zggsyh.png";
import bank03 from "@/assets/img/zgjsyh.png";
import bank04 from "@/assets/img/zgnyyh.png";
import bank05 from "@/assets/img/rzyh.png";
import bank06 from "@/assets/img/hxyh.png";
import bank07 from "@/assets/img/zgjsyh.png";
import bank08 from "@/assets/img/pfyh.png";
import bank09 from "@/assets/img/zgpayh.png";
import bank10 from "@/assets/img/zgmsyh.png";
import bank11 from "@/assets/img/zgyh.png";
import { useStore } from "vuex";

const store = useStore();
const router = useRouter();

// defineComponent({
//   name: "HomePage",
// });

// Import images
import title01 from "@/assets/img/title01.jpg";
import banner01 from "@/assets/img/banner01.png";

// Carousel data
const currentSlide = ref(0);
const carouselImages = ref([
  { src: title01, alt: "Green agricultural fields" },
  { src: banner01, alt: "Agriculture banner" },
]);
//银行列表
let bankList = ref([0]);

// Auto-rotate carousel
onMounted(async() => {

const imgArray = ref([
    {
      id: "1001",
      icon: bank01,
    },
    {
      id: "1002",
      icon: bank02,
    },
    {
      id: "1003",
      icon: bank03,
    },
    {
      id: "1004",
      icon: bank04,
    },
    {
      id: "1005",
      icon: bank05,
    },
    {
      id: "1006",
      icon: bank06,
    },
    {
      id: "1007",
      icon: bank07,
    },
    {
      id: "1008",
      icon: bank08,
    },
    {
      id: "1009",
      icon: bank09,
    },
    {
      id: "1010",
      icon: bank10,
    },
    {
      id: "1011",
      icon: bank11,
    },
  ]);
  //编辑展示的银行产品信息
  try {
    const bankdata = await selectBank();
    if (bankdata && bankdata.length > 0) {
      let bankMsg='';
      bankdata.forEach((item) => {
        let matched = null;
        imgArray.value.forEach((item1) => {
          if (String(item.bankId) === String(item1.id)) {
            matched = item1.icon;
          }
        });
        if (!matched && item.picture) {
          item['icon'] = `${store.state.imgShowRoad}/file/bank/${item.picture}`;
        } else {
          item['icon'] = matched || bank01;
        }
        bankMsg = item['introduce'].split('，')[0].replace(/"/g, '');
        item['name'] = bankMsg;
        item['tags'] = [
          { text: '材料简单', type: 'primary' },
          { text: '审核便捷', type: 'secondary' },
          { text: '放款快', type: 'tertiary' }
        ]
      });
      bankList.value = bankdata;
    } else {
      throw new Error('No data');
    }
  } catch (e) {
    console.warn('银行数据加载失败，使用mock数据', e);
    bankList.value = [
      {
        bankId: '1001',
        icon: bank01,
        name: '青易贷',
        money: '100000',
        introduce: '青易贷，在线申请，期限长还款方式灵活',
        rate: '4.5',
        repayment: '36',
        bankName: '中国农业银行',
        tags: [
          { text: '材料简单', type: 'primary' },
          { text: '审核便捷', type: 'secondary' },
          { text: '放款快', type: 'tertiary' }
        ]
      },
      {
        bankId: '1002',
        icon: bank02,
        name: '农业贷',
        money: '100000',
        introduce: '农业贷，在线申请，期限长还款方式灵活',
        rate: '4.8',
        repayment: '36',
        bankName: '中国工商银行',
        tags: [
          { text: '材料简单', type: 'primary' },
          { text: '审核便捷', type: 'secondary' },
          { text: '放款快', type: 'tertiary' }
        ]
      },
      {
        bankId: '1003',
        icon: bank03,
        name: '助农贷',
        money: '150000',
        introduce: '助农贷，在线申请，期限长还款方式灵活',
        rate: '4.3',
        repayment: '36',
        bankName: '中国建设银行',
        tags: [
          { text: '材料简单', type: 'primary' },
          { text: '审核便捷', type: 'secondary' },
          { text: '放款快', type: 'tertiary' }
        ]
      },
      {
        bankId: '1005',
        icon: bank05,
        name: '阳光贷',
        money: '100000',
        introduce: '阳光贷，在线申请，期限长还款方式灵活',
        rate: '5.0',
        repayment: '36',
        bankName: '日照银行',
        tags: [
          { text: '材料简单', type: 'primary' },
          { text: '审核便捷', type: 'secondary' },
          { text: '放款快', type: 'tertiary' }
        ]
      },
    ];
  }

  const items = document.querySelectorAll(".carousel-item");
  const dots = document.querySelectorAll(".carousel-dot");
  let currentIndex = 0;

  // 显示指定索引的轮播项
  function showSlide(index) {
    items.forEach((item) => (item.style.opacity = "0"));
    dots.forEach((dot) => (dot.style.opacity = "0.5"));

    items[index].style.opacity = "1";
    dots[index].style.opacity = "1";
  }

  // 切换到下一张
  function nextSlide() {
    currentIndex = (currentIndex + 1) % items.length;
    showSlide(currentIndex);
  }

  // 初始显示第一张
  showSlide(0);

  // 设置自动轮播
  setInterval(nextSlide, 2000);

  // 点击指示器切换轮播
  dots.forEach((dot, index) => {
    dot.addEventListener("click", () => {
      currentIndex = index;
      showSlide(currentIndex);
    });
  });


});
const currentFinSlide = ref(0);
const totalSlides = 4;

const nextSlide = () => {
  if (currentFinSlide.value < totalSlides - 1) {
    currentFinSlide.value++;
  } else {
    currentFinSlide.value = 0;
  }
};

const prevSlide = () => {
  if (currentFinSlide.value > 0) {
    currentFinSlide.value--;
  } else {
    currentFinSlide.value = totalSlides - 1;
  }
};
// 银行查询接口
const selectBank = async () => {
  try {
    const response = await apiClient.get("/finance/selectbank");
    console.log("请求成功", response);
    return response;
  } catch (error) {
    console.error("请求失败", error);
    throw error;
  }
};

// 分页逻辑
const currentPage = ref(1);
const itemsPerPage = 10;

const totalPages = computed(() => {
  return Math.ceil(bankList.value.length / itemsPerPage);
});

const paginatedBankList = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  return bankList.value.slice(start, end);
});

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--;
  }
};

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++;
  }
};

const goToPage = (page) => {
  currentPage.value = page;
};

//点击某金融产品，跳转
const finanProDetail = (item)=> {
  router.push(`/home/financing?bankId=${item.bankId}&bankname=${item.name}&bankintro=${item.introduce}&bankmoney=${item.money}&bankrate=${item.rate}&bankrepay=${item.repayment}`)
};

const aimatch = ()=> {
  if(store.state.token!=''){
    router.push(`/home/aimatch`)
  } else {
    ElMessage.error('您未登录，请先登录');
  }
};
</script>