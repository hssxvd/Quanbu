<template>
  <div class="mx-auto px-20">
    <div class="container mx-auto px-4 pb-4">
      <div class="flex items-center justify-center">
        <input
          type="text"
          v-model="searchKey"
          placeholder="输入关键词搜索"
          class="border border-gray-300 rounded-l px-4 py-2 w-2/3 focus:outline-none"
        />
        <button
          class="bg-[#007029] text-white mx-2 px-4 py-2 rounded-md flex items-center focus:outline-none"
          @click="searchGoods()"
        >
          搜索
        </button>
      </div>
    </div>

    <div class="space-y-2">
      <div class="space-y-2">
        <div class="grid gap-4 md:grid-cols-3">
          <div
            v-for="(item, index) in goodsData"
            :key="index"
            class="border border-gray-200 rounded-lg overflow-hidden hover:shadow-lg transition-shadow"
          >
            <div class="h-40 bg-gray-100">
              <img
                :src="getImageUrl(item.picture)"
                class="w-full h-full object-cover"
                alt=""
              />
            </div>
            <div class="p-4">
              <h3 class="font-medium text-lg mb-2">订单 #{{ item.purchaseId }}</h3>
              <p v-if="item.title" class="text-gray-700 text-sm mb-2">商品：{{ item.title }}</p>
              <p class="text-gray-500 text-xs mb-2">状态：{{ getStatusText(item.purchaseStatus) }}</p>
              <p class="text-gray-500 text-xs mb-2">下单时间：{{ formatTime(item.createTime) }}</p>
              <div class="flex justify-between items-center">
                <span class="text-red-600 font-medium">¥{{ item.totalPrice }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div v-if="goodsData.length === 0" class="px-3 py-3 text-center text-gray-500">
        暂无数据
      </div>

      <div class="flex justify-center items-center mt-8 text-sm">
        <Pagination
          :current-page="pagination.currentPage"
          :page-size="pagination.pageSize"
          :total="pagination.total"
          @update:current-page="setCurrentPage"
          @update:page-size="setPageSize"
          @page-change="loadData"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import { apiClient } from "../api/apiService.js";
import { useStore } from "vuex";
import Pagination from "./Pagination.vue";

const store = useStore();

const searchKey = ref("");
const goodsData = ref([]);

const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0,
});

const getImageUrl = (picture) => {
  if (!picture) {
    return "/src/assets/img/rice.png";
  }
  if (picture.startsWith("http")) {
    return picture;
  }
  return store.state.imgShowRoad + "/file/order/" + picture;
};

const getStatusText = (status) => {
  return status === 1 ? "已完成" : status === 0 ? "待处理" : "未知";
};

const formatTime = (time) => {
  if (!time) return "";
  return time.split("T")[0];
};

onMounted(async () => {
  await loadData();
});

const loadData = async () => {
  try {
    const response = await apiClient.get("/purchase/buys");
    if (response.flag && response.data) {
      goodsData.value = response.data;
      pagination.total = response.data.length;
    } else {
      goodsData.value = [];
      pagination.total = 0;
    }
  } catch (error) {
    console.error("获取买入订单失败:", error);
    goodsData.value = [];
    pagination.total = 0;
  }
};

const searchGoods = () => {
  loadData();
};

const setCurrentPage = (page) => {
  pagination.currentPage = page;
};

const setPageSize = (size) => {
  pagination.pageSize = size;
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