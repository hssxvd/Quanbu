<template>
  <!-- Hero Section -->
  <section class="hero-section relative h-[500px] w-full">
    <!-- 轮播容器 -->
    <div class="carousel-container w-full h-full relative overflow-hidden">
      <!-- 第一页 -->
      <div
        class="carousel-item absolute inset-0 w-full h-full opacity-100 transition-opacity duration-500 bg-cover bg-center cursor-pointer"
        :style="{ backgroundImage: `url(${banner01})` }"
        id="carousel-item01"
        @click="goToFinancing"
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
        class="carousel-item absolute inset-0 w-full h-full opacity-0 transition-opacity duration-500 bg-cover bg-center cursor-pointer"
        :style="{ backgroundImage: `url(${banner02})` }"
        id="carousel-item02"
        @click="goToExpert"
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
        class="carousel-item absolute inset-0 w-full h-full opacity-0 transition-opacity duration-500 bg-cover bg-center cursor-pointer"
        :style="{ backgroundImage: `url(${banner03})` }"
        id="carousel-item03"
        @click="goToAgriPro"
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

  <!-- Product Cards -->
  <section class="py-12 bg-gray-50">
    <div class="container mx-auto px-4">
      <h2 class="text-3xl font-bold text-center mb-1">金融产品</h2>
      <h5 class="text-l font-bold text-center mb-8 text-gray-400">
        Financial Products
      </h5>

      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <div
          v-for="(product, index) in bankFour"
          :key="index"
          class="bg-white rounded-lg shadow-sm overflow-hidden"
        >
          <div class="p-4">
            <div class="bg-white rounded-lg shadow-md p-5">
              <div class="flex flex-col items-center mb-4">
                <img :src="product.icon" class="w-48 h-12 mb-2" />
                <div class="text-lg font-bold text-gray-700">
                  {{product.name}}
                </div>
              </div>
              <div class="text-center mb-4">
                <div class="text-sm text-gray-400 mb-1">最高可申请额度</div>
                <div class="text-3xl font-bold text-green-800">
                  {{product.money}}
                </div>
              </div>
              <div class="text-center mb-4">
                <div class="text-sm text-gray-400">在线申请</div>
                <div class="text-sm text-gray-400">期限长 还款方式灵活</div>
              </div>
              <button
                class="w-full py-2 bg-green-800 text-white rounded text-sm hover:bg-green-900 transition-colors" @click="finanProDetail(product)"
              >
                我要贷款
              </button>
            </div>
          </div>
        </div>
      </div>
      <div class="flex justify-center gap-5 mt-6">
        <button
          class="px-6 py-2 bg-green-800 text-white rounded-full text-sm hover:bg-green-900 transition-colors flex items-center gap-2" @click="aimatch()"
        >
          <img src="../assets/img/icon_match.png" class="w-4 h-4" />
          智能匹配
        </button>
        <button
          class="px-6 py-2 bg-white text-gray-800 border border-gray-200 rounded-full text-sm hover:bg-gray-50 transition-colors" @click="finanProMore()"
        >
          查看更多
        </button>
      </div>
    </div>
  </section>
  <section>
    <div class="container mx-auto px-4">
      <!-- Specialists Section -->
      <div class="mb-16">
        <h2 class="text-3xl font-bold text-center mb-1">专家介绍</h2>
        <h5 class="text-l font-bold text-center mb-8 text-gray-400">
          Expert Introduction
        </h5>

        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-10 mb-8">
          <div
            v-for="(specialist, index) in expertFive"
            :key="index"
            class="rounded-lg overflow-hidden shadow-md border border-[rgba(0,112,41,0.3)] hover:bg-green-700 hover:text-white transition-colors duration-300"
          >
            <div class="h-32 w-full flex items-center justify-center relative">
              <div
                class="absolute bottom-0 left-1/2 transform -translate-x-1/2 w-32 h-16 bg-[#007029] rounded-t-full"
                style="z-index: 1"
              ></div>
              <img
                :src="specialist.avatar"
                class="h-32 w-32 object-cover relative"
                style="z-index: 2"
              />
            </div>
            <div class="p-4">
              <div class="flex gap-2 mb-4">
                <div
                  class="text-center font-bold text-base py-1 px-2 rounded bg-green-800 text-white flex-1"
                  :class="index === 0 ? 'bg-green-900' : ''"
                  @click="openConsultDialog(specialist)"
                >
                  直接咨询
                </div>
                <div
                  class="text-center font-bold text-base py-1 px-2 rounded bg-green-800 text-white flex-1"
                  :class="index === 0 ? 'bg-green-900' : ''"
                  @click="openReserveDialog(specialist)"
                >
                  线下预约
                </div>
              </div>
              <div class="text-xs">
                <div class="mb-1 flex">
                  <span :class="index === 0 ? 'text-gray-300' : 'text-gray-400'"
                    >专家姓名:</span
                  >
                  <span class="font-medium ml-1">{{ specialist.realName }}</span>
                </div>
                <div class="mb-1 flex">
                  <span :class="index === 0 ? 'text-gray-300' : 'text-gray-400'"
                    >专业:</span
                  >
                  <span class="font-medium ml-1">{{
                    specialist.profession
                  }}</span>
                </div>
                <div class="mb-1 flex">
                  <span :class="index === 0 ? 'text-gray-300' : 'text-gray-400'"
                    >单位:</span
                  >
                  <span class="font-medium ml-1">{{ specialist.belong }}</span>
                </div>
                <div class="mb-1 flex">
                  <span :class="index === 0 ? 'text-gray-300' : 'text-gray-400'"
                    >联系电话:</span
                  >
                  <span class="font-medium ml-1">{{ specialist.phone }}</span>
                </div>
                <div class="mb-1 flex">
                  <span :class="index === 0 ? 'text-gray-300' : 'text-gray-400'"
                    >职称:</span
                  >
                  <span class="font-medium ml-1">{{ specialist.position }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="flex justify-center">
          <button
            class="px-6 py-2 bg-white text-gray-800 border border-gray-200 rounded-full text-sm hover:bg-gray-50 transition-colors" @click="expertAssis('experts')" 
          >
            查看更多
          </button>
        </div>
      </div>
    </div>
  </section>

  <section>
    <div class="container mx-auto px-4">
      <!-- Specialists Section -->
      <div class="mb-16">
        <div class="flex flex-col md:flex-row">
          <!-- Left side - Title and Image -->
          <div class="relative w-full max-w-md mx-auto p-2">
            <!-- Base green card with text -->
            <div class="bg-green-800 text-white p-6 w-3/5 h-64 relative z-10">
              <h2 class="text-xl font-bold mb-1">专家问答</h2>
              <p class="text-xs text-gray-200">Expert Q&A</p>
            </div>

            <!-- Overlapping soybeans image with button -->
            <div class="absolute top-12 right-0 w-3/5 z-20">
              <div class="relative">
                <!-- Soybeans image -->
                <img
                  src="../assets/img/huangdou.png"
                  alt="Soybeans"
                  class="w-54 h-64 object-cover"
                />

                <!-- Green button at bottom -->
                <div
                  class="absolute w-3/5 bottom-10 left-10 right-8 bg-green-800 text-white py-2 text-center" @click="expertAssis('qa')"
                >
                  <span class="text-sm">查看更多</span>
                </div>
              </div>
            </div>
          </div>

          <!-- Right side - FAQ Items -->
          <div class="w-full md:w-3/4 md:pl-2">
            <div v-for="(faq, index) in quesAnsThree" :key="index" class="mb-8">
              <div class="flex items-start">
                <div class="mr-6">
                  <div class="text-4xl font-bold text-green-700">Q&A</div>
                  <div class="text-xs text-gray-500">专家：{{ faq?.expertName || '未知' }}</div>
                </div>
                <div>
                  <h3 class="text-lg font-bold text-green-700 mb-2">
                    {{ faq?.question || '问题标题' }}
                  </h3>
                  <p class="text-sm text-gray-600 leading-relaxed">
                    {{ faq?.answer || '暂无回答' }}
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="flex justify-center">
          <button
            class="px-6 py-2 bg-white text-gray-800 border border-gray-200 rounded-full text-sm hover:bg-gray-50 transition-colors" @click="expertAssis('qa')"
          >
            查看更多
          </button>
        </div>
      </div>
    </div>
  </section>

  <section>
    <div class="container mx-auto px-4">
      <!-- Specialists Section -->
      <div class="mb-16 mx-auto">
        <h2 class="text-3xl font-bold text-center mb-1">农业知识</h2>
        <h5 class="text-l font-bold text-center mb-8 text-gray-400">
          Agricultural Knowledge
        </h5>

        <div v-if="knowledgeTwo.length > 0">
          <div class="flex flex-col md:flex-row items-start mb-12">
            <div class="w-full md:w-1/5 mb-4 md:mb-0 flex justify-center items-center">
              <img :src="pic01" alt="knowledge" class="rounded-md w-64 h-74 object-cover" />
            </div>
            <div class="w-full md:w-4/5 md:px-8 flex flex-col h-[250px]">
              <div class="flex-grow">
                <h3 class="text-lg font-bold mb-4 border-l-4 border-green-700 pl-3">
                  {{ knowledgeTwo[0].title }}
                </h3>
                <p class="text-l text-gray-600 leading-relaxed">
                  {{ knowledgeTwo[0].content }}
                </p>
              </div>
              <button class="bg-green-700 text-white py-2 px-6 rounded-md text-sm hover:bg-green-800 transition-colors self-start" @click="articleDetail(knowledgeTwo[0])">
                查看详情
              </button>
            </div>
          </div>
          <div class="flex flex-col md:flex-row-reverse items-start">
            <div class="w-full md:w-1/5 mb-4 md:mb-0 flex justify-center items-center">
              <img :src="pic02" alt="knowledge" class="rounded-md w-64 h-74 object-cover" />
            </div>
            <div class="w-full md:w-4/5 md:px-8 flex flex-col h-[250px]">
              <div class="flex-grow">
                <h3 class="text-lg font-bold mb-4 border-l-4 border-green-700 pl-3">
                  {{ knowledgeTwo[1].title }}
                </h3>
                <p class="text-l text-gray-600 leading-relaxed mb-6">
                  {{ knowledgeTwo[1].content }}
                </p>
              </div>
              <button class="bg-green-700 text-white py-2 px-6 rounded-md text-sm hover:bg-green-800 transition-colors self-start" @click="articleDetail(knowledgeTwo[1])">
                查看详情
              </button>
            </div>
          </div>
        </div>
        <div v-else class="text-center py-8">
          <p class="text-gray-500">暂无农业知识内容</p>
        </div>
        <div class="flex justify-center">
          <button
            class="px-6 py-2 bg-white text-gray-800 border border-gray-200 rounded-full text-sm hover:bg-gray-50 transition-colors" @click="expertAssis('knowledge')" 
          >
            查看更多
          </button>
        </div>
      </div>
    </div>
  </section>
  <section>
    <div class="container mx-auto px-4">
      <!-- Specialists Section -->
      <div class="mb-16 mx-auto">
        <h2 class="text-3xl font-bold text-center mb-1">助农电商</h2>
        <h5 class="text-l font-bold text-center mb-8 text-gray-400">
          Agricultural E-commerce
        </h5>
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-6 mb-8">
          <!-- Product Cards (using v-for) -->
          <div
            v-for="(product, index) in goodsFive"
            :key="index"
            class="border border-gray-200 rounded-md overflow-hidden shadow-sm hover:shadow-md transition-shadow"
            @click="goodsDetail(product)"
          >
            <div class="w-full">
              <img
                :src="product?.picture"
                class="w-full h-64 object-cover"
              />
            </div>
            <div class="p-4">
              <p class="text-m text-gray-700 mb-2 line-clamp-2 h-5">
                {{ product?.title || '商品名称' }}
              </p>
              <p class="text-sm text-gray-700 mb-2 line-clamp-2 h-10">
                {{ product?.content || '商品描述' }}
              </p>
              <p class="text-red-500 font-medium">¥{{ product?.price || '0' }}</p>
            </div>
          </div>
        </div>
        <div class="flex justify-center">
          <button
            class="px-6 py-2 bg-white text-gray-800 border border-gray-200 rounded-full text-sm hover:bg-gray-50 transition-colors" @click="proMore()"
          >
            查看更多
          </button>
        </div>
      </div>
    </div>
  </section>
  <ExpertConsultDialog ref="consultDialog" :expertUserName="expertUserName"/>
<ExpertReserveDialog ref="reserveDialog" :expertUserName="expertUserName"/>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { UserIcon } from "lucide-vue-next";
import { defineComponent } from "vue";
import { apiClient } from "../api/apiService.js";
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { useStore } from "vuex";
import ExpertConsultDialog from "../components/ExpertConsultDialog.vue";
import ExpertReserveDialog from "../components/ExpertReserveDialog.vue";
const store = useStore();
defineComponent({
  name: "HomePage",
});

const consultDialog = ref();
const expertUserName = ref('');
const openConsultDialog = (expert) => {
  expertUserName.value = expert.userName;
  consultDialog.value.open();
};

const reserveDialog = ref();
const openReserveDialog = (expert) => {
  expertUserName.value = expert.userName;
  reserveDialog.value.open();
};

// Import images
import title01 from "@/assets/img/title01.jpg";
import banner01 from "@/assets/img/banner01.png";
import banner02 from "@/assets/img/banner02.png";
import banner03 from "@/assets/img/banner03.png";
import bank01 from "@/assets/img/zgnyyh.png";
import bank02 from "@/assets/img/zggsyh.png";
import bank03 from "@/assets/img/zgjsyh.png";
import bank05 from "@/assets/img/hxyh.png";
import expert01 from "@/assets/img/expert01.png";
import expert02 from "@/assets/img/expert02.png";
import expert03 from "@/assets/img/expert03.png";
import expert04 from "@/assets/img/expert04.png";
import expert05 from "@/assets/img/expert05.png";
import expert06 from "@/assets/img/expert06.png";
import expert07 from "@/assets/img/expert07.png";
import expert08 from "@/assets/img/expert08.png";
import expert09 from "@/assets/img/expert09.png";
import expert10 from "@/assets/img/expert10.png";
import expert11 from "@/assets/img/expert11.png";
import riceImg from "@/assets/img/rice.png";
import cornImg from "@/assets/img/jixinguo.jpg";
import teaImg from "@/assets/img/tea.png";
import watermelonImg from "@/assets/img/watermelon_20250513154759.png";
import yangcongImg from "@/assets/img/yangcong_20250513154843.png";
import zheergenImg from "@/assets/img/zheergen_20250513155020.png";
import renshenguoImg from "@/assets/img/renshenguo.jpg";

const router = useRouter();

const expertAvatars = [
  expert01, expert02, expert03, expert04, expert05,
  expert06, expert07, expert08, expert09, expert10, expert11
];

const getExpertAvatar = (userName) => {
  let hash = 0;
  for (let i = 0; i < (userName || '').length; i++) {
    hash = userName.charCodeAt(i) + ((hash << 5) - hash);
  }
  const index = Math.abs(hash) % expertAvatars.length;
  return expertAvatars[index] || expert01;
};

// Carousel data
const currentSlide = ref(0);
const carouselImages = ref([
  { src: title01, alt: "Green agricultural fields" },
  { src: banner01, alt: "Agriculture banner" },
]);

const bankFour = ref([]);
const expertFive = ref([]);
const quesAnsThree = ref([]);
const knowledgeTwo = ref([]);
const pic01 = ref('');
const pic02 = ref('');
const displayKnowledge = computed(() => {
  return knowledgeTwo.value.length > 0 ? knowledgeTwo.value : [];
});

const goodsFive = ref([]);

// Auto-rotate carousel
onMounted(async () => {
  await new Promise(resolve => setTimeout(resolve, 300));
  const items = document.querySelectorAll(".carousel-item");
  const dots = document.querySelectorAll(".carousel-dot");
  let currentIndex = 0;

  const showSlide = (index) => {
    items.forEach((item) => (item.style.opacity = "0"));
    dots.forEach((dot) => (dot.style.opacity = "0.5"));
    if (items[index]) items[index].style.opacity = "1";
    if (dots[index]) dots[index].style.opacity = "1";
  };

  const nextSlide = () => {
    currentIndex = (currentIndex + 1) % items.length;
    showSlide(currentIndex);
  };

  showSlide(0);
  setInterval(nextSlide, 2000);

  dots.forEach((dot, index) => {
    dot.addEventListener("click", () => {
      currentIndex = index;
      showSlide(currentIndex);
    });
  });

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
      id: "1005",
      icon: bank05,
    },
  ]);

//编辑展示的银行产品信息
  try {
    const bankdata = await selectBank();
    if (bankdata && bankdata.length > 0) {
      const tempFirstFour = bankdata.slice(0, 4);
      let bankMsg = '';
      tempFirstFour.forEach((item) => {
        imgArray.value.map((item1) => {
          if (item.bankId == item1.id) {
            item['icon'] = item1.icon;
          }
        });
        bankMsg = item['introduce']?.split('，')[0]?.replace(/"/g, '') || '';
        item['name'] = bankMsg;
      });
      bankFour.value = tempFirstFour;
    } else {
      throw new Error('No data');
    }
  } catch (e) {
    console.warn('银行数据加载失败，使用mock数据', e);
    bankFour.value = [
      {
        bankId: '1001',
        icon: bank01,
        name: '青易贷',
        money: '100000',
        introduce: '青易贷，在线申请，期限长还款方式灵活',
        rate: '4.5%',
        repayment: '等额本息',
      },
      {
        bankId: '1002',
        icon: bank02,
        name: '农业贷',
        money: '100000',
        introduce: '农业贷，在线申请，期限长还款方式灵活',
        rate: '4.8%',
        repayment: '等额本息',
      },
      {
        bankId: '1003',
        icon: bank01,
        name: '助农贷',
        money: '150000',
        introduce: '助农贷，在线申请，期限长还款方式灵活',
        rate: '4.3%',
        repayment: '等额本息',
      },
      {
        bankId: '1005',
        icon: bank05,
        name: '阳光贷',
        money: '100000',
        introduce: '阳光贷，在线申请，期限长还款方式灵活',
        rate: '5.0%',
        repayment: '等额本息',
      },
    ];
  }

//编辑展示的专家信息
  try {
    const expertdata = await selectExpert();
    if (expertdata && expertdata.length > 0) {
      const tempExpertFive = expertdata.slice(0, 5);
      tempExpertFive.forEach(expert => {
        expert.avatar = getExpertAvatar(expert.userName);
      });
      expertFive.value = tempExpertFive;
    } else {
      throw new Error('No data');
    }
  } catch (e) {
    console.warn('专家数据加载失败，使用mock数据', e);
    expertFive.value = [
      {
        id: 1,
        realName: '周强',
        profession: '农业经济学',
        belong: '山东农业大学',
        phone: '13592312577',
        position: '中级工程师',
        avatar: expert01,
      },
      {
        id: 2,
        realName: '吴昊',
        profession: '农业经济管理学',
        belong: '青岛农业大学',
        phone: '13592312576',
        position: '高级工程师',
        avatar: expert02,
      },
      {
        id: 3,
        realName: '李荣荣',
        profession: '农学',
        belong: '山东农业大学',
        phone: '13592312574',
        position: '中级工程师',
        avatar: expert03,
      },
      {
        id: 4,
        realName: '王玉婷',
        profession: '生物化学',
        belong: '青岛农业大学',
        phone: '13792312520',
        position: '中级工程师',
        avatar: expert04,
      },
      {
        id: 5,
        realName: '刘芳',
        profession: '农业经济管理学',
        belong: '山东农业大学',
        phone: '13792312549',
        position: '高级工程师',
        avatar: expert05,
      },
    ];
  }

//编辑展示的问答信息
  try {
    const questiondata = await selectQuesAns();
    if (questiondata && questiondata.length > 0) {
      const tempquesAnsThree = questiondata.slice(0, 3);
      quesAnsThree.value = tempquesAnsThree;
    } else {
      throw new Error('No data');
    }
  } catch (e) {
    console.warn('问答数据加载失败，使用mock数据', e);
    quesAnsThree.value = [
      {
        id: 1,
        expertName: '周强',
        question: '新疆哈密瓜甜度是否跟日照有关？',
        answer: '哈密瓜甜度跟充足的阳光和较大的昼夜温差有关。白天可以充分发挥光合作用，而夜晚的呼吸消耗较小，有利于糖分沉淀。',
      },
      {
        id: 2,
        expertName: '吴昊',
        question: '苹果树种植多久浇一次水比较好？',
        answer: '新栽苗木：定植后立即浇水，以后每隔5-7天浇1次，连浇3次，促进根系恢复。生长期：土壤干燥时（表土下5cm干燥）每周浇1次，保持土壤湿润但不积水。',
      },
      {
        id: 3,
        expertName: '李荣荣',
        question: '番茄种植时为什么会出现裂果？如何预防？',
        answer: '由于水分波动大（久旱后突然大量浇水或降雨），钙元素缺乏导致果皮韧性差。预防措施：保持土壤湿度稳定，避免忽干忽湿；补充钙肥。',
      },
    ];
  }

//编辑展示的农业知识信息
  try {
    const knowledgedata = await selectKnowledge();
    console.log('knowledgedata:', knowledgedata);
    console.log('knowledgedata length:', knowledgedata?.length);
    if (knowledgedata && knowledgedata.length > 0) {
      const tempquesKnowledge = knowledgedata.slice(0, 2);
      knowledgeTwo.value = [...tempquesKnowledge];
      console.log('knowledgeTwo set with real data:', knowledgeTwo.value);
      console.log('knowledgeTwo[0].knowledgeId:', knowledgeTwo.value[0]?.knowledgeId);
      console.log('knowledgeTwo[0].id:', knowledgeTwo.value[0]?.id);
      pic01.value = '/api/file/' + (tempquesKnowledge[0]?.picPath || '');
      pic02.value = '/api/file/' + (tempquesKnowledge[1]?.picPath || '');
      console.log('knowledgeTwo.length:', knowledgeTwo.value.length);
    } else {
      throw new Error('No data');
    }
  } catch (e) {
    console.warn('农业知识数据加载失败，使用mock数据', e);
    knowledgeTwo.value = [
      {
        id: 1,
        title: '水稻种植全过程',
        content: '水稻种植第一步：晒种 水稻种植第二步：选种 水稻种植第三步：整秧板 水稻种植第四步：播种 水稻种植第五步：插秧 水稻种植第六步：灌溉 水稻种植第七步：田间管理（施肥）',
        picPath: riceImg,
      },
      {
        id: 2,
        title: '玉米种植过程详解',
        content: '玉米一直都被誉为长寿食品，含有丰富的蛋白质、脂肪、维生素、微量元素、纤维素等，具有开发高营养、高生物学功能食品的巨大潜力。但由于其遗传性较为复杂，变异种类丰富，在常规的育种过程中存在着周期过长、变异系数过大、影响子代生长发育的缺点，而现代生物育种技术不但克服了上述缺点和不足，同时也提高了育种速度和质量。',
        picPath: cornImg,
      },
    ];
    pic01.value = riceImg;
    pic02.value = cornImg;
  }

//编辑展示的商品信息
  try {
    const goodsdata = await selectGoods();
    if (goodsdata && goodsdata.length > 0) {
      const tempgoodsdata = goodsdata.slice(0, 5);
      goodsFive.value = tempgoodsdata;
    } else {
      throw new Error('No data');
    }
  } catch (e) {
    console.warn('商品数据加载失败，使用mock数据', e);
    goodsFive.value = [
      {
        id: 1,
        title: '优质茶叶',
        content: '精选高山云雾茶，香气浓郁，口感醇厚',
        price: '128.00',
        picture: teaImg,
      },
      {
        id: 2,
        title: '新疆西瓜',
        content: '皮薄肉甜，水分充足，夏日解暑佳品',
        price: '29.90',
        picture: watermelonImg,
      },
      {
        id: 3,
        title: '新鲜洋葱',
        content: '农家自种，脆嫩多汁，营养丰富',
        price: '5.90',
        picture: yangcongImg,
      },
      {
        id: 4,
        title: '贵州折耳根',
        content: '特产鱼腥草，清热解毒，凉拌必备',
        price: '15.80',
        picture: zheergenImg,
      },
      {
        id: 5,
        title: '人参果',
        content: '稀有水果，清甜可口，富含营养',
        price: '38.00',
        picture: renshenguoImg,
      },
    ];
  }
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
    if (response && response.flag === true) {
      return response.data || [];
    }
    return [];
  } catch (error) {
    console.error("请求失败", error);
    throw error;
  }
};

const selectExpert = async () => {
  try {
    const response = await apiClient.get("/question/findExpertUser/1");
    console.log("请求成功", response);
    if (response && response.flag === true && response.data && response.data.list) {
      return response.data.list;
    }
    return [];
  } catch (error) {
    console.error("请求失败", error);
    throw error;
  }
};

const selectQuesAns = async () => {
  try {
    const response = await apiClient.get("/question/findAllQues/1");
    console.log("请求成功", response);
    if (response && response.flag === true && response.data && response.data.list) {
      return response.data.list;
    }
    return [];
  } catch (error) {
    console.error("请求失败", error);
    throw error;
  }
};

const selectKnowledge = async () => {
  try {
    const response = await apiClient.get("/knowledge/1");
    console.log("knowledge response", response);
    console.log("knowledge data", response.data);
    console.log("knowledge data.list", response.data?.list);
    if (response && response.flag === true) {
      if (Array.isArray(response.data)) {
        return response.data;
      } else if (response.data && response.data.list) {
        console.log("returning list", response.data.list);
        return response.data.list;
      }
    }
    console.log("returning empty array");
    return [];
  } catch (error) {
    console.error("knowledge请求失败", error);
    throw error;
  }
};

const selectGoods = async () => {
  try {
    const response = await apiClient.get("/order/goods/1");
    console.log("请求成功", response);
    if (response && response.flag === true && response.data && response.data.list) {
      return response.data.list;
    }
    return [];
  } catch (error) {
    console.error("请求失败", error);
    throw error;
  }
};
//点击金融产品更多，跳转
const finanProMore = () => {
  router.push(`/home/financinglist`).catch((err) => err);
};
//点击某金融产品，跳转
const finanProDetail = (item)=> {
  router.push(`/home/financing?bankId=${item.bankId}&bankname=${item.name}&bankintro=${item.introduce}&bankmoney=${item.money}&bankrate=${item.rate}&bankrepay=${item.repayment}`)
};

const aimatch = ()=> {
  if(!window.localStorage.token){
    ElMessage.error('您未登录，请先登录');
  } else {
    router.push(`/home/aimatch`)
  }
};

//点击农业知识、专家、问答，跳转
const expertAssis = (item)=> {
  router.push(`/home/expertassist?activeSection=${item}`)
};

//点击某知识，跳转至知识详情
const articleDetail = (item)=> {
  const id = item.knowledgeId || item.id;
  console.log("articleDetail knowledgeId:", id);
  router.push(`/home/AgriKnlg?knowledgeId=${id}&picPath=${item.picPath}&title=${item.title}&content=${item.content}&ownName=${item.ownName}&updateTime=${item.updateTime}`)
};

//点击产品更多，跳转
const proMore = () => {
  router.push(`/home/agripro`).catch((err) => err);
};
//点击产品详情，跳转
const goodsDetail = (item)=> {
  router.push(`/goodsDetail?orderId=${item.orderId}&title=${item.title}&price=${item.price}&content=${item.content}&picture=${item.picture}&updateTime=${item.updateTime}`)

};

const goToFinancing = () => {
  router.push('/home/bannerarticle?id=1').catch((err) => err);
};

const goToExpert = () => {
  router.push('/home/bannerarticle?id=2').catch((err) => err);
};

const goToAgriPro = () => {
  router.push('/home/bannerarticle?id=3').catch((err) => err);
};

</script>