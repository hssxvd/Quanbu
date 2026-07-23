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
              <h3 class="font-medium text-lg mb-2">{{ item.title || '商品 #' + item.orderId }}</h3>
              <p class="text-gray-500 text-sm mb-2 line-clamp-2">
                {{ item.content }}
              </p>
              <p class="text-gray-500 text-xs mb-2">状态：{{ getStatusText(item.purchaseStatus) }}</p>
              <p v-if="item.buyerName" class="text-gray-500 text-xs mb-2">买家：{{ item.buyerName }}</p>
              <p class="text-gray-500 text-xs mb-2">成交时间：{{ formatTime(item.createTime) }}</p>
              <div class="flex justify-between items-center mt-2">
                <span class="text-red-600 font-medium">¥{{ item.sumPrice }}</span>
                <div class="flex gap-2">
                  <button
                    v-if="item.purchaseStatus === 1"
                    class="bg-[#007029] text-white text-xs px-3 py-1 rounded hover:bg-[#005d23] transition-colors"
                    @click.stop="deliverOrder(item)"
                  >
                    发货
                  </button>
                  <span v-else-if="item.purchaseStatus === 2" class="text-orange-600 text-xs">已发货</span>
                  <span v-else-if="item.purchaseStatus === 3" class="text-green-600 text-xs">已收货</span>
                </div>
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
import { ElMessage } from 'element-plus';
import { getImageUrl } from "../utils/imageUtils.js";
import Pagination from "./Pagination.vue";

const store = useStore();

const searchKey = ref("");
const goodsData = ref([]);

const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0,
});

const getStatusText = (status) => {
  const statusMap = {
    1: "已下单",
    2: "已发货",
    3: "已收货",
    4: "已完成"
  };
  return statusMap[status] || "未知";
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
    const response = await apiClient.get("/purchase/sells");
    if (response.flag && response.data) {
      goodsData.value = response.data;
      pagination.total = response.data.length;
    } else {
      goodsData.value = [];
      pagination.total = 0;
    }
  } catch (error) {
    console.error("获取卖出订单失败:", error);
    goodsData.value = [];
    pagination.total = 0;
  }
};

const searchGoods = () => {
  loadData();
};

const deliverOrder = async (item) => {
  try {
    const response = await apiClient.post(`/purchase/deliver/${item.purchaseId}`);
    if (response.flag) {
      ElMessage.success("发货成功");
      await loadData();
    } else {
      ElMessage.error(response.message || "发货失败");
    }
  } catch (error) {
    console.error("发货失败:", error);
    ElMessage.error("发货失败");
  }
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