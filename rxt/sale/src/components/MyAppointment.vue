<template>
  <div class="p-4">
    <h2 class="text-xl font-bold text-gray-800 mb-6">预约管理</h2>

    <div class="space-y-4">
      <div v-for="item in appointmentList" :key="item.id" 
           class="border border-gray-200 rounded-lg p-4 shadow-sm">
        <div class="flex justify-between items-start mb-3">
          <div class="flex items-center space-x-3">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="text-green-600"><rect x="3" y="4" width="18" height="18" rx="2" ry="2"/><line x1="16" x2="16" y1="2" y2="6"/><line x1="8" x2="8" y1="2" y2="6"/><line x1="3" x2="21" y1="10" y2="10"/></svg>
            <h3 class="font-medium text-gray-800 text-lg">农作物: {{ item.plantName }}</h3>
          </div>
          <el-button v-if="item.status === 1" type="success" size="small" disabled>已答复</el-button>
          <el-button v-else type="warning" size="small" disabled>未答复</el-button>
        </div>

        <div class="space-y-2 mb-3">
          <p><span class="text-gray-500">农作物详细信息:</span> {{ item.plantDetail }}</p>
          <p><span class="text-gray-500">生长情况:</span> {{ item.plantCondition }}</p>
          <p><span class="text-gray-500">土壤条件:</span> {{ item.soilCondition }}</p>
          <p><span class="text-gray-500">种植面积:</span> {{ item.area }}亩</p>
          <p><span class="text-gray-500">联系方式:</span> {{ item.phone }}</p>
          <p><span class="text-gray-500">地址:</span> {{ item.address }}</p>
          <p v-if="item.message" class="text-gray-600"><span class="text-gray-500">留言:</span> {{ item.message }}</p>
        </div>

        <div v-if="item.answer" class="mb-3 p-3 bg-green-50 rounded-lg">
          <p class="text-gray-700">{{ item.answer }}</p>
        </div>

        <div class="flex justify-between items-center text-sm text-gray-500 mb-3">
          <span>提问者: {{ item.questioner }}</span>
        </div>

        <div class="flex justify-end space-x-2">
          <el-button size="small" @click="editAppointment(item)">编辑</el-button>
        </div>
      </div>
    </div>

    <div v-if="appointmentList.length === 0" class="px-3 py-10 text-center text-gray-500">
      暂无数据
    </div>

    <el-dialog title="编辑预约" v-model="showEditDialog" width="500px">
      <el-form :model="currentAppointment" label-width="100px">
        <el-form-item label="回答">
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

const appointmentList = ref([]);
const showEditDialog = ref(false);
const currentAppointment = ref({});
const editingAnswer = ref("");

onMounted(async () => {
  await loadAppointmentList();
});

const loadAppointmentList = async () => {
  const role = store.getters.isExpert ? "expert" : "user";
  try {
    const response = await apiClient.get(`/reserve/selectByKind/${role}`);
    if (response.flag && response.data) {
      appointmentList.value = response.data;
    }
  } catch (error) {
    console.error("查询预约列表失败", error);
  }
};

const editAppointment = (item) => {
  currentAppointment.value = { ...item };
  editingAnswer.value = item.answer || "";
  showEditDialog.value = true;
};

const submitEdit = async () => {
  if (!editingAnswer.value.trim()) {
    ElMessage.warning("请输入回答内容");
    return;
  }

  try {
    const param = {
      id: currentAppointment.value.id,
      expertName: currentAppointment.value.expertName,
      questioner: currentAppointment.value.questioner,
      area: currentAppointment.value.area,
      address: currentAppointment.value.address,
      plantName: currentAppointment.value.plantName,
      soilCondition: currentAppointment.value.soilCondition,
      plantCondition: currentAppointment.value.plantCondition,
      plantDetail: currentAppointment.value.plantDetail,
      phone: currentAppointment.value.phone,
      message: currentAppointment.value.message,
      answer: editingAnswer.value,
      status: 1,
    };

    const response = await apiClient.put("/reserve/update", param);
    if (response.flag) {
      ElMessage.success("修改成功");
      showEditDialog.value = false;
      await loadAppointmentList();
    } else {
      ElMessage.error(response.message || "操作失败");
    }
  } catch (error) {
    console.error("提交预约失败", error);
    ElMessage.error("操作失败");
  }
};
</script>