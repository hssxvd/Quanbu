<template>
<section class="py-12 bg-white border-t-2 border-gray-300">
  <div class="w-2/3 mx-auto p-1 ">
          <!-- Back Button -->
      <div class="mb-6">
        <button class="flex items-center text-gray-500 hover:text-gray-700" @click="goBack">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            class="h-4 w-4 mr-1"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M15 19l-7-7 7-7"
            />
          </svg>
          返回
        </button>
      </div>
    <div class="flex flex-col md:flex-row justify-center gap-8 w-full">
      <!-- 商品图片区域 -->
      <div class="w-full md:w-3/5 ml-auto">
        <div class="rounded-lg overflow-hidden">
          <img :src="getImageUrl(picture)" class="w-2/3 h-auto object-cover ml-auto" />
        </div>
      </div>

      <!-- 商品信息区域 -->
      <div class="w-full md:w-2/5">
        <h2 class="text-xl font-bold mb-2">{{ title}}</h2>

        <!-- 价格区域 -->
        <div class="mb-6">
          <span class="text-[#007029] text-3xl font-bold">¥{{ totalPrice }}</span>
          <span v-if="quantity > 1" class="text-gray-500 text-sm ml-2">
            (¥{{ currentPrice }} × {{ quantity }})
          </span>
        </div>

        <!-- 发布信息 -->
        <div class="flex flex-wrap gap-x-6 gap-y-2 text-sm text-gray-500 mb-6">
          <div>发布时间: {{ updateTime }}</div>
          <div>卖家: {{ ownName || '未知' }}</div>
        </div>
        <div v-if="isOwnProduct" class="mb-4 text-sm text-orange-600 bg-orange-50 px-3 py-2 rounded">
          这是您自己发布的商品，无法购买
        </div>

        <!-- 规格选择 -->
        <div v-if="specList.length > 0" class="mb-6">
          <h3 class="font-medium mb-3">规格</h3>
          <div class="flex flex-wrap gap-2">
            <button
              v-for="spec in specList"
              :key="spec.specId"
              @click="selectSpec(spec)"
              :class="[
                'px-4 py-2 border rounded-md text-sm transition-colors',
                selectedSpec && selectedSpec.specId === spec.specId
                  ? 'border-[#007029] text-[#007029] bg-[#f0f9f2]'
                  : 'border-gray-300 text-gray-700 hover:border-[#007029]',
                (spec.specStock != null && spec.specStock <= 0) ? 'opacity-50 cursor-not-allowed' : ''
              ]"
              :disabled="spec.specStock != null && spec.specStock <= 0"
            >
              {{ spec.specName }}
              <span class="text-xs ml-1">¥{{ spec.specPrice }}</span>
              <span v-if="spec.specStock != null && spec.specStock <= 0" class="text-xs text-red-500 ml-1">(无货)</span>
            </button>
          </div>
          <div v-if="selectedSpec" class="text-xs text-gray-500 mt-2">
            库存: {{ selectedSpec.specStock }}件
          </div>
        </div>
        <div v-else class="mb-6 text-sm text-gray-400">
          该商品暂无规格选项
        </div>

        <!-- 互动按钮 -->
        <div class="flex items-center gap-6 mb-8">
          <button class="flex items-center gap-1 text-gray-600 hover:text-[#007029]">
            <ThumbsUpIcon class="w-5 h-5" />
            <span>点赞</span>
          </button>
          <button class="flex items-center gap-1 text-gray-600 hover:text-[#007029]">
            <StarIcon class="w-5 h-5" />
            <span>收藏</span>
          </button>
          <button class="flex items-center gap-1 text-gray-600 hover:text-[#007029]">
            <MessageSquareIcon class="w-5 h-5" />
            <span>评论</span>
          </button>
        </div>

        <!-- 数量选择 -->
        <div class="mb-8">
          <h3 class="font-medium mb-2">数量</h3>
          <div class="flex items-center">
            <button @click="decreaseQuantity" 
                    class="w-10 h-10 flex items-center justify-center border border-gray-300 rounded-l-md hover:bg-gray-100">
              <MinusIcon class="w-4 h-4" />
            </button>
            <input type="number" v-model="quantity" min="1" 
                   class="w-16 h-10 border-t border-b border-gray-300 text-center focus:outline-none" />
            <button @click="increaseQuantity" 
                    class="w-10 h-10 flex items-center justify-center border border-gray-300 rounded-r-md hover:bg-gray-100">
              <PlusIcon class="w-4 h-4" />
            </button>
          </div>
        </div>

        <!-- 购买按钮 -->
        <div class="flex gap-4">
          <button
            class="px-8 py-3 bg-[#007029] text-white rounded-md hover:bg-[#005d23] transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
            @click="buyNow()"
            :disabled="isOwnProduct"
          >
            立即购买
          </button>
          <button
            class="px-8 py-3 border border-[#007029] text-[#007029] rounded-md hover:bg-[#f0f9f2] transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
            @click="addShopCart()"
            :disabled="isOwnProduct"
          >
            加入购物车
          </button>
        </div>
      </div>
    </div>

    <!-- 商品详情 -->
    <div class="mt-12">
      <div class="border-b border-gray-200">
        <div class="flex">
          <button 
            v-for="(tab, index) in tabs" 
            :key="index"
            @click="activeTab = tab.id"
            :class="[
              'px-6 py-3 font-medium',
              activeTab === tab.id 
                ? 'text-[#007029] border-b-2 border-[#007029]' 
                : 'text-gray-500 hover:text-[#007029]'
            ]"
          >
            {{ tab.name }}
          </button>
        </div>
      </div>
      
      <div class="py-6">
        <div v-if="activeTab === 'details'" class="prose max-w-none">
          
          <p>{{ content }}</p>
          <div class="mt-4 flex flex-col md:flex-row justify-center">
            <img :src="getImageUrl(picture)" class="w-2/3 h-auto rounded-lg" />
          </div>
        </div>
        
        <div v-if="activeTab === 'specs'" class="prose max-w-none">
          <h3>规格参数</h3>
          <table v-if="specList.length > 0" class="w-full border-collapse">
            <tbody>
              <tr v-for="(spec, index) in specList" :key="spec.specId"
                  :class="index % 2 === 0 ? 'bg-gray-50' : ''">
                <td class="py-3 px-4 border border-gray-200 font-medium">{{ spec.specName }}</td>
                <td class="py-3 px-4 border border-gray-200">¥{{ spec.specPrice }} / 库存{{ spec.specStock }}件</td>
              </tr>
            </tbody>
          </table>
          <div v-else class="text-gray-500 text-center py-8">
            暂无规格参数
          </div>
        </div>

        <div v-if="activeTab === 'comments'" class="prose max-w-none">
          <h3>用户评价</h3>
          <div v-if="comments.length > 0">
            <div v-for="(comment, index) in comments" :key="index"
                 class="border-b border-gray-100 py-4">
              <div class="flex items-center gap-2 mb-2">
                <div class="w-8 h-8 rounded-full bg-gray-200 overflow-hidden flex items-center justify-center text-xs text-gray-500">
                  {{ comment.buyerName ? comment.buyerName.charAt(0) : '用' }}
                </div>
                <div class="font-medium">{{ comment.buyerName || '匿名用户' }}</div>
                <div class="text-sm text-yellow-500">{{ '★'.repeat(comment.rating || 5) }}</div>
                <div class="text-sm text-gray-500">{{ formatDate(comment.createTime) }}</div>
              </div>
              <div>{{ comment.content }}</div>
            </div>
          </div>
          <div v-else class="text-gray-500 text-center py-8">
            暂无评价
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
</template>

<script setup>
import { ThumbsUpIcon, StarIcon, MessageSquareIcon, MinusIcon, PlusIcon } from 'lucide-vue-next';
import { useRoute, useRouter } from "vue-router";
import { ref, onMounted, computed } from "vue";
import { apiClient } from "../api/apiService.js";
import { ElMessage, ElMessageBox } from 'element-plus';
import { useStore } from 'vuex';
import { getImageUrl } from "../utils/imageUtils.js";

const store = useStore();

const route = useRoute();
const router = useRouter();

let orderId = ref();
let title = ref();
let price = ref();
let content = ref();
let picture = ref();
let updateTime = ref();
let ownName = ref('');

// 规格相关
const specList = ref([]);
const selectedSpec = ref(null);
const currentPrice = ref(0);
const originalPrice = ref(0);
const comments = ref([]);

onMounted(async () => {
  orderId.value = route.query.orderId;
  title.value = route.query.title;
  price.value = route.query.price;
  content.value = route.query.content;
  picture.value = route.query.picture;
  ownName.value = route.query.ownName || '';
  if (route.query.updateTime) {
    updateTime.value = route.query.updateTime.split('T')[0];
  }
  currentPrice.value = Number(price.value) || 0;
  originalPrice.value = (Number(price.value) || 0) * 1.5;

  // 加载商品规格
  if (orderId.value) {
    try {
      const res = await apiClient.get(`/spec/list/${orderId.value}`);
      if (res.flag && res.data) {
        specList.value = res.data;
        // 默认选中第一个有库存的规格
        const available = specList.value.find(s => s.specStock == null || s.specStock > 0);
        if (available) {
          selectedSpec.value = available;
          currentPrice.value = Number(available.specPrice);
        }
      }
    } catch (err) {
      console.error("加载规格失败", err);
    }

    // 加载商品评价
    try {
      const res = await apiClient.get(`/comment/order/${orderId.value}`);
      if (res.flag && res.data) {
        comments.value = res.data;
      }
    } catch (err) {
      console.error("加载评价失败", err);
    }
  }
});

const selectSpec = (spec) => {
  if (spec.specStock != null && spec.specStock <= 0) return;
  selectedSpec.value = spec;
  currentPrice.value = Number(spec.specPrice);
};

const isOwnProduct = computed(() => {
  const currentUser = store.state.loginUsername;
  return ownName.value && currentUser && ownName.value === currentUser;
});

const totalPrice = computed(() => {
  return (currentPrice.value * quantity.value).toFixed(2);
});


// 商品数据
const product = {
  id: 1,
  name: '西瓜',
  description: '云南正宗8424薄皮鲜嫩瓜无籽甜冰淇淋大西瓜水果',
  fullDescription: '云南正宗8424薄皮鲜嫩瓜无籽甜冰淇淋大西瓜水果，果肉鲜红，口感甜脆多汁。采用有机种植方式，无农药残留，健康安全。夏季解暑佳品，富含多种维生素和矿物质，既能解渴又能补充营养。',
  price: 5.00,
  originalPrice: 8.00,
  publishDate: new Date('2022-09-01'),
  updateDate: new Date('2022-09-01'),
  image: 'https://hebbkx1anhila5yf.public.blob.vercel-storage.com/image-ciLgtMxW2BaxTDumsM2ug1huIrJPQq.png',
  detailImage: 'https://hebbkx1anhila5yf.public.blob.vercel-storage.com/image-ciLgtMxW2BaxTDumsM2ug1huIrJPQq.png',
  specifications: ['2.5kg', '5kg', '8kg', '整箱'],
  specTable: [
    { name: '产地', value: '云南' },
    { name: '品种', value: '8424' },
    { name: '重量', value: '约2.5kg-10kg' },
    { name: '保存方式', value: '常温/冷藏' },
    { name: '保质期', value: '7天' }
  ],
  comments: [
    { 
      username: '匿名用户1', 
      avatar: '', 
      date: new Date('2022-09-05'), 
      content: '非常好，而且很新鲜，物流也很快，下次还会购买！' 
    },
    { 
      username: '匿名用户2', 
      avatar: '', 
      date: new Date('2022-09-03'), 
      content: '质量好，口感不错，就是觉得价格稍微有点贵。' 
    }
  ]
};

// 状态管理
const quantity = ref(1);
const activeTab = ref('details');

const tabs = [
  { id: 'details', name: '商品详情' },
  { id: 'specs', name: '规格参数' },
  { id: 'comments', name: '用户评价' }
];

// 方法
const increaseQuantity = () => {
  quantity.value++;
};

const decreaseQuantity = () => {
  if (quantity.value > 1) {
    quantity.value--;
  }
};

const formatDate = (date) => {
  if (!date) return '';
  const d = new Date(date);
  if (isNaN(d.getTime())) return '';
  return d.toISOString().split('T')[0];
};

const goBack = () => {
  // 返回时携带当前页面状态（需配合路由的state配置）
  router.back();
};

const addShopCart = async() => {
  if (!orderId.value) {
    ElMessage.error("商品信息异常，请刷新页面重试");
    return;
  }
  try {
    const response = await apiClient.post(`/cart/add/${orderId.value}`);
    if (response.flag) {
      ElMessage.success("已添加至购物车");
    } else {
      ElMessage.error(response.message || "添加购物车失败");
    }
  } catch (error) {
    console.error("添加购物车失败:", error);
    ElMessage.error("添加失败");
  }
};

const buyNow = async() => {
  if (!orderId.value) {
    ElMessage.error("商品信息异常，请刷新页面重试");
    return;
  }
  if (specList.value.length > 0 && !selectedSpec.value) {
    ElMessage.warning("请选择规格");
    return;
  }
  try {
    const params = {};
    if (selectedSpec.value) {
      params.specId = selectedSpec.value.specId;
    }
    const response = await apiClient.post(`/purchase/directBuy/${orderId.value}`, null, { params });
    if (response.flag) {
      ElMessage.success("购买成功！");
      await ElMessageBox.confirm(
        `恭喜！您已成功购买该商品。`,
        "购买成功",
        {
          confirmButtonText: "查看订单",
          cancelButtonText: "继续逛逛",
          type: "success",
          distinguishCancelAndClose: true,
        }
      ).then(() => {
        router.push("/personal/mybuy");
      }).catch(() => {});
    } else {
      ElMessage.error(response.message || "购买失败");
    }
  } catch (error) {
    console.error("购买失败:", error);
    ElMessage.error("购买失败");
  }
};

</script>

<style scoped>
/* 可以添加额外的样式 */
</style>