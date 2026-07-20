<template>
  <div class="space-y-6">
    <h1 class="text-2xl font-bold text-green-800 mb-6">专家介绍</h1>

          <div class="container mx-auto px-4">
            <!-- Specialists Section -->
            <div class="mb-16">
              <div class="grid grid-cols-5 gap-10 mb-8">
                <div
                  v-for="(specialist, index) in expertData"
                  :key="index"
                  class="rounded-lg overflow-hidden shadow-md border border-[rgba(0,112,41,0.3)] hover:bg-green-700 hover:text-white transition-colors duration-300"
                >
                  <div
                    class="h-32 w-full flex items-center justify-center relative"
                  >
                    <div
                      class="absolute bottom-0 left-1/2 transform -translate-x-1/2 w-32 h-16 bg-[#007029] rounded-t-full"
                      style="z-index: 1"
                    ></div>
                    <img
                      :src="$store.state.imgShowRoad + '/file/avatar/' + specialist.avatar"
                      class="h-32 w-32 object-cover relative"
                      style="z-index: 2"
                    />
                  </div>
                  <div class="p-4">
                    <div class="flex gap-2 mb-4">
                      <div
                        class="text-center font-bold text-base py-1 px-2 rounded bg-green-800 text-white flex-1 cursor-pointer"
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
                        <span
                          :class="
                            index === 0 ? 'text-gray-300' : 'text-gray-400'
                          "
                          >专家姓名:</span
                        >
                        <span class="font-medium ml-1">{{
                          specialist.realName
                        }}</span>
                      </div>
                      <div class="mb-1 flex">
                        <span
                          :class="
                            index === 0 ? 'text-gray-300' : 'text-gray-400'
                          "
                          >专业:</span
                        >
                        <span class="font-medium ml-1">{{
                          specialist.profession
                        }}</span>
                      </div>

                      <div class="mb-1 flex">
                        <span
                          :class="
                            index === 0 ? 'text-gray-300' : 'text-gray-400'
                          "
                          >单位:</span
                        >
                        <span class="font-medium ml-1">{{
                          specialist.belong
                        }}</span>
                      </div>
                      <div class="mb-1 flex">
                        <span
                          :class="
                            index === 0 ? 'text-gray-300' : 'text-gray-400'
                          "
                          >联系电话:</span
                        >
                        <span class="font-medium ml-1">{{
                          specialist.phone
                        }}</span>
                      </div>
                      <div class="mb-1 flex">
                        <span
                          :class="
                            index === 0 ? 'text-gray-300' : 'text-gray-400'
                          "
                          >单位:</span
                        >
                        <span class="font-medium ml-1">{{
                          specialist.belong
                        }}</span>
                      </div>
                    </div>
                  </div>
                </div>
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
  
  <ExpertConsultDialog ref="consultDialog" :expertName="expertName"/>
  <ExpertReserveDialog ref="reserveDialog" :expertName="expertName"/>
</template>

<script setup>
import { ref } from "vue";
import { onMounted } from "vue";
import { UserIcon } from "lucide-vue-next";
import { defineComponent } from "vue";
import { apiClient } from "../api/apiService.js";
import { useRouter } from "vue-router";
import { ElMessage } from 'element-plus';
import Pagination from "../components/Pagination.vue";
import ExpertConsultDialog from "../components/ExpertConsultDialog.vue";
import ExpertReserveDialog from "../components/ExpertReserveDialog.vue";

// // 表格数据
// const expertData = ref([]);

// // 后端请求数据数据
// const selectData = async () => {
//   try {
    
//     const response = await apiClient.get(`/question/findExpertUser/${page}`, {
//       headers: {
//         Authorization: window.localStorage.token,
//       },
//     });
//     if (response.flag == true) {
//         console.log("请求成功", response.data);
//         expertData =  response.data.list;
//     }
//     else {
//       ElMessage.error('您未登录，请先登录');
//     }
//   } catch (error) {
//     console.error("请求失败", error);
//     throw error;
//   }
// };


// // 初始化加载
// onMounted(() => {
//   selectData();
// });


// 分页状态
const pagination = ref({
  currentPage: 1,
  pageSize: 10,
  total: 0,
});

// 表格数据
const expertData = ref([]);

// 后端请求数据数据
const selectData = async ({ page }) => {
  try {
    const response = await apiClient.get(`/question/findExpertUser/${page}`, {
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
    expertData.value = res.list
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

const consultDialog = ref();
const expertName = ref('');
const openConsultDialog = (expert) => {
  if(!window.localStorage.token){
    ElMessage.error('您未登录，请先登录');
    return;
  }
  expertName.value=expert.userName;
  consultDialog.value.open();
};

const reserveDialog = ref();
const openReserveDialog = (expert) => {
  if(!window.localStorage.token){
    ElMessage.error('您未登录，请先登录');
    return;
  }
  expertName.value=expert.userName;
  reserveDialog.value.open();
};


// 初始化加载
onMounted(() => {
  loadData({
    page: pagination.value.currentPage
  });
});

</script>