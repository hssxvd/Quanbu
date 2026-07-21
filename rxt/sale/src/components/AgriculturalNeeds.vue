<template>
  <!-- 搜索栏 -->
  <div class="container mx-auto px-4 py-6">
    <div class="max-w-2xl mx-auto">
      <div class="flex items-center shadow-lg rounded-full overflow-hidden border-2 border-[#007029]">
        <input
          v-model="searchKey"
          type="text"
          placeholder="输入关键词搜索求购信息"
          class="flex-1 px-6 py-3 focus:outline-none text-base"
        />
        <button
          class="bg-[#007029] text-white px-6 py-3 flex items-center hover:bg-[#005a1f] transition-colors" 
          @click="searchNeeds()"
        >
          <search-icon class="w-5 h-5 mr-2" />
          <span class="font-medium">搜索</span>
        </button>
      </div>
    </div>
  </div>

  <!-- 需求列表 -->
  <div class="container mx-auto px-4 py-4">
    <div v-if="needsData.length === 0" class="text-center py-16 text-gray-400">
      <p class="text-lg">暂无求购信息</p>
    </div>
    
    <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
      <div
        v-for="(product, index) in needsData"
        :key="index"
        class="bg-white rounded-xl shadow-sm hover:shadow-xl transition-all duration-300 border border-gray-100 overflow-hidden cursor-pointer"
      >
        <!-- 商品图片 -->
        <div class="relative w-full aspect-square overflow-hidden bg-gray-100">
          <img
            :src="$store.state.imgShowRoad + '/file/order/' + product.picture"
            class="w-full h-full object-cover hover:scale-105 transition-transform duration-300"
          />
          <!-- 求购标签 -->
          <div class="absolute top-3 left-3 bg-gradient-to-r from-[#007029] to-[#00a843] px-3 py-1 rounded-full">
            <span class="text-white text-xs font-medium">求购</span>
          </div>
        </div>

        <div class="p-4">
          <!-- 标题 -->
          <h3 class="text-gray-800 font-semibold text-lg mb-2 line-clamp-1">
            {{ product.title }}
          </h3>

          <!-- 描述 -->
          <p class="text-gray-600 text-sm mb-4 line-clamp-2 leading-relaxed">
            {{ product.content }}
          </p>

          <!-- 底部操作区 -->
          <div class="flex items-center justify-between pt-3 border-t border-gray-100">
            <span class="text-xs text-gray-400">
              发布人: {{ product.ownName }}
            </span>
            <button 
              @click.stop="showContactDialog(product)"
              class="bg-[#007029] text-white px-4 py-1.5 rounded-full text-sm hover:bg-[#005a1f] transition-colors"
            >
              查看联系方式
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- 联系信息弹窗 -->
  <el-dialog
    v-model="contactDialogVisible"
    title="联系信息"
    width="400px"
  >
    <div class="space-y-4 py-2">
      <div class="flex items-center p-3 bg-gray-50 rounded-lg">
        <span class="text-gray-600 font-medium w-20">联系人：</span>
        <span class="text-gray-800">{{contPerson}}</span>
      </div>
      <div class="flex items-center p-3 bg-gray-50 rounded-lg">
        <span class="text-gray-600 font-medium w-20">联系电话：</span>
        <span class="text-gray-800">{{contTel}}</span>
      </div>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="contactDialogVisible = false">关闭</el-button>
      </span>
    </template>
  </el-dialog>

  <!-- 分页 -->
  <div class="flex justify-center items-center py-8">
    <Pagination
      :current-page="pagination.currentPage"
      :page-size="pagination.pageSize"
      :total="pagination.total"
      @update:current-page="setCurrentPage"
      @update:page-size="setPageSize"
      @page-change="loadData"
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
import renshenguo from "@/assets/img/renshenguo.jpg";
import { onMounted } from "vue";
import { defineComponent } from "vue";
import { apiClient } from "../api/apiService.js";
import { useRouter } from "vue-router";
import Pagination from "../components/Pagination.vue";
import { ElMessage } from 'element-plus';

const router = useRouter();

// 联系信息弹窗
const contactDialogVisible = ref(false);
const contPerson = ref("");
const contTel = ref("");

const showContactDialog = async (item) => {
  const ownName = item.ownName;
  const res = await selectNeedsPerson({ ownName });
  if (res){
    contPerson.value = res.realName;
    contTel.value = res.phone;
    contactDialogVisible.value = true;
  }
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
const needsData = ref([]);

// 后端请求数据数据
const selectData = async ({ page }) => {
  try {
    //检索URL
    const url1 = `/order/search/needs/${page}`;
    const url2 = `/order/searchNeedsByKeys/${searchKey.value}/${page}`;
    const url = searchFlag.value == 1 && searchKey.value ? url2 : url1;
    
    const response = await apiClient.get(url);
    console.log("请求成功", response);
    return response;
  } catch (error) {
    console.error("请求失败", error);
    throw error;
  }
};

// 加载数据
const loadData = async ({ page }) => {
  try {
    const res = await selectData({ page });
    console.log("loadData 接收到的响应:", res);
    
    // 根据实际后端返回的数据结构调整
    if (res?.flag && res?.data) {
      // 如果 data 是数组
      if (Array.isArray(res.data)) {
        needsData.value = res.data;
        pagination.value.total = res.data.length;
      } 
      // 如果 data 是包含 list 和 total 的对象
      else if (res.data.list) {
        needsData.value = res.data.list;
        pagination.value.total = res.data.total;
      }
    }
    
    console.log("needsData:", needsData.value);
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
const searchNeeds = ()=> {
  pagination.value.currentPage = 1;
  searchFlag.value = 1;
  loadData({
    page: pagination.value.currentPage,
  });
};

// 后端请求数据数据
const selectNeedsPerson = async ({ ownName }) => {
  try {
    //检索URL
    const response = await apiClient.get(`/user/${ownName}`);

    if (response && response.flag == true) {
      return response.data;
    }
    else {
      ElMessage.error('您未登录，请先登录');
    }
  } catch (error) {
    console.error("请求失败", error);
    throw error;
  }
};
</script>

<style scoped>
.line-clamp-1 {
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>