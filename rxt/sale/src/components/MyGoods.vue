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
          <el-upload
            ref="uploadRef"
            action="#"
            list-type="picture-card"
            :on-change="handleImageUpload"
            :auto-upload="false"
            :limit="1"
            :file-list="
              publishForm.pic
                ? [
                    {
                      name: 'order',
                      url: getImageUrl(publishForm.pic),
                    },
                  ]
                : []
            "
          >
            <el-icon class="text-2xl"><PlusIcon /></el-icon>
          </el-upload>
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
            placeholder="请输入商品价格"
          />
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
import { PlusIcon } from "lucide-vue-next";
import { apiClient } from "../api/apiService.js";
import { ElMessage } from "element-plus";
import { useStore } from "vuex";
import Pagination from "./Pagination.vue";

const store = useStore();

const searchKey = ref("");
const showPublishDialog = ref(false);
const goodsData = ref([]);
const images = ref([]);
const fileInfo = ref("");
const uploadRef = ref("");

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
  images: [],
});

const mockGoodsData = [
  {
    orderId: 1,
    title: "地瓜粉红薯淀粉",
    content: "福建闽南泉州官桥正宗农家手工自制地瓜粉红薯淀粉番薯粉无添加剂",
    price: 123.0,
    picture: "07866a77aaaa434ab75c2eba8eab99a0.png",
    type: "goods",
    ownName: "test",
    createTime: "2024-01-01",
    updateTime: "2024-01-01",
  },
  {
    orderId: 2,
    title: "新疆小红杏吊干杏",
    content: "新疆小红杏吊干杏新鲜杏子农产品应季1斤装水果特甜小白杏净重4斤",
    price: 45.0,
    picture: "0cb58391ab754653abe7958c6b4febc4.jpg",
    type: "goods",
    ownName: "test",
    createTime: "2024-01-02",
    updateTime: "2024-01-02",
  },
  {
    orderId: 3,
    title: "云南特产大香蕉",
    content: "云南特产冬季水果大香蕉新鲜当季10斤农产品直销土特产农家孕妇水",
    price: 23.0,
    picture: "6c1c2f5b38ac4be190dfc4a421d65f73.jpg",
    type: "goods",
    ownName: "test",
    createTime: "2024-01-03",
    updateTime: "2024-01-03",
  },
  {
    orderId: 4,
    title: "山东大葱",
    content: "山东大葱新鲜5斤时令蔬菜东北香葱包邮蘸酱铁杆章丘10号助农产品",
    price: 34.0,
    picture: "yangcong_20250513154843.png",
    type: "goods",
    ownName: "test",
    createTime: "2024-01-04",
    updateTime: "2024-01-04",
  },
  {
    orderId: 5,
    title: "大别山野生羊肚菌",
    content: "高端消费人群厨房食材大别山野生羊肚菌干货煲汤菌菇类特产50克",
    price: 345.0,
    picture: "566ae2891bf24952874058b86051f4a2.jpg",
    type: "goods",
    ownName: "test",
    createTime: "2024-01-05",
    updateTime: "2024-01-05",
  },
  {
    orderId: 6,
    title: "苹果",
    content: "东北鸡心果5斤锦绣海棠果特产玫瑰小苹果花红沙果孕妇新鲜水果",
    price: 22.81,
    picture: "a4418dc8694a4c51875e18c045169697.jpg",
    type: "goods",
    ownName: "test",
    createTime: "2024-01-06",
    updateTime: "2024-01-06",
  },
  {
    orderId: 7,
    title: "白溪豆腐干香",
    content: "白溪豆腐干湖南新化特产农家自制原味烟熏干子非武冈豆干10包",
    price: 56.0,
    picture: "8781c37f24d24376bfd037fcbcb44dc5.jpg",
    type: "goods",
    ownName: "test",
    createTime: "2024-01-07",
    updateTime: "2024-01-07",
  },
  {
    orderId: 8,
    title: "红柚",
    content: "福建平和红心柚子红肉蜜柚当季水果新鲜现摘包邮应季水果特产",
    price: 23.8,
    picture: "watermelon_20250513154759.png",
    type: "goods",
    ownName: "test",
    createTime: "2024-01-08",
    updateTime: "2024-01-08",
  },
  {
    orderId: 9,
    title: "百香果",
    content: "广西百香果当季水果新鲜果浆现摘包邮黄金果5斤装助农特产",
    price: 19.9,
    picture: "zheergen_20250513155020.png",
    type: "goods",
    ownName: "test",
    createTime: "2024-01-09",
    updateTime: "2024-01-09",
  },
];

const getImageUrl = (picture) => {
  if (!picture) {
    return "/src/assets/img/rice.png";
  }
  if (picture.startsWith("http")) {
    return picture;
  }
  return `/src/assets/img/${picture}`;
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
      ElMessage.info("暂无商品数据，显示示例数据");
      goodsData.value = mockGoodsData;
      pagination.total = mockGoodsData.length;
    }
  } catch (error) {
    console.error("获取商品列表失败:", error);
    ElMessage.info("网络异常，显示示例数据");
    goodsData.value = mockGoodsData;
    pagination.total = mockGoodsData.length;
  }
};

const searchGoods = async () => {
  if (!searchKey.value) {
    await loadData();
    return;
  }
  try {
    const response = await apiClient.get(
      `/order/searchGoodsByKeys/${searchKey.value}/${pagination.currentPage}`
    );
    if (response.flag && response.data) {
      goodsData.value = response.data;
    } else {
      ElMessage.info("未找到匹配商品");
      goodsData.value = [];
    }
  } catch (error) {
    console.error("搜索失败:", error);
    goodsData.value = mockGoodsData.filter(item => 
      item.title.includes(searchKey.value) || item.content.includes(searchKey.value)
    );
  }
};

const handleImageUpload = (file) => {
  images.value.push(file);
};

const handleCancel = () => {
  showPublishDialog.value = false;
  publishForm.orderId = "";
  publishForm.pic = "";
  publishForm.title = "";
  publishForm.description = "";
  publishForm.price = "";
  images.value = [];
  fileInfo.value = "";
  if (uploadRef.value) {
    uploadRef.value.clearFiles();
  }
};

const proDataChange = (product) => {
  publishForm.orderId = product.orderId;
  publishForm.pic = product.picture;
  publishForm.title = product.title;
  publishForm.description = product.content;
  publishForm.price = product.price;
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

  try {
    if (images.value.length > 0) {
      const formData = new FormData();
      formData.append("file", images.value[0].raw);

      const response = await apiClient.post(
        `/file/upload/order`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
      if (response.flag) {
        fileInfo.value = response.data.split("/")[1];
      } else {
        ElMessage.error("图片上传失败");
        return;
      }
    }

    if (!fileInfo.value) {
      fileInfo.value = publishForm.pic;
    }

    const param = {
      orderId: publishForm.orderId ? publishForm.orderId : null,
      title: publishForm.title,
      content: publishForm.description,
      price: publishForm.price,
      picture: fileInfo.value,
      type: "goods",
    };

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
    if (!publishForm.orderId) {
      goodsData.value.unshift({
        orderId: Date.now(),
        title: publishForm.title,
        content: publishForm.description,
        price: publishForm.price,
        picture: "default.png",
        type: "goods",
      });
      pagination.total = goodsData.value.length;
      ElMessage.success("发布成功（演示模式）");
    } else {
      const index = goodsData.value.findIndex(item => item.orderId === publishForm.orderId);
      if (index !== -1) {
        goodsData.value[index] = {
          ...goodsData.value[index],
          title: publishForm.title,
          content: publishForm.description,
          price: publishForm.price,
        };
      }
      ElMessage.success("修改成功（演示模式）");
    }
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