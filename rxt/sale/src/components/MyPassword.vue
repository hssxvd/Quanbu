<template>
  <div class="max-w-md mx-auto pr-48">
    <div class="space-y-6">
      <div class="space-y-1">
        <label class="block text-gray-700 font-medium">当前密码</label>
        <input
          type="password"
          v-model="passwordForm.currentPassword"
          placeholder="请输入当前密码"
          class="w-full border border-gray-300 rounded-md px-4 py-3 focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent"
        />
      </div>

      <div class="space-y-1">
        <label class="block text-gray-700 font-medium">新密码</label>
        <input
          type="password"
          v-model="passwordForm.newPassword"
          placeholder="请输入新密码"
          class="w-full border border-gray-300 rounded-md px-4 py-3 focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent"
        />
        <p class="text-xs text-gray-500 mt-1">
          密码必须以字母开头，长度在6-16之间，只能包含英文字母、数字和下划线
        </p>
      </div>

      <div class="space-y-1">
        <label class="block text-gray-700 font-medium">确认新密码</label>
        <input
          type="password"
          v-model="passwordForm.confirmPassword"
          placeholder="请再次输入新密码"
          class="w-full border border-gray-300 rounded-md px-4 py-3 focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent"
        />
      </div>

      <div class="pt-4">
        <button
          @click="updatePassword"
          class="w-full bg-green-700 hover:bg-green-800 text-white font-medium py-3 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-offset-2 transition-colors"
        >
          确认修改
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import { apiClient } from "../api/apiService.js";
import { ElMessage } from "element-plus";

const passwordForm = reactive({
  currentPassword: "",
  newPassword: "",
  confirmPassword: ""
});

const updatePassword = async () => {
  if (!passwordForm.currentPassword) {
    ElMessage.warning("请输入当前密码");
    return;
  }
  if (!passwordForm.newPassword) {
    ElMessage.warning("请输入新密码");
    return;
  }
  if (!passwordForm.confirmPassword) {
    ElMessage.warning("请再次输入新密码");
    return;
  }
  if (passwordForm.newPassword !== passwordForm.confirmPassword) {
    ElMessage.warning("两次输入的新密码不一致");
    return;
  }

  const passwordRegex = /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/;
  if (!passwordRegex.test(passwordForm.newPassword)) {
    ElMessage.warning("密码格式不正确，请确保以字母开头，长度6-16位，只包含字母、数字和下划线");
    return;
  }

  try {
    const param = {
      oldPassword: passwordForm.currentPassword,
      newPassword: passwordForm.newPassword
    };

    const response = await apiClient.post("/user/loginUpdatePassword", param, {
      headers: {
        "Content-Type": "application/json",
        Authorization: window.localStorage.token,
      },
    });

    if (response.flag) {
      ElMessage.success("密码修改成功");
      passwordForm.currentPassword = "";
      passwordForm.newPassword = "";
      passwordForm.confirmPassword = "";
    } else {
      ElMessage.error("密码修改失败，请检查当前密码是否正确");
    }
  } catch (error) {
    console.error("密码修改失败", error);
    ElMessage.error("密码修改失败");
  }
};
</script>

<style scoped>
</style>