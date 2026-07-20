<template>
  <div class="max-w-2xl mx-auto pr-48">
    <el-form :model="expertForm" label-width="100px" class="space-y-4">
      <el-form-item label="真实姓名">
        <el-input v-model="expertForm.realName" placeholder="请输入真实姓名"></el-input>
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="expertForm.phone" placeholder="请输入手机号"></el-input>
      </el-form-item>
      <el-form-item label="从事专业">
        <el-input v-model="expertForm.profession" placeholder="请输入从事专业"></el-input>
      </el-form-item>
      <el-form-item label="职位">
        <el-input v-model="expertForm.position" placeholder="请输入职位"></el-input>
      </el-form-item>
      <el-form-item label="所属单位">
        <el-input v-model="expertForm.belong" placeholder="请输入所属单位"></el-input>
      </el-form-item>
      <el-form-item class="text-center">
        <el-button type="primary" @click="saveExpert" class="bg-green-600 hover:bg-green-700">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { reactive, onMounted } from "vue";
import { apiClient } from "../api/apiService.js";
import { ElMessage, ElForm, ElFormItem, ElInput, ElButton } from "element-plus";

const expertForm = reactive({
  userName: "",
  realName: "",
  phone: "",
  profession: "",
  position: "",
  belong: "",
});

onMounted(async () => {
  await loadExpertInfo();
});

const loadExpertInfo = async () => {
  try {
    const response = await apiClient.get("/user/searchexpert", {
      headers: {
        Authorization: window.localStorage.token,
      },
    });
    if (response.flag && response.data) {
      expertForm.userName = response.data.userName || "";
      expertForm.realName = response.data.realName || "";
      expertForm.phone = response.data.phone || "";
      expertForm.profession = response.data.profession || "";
      expertForm.position = response.data.position || "";
      expertForm.belong = response.data.belong || "";
    }
  } catch (error) {
    console.error("查询专家信息失败", error);
  }
};

const saveExpert = async () => {
  try {
    const response = await apiClient.post("/user/addUpdexpert", expertForm, {
      headers: {
        "Content-Type": "application/json",
        Authorization: window.localStorage.token,
      },
    });
    if (response.flag) {
      ElMessage.success("修改专家信息成功");
    } else {
      ElMessage.error(response.message || "修改专家信息失败");
    }
  } catch (error) {
    console.error("保存专家信息失败", error);
    ElMessage.error("保存专家信息失败");
  }
};
</script>

<style scoped>
</style>