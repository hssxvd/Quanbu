<template>
  <div class="min-h-screen bg-gray-50 py-12">
    <div class="container mx-auto px-6">
      <div class="max-w-4xl mx-auto">
        <h1 class="text-4xl font-bold text-center text-gray-800 mb-8">联系方式</h1>
        
        <div class="bg-white rounded-lg shadow-md p-8 mb-8">
          <h2 class="text-2xl font-bold text-green-800 mb-6">联系我们</h2>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
            <div class="space-y-6">
              <div class="flex items-start">
                <div class="w-12 h-12 bg-green-100 rounded-full flex items-center justify-center mr-4 flex-shrink-0">
                  <span class="text-xl">📞</span>
                </div>
                <div>
                  <h3 class="font-bold text-gray-800 mb-1">客服热线</h3>
                  <p class="text-gray-600">400-888-8888</p>
                  <p class="text-gray-500 text-sm">服务时间：9:00 - 18:00</p>
                </div>
              </div>
              
              <div class="flex items-start">
                <div class="w-12 h-12 bg-green-100 rounded-full flex items-center justify-center mr-4 flex-shrink-0">
                  <span class="text-xl">✉️</span>
                </div>
                <div>
                  <h3 class="font-bold text-gray-800 mb-1">电子邮箱</h3>
                  <p class="text-gray-600">service@rongxiaotong.com</p>
                </div>
              </div>
              
              <div class="flex items-start">
                <div class="w-12 h-12 bg-green-100 rounded-full flex items-center justify-center mr-4 flex-shrink-0">
                  <span class="text-xl">📍</span>
                </div>
                <div>
                  <h3 class="font-bold text-gray-800 mb-1">公司地址</h3>
                  <p class="text-gray-600">山东省济南市高新区科技园区A座</p>
                </div>
              </div>
              
              <div class="flex items-start">
                <div class="w-12 h-12 bg-green-100 rounded-full flex items-center justify-center mr-4 flex-shrink-0">
                  <span class="text-xl">💬</span>
                </div>
                <div>
                  <h3 class="font-bold text-gray-800 mb-1">在线客服</h3>
                  <p class="text-gray-600">工作时间内可在线咨询</p>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="bg-white rounded-lg shadow-md p-8">
          <h2 class="text-2xl font-bold text-green-800 mb-6">意见反馈</h2>
          <el-form :model="form" label-width="80px">
            <el-form-item label="您的姓名" required>
              <el-input v-model="form.name" placeholder="请输入姓名" />
            </el-form-item>
            <el-form-item label="联系电话" required>
              <el-input v-model="form.phone" placeholder="请输入联系电话" />
            </el-form-item>
            <el-form-item label="反馈内容" required>
              <el-input v-model="form.content" type="textarea" :rows="4" placeholder="请输入您的意见或建议" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm">提交反馈</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { ElMessage } from "element-plus";

const form = ref({
  name: "",
  phone: "",
  content: ""
});

const submitForm = () => {
  if (!form.value.name || !form.value.name.trim()) {
    ElMessage.warning("请输入姓名");
    return;
  }
  if (!form.value.phone || !form.value.phone.trim()) {
    ElMessage.warning("请输入联系电话");
    return;
  }
  if (!form.value.content || !form.value.content.trim()) {
    ElMessage.warning("请输入反馈内容");
    return;
  }
  ElMessage.success("反馈提交成功，我们会尽快处理");
  form.value = {
    name: "",
    phone: "",
    content: ""
  };
};
</script>
