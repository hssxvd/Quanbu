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

// 使用 defineProps 接收父组件传递的值
const props = defineProps({
  expertName: {
    type: String,
    default: "默认值",
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
  if (
    !form.value.crop ||
    !form.value.cropIssues ||
    !form.value.plantingArea ||
    !form.value.contact ||
    !form.value.address ||
    !form.value.plantingConditions ||
    !form.value.soilConditions
  ) {
    ElMessage.error("请填写所有必填项");
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
      expertName: props.expertName,
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