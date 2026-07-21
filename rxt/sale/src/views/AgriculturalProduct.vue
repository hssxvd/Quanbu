<template>
  <section class="py-6 bg-white border-t-2 border-gray-300">
    <div class="container w-2/3 mx-auto">
      <!-- 顶部导航 -->
      <div class="border-b border-gray-200">
        <div
          class="container mx-auto px-4 flex justify-between items-center py-3"
        >
          <div class="flex space-x-6">
            <div
              :class="[
                'cursor-pointer pb-1',
                activeTab === 'info'
                  ? 'text-[#007029] font-medium border-b-2 border-[#007029]'
                  : 'text-gray-500 hover:text-gray-700',
              ]"
              @click="activeTab = 'info'"
              class="text-xl"
            >
              商品货源
            </div>
            <div
              :class="[
                'cursor-pointer pb-1',
                activeTab === 'display'
                  ? 'text-[#007029] font-medium border-b-2 border-[#007029]'
                  : 'text-gray-500 hover:text-gray-700',
              ]"
              @click="activeTab = 'display'"
              class="text-xl"
            >
              求购需求
            </div>
          </div>
          <div class="flex space-x-2">
            <button
              class="bg-[#007029] text-white px-3 py-1 rounded flex items-center" @click="shoppingCart()"
            >
              <shopping-cart-icon :stroke-width="3" class="w-4 h-4 mr-1" />
              购物车
            </button>
            <button
              class="bg-[#007029] text-white px-3 py-1 rounded flex items-center"
              @click="showPublishDialog = true"
            >
              <PlusIcon :stroke-width="3" class="w-4 h-4 mr-1" />
              免费发布商品
            </button>
          </div>
        </div>
      </div>

      <!-- 商品信息页面 -->
      <div v-if="activeTab === 'info'">
        <!-- 搜索栏 -->
        <AgriculturalGoods/>
      </div>

      <!-- 产品展示页面 -->
      <div v-else-if="activeTab === 'display'" class="mx-auto px-4">
        <AgriculturalNeeds/>
          
            
      </div>
    </div>

    <el-dialog v-model="showPublishDialog" title="发布商品" width="500px">
      <el-form :model="publishForm" label-width="80px">
        <el-form-item label="商品标题">
          <el-input v-model="publishForm.title" placeholder="2-8字" />
        </el-form-item>
        <el-form-item label="详细介绍">
          <el-input v-model="publishForm.description" type="textarea" placeholder="10-150字" />
        </el-form-item>
        <el-form-item label="价格">
          <el-input v-model="publishForm.price" placeholder="请输入价格" />
        </el-form-item>
        <el-form-item label="商品图片">
          <el-upload
            ref="uploadRef"
            :auto-upload="false"
            :on-change="handleImageUpload"
            list-type="picture-card"
          >
            <el-icon><PlusIcon /></el-icon>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="handleCancel">取消</el-button>
        <el-button type="primary" @click="submitPublish">发布</el-button>
      </template>
    </el-dialog>
  </section>
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
import AgriculturalGoods from "../components/AgriculturalGoods.vue"; // 引入商品组件
import AgriculturalNeeds from "../components/AgriculturalNeeds.vue"; // 引入求购需求组件
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';
import { useStore } from "vuex";
import { apiClient } from "../api/apiService.js";

const store = useStore();
const router = useRouter();
// 当前激活的标签页
const activeTab = ref("info");

const shoppingCart = () => {
  if(store.state.token!=''){
    router.push(`/home/shopcart`)
  } else {
    ElMessage.error('您未登录，请先登录');
  }
}

// 发布商品相关
const showPublishDialog = ref(false);
const publishForm = ref({
  title: '',
  description: '',
  price: '',
  images: []
});

const handleImageUpload = (file) => {
  publishForm.value.images.push(file);
};

const uploadRef = ref();

const handleCancel = () => {
  showPublishDialog.value = false;
  publishForm.value = {
    title: '',
    description: '',
    price: '',
    images: []
  };
  if (uploadRef.value) {
    uploadRef.value.clearFiles();
  }
};

const submitPublish = async () => {
  if (!publishForm.value.title || !publishForm.value.price|| !publishForm.value.description) {
    ElMessage.error('请完整填写商品信息');
    return;
  }
  if (publishForm.value.title.length < 2 ||publishForm.value.title.length>8) {
    ElMessage.error('商品标题长度在2-8字之间');
    return;
  }
  if (publishForm.value.description.length < 10 ||publishForm.value.description.length>150) {
    ElMessage.error('详细介绍长度在10-150字之间');
    return;
  }

  try {
    const uploadedImages = [];
    for (const file of publishForm.value.images) {
      const formData = new FormData();
      formData.append("file", file.raw);

      const response = await apiClient.post(
        `${store.state.fileUploadRoad}/file/upload/order`,
        formData,
        {
          headers: {
            Authorization: window.localStorage.token,
            "Content-Type": "multipart/form-data",
          },
        }
      );

      if (response.flag) {
        uploadedImages.push(response.data.split('/')[1]);
      } else {
        ElMessage.error("图片上传失败");
        return;
      }
    }

    const param = {
      title: publishForm.value.title,
      content: publishForm.value.description,
      price: publishForm.value.price,
      type: 'goods',
      picture: uploadedImages.join(',')
    };

    const responseOrder = await apiClient.post(
      `/order/add`,
      param,
      {
        headers: {
          Authorization: window.localStorage.token,
        },
      }
    );

    if (responseOrder.flag) {
      ElMessage.success("发布成功");
    } else {
      ElMessage.error("发布失败");
      return;
    }

    showPublishDialog.value = false;
    publishForm.value = {
      title: '',
      description: '',
      price: '',
      images: []
    };
    if (uploadRef.value) {
      uploadRef.value.clearFiles();
    }
  } catch (error) {
    console.error('发布商品失败:', error);
    ElMessage.error('发布商品失败');
  }
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