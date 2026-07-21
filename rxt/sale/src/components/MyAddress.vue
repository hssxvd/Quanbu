<template>
  <div class="max-w-2xl mx-auto pr-48">
    <div class="space-y-4">
      <div
        v-for="address in addressList"
        :key="address.id"
        class="border border-gray-200 rounded-lg p-4 hover:shadow-md transition-shadow"
      >
        <div class="flex items-center justify-between mb-2">
          <div class="flex items-center space-x-2">
            <span class="font-medium text-gray-800">{{ address.consignee }}</span>
            <span class="text-gray-600">{{ address.phone }}</span>
          </div>
          <div class="flex items-center space-x-2">
            <span
              v-if="address.isDefault === 1"
              class="text-green-700 text-sm font-medium bg-green-50 px-2 py-0.5 rounded"
            >
              默认
            </span>
            <button
              @click="editAddress(address)"
              class="text-gray-500 hover:text-green-700 p-1"
            >
              <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M17 3a2.85 2.83 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5Z"/><path d="m15 5 4 4"/></svg>
            </button>
            <button
              @click="deleteAddress(address.id)"
              class="text-gray-500 hover:text-red-600 p-1"
            >
              <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M3 6h18"/><path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6"/><path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2"/></svg>
            </button>
          </div>
        </div>
        <p class="text-gray-600 text-sm">{{ address.province }} {{ address.city }} {{ address.district }} {{ address.addressDetail }}</p>
      </div>

      <button
        @click="addAddress"
        class="w-full border-2 border-dashed border-gray-300 rounded-lg p-4 text-gray-500 hover:border-green-500 hover:text-green-700 transition-colors flex items-center justify-center space-x-2"
      >
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M5 12h14"/><path d="M12 5v14"/></svg>
        <span>+ 添加新地址</span>
      </button>
    </div>

    <el-dialog
      :title="currentAddress.id ? '编辑地址' : '添加地址'"
      v-model="dialogVisible"
      width="500px"
    >
      <el-form :model="currentAddress" label-width="80px">
        <el-form-item label="收货人">
          <el-input v-model="currentAddress.consignee" placeholder="请输入收货人"></el-input>
        </el-form-item>
        <el-form-item label="手机号码">
          <el-input v-model="currentAddress.phone" placeholder="请输入手机号码"></el-input>
        </el-form-item>
        <el-form-item label="省">
          <el-select v-model="currentAddress.province" placeholder="请选择省份" @change="handleProvinceChange">
            <el-option v-for="item in provinces" :key="item.value" :label="item.label" :value="item.label"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="市">
          <el-select v-model="currentAddress.city" placeholder="请选择城市" @change="handleCityChange" :disabled="!currentAddress.province">
            <el-option v-for="item in cityList" :key="item.value" :label="item.label" :value="item.label"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="县">
          <el-select v-model="currentAddress.district" placeholder="请选择区县" :disabled="!currentAddress.city">
            <el-option v-for="item in districtList" :key="item.value" :label="item.label" :value="item.label"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="详细地址">
          <el-input v-model="currentAddress.addressDetail" type="textarea" placeholder="请输入详细地址"></el-input>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="isDefaultChecked">设为默认收货地址</el-checkbox>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveAddress">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, watch } from "vue";
import { apiClient } from "../api/apiService.js";
import { ElMessage, ElDialog, ElForm, ElFormItem, ElInput, ElCheckbox, ElButton, ElSelect, ElOption } from "element-plus";
import { provinces, cities, districts } from "../data/areaData.js";

const addressList = ref([]);
const dialogVisible = ref(false);
const isDefaultChecked = ref(false);

const cityList = ref([]);
const districtList = ref([]);

const currentAddress = reactive({
  id: null,
  consignee: "",
  phone: "",
  province: "",
  city: "",
  district: "",
  addressDetail: "",
  isDefault: 0,
});

onMounted(async () => {
  await selectAddresses();
});

const selectAddresses = async () => {
  try {
    const response = await apiClient.get("/address/list", {
      headers: {
        Authorization: window.localStorage.token,
      },
    });
    if (response.flag) {
      addressList.value = response.data;
    } else {
      ElMessage.error("查询地址失败");
    }
  } catch (error) {
    console.error("查询地址失败", error);
  }
};

const handleProvinceChange = (value) => {
  currentAddress.city = "";
  currentAddress.district = "";
  districtList.value = [];
  const province = provinces.find(p => p.label === value);
  if (province) {
    cityList.value = cities[province.value] || [];
  } else {
    cityList.value = [];
  }
};

const handleCityChange = (value) => {
  currentAddress.district = "";
  const province = provinces.find(p => p.label === currentAddress.province);
  if (province) {
    const city = cities[province.value]?.find(c => c.label === value);
    if (city) {
      districtList.value = districts[city.value] || [];
    } else {
      districtList.value = [];
    }
  } else {
    districtList.value = [];
  }
};

const addAddress = () => {
  currentAddress.id = null;
  currentAddress.consignee = "";
  currentAddress.phone = "";
  currentAddress.province = "";
  currentAddress.city = "";
  currentAddress.district = "";
  currentAddress.addressDetail = "";
  currentAddress.isDefault = 0;
  isDefaultChecked.value = false;
  cityList.value = [];
  districtList.value = [];
  dialogVisible.value = true;
};

const editAddress = (address) => {
  currentAddress.id = address.id;
  currentAddress.consignee = address.consignee;
  currentAddress.phone = address.phone;
  currentAddress.province = address.province || "";
  currentAddress.city = address.city || "";
  currentAddress.district = address.district || "";
  currentAddress.addressDetail = address.addressDetail || "";
  currentAddress.isDefault = address.isDefault;
  isDefaultChecked.value = address.isDefault === 1;
  
  cityList.value = [];
  districtList.value = [];
  
  if (currentAddress.province) {
    const province = provinces.find(p => p.label === currentAddress.province);
    if (province) {
      cityList.value = cities[province.value] || [];
      if (currentAddress.city) {
        const city = cities[province.value]?.find(c => c.label === currentAddress.city);
        if (city) {
          districtList.value = districts[city.value] || [];
        }
      }
    }
  }
  
  dialogVisible.value = true;
};

const saveAddress = async () => {
  currentAddress.isDefault = isDefaultChecked.value ? 1 : 0;

  try {
    let response;
    if (currentAddress.id) {
      response = await apiClient.put("/address/update", currentAddress, {
        headers: {
          "Content-Type": "application/json",
          Authorization: window.localStorage.token,
        },
      });
    } else {
      response = await apiClient.post("/address/add", currentAddress, {
        headers: {
          "Content-Type": "application/json",
          Authorization: window.localStorage.token,
        },
      });
    }

    if (response.flag) {
      ElMessage.success(currentAddress.id ? "更新地址成功" : "添加地址成功");
      dialogVisible.value = false;
      await selectAddresses();
    } else {
      ElMessage.error(currentAddress.id ? "更新地址失败" : "添加地址失败");
    }
  } catch (error) {
    console.error("保存地址失败", error);
    ElMessage.error("保存地址失败");
  }
};

const deleteAddress = async (id) => {
  try {
    const response = await apiClient.delete(`/address/${id}`, {
      headers: {
        Authorization: window.localStorage.token,
        "Content-Type": "application/json",
      },
    });
    if (response.flag) {
      ElMessage.success("删除地址成功");
      await selectAddresses();
    } else {
      ElMessage.error("删除地址失败");
    }
  } catch (error) {
    console.error("删除地址失败", error);
    ElMessage.error("删除地址失败");
  }
};
</script>

<style scoped>
</style>