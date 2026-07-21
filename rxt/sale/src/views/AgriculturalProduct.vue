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
    router.push(`/shopcart`)
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

const fileInfo = ref('');

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
    // 上传图片
    const uploadedImages = [];
    for (const file of publishForm.value.images) {
      const formData = new FormData();
      formData.append("file", file.raw);


      //上传商品图片
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
        fileInfo.value = response.data.split('/')[1];
      } else {
        ElMessage.error("发布失败");
      }
      const param = ref({
          title: publishForm.value.title,
          content: publishForm.value.description,
          price: publishForm.value.price,
          type: 'goods',
          picture: fileInfo.value
          })
      //发布商品
       const responseOrder = await apiClient.post(
        `/order`,
        param.value,
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
      }

    }

    showPublishDialog.value = false;
    // 重置表单
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