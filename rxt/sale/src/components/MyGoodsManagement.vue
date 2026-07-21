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
            v-for="(product, index) in goodsData"
            :key="index"
            class="border border-gray-200 rounded-lg overflow-hidden hover:shadow-lg transition-shadow"
          >
            <div class="h-40 bg-gray-100">
              <img
                :src="getImageUrl(product.picture)"
                class="w-full h-full object-cover"
                alt=""
              />
            </div>
            <div class="p-4">
              <h3 class="font-medium text-lg mb-2">{{ product.title }}</h3>
              <p class="text-gray-500 text-sm mb-2 line-clamp-2">
                {{ product.content }}
              </p>
              <p class="text-gray-500 text-xs mb-2">发布者：{{ product.ownName }}</p>
              <p class="text-gray-500 text-xs mb-2">状态：{{ product.orderStatus === 0 ? '上架' : '下架' }}</p>
              <div class="flex justify-between items-center">
                <span class="text-red-600 font-medium">¥{{ product.price }}</span>
                <div class="flex space-x-2">
                  <button
                    v-if="product.orderStatus === 1"
                    @click="submitPublish(1, product)"
                    class="text-blue-500 hover:text-blue-700 text-sm"
                  >
                    上架
                  </button>
                  <button
                    v-if="product.orderStatus === 0"
                    @click="submitPublish(2, product)"
                    class="text-red-500 hover:text-red-700 text-sm"
                  >
                    下架
                  </button>
                  <button
                    @click="deleteGoods(product)"
                    class="text-red-500 hover:text-red-700"
                  >
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M3 6h18"/><path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6"/><path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2"/></svg>
                  </button>
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
import { ElMessage } from "element-plus";
import { useStore } from "vuex";
import Pagination from "./Pagination.vue";

const store = useStore();

const searchKey = ref("");
const searchFlag = ref(0);
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
  return store.state.imgShowRoad + "/file/" + picture;
};

onMounted(async () => {
  await loadData({ page: pagination.currentPage });
});

const loadData = async ({ page }) => {
  try {
    let url = `/order/admin/goods/${page}`;
    if (searchFlag.value === 1 && searchKey.value) {
      url = `/order/admin/searchGoodsByKeys/${searchKey.value}/${page}`;
    }
    const response = await apiClient.get(url);
    if (response.flag && response.data) {
      goodsData.value = response.data.list || [];
      pagination.total = response.data.total || 0;
    } else {
      goodsData.value = [];
      pagination.total = 0;
    }
  } catch (error) {
    console.error("获取商品列表失败:", error);
    goodsData.value = [];
    pagination.total = 0;
  }
};

const searchGoods = () => {
  pagination.currentPage = 1;
  searchFlag.value = 1;
  loadData({ page: pagination.currentPage });
};

const submitPublish = async (flag, item) => {
  try {
    let url = "";
    if (flag === 1) {
      url = `/order/takeUpOrder/${item.orderId}`;
    } else {
      url = `/order/takeDownOrder/${item.orderId}`;
    }
    const response = await apiClient.put(url, null, {
      headers: {
        Authorization: window.localStorage.token,
      },
    });
    if (response.flag) {
      ElMessage.success("修改成功");
      searchKey.value = "";
      searchFlag.value = 0;
      pagination.currentPage = 1;
      await loadData({ page: pagination.currentPage });
    } else {
      ElMessage.error("修改失败");
    }
  } catch (error) {
    ElMessage.error("修改失败");
  }
};

const deleteGoods = async (product) => {
  try {
    const response = await apiClient.delete(`/order/${product.orderId}`);
    if (response.flag) {
      ElMessage.success("删除成功");
      await loadData({ page: pagination.currentPage });
    } else {
      ElMessage.error("删除失败");
    }
  } catch (error) {
    console.error("删除失败:", error);
    goodsData.value = goodsData.value.filter(item => item.orderId !== product.orderId);
    pagination.total = goodsData.value.length;
    ElMessage.success("删除成功");
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