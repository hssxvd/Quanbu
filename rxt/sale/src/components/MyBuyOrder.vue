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
              <p class="text-gray-500 text-xs mb-2">卖家：{{ item.sellerName || '未知' }}</p>
              <p class="text-gray-500 text-xs mb-2">
                状态：
                <span :class="getStatusClass(item.purchaseStatus)">{{ getStatusText(item.purchaseStatus) }}</span>
              </p>
              <p class="text-gray-500 text-xs mb-2">下单时间：{{ formatTime(item.createTime) }}</p>
              <div class="flex justify-between items-center">
                <span class="text-red-600 font-medium">¥{{ item.totalPrice }}</span>
                <div class="flex gap-2">
                  <button
                    v-if="item.purchaseStatus === 2"
                    class="bg-[#007029] text-white text-xs px-3 py-1 rounded hover:bg-[#005d23] transition-colors"
                    @click.stop="confirmReceive(item)"
                  >
                    确认收货
                  </button>
                  <button
                    v-if="item.purchaseStatus === 3 && !item.hasComment"
                    class="bg-orange-500 text-white text-xs px-3 py-1 rounded hover:bg-orange-600 transition-colors"
                    @click.stop="showCommentDialog(item)"
                  >
                    评价
                  </button>
                  <span v-if="item.purchaseStatus === 3 && item.hasComment" class="text-green-600 text-xs">已评价</span>
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

    <!-- 评价弹窗 -->
    <el-dialog v-model="commentDialogVisible" title="订单评价" width="500px">
      <el-form :model="commentForm" label-width="80px">
        <el-form-item label="评分">
          <div class="flex gap-1">
            <span
              v-for="star in 5"
              :key="star"
              class="text-2xl cursor-pointer"
              :class="star <= commentForm.rating ? 'text-yellow-400' : 'text-gray-300'"
              @click="commentForm.rating = star"
            >
              ★
            </span>
          </div>
        </el-form-item>
        <el-form-item label="评价内容">
          <el-input
            v-model="commentForm.content"
            type="textarea"
            :rows="4"
            placeholder="请输入评价内容"
            maxlength="200"
            show-word-limit
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="commentDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitComment">提交评价</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import { apiClient } from "../api/apiService.js";
import { useStore } from "vuex";
import { ElMessage } from 'element-plus';
import Pagination from "./Pagination.vue";

const store = useStore();

const searchKey = ref("");
const goodsData = ref([]);

const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0,
});

// 评价相关
const commentDialogVisible = ref(false);
const commentForm = reactive({
  purchaseId: null,
  rating: 5,
  content: ""
});
const currentOrder = ref(null);

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
  const statusMap = {
    1: "已下单",
    2: "已发货",
    3: "已收货",
    4: "已完成"
  };
  return statusMap[status] || "未知";
};

const getStatusClass = (status) => {
  const classMap = {
    1: "text-blue-600",
    2: "text-orange-600",
    3: "text-green-600",
    4: "text-gray-600"
  };
  return classMap[status] || "text-gray-600";
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
      // 检查每个订单是否有评价
      const orders = response.data;
      for (const order of orders) {
        try {
          const commentRes = await apiClient.get(`/comment/purchase/${order.purchaseId}`);
          order.hasComment = commentRes.flag && commentRes.data != null;
        } catch {
          order.hasComment = false;
        }
      }
      goodsData.value = orders;
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

const confirmReceive = async (item) => {
  try {
    const response = await apiClient.post(`/purchase/confirm/${item.purchaseId}`);
    if (response.flag) {
      ElMessage.success("确认收货成功");
      await loadData();
    } else {
      ElMessage.error(response.message || "确认收货失败");
    }
  } catch (error) {
    console.error("确认收货失败:", error);
    ElMessage.error("确认收货失败");
  }
};

const showCommentDialog = (item) => {
  currentOrder.value = item;
  commentForm.purchaseId = item.purchaseId;
  commentForm.rating = 5;
  commentForm.content = "";
  commentDialogVisible.value = true;
};

const submitComment = async () => {
  if (!commentForm.content.trim()) {
    ElMessage.warning("请输入评价内容");
    return;
  }
  if (commentForm.rating < 1 || commentForm.rating > 5) {
    ElMessage.warning("请选择评分");
    return;
  }

  try {
    const response = await apiClient.post("/comment/add", {
      purchaseId: commentForm.purchaseId,
      rating: commentForm.rating,
      content: commentForm.content.trim()
    });
    if (response.flag) {
      ElMessage.success("评价成功");
      commentDialogVisible.value = false;
      await loadData();
    } else {
      ElMessage.error(response.message || "评价失败");
    }
  } catch (error) {
    console.error("评价失败:", error);
    ElMessage.error("评价失败");
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