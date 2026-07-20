<template>
  <div class="space-y-6">
    <div
      v-for="(article, index) in articleData"
      :key="index"
      class="flex rounded-md shadow-[0_0_0_1px_rgba(144,238,144,0.09)] hover:shadow-[0_0_0_2px_rgba(0,112,41,0.12)] transition-all duration-300"
    >
      <div class="flex-1 bg-white p-4 rounded-md flex">
        <div class="flex-shrink-0 mr-4">
          <img
            :src="$store.state.imgShowRoad + '/file/order/' + article.picPath"
            alt="Tea plant"
            class="w-24 h-24 object-cover rounded-md"
          />
        </div>
        <div class="flex-1" @click="articleDetail(article)">
          <h2 class="text-lg font-medium mb-2">{{ article.title }}</h2>
          <p class="text-gray-600 text-sm leading-relaxed">
            {{ article.content }}
          </p>
        </div>
      </div>
    </div>
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
  </div>
</template>

<script setup>
import { ref } from "vue";
import { onMounted } from "vue";
import { UserIcon } from "lucide-vue-next";
import { defineComponent } from "vue";
import { apiClient } from "../api/apiService.js";
import { useRouter } from "vue-router";
import Pagination from "../components/Pagination.vue";

const router = useRouter();

// 分页状态
const pagination = ref({
  currentPage: 1,
  pageSize: 10,
  total: 0,
});

// 表格数据
const articleData = ref([]);

// 后端请求数据数据
const selectData = async ({ page }) => {
  try {
    const response = await apiClient.get(`/knowledge/${page}`, {
      headers: {
        Authorization: window.localStorage.token,
      },
    });
    console.log("请求成功", response.data);
    return response.data;
  } catch (error) {
    console.error("请求失败", error);
    throw error;
  }
};

// 加载数据
const loadData = async ({ page }) => {
  try {
    const res = await selectData({ page })
    articleData.value = res.list
    pagination.value.total = res.total
  } catch (error) {
    console.error('数据加载失败:', error)
  }
}

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
    page: pagination.value.currentPage
  });
});


//点击某知识，跳转至知识详情
const articleDetail = (item)=> {
  router.push(`/AgriKnlg?knowledgeId=${item.knowledgeId}&picPath=${item.picPath}&title=${item.title}&content=${item.content}&ownName=${item.ownName}&updateTime=${item.updateTime}`)
};
</script>