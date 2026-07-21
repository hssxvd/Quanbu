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
  if (!form.value.title || !form.value.title.trim()) {
    ElMessage.warning("请输入咨询标题");
    return;
  }
  if (form.value.title.trim().length > 100) {
    ElMessage.warning("咨询标题不能超过100个字符");
    return;
  }

  if (!form.value.content || !form.value.content.trim()) {
    ElMessage.warning("请输入咨询内容");
    return;
  }
  if (form.value.content.trim().length < 10) {
    ElMessage.warning("咨询内容不少于10个字");
    return;
  }
  if (form.value.content.trim().length > 500) {
    ElMessage.warning("咨询内容不能超过500个字符");
    return;
  }

  if (!form.value.crop || !form.value.crop.trim()) {
    ElMessage.warning("请输入农作物名称");
    return;
  }
  if (form.value.crop.trim().length > 50) {
    ElMessage.warning("农作物名称不能超过50个字符");
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

  try {
    const param = ref({
      title: form.value.title,
      plantName: form.value.crop,
      phone: form.value.contact,
      expertName: props.expertUserName,
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