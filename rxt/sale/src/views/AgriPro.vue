<template>
  <div class="min-h-screen bg-gray-50">
    <section class="hero-section relative h-[400px] w-full">
      <div
        class="absolute inset-0 w-full h-full bg-cover bg-center"
        :style="{ backgroundImage: `url(${banner03})` }"
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
    </section>

    <section class="py-8 bg-white border-t-2 border-gray-300">
      <div class="container mx-auto px-4">
        <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
          <div class="text-center p-6 bg-green-50 rounded-lg">
            <div class="text-4xl mb-2">🌾</div>
            <h3 class="font-bold text-green-800 mb-1">优质农产品</h3>
            <p class="text-sm text-gray-600">精选各地特色农产品</p>
          </div>
          <div class="text-center p-6 bg-green-50 rounded-lg">
            <div class="text-4xl mb-2">🚚</div>
            <h3 class="font-bold text-green-800 mb-1">产地直发</h3>
            <p class="text-sm text-gray-600">从田间到餐桌的新鲜直达</p>
          </div>
          <div class="text-center p-6 bg-green-50 rounded-lg">
            <div class="text-4xl mb-2">🛡️</div>
            <h3 class="font-bold text-green-800 mb-1">品质保障</h3>
            <p class="text-sm text-gray-600">严格品控，安心购买</p>
          </div>
          <div class="text-center p-6 bg-green-50 rounded-lg">
            <div class="text-4xl mb-2">💰</div>
            <h3 class="font-bold text-green-800 mb-1">实惠价格</h3>
            <p class="text-sm text-gray-600">源头采购，价格透明</p>
          </div>
        </div>

        <div class="flex justify-between items-center mb-6">
          <div>
            <h2 class="text-2xl font-bold text-gray-800">农产品交易</h2>
            <p class="text-gray-500">为您提供优质的农产品选购服务</p>
          </div>
          <div class="flex gap-4">
            <input
              v-model="searchKeyword"
              type="text"
              placeholder="搜索农产品..."
              class="border border-gray-200 rounded-md px-4 py-2 focus:outline-none focus:border-green-800"
            />
            <button
              class="px-6 py-2 bg-green-800 text-white rounded-md hover:bg-green-900 transition-colors"
              @click="searchGoods"
            >
              搜索
            </button>
          </div>
        </div>

        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-6">
          <div
            v-for="(product, index) in goodsList"
            :key="index"
            class="border border-gray-200 rounded-md overflow-hidden shadow-sm hover:shadow-md transition-shadow cursor-pointer"
            @click="goodsDetail(product)"
          >
            <div class="w-full">
              <img
                :src="(product.picture && !product.picture.startsWith('http') ? '/api/file/' + product.picture : product.picture) || teaImg"
                class="w-full h-64 object-cover"
              />
            </div>
            <div class="p-4">
              <p class="text-m text-gray-700 mb-2 line-clamp-2 h-10">
                {{ product.title || '商品名称' }}
              </p>
              <p class="text-sm text-gray-600 mb-2 line-clamp-2 h-10">
                {{ product.content || '商品描述' }}
              </p>
              <p class="text-red-500 font-medium">¥{{ product.price || '0' }}</p>
            </div>
          </div>
        </div>

        <div v-if="goodsList.length === 0" class="text-center py-12">
          <p class="text-gray-500">暂无商品</p>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { apiClient } from "../api/apiService.js";
import banner03 from "@/assets/img/banner03.png";
import teaImg from "@/assets/img/tea.png";

const router = useRouter();
const goodsList = ref([]);
const searchKeyword = ref("");

const goodsDetail = (item) => {
  router.push(`/goodsDetail?orderId=${item.orderId}&title=${item.title}&price=${item.price}&content=${item.content}&picture=${item.picture}&updateTime=${item.updateTime}`);
};

const searchGoods = () => {
  if (!searchKeyword.value) {
    loadGoods();
    return;
  }
  goodsList.value = goodsList.value.filter(item => 
    item.title && item.title.includes(searchKeyword.value)
  );
};

const selectGoods = async () => {
  try {
    const response = await apiClient.get("/order/goods/1");
    if (response && response.flag === true && response.data && response.data.list) {
      return response.data.list;
    }
    return [];
  } catch (error) {
    console.error("请求失败", error);
    throw error;
  }
};

const loadGoods = async () => {
  try {
    const goodsdata = await selectGoods();
    if (goodsdata && goodsdata.length > 0) {
      goodsList.value = goodsdata;
    } else {
      goodsList.value = getMockGoods();
    }
  } catch (e) {
    console.warn("商品数据加载失败，使用mock数据", e);
    goodsList.value = getMockGoods();
  }
};

const getMockGoods = () => [
  {
    id: 1,
    title: "优质茶叶",
    content: "精选高山云雾茶，香气浓郁，口感醇厚",
    price: "128.00",
    picture: teaImg,
  },
  {
    id: 2,
    title: "新疆西瓜",
    content: "皮薄肉甜，水分充足，夏日解暑佳品",
    price: "29.90",
    picture: teaImg,
  },
  {
    id: 3,
    title: "新鲜洋葱",
    content: "农家自种，脆嫩多汁，营养丰富",
    price: "5.90",
    picture: teaImg,
  },
  {
    id: 4,
    title: "贵州折耳根",
    content: "特产鱼腥草，清热解毒，凉拌必备",
    price: "15.80",
    picture: teaImg,
  },
  {
    id: 5,
    title: "人参果",
    content: "稀有水果，清甜可口，富含营养",
    price: "38.00",
    picture: teaImg,
  },
];

onMounted(() => {
  loadGoods();
});
</script>
