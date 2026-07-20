<template>
  <div class="space-y-6">
    <h1 class="text-2xl font-bold text-green-800 mb-6">农业问答</h1>

    <!-- Search Bar -->
    <div class="mb-6">
      <div class="relative">
        <input
          v-model="searchKey"
          type="text"
          placeholder="请输入问题..."
          class="w-full px-4 py-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-green-500"
        />
        <button
          class="absolute right-2 top-2 bg-green-700 text-white px-4 py-1 rounded-md" @click="searchQues()"
        >
          搜索
        </button>
      </div>
    </div>

    <!-- Q&A Items -->
    <div
      v-for="(qa, index) in questionData"
      :key="index"
      class="border border-gray-100 rounded-md p-4 hover:shadow-md transition relative"
    >
      <div class="flex items-start relative">
        <div
          v-if="qa.status !== undefined"
          class="absolute top-0 right-0 text-white text-xs px-2 py-1 rounded-bl-md"
          :class="qa.status === 1 ? 'bg-[#007029]' : 'bg-yellow-500'"
        >
          {{ qa.status === 1 ? "已回答" : "未回答" }}
        </div>
        <div
          class="bg-green-100 text-green-800 font-bold rounded-full w-6 h-6 flex items-center justify-center mr-3 mt-1"
        >
          Q
        </div>
        <div @click="quesDetail(qa)">
          <h3 class="font-medium text-lg">{{ qa.question }}</h3>
        </div>
      </div>

      <div class="mt-4 ml-9 border-t pt-3" v-if="qa.answer">
        <div class="flex">
          <div
            class="w-6 h-6 rounded-full bg-orange-200 flex items-center justify-center text-gray-600 mr-2 flex-shrink-0"
          >
            <span class="text-xs">A</span>
          </div>
          <div>
            <p class="text-gray-700">{{ qa.answer }}</p>
            <div class="flex items-center text-sm text-gray-500 mt-2">
              <span>专家：{{ qa.expertName }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="flex justify-center mt-8">
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
const questionData = ref([]);
// 检索关键字内容
const searchKey = ref("");
const searchFlag = ref(0);


// 后端请求数据数据
const selectData = async ({ page }) => {
  try {
    //检索URL
    const url1=ref(`/question/findAllQues/${page}`);
    const url2=ref(`/question/findPageQues/${searchKey.value}/${page}`);
    const url = ref("");
    if (searchFlag.value == 1 && searchKey.value) {
      url.value = url2.value;
    } else {
      url.value = url1.value;
    }
    const response = await apiClient.get(url.value, {
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
    console.log('Loading page:', page);
    const res = await selectData({ page });
    console.log('API response:', res);
    if (res && res.list && res.total !== undefined) {
      questionData.value = res.list;
      pagination.value.total = res.total;
      console.log('Updated pagination:', pagination.value);
    } else {
      console.error('Invalid API response structure:', res);
    }
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

//点击某问答，跳转至问答详情
const quesDetail = (item)=> {
  router.push(`/question?question=${item.question}&answer=${item.answer}&expertName=${item.expertName}`)
};

//点击检索
const searchQues = ()=> {
  pagination.value.currentPage = 1;
  searchFlag.value = 1;
  loadData({
    page: pagination.value.currentPage,
  });
};

</script>