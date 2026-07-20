<template>
  <div class="max-w-2xl mx-auto pr-48">
    <div class="space-y-4">
      <div class="flex justify-center">
        <el-form label-width="80px">
          <el-upload
            ref="uploadRef"
            action="#"
            list-type="picture-card"
            :on-change="handleImageUpload"
            :on-remove="() => avatar.value = ''"
            :auto-upload="false"
            :limit="1"
            :file-list="
              avatar
                ? [
                    {
                      name: 'avatar',
                      url: $store.state.imgShowRoad + '/file/avatar/' + avatar,
                    },
                  ]
                : []
            "
            class="[&_.el-upload-list__item]:!rounded-full [&_.el-upload--picture-card]:!rounded-full"
          >
            <el-icon class="text-2xl"><PlusIcon /></el-icon>
          </el-upload>
        </el-form>
      </div>

      <div class="flex items-center">
        <label class="w-20 text-right mr-4 text-gray-600">昵称:</label>
        <input
          type="text"
          v-model="nickname"
          class="flex-1 border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-1 focus:ring-green-500"
        />
      </div>

      <div class="flex items-center">
        <label class="w-20 text-right mr-4 text-gray-600">姓名:</label>
        <input
          type="text"
          v-model="name"
          class="flex-1 border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-1 focus:ring-green-500"
        />
      </div>

      <div class="flex items-center">
        <label class="w-20 text-right mr-4 text-gray-600">手机号:</label>
        <input
          type="text"
          v-model="phone"
          class="flex-1 border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-1 focus:ring-green-500"
        />
      </div>

      <div class="flex items-center">
        <label class="w-20 text-right mr-4 text-gray-600">身份证:</label>
        <input
          type="text"
          v-model="idCard"
          class="flex-1 border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-1 focus:ring-green-500"
        />
      </div>

      <div class="flex items-center">
        <label class="w-20 text-right mr-4 text-gray-600">地址:</label>
        <input
          type="text"
          v-model="address"
          class="flex-1 border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-1 focus:ring-green-500"
        />
      </div>

      <div class="flex justify-center mt-8">
        <button
          @click="saveProfile"
          class="bg-green-700 hover:bg-green-800 text-white font-medium py-2 px-12 rounded-full focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-offset-2"
        >
          保存
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { PlusIcon } from "lucide-vue-next";
import { apiClient } from "../api/apiService.js";
import { ElMessage } from "element-plus";
import { useStore } from "vuex";

const store = useStore();

const userName = ref("");
const nickname = ref("");
const name = ref("");
const phone = ref("");
const idCard = ref("");
const address = ref("");
const avatar = ref("");

const image = ref([]);
const imageDB = ref('');

onMounted(async () => {
  await selectPersMsg();
});

const handleImageUpload = (file) => {
  image.value.push(file);
};

const saveProfile = async () => {
  try {
    for (const file of image.value) {
      const formData = new FormData();
      formData.append("file", file.raw);

      const response = await apiClient.post(
        `${store.state.fileUploadRoad}/file/upload/avatar`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
      if (response.flag) {
        imageDB.value = response.data.split("/")[1];
      } else {
        ElMessage.error("修改个人信息失败，请重试。");
        return;
      }
    }

    if(!imageDB.value){
      imageDB.value = avatar.value;
    }

    const param = {
      realName: name.value,
      nickName: nickname.value,
      phone: phone.value,
      identityNum: idCard.value,
      address: address.value,
      avatar: imageDB.value,
    };

    const response = await apiClient.post(
      `/user/loginUpdateByUsername`,
      param
    );

    if (response.flag) {
      ElMessage.success("修改个人信息成功。");
      image.value = [];
      imageDB.value = '';
    } else {
      ElMessage.error("修改个人信息失败，请重试。");
    }
  } catch (error) {
    console.error("修改个人信息失败，请重试：", error);
    ElMessage.error("修改个人信息失败，请重试。");
  }
};

const selectPersMsg = async () => {
  try {
    const response = await apiClient.get("/user/loginSelectByUsername");
    console.log("请求成功", response.data);
    if (response.flag == true) {
      userName.value = response.data.userName;
      nickname.value = response.data.nickName;
      name.value = response.data.realName;
      phone.value = response.data.phone;
      idCard.value = response.data.identityNum;
      address.value = response.data.address;
      avatar.value = response.data.avatar;
    } else {
      console.error("请求失败");
    }
  } catch (error) {
    console.error("请求失败", error);
  }
};
</script>

<style scoped>
:deep(.el-upload-list__item-thumbnail),
:deep(.el-upload-list__item img),
:deep(.el-upload-list__item) {
  border-radius: 50% !important;
  object-fit: cover;
  border: none !important;
  background-color: #007029 !important;
}
</style>