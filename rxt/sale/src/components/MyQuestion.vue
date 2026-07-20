<template>
  <div class="p-4">
    <h2 class="text-xl font-bold text-gray-800 mb-6">问答管理</h2>

    <div class="space-y-4">
      <div v-for="item in questionList" :key="item.id" 
           class="border border-gray-200 rounded-lg p-4 shadow-sm">
        <div class="flex justify-between items-start mb-3">
          <div class="flex items-center space-x-3">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="text-green-600"><circle cx="12" cy="12" r="10"/><path d="M8.5 14.5a2.5 2.5 0 0 1 3-4"/><path d="M9.5 17.5c2 0 3.5-1.2 3.5-3.5"/></svg>
            <h3 class="font-medium text-gray-800 text-lg">{{ item.title }}</h3>
          </div>
          <el-button v-if="item.status === 1" type="success" size="small" disabled>已回答</el-button>
          <el-button v-else type="warning" size="small" disabled>未回答</el-button>
        </div>

        <div class="mb-3">
          <p class="text-gray-600">{{ item.question }}</p>
        </div>

        <div v-if="item.answer" class="mb-3 p-3 bg-green-50 rounded-lg">
          <p class="text-gray-700">{{ item.answer }}</p>
        </div>

        <div class="flex justify-between items-center text-sm text-gray-500 mb-3">
          <span>作者: {{ item.questioner }}</span>
          <span>农作物: {{ item.plantName }}</span>
        </div>

        <div class="flex justify-end space-x-2">
          <el-button v-if="$store.getters.isExpert" size="small" @click="editQuestion(item)">编辑</el-button>
          <el-button v-if="!$store.getters.isExpert" size="small" type="danger" @click="deleteQuestion(item.id)">删除</el-button>
        </div>
      </div>
    </div>

    <div v-if="questionList.length === 0" class="px-3 py-10 text-center text-gray-500">
      暂无数据
    </div>

    <el-dialog title="编辑问答" v-model="showEditDialog" width="500px">
      <el-form :model="currentQuestion" label-width="80px">
        <el-form-item label="问题标题">
          <el-input v-model="currentQuestion.title" disabled></el-input>
        </el-form-item>
        <el-form-item label="问题内容">
          <el-input type="textarea" v-model="currentQuestion.question" disabled :rows="3"></el-input>
        </el-form-item>
        <el-form-item label="回答内容">
          <el-input type="textarea" v-model="editingAnswer" placeholder="请输入回答内容" :rows="4"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showEditDialog = false">取消</el-button>
        <el-button type="primary" @click="submitEdit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { apiClient } from "../api/apiService.js";
import { ElMessage, ElDialog, ElForm, ElFormItem, ElInput, ElButton } from "element-plus";
import { useStore } from "vuex";

const store = useStore();

const questionList = ref([]);
const showEditDialog = ref(false);
const currentQuestion = ref({});
const editingAnswer = ref("");

onMounted(async () => {
  await loadQuestionList();
});

const loadQuestionList = async () => {
  const role = store.getters.isExpert ? "expert" : "user";
  try {
    const response = await apiClient.get(`/question/selectByKind/${role}`);
    if (response.flag && response.data) {
      questionList.value = response.data;
    }
  } catch (error) {
    console.error("查询问答列表失败", error);
  }
};

const editQuestion = (item) => {
  currentQuestion.value = { ...item };
  editingAnswer.value = item.answer || "";
  showEditDialog.value = true;
};

const deleteQuestion = async (id) => {
  try {
    const response = await apiClient.delete(`/question/delete/${id}`);
    if (response.flag) {
      ElMessage.success("删除成功");
      await loadQuestionList();
    } else {
      ElMessage.error(response.message || "删除失败");
    }
  } catch (error) {
    console.error("删除问答失败", error);
    ElMessage.error("删除失败");
  }
};

const submitEdit = async () => {
  if (!editingAnswer.value.trim()) {
    ElMessage.warning("请输入回答内容");
    return;
  }

  try {
    const param = {
      id: currentQuestion.value.id,
      title: currentQuestion.value.title,
      plantName: currentQuestion.value.plantName,
      phone: currentQuestion.value.phone,
      questioner: currentQuestion.value.questioner,
      expertName: currentQuestion.value.expertName,
      status: 1,
      question: currentQuestion.value.question,
      answer: editingAnswer.value,
    };

    const response = await apiClient.put("/question/update", param);
    if (response.flag) {
      ElMessage.success("修改成功");
      showEditDialog.value = false;
      await loadQuestionList();
    } else {
      ElMessage.error(response.message || "操作失败");
    }
  } catch (error) {
    console.error("提交问答失败", error);
    ElMessage.error("操作失败");
  }
};
</script>