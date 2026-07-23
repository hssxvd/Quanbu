<template>
  <!-- 搜索栏 -->
  <div class="container mx-auto px-4 py-4">
    <div class="flex items-center justify-center">
      <input
        v-model="searchKey"
        type="text"
        placeholder="输入关键词搜索"
        class="border border-gray-300 rounded-l px-4 py-2 w-2/3 focus:outline-none"
      />
      <button
        class="bg-[#007029] text-white px-4 py-2 rounded-r flex items-center" @click="searchGoods()"
      >
        <search-icon class="w-4 h-4 mr-1" />
        搜索
      </button>
    </div>
  </div>
  <!-- 产品列表 -->
  <div class="container mx-auto px-4 py-4">
    <div
      class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4"
    >
      <div
        v-for="(product, index) in goodsData"
        :key="index"
        class="border border-gray-200 rounded overflow-hidden mx-4"
        @click="goodsDetail(product)"
      >
        <div class="relative">
          <img
            :src="getImageUrl(product.picture)"
            class="w-full aspect-square object-cover"
          />
        </div>
        <div class="p-3">
          <div class=" text-l text-gray-700 line-clamp-2 mb-2">{{ product.title }}</div>
          <p class="text-xs text-gray-700 line-clamp-2">
            {{ product.content }}
          </p>
          <p class="text-gray-500 text-xs mt-1">卖家：{{ product.ownName || '未知' }}</p>
          <p class="text-red-500 font-bold mt-1">¥{{ product.price }}</p>
        </div>
      </div>
    </div>
  </div>

  <!-- 分页 -->
  <div class="flex justify-center items-center mt-8 text-sm">
    <Pagination
      :current-page="pagination.currentPage"
      :page-size="pagination.pageSize"
      :total="pagination.total"
      @update:current-page="setCurrentPage"
      @update:page-size="setPageSize"
      @page-change="loadData"
      class="mt-6"
    />
  </div>
</template>

<script setup>
import { ref } from "vue";
import {
  ShoppingCartIcon,
  UserIcon,
  SearchIcon,
  PlusIcon,
} from "lucide-vue-next";
import { onMounted } from "vue";
import { defineComponent } from "vue";
import { apiClient } from "../api/apiService.js";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import Pagination from "../components/Pagination.vue";

const router = useRouter();
const store = useStore();

const getImageUrl = (picture) => {
  if (!picture) {
    return "/src/assets/img/rice.png";
  }
  if (picture.startsWith("http")) {
    return picture;
  }
  return store.state.imgShowRoad + "/file/order/" + picture;
};

// 检索关键字内容
const searchKey = ref("");
const searchFlag = ref(0);

// 分页状态
const pagination = ref({
  currentPage: 1,
  pageSize: 28,
  total: 0,
});

// 表格数据
const goodsData = ref([]);

const selectData = async ({ page }) => {
  try {
    const url1 = `/order/public/goods/${page}`;
    const url2 = `/order/public/searchGoodsByKeys/${searchKey.value}/${page}`;
    const url = searchFlag.value == 1 && searchKey.value ? url2 : url1;
    
    const response = await apiClient.get(url);
    console.log("请求成功", response);
    return response;
  } catch (error) {
    console.error("请求失败", error);
    throw error;
  }
};

const loadData = async ({ page }) => {
  try {
    const res = await selectData({ page });
    console.log("loadData 接收到的响应:", res);
    
    if (res?.flag && res?.data) {
      if (res.data.list) {
        goodsData.value = res.data.list;
        pagination.value.total = res.data.total;
      } else if (Array.isArray(res.data)) {
        goodsData.value = res.data;
        pagination.value.total = res.data.length;
      }
    }
    
    console.log("goodsData:", goodsData.value);
    console.log("pagination.total:", pagination.value.total);
  } catch (error) {
    console.error("数据加载失败:", error);
  }
};

// 更新当前页
const setCurrentPage = (page) => {
  pagination.value.currentPage = page;
};

// 更新每页条数
const setPageSize = (size) => {
  pagination.value.pageSize = size;
};

// 初始化加载
onMounted(() => {
  loadData({
    page: pagination.value.currentPage,
  });
});

//点击检索
const searchGoods = ()=> {
  pagination.value.currentPage = 1;
  searchFlag.value = 1;
  loadData({
    page: pagination.value.currentPage,
  });
};


const goodsDetail = (item)=> {
  router.push(`/home/goodsDetail?orderId=${item.orderId}&title=${item.title}&price=${item.price}&content=${item.content}&picture=${item.picture}&updateTime=${item.updateTime}&ownName=${item.ownName || ''}`)

};

</script>

<style scoped>
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>