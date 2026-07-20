<template>
  <el-dialog v-model="visible" title="专家咨询" width="600px">
    <el-form :model="form" label-width="80px">
      <el-form-item label="标题" required>
        <el-input v-model="form.title" placeholder="请输入咨询标题" />
      </el-form-item>
      <el-form-item label="咨询内容" required>
        <el-input
          v-model="form.content"
          type="textarea"
          :rows="4"
          placeholder="请详细描述您的问题"
        />
      </el-form-item>
      <el-form-item label="农作物" required>
        <el-input v-model="form.crop" placeholder="请输入相关农作物" />
      </el-form-item>
      <el-form-item label="联系方式" required>
        <el-input v-model="form.contact" placeholder="请输入您的联系方式" />
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
  title: "",
  content: "",
  crop: "",
  contact: "",
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
    title: "",
    content: "",
    crop: "",
    contact: ""
  };
};

const handleCancel = () => {
  visible.value = false;
  resetForm();
};

const handleSubmit = async () => {
  if (
    !form.value.title ||
    !form.value.content ||
    !form.value.crop ||
    !form.value.contact
  ) {
    ElMessage.error("请填写必填项");
    return;
  }
  try {
    const param = ref({
      title: form.value.title,
      plantName: form.value.crop,
      phone: form.value.contact,
      expertName: props.expertName,
      status: 0,
      question: form.value.content,
    });
    //增加咨询
    const response = await apiClient.post(`/question/add`, param.value);

    if (response.flag) {
      ElMessage.success("咨询成功，请耐心等待专家答复。");
      visible.value = false;
    } else {
      ElMessage.error("咨询失败，请重试。");
    }
  }  catch (error) {
    console.error('咨询失败，请重试:', error);
  }
};

defineExpose({
  open,
});


</script>