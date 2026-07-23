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
        <button
          @click="showPublishDialog = true"
          class="flex items-center space-x-2 bg-[#007029] hover:bg-green-800 text-white font-medium py-2 px-4 rounded-md focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-offset-2"
        >
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 5v14"/><path d="M5 12h14"/></svg>
          <span>发布新商品</span>
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
              <div class="flex justify-between items-center">
                <span class="text-red-600 font-medium">¥{{ product.price }}</span>
                <div class="flex space-x-2">
                  <button
                    @click="proDataChange(product); showPublishDialog = true"
                    class="text-blue-500 hover:text-blue-700"
                  >
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M17 3a2.85 2.83 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5Z"/><path d="m15 5 4 4"/></svg>
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

    <el-dialog
      v-model="showPublishDialog"
      title="发布商品"
      width="600px"
    >
      <el-form :model="publishForm" label-width="80px">
        <el-form-item label="商品图片" required>
          <div class="flex items-center space-x-4">
            <div class="w-24 h-24 border border-dashed border-gray-300 rounded flex items-center justify-center cursor-pointer"
                 @click="triggerFileUpload">
              <img v-if="fileInfo" :src="fileInfo" class="w-full h-full object-cover rounded" />
              <svg v-else xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="text-gray-400"><path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"/><polyline points="17 8 12 3 7 8"/><line x1="12" x2="12" y1="3" y2="15"/></svg>
            </div>
            <input type="file" ref="fileInput" style="display:none" @change="handleFileUpload" />
          </div>
        </el-form-item>

        <el-form-item label="商品标题" required>
          <el-input
            v-model="publishForm.title"
            placeholder="请输入商品标题"
          />
        </el-form-item>

        <el-form-item label="详细介绍" required>
          <el-input
            v-model="publishForm.description"
            type="textarea"
            :rows="4"
            placeholder="请输入商品详细介绍"
          />
        </el-form-item>

        <el-form-item label="定价" required>
          <el-input-number
            v-model="publishForm.price"
            :min="0"
            :precision="2"
            placeholder="请输入商品价格（无规格时使用）"
          />
        </el-form-item>

        <el-form-item label="商品规格">
          <div class="w-full">
            <div class="flex items-center justify-between mb-2">
              <span class="text-sm text-gray-500">设置多个规格（如3斤、5斤、9斤），不同规格不同价格</span>
              <el-button size="small" type="primary" @click="addSpecRow">
                <el-icon><PlusIcon /></el-icon>
                添加规格
              </el-button>
            </div>
            <div v-if="publishForm.specs.length > 0" class="grid grid-cols-1 gap-2">
              <div class="flex items-center gap-2 px-2 py-1 bg-gray-50 rounded text-xs text-gray-500">
                <span class="w-24">规格名称</span>
                <span class="w-20">价格（元）</span>
                <span class="w-16">库存</span>
                <span class="w-12">操作</span>
              </div>
              <div v-for="(spec, index) in publishForm.specs" :key="index" class="flex items-center gap-2">
                <el-input v-model="spec.specName" placeholder="如：3斤装" class="w-24" />
                <el-input v-model="spec.specPrice" placeholder="价格" class="w-20" />
                <el-input v-model="spec.specStock" placeholder="库存" class="w-16" />
                <el-button size="small" type="danger" @click="removeSpecRow(index)">删除</el-button>
              </div>
            </div>
            <div v-if="publishForm.specs.length === 0" class="text-xs text-gray-400">
              未设置规格时使用默认定价
            </div>
          </div>
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="handleCancel">取消</el-button>
          <el-button type="primary" @click="submitPublish">发布</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import { apiClient } from "../api/apiService.js";
import { ElMessage } from "element-plus";
import { useStore } from "vuex";
import Pagination from "./Pagination.vue";
import { PlusIcon } from "lucide-vue-next";

const store = useStore();

const searchKey = ref("");
const showPublishDialog = ref(false);
const goodsData = ref([]);
const fileInfo = ref(null);
const fileInput = ref(null);

const pagination = reactive({
  currentPage: 1,
  pageSize: 12,
  total: 0,
});

const publishForm = reactive({
  orderId: "",
  pic: "",
  title: "",
  description: "",
  price: "",
  specs: [],
});

const addSpecRow = () => {
  publishForm.specs.push(reactive({ specName: "", specPrice: "", specStock: "" }));
};

const removeSpecRow = (index) => {
  publishForm.specs.splice(index, 1);
};

const getImageUrl = (picture) => {
  if (!picture) {
    return "/src/assets/img/rice.png";
  }
  if (picture.startsWith("http")) {
    return picture;
  }
  return store.state.imgShowRoad + "/file/order/" + picture;
};

onMounted(async () => {
  await loadData();
});

const loadData = async () => {
  try {
    const response = await apiClient.get(`/order/goods/${pagination.currentPage}`);
    if (response.flag && response.data && response.data.length > 0) {
      goodsData.value = response.data;
      pagination.total = response.data.length;
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

const searchGoods = async () => {
  if (!searchKey.value) {
    await loadData();
    return;
  }
  try {
    const response = await apiClient.get(
      `/order/search/searchGoodsByKeys/${searchKey.value}/${pagination.currentPage}`
    );
    if (response.flag && response.data) {
      goodsData.value = response.data;
    } else {
      ElMessage.info("未找到匹配商品");
      goodsData.value = [];
    }
  } catch (error) {
    console.error("搜索失败:", error);
    goodsData.value = [];
  }
};

const triggerFileUpload = () => {
  fileInput.value.click();
};

const handleFileUpload = async (event) => {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      fileInfo.value = e.target.result;
    };
    reader.readAsDataURL(file);

    const formData = new FormData();
    formData.append("file", file);
    try {
      const response = await apiClient.post("/file/upload/order", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      if (response.flag && response.data) {
        publishForm.pic = response.data.split('/')[1];
      }
    } catch (error) {
      console.error("上传图片失败", error);
      ElMessage.error("上传图片失败");
    }
  }
};

const handleCancel = () => {
  showPublishDialog.value = false;
  publishForm.orderId = "";
  publishForm.pic = "";
  publishForm.title = "";
  publishForm.description = "";
  publishForm.price = "";
  publishForm.specs = [];
  fileInfo.value = null;
};

const proDataChange = async (product) => {
  publishForm.orderId = product.orderId;
  publishForm.pic = product.picture;
  publishForm.title = product.title;
  publishForm.description = product.content;
  publishForm.price = product.price;
  fileInfo.value = getImageUrl(product.picture);
  
  publishForm.specs = [];
  if (product.orderId) {
    try {
      const res = await apiClient.get(`/spec/list/${product.orderId}`);
      if (res.flag && res.data) {
        for (const spec of res.data) {
          publishForm.specs.push(reactive({
            specName: spec.specName,
            specPrice: spec.specPrice,
            specStock: spec.specStock
          }));
        }
      }
    } catch (err) {
      console.error("加载规格失败", err);
    }
  }
};

const deleteGoods = async (product) => {
  try {
    const response = await apiClient.delete(`/order/${product.orderId}`);
    if (response.flag) {
      ElMessage.success("删除成功");
      await loadData();
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

const submitPublish = async () => {
  if (!publishForm.title || !publishForm.price || !publishForm.description) {
    ElMessage.error("请完整填写商品信息");
    return;
  }

  if (publishForm.title.length < 2 || publishForm.title.length > 30) {
    ElMessage.error("商品标题长度在2-30字之间");
    return;
  }

  if (publishForm.description.length < 10 || publishForm.description.length > 150) {
    ElMessage.error("详细介绍长度在10-150字之间");
    return;
  }

  const validSpecs = publishForm.specs.filter(s => {
    const name = String(s.specName || "").trim();
    const price = s.specPrice;
    return name.length > 0 && price !== "" && price !== null && price !== undefined && !isNaN(Number(price));
  });
  for (const spec of validSpecs) {
    if (isNaN(Number(spec.specPrice)) || Number(spec.specPrice) <= 0) {
      ElMessage.error('规格价格必须为正数');
      return;
    }
  }

  try {
    const param = {
      orderId: publishForm.orderId ? publishForm.orderId : null,
      title: publishForm.title,
      content: publishForm.description,
      price: publishForm.price,
      picture: publishForm.pic,
      type: "goods",
      specs: validSpecs,
    };
    console.log("DEBUG: publishForm.specs =", JSON.stringify(publishForm.specs));
    console.log("DEBUG: validSpecs =", JSON.stringify(validSpecs));
    console.log("DEBUG: sending params =", JSON.stringify(param));

    let response;
    if (publishForm.orderId) {
      response = await apiClient.put(`/order/${publishForm.orderId}`, param);
    } else {
      response = await apiClient.post("/order/add", param);
    }

    if (response.flag) {
      ElMessage.success(publishForm.orderId ? "修改成功" : "发布成功");
      handleCancel();
      await loadData();
    } else {
      ElMessage.error(publishForm.orderId ? "修改失败" : "发布失败");
    }
  } catch (error) {
    console.error("操作失败:", error);
    handleCancel();
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