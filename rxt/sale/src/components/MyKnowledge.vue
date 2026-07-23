<template>
  <div class="p-4">
    <div class="flex justify-between items-center mb-6">
      <h2 class="text-xl font-bold text-gray-800">知识管理</h2>
      <el-button type="primary" @click="showPublishDialog = true" class="bg-green-600 hover:bg-green-700">
        + 发布知识
      </el-button>
    </div>

    <div class="grid grid-cols-3 gap-6">
      <div v-for="knowledge in knowledgeList" :key="knowledge.knowledgeId" 
           class="border border-gray-200 rounded-lg overflow-hidden shadow-sm hover:shadow-md transition-shadow">
        <div class="h-40 bg-gray-100 flex items-center justify-center">
          <img v-if="knowledge.picPath" :src="getImgUrl(knowledge.picPath)" :alt="knowledge.title" 
               class="w-full h-full object-cover" />
          <div v-else class="text-gray-400 text-center">
            <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="3" width="18" height="18" rx="2" ry="2"/><circle cx="8.5" cy="8.5" r="1.5"/><polyline points="21 15 16 10 5 21"/></svg>
          </div>
        </div>
        <div class="p-4">
          <h3 class="font-medium text-gray-800 mb-2 truncate">{{ knowledge.title }}</h3>
          <p class="text-gray-600 text-sm line-clamp-3 mb-4">{{ knowledge.content }}</p>
          <div class="flex justify-end space-x-2">
            <el-button size="small" @click="editKnowledge(knowledge)">编辑</el-button>
            <el-button size="small" type="danger" @click="deleteKnowledge(knowledge.knowledgeId)">删除</el-button>
          </div>
        </div>
      </div>
    </div>

    <div v-if="knowledgeList.length === 0" class="px-3 py-10 text-center text-gray-500">
      暂无数据
    </div>

    <el-dialog title="发布知识" v-model="showPublishDialog" width="500px">
      <el-form :model="publishForm" label-width="80px">
        <el-form-item label="图片">
          <div class="flex items-center space-x-4">
            <div class="w-24 h-24 border border-dashed border-gray-300 rounded flex items-center justify-center cursor-pointer"
                 @click="triggerFileUpload">
              <img v-if="fileInfo" :src="fileInfo" class="w-full h-full object-cover rounded" />
              <svg v-else xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="text-gray-400"><path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"/><polyline points="17 8 12 3 7 8"/><line x1="12" x2="12" y1="3" y2="15"/></svg>
            </div>
            <input type="file" ref="fileInput" style="display:none" @change="handleFileUpload" />
          </div>
        </el-form-item>
        <el-form-item label="标题">
          <el-input v-model="publishForm.title" placeholder="请输入标题"></el-input>
        </el-form-item>
        <el-form-item label="内容">
          <el-input type="textarea" v-model="publishForm.content" placeholder="请输入内容" :rows="4"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showPublishDialog = false">取消</el-button>
        <el-button type="primary" @click="submitKnowledge">发布</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import { apiClient } from "../api/apiService.js";
import { ElMessage, ElDialog, ElForm, ElFormItem, ElInput, ElButton } from "element-plus";
import { useStore } from "vuex";
import { getImageUrl } from "../utils/imageUtils.js";

const store = useStore();

const knowledgeList = ref([]);
const showPublishDialog = ref(false);
const fileInfo = ref(null);
const fileInput = ref(null);

const publishForm = reactive({
  knowledgeId: null,
  title: "",
  content: "",
  picPath: "",
});

const getImgUrl = (picPath) => {
  return getImageUrl(picPath);
};

onMounted(async () => {
  await loadKnowledgeList();
});

const loadKnowledgeList = async () => {
  try {
    const response = await apiClient.get("/knowledge/selectByUsername");
    if (response.flag && response.data) {
      knowledgeList.value = response.data;
    }
  } catch (error) {
    console.error("查询知识列表失败", error);
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
      const response = await apiClient.post("/file/upload/knowledge", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      if (response.flag && response.data) {
        publishForm.picPath = response.data;
      }
    } catch (error) {
      console.error("上传图片失败", error);
      ElMessage.error("上传图片失败");
    }
  }
};

const editKnowledge = (knowledge) => {
  publishForm.knowledgeId = knowledge.knowledgeId;
  publishForm.title = knowledge.title;
  publishForm.content = knowledge.content;
  publishForm.picPath = knowledge.picPath;
  fileInfo.value = getImgUrl(knowledge.picPath);
  showPublishDialog.value = true;
};

const deleteKnowledge = async (id) => {
  try {
    const response = await apiClient.delete(`/knowledge/${id}`);
    if (response.flag) {
      ElMessage.success("删除成功");
      await loadKnowledgeList();
    } else {
      ElMessage.error(response.message || "删除失败");
    }
  } catch (error) {
    console.error("删除知识失败", error);
    ElMessage.error("删除失败");
  }
};

const submitKnowledge = async () => {
  if (!publishForm.title || !publishForm.content) {
    ElMessage.warning("请填写标题和内容");
    return;
  }

  try {
    const param = {
      knowledgeId: publishForm.knowledgeId,
      title: publishForm.title,
      content: publishForm.content,
      picPath: publishForm.picPath,
    };

    let response;
    if (publishForm.knowledgeId) {
      response = await apiClient.put(`/knowledge/${publishForm.knowledgeId}`, param);
    } else {
      response = await apiClient.post("/knowledge", param);
    }

    if (response.flag) {
      ElMessage.success(publishForm.knowledgeId ? "修改成功" : "发布成功");
      showPublishDialog.value = false;
      resetForm();
      await loadKnowledgeList();
    } else {
      ElMessage.error(response.message || "操作失败");
    }
  } catch (error) {
    console.error("提交知识失败", error);
    ElMessage.error("操作失败");
  }
};

const resetForm = () => {
  publishForm.knowledgeId = null;
  publishForm.title = "";
  publishForm.content = "";
  publishForm.picPath = "";
  fileInfo.value = null;
};
</script>

<style scoped>
.line-clamp-3 {
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>