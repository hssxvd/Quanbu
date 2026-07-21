<template>
  <el-dialog v-model="visible" title="专家预约" width="600px">
    <el-form :model="form" label-width="120px">
      <el-form-item label="农作物" required>
        <el-input v-model="form.crop" placeholder="请输入相关农作物" />
      </el-form-item>
      <el-form-item label="作物详细信息" required>
        <el-input
          v-model="form.cropIssues"
          type="textarea"
          :rows="3"
          placeholder="请描述农作物详细信息"
        />
      </el-form-item>
      <el-form-item label="生长情况" required>
        <el-input v-model="form.plantingConditions" placeholder="请输入农作物生长情况" />
      </el-form-item>
      <el-form-item label="土壤条件" required>
        <el-input v-model="form.soilConditions" placeholder="请输入土壤条件" />
      </el-form-item>
      <el-form-item label="种植面积（亩）" required>
        <el-input v-model="form.plantingArea" placeholder="请输入种植面积" />
      </el-form-item>
      <el-form-item label="联系方式" required>
        <el-input v-model="form.contact" placeholder="请输入您的联系方式" />
      </el-form-item>
      <el-form-item label="地址" required>
        <el-input v-model="form.address" placeholder="请输入您的地址" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleCancel">取消</el-button>
        <el-button type="primary" @click="handleSubmit"> 确认 </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref } from "vue";
import { ElMessage } from "element-plus";
import { apiClient } from "../api/apiService.js";

const visible = ref(false);
const form = ref({
  crop: "",
  cropIssues: "",
  plantingConditions: "",
  soilConditions: "",
  plantingArea: "",
  contact: "",
  address: ""
});

const props = defineProps({
  expertUserName: {
    type: String,
    default: "",
  },
});

const open = () => {
  visible.value = true;
};

const resetForm = () => {
  form.value = {
    crop: "",
    cropIssues: "",
    plantingConditions: "",
    soilConditions: "",
    plantingArea: "",
    contact: "",
    address: ""
  };
};

const handleCancel = () => {
  visible.value = false;
  resetForm();
};

const handleSubmit = async () => {
  if (!form.value.crop || !form.value.crop.trim()) {
    ElMessage.warning("请输入农作物名称");
    return;
  }
  if (form.value.crop.trim().length > 50) {
    ElMessage.warning("农作物名称不能超过50个字符");
    return;
  }

  if (!form.value.cropIssues || !form.value.cropIssues.trim()) {
    ElMessage.warning("请输入作物详细信息");
    return;
  }
  if (form.value.cropIssues.trim().length > 500) {
    ElMessage.warning("作物详细信息不能超过500个字符");
    return;
  }

  if (!form.value.plantingConditions || !form.value.plantingConditions.trim()) {
    ElMessage.warning("请输入农作物生长情况");
    return;
  }
  if (form.value.plantingConditions.trim().length > 200) {
    ElMessage.warning("农作物生长情况不能超过200个字符");
    return;
  }

  if (!form.value.soilConditions || !form.value.soilConditions.trim()) {
    ElMessage.warning("请输入土壤条件");
    return;
  }
  if (form.value.soilConditions.trim().length > 200) {
    ElMessage.warning("土壤条件不能超过200个字符");
    return;
  }

  if (!form.value.plantingArea || !form.value.plantingArea.trim()) {
    ElMessage.warning("请输入种植面积");
    return;
  }
  const areaRegex = /^\d+(\.\d+)?$/;
  if (!areaRegex.test(form.value.plantingArea.trim())) {
    ElMessage.warning("种植面积必须是数字");
    return;
  }
  const areaNum = parseFloat(form.value.plantingArea.trim());
  if (areaNum <= 0 || areaNum > 999999) {
    ElMessage.warning("种植面积必须大于0且不超过999999亩");
    return;
  }

  const phoneRegex = /^1[3-9]\d{9}$/;
  if (!form.value.contact || !form.value.contact.trim()) {
    ElMessage.warning("请输入联系方式");
    return;
  }
  if (!phoneRegex.test(form.value.contact.trim())) {
    ElMessage.warning("请输入正确的手机号码格式");
    return;
  }

  if (!form.value.address || !form.value.address.trim()) {
    ElMessage.warning("请输入地址");
    return;
  }
  if (form.value.address.trim().length > 200) {
    ElMessage.warning("地址不能超过200个字符");
    return;
  }

  try {
    const param = ref({
      plantName: form.value.crop,
      plantDetail: form.value.cropIssues,
      plantCondition: form.value.plantingConditions,
      soilCondition: form.value.soilConditions,
      area: form.value.plantingArea,
      phone: form.value.contact,
      address: form.value.address,
      expertName: props.expertUserName,
      status: 0
    });
    //增加预约
    const response = await apiClient.post(`/reserve/add`, param.value, {
      headers: {
        "Content-Type": "application/json",
        Authorization: window.localStorage.token,
      },
    });

    if (response.flag) {
      ElMessage.success("预约成功，请耐心等待专家答复。");
      visible.value = false;
    } else {
      ElMessage.error("预约失败，请重试。");
    }
  }  catch (error) {
    console.error('预约失败，请重试:', error);
  }
};

defineExpose({
  open,
});


</script>

<style scoped>
.el-input {
  width: 70%;
}
</style>