<template>
  <section class="py-6 bg-white border-t-2 border-gray-300">
    <div class="container w-2/3 mx-auto">
      <div class="flex items-center justify-between mb-6">
        <h1 class="text-2xl font-bold text-gray-800">购物车</h1>
        <button
          class="flex items-center text-gray-500 hover:text-gray-700"
          @click="$router.back()"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            class="h-4 w-4 mr-1"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M15 19l-7-7 7-7"
            />
          </svg>
          返回
        </button>
      </div>

      <div v-if="cartList.length === 0" class="text-center py-20 text-gray-500">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="h-16 w-16 mx-auto mb-4 text-gray-300"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="1.5"
            d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 100 4 2 2 0 000-4z"
          />
        </svg>
        <p class="text-lg">购物车是空的</p>
        <button
          class="mt-4 bg-[#007029] text-white px-6 py-2 rounded hover:bg-green-800"
          @click="$router.push('/home/agripro')"
        >
          去逛逛
        </button>
      </div>

      <div v-else>
        <div class="mb-6 border border-gray-200 rounded-lg p-4 bg-white">
          <div class="flex items-center justify-between">
            <div class="flex items-start space-x-3">
              <div class="mt-1">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-[#007029]" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
                </svg>
              </div>
              <div>
                <div class="flex items-center space-x-2">
                  <span class="text-sm text-gray-500">收货地址：</span>
                  <template v-if="selectedAddress">
                    <span class="font-medium text-gray-800">{{ selectedAddress.consignee }}</span>
                    <span class="text-gray-600">{{ selectedAddress.phone }}</span>
                  </template>
                  <template v-else>
                    <span class="text-red-500">请选择收货地址</span>
                  </template>
                </div>
                <p class="text-sm text-gray-500 mt-1" v-if="selectedAddress">
                  {{ selectedAddress.province }} {{ selectedAddress.city }} {{ selectedAddress.district }} {{ selectedAddress.addressDetail }}
                </p>
              </div>
            </div>
            <button
              class="flex-shrink-0 text-sm text-[#007029] hover:text-green-800 border border-[#007029] px-3 py-1 rounded hover:bg-green-50"
              @click="openAddressDialog"
            >
              更换地址
            </button>
          </div>
        </div>

        <div class="bg-white rounded-lg shadow-sm overflow-hidden">
          <table class="min-w-full divide-gray-200">
            <thead class="bg-gray-50">
              <tr>
                <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  商品信息
                </th>
                <th scope="col" class="px-6 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  单价
                </th>
                <th scope="col" class="px-6 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  数量
                </th>
                <th scope="col" class="px-6 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  小计
                </th>
                <th scope="col" class="px-6 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  操作
                </th>
              </tr>
            </thead>
            <tbody class="bg-white divide-gray-200">
              <tr v-for="item in cartList" :key="item.shoppingId" class="hover:bg-gray-50">
                <td class="px-6 py-4">
                  <div class="flex items-center space-x-4">
                    <img
                      :src="getImageUrl(item.picture)"
                      class="w-16 h-16 object-cover rounded"
                      @error="handleImageError"
                    />
                    <div>
                      <div class="text-sm font-medium text-gray-900">{{ item.title }}</div>
                      <div class="text-xs text-gray-500 mt-1 line-clamp-2">{{ item.content }}</div>
                    </div>
                  </div>
                </td>
                <td class="px-6 py-4 text-center text-sm text-gray-900">
                  ¥{{ item.price }}
                </td>
                <td class="px-6 py-4 text-center">
                  <div class="flex items-center justify-center space-x-2">
                    <button
                      class="w-7 h-7 rounded border border-gray-300 flex items-center justify-center text-gray-500 hover:bg-gray-100 disabled:opacity-50"
                      :disabled="item.count <= 1"
                      @click="changeCount(item, -1)"
                    >-</button>
                    <span class="w-10 text-center text-sm">{{ item.count }}</span>
                    <button
                      class="w-7 h-7 rounded border border-gray-300 flex items-center justify-center text-gray-500 hover:bg-gray-100"
                      @click="changeCount(item, 1)"
                    >+</button>
                  </div>
                </td>
                <td class="px-6 py-4 text-center text-sm font-medium text-[#007029]">
                  ¥{{ (item.price * item.count).toFixed(2) }}
                </td>
                <td class="px-6 py-4 text-center">
                  <button
                    class="text-red-500 hover:text-red-700 text-sm"
                    @click="confirmDelete(item)"
                  >删除</button>
                </td>
              </tr>
            </tbody>
          </table>

          <div class="flex justify-between items-center px-6 py-4 bg-gray-50 border-t border-gray-200">
            <span class="text-sm text-gray-500">
              共 {{ cartList.length }} 种商品
            </span>
            <div class="flex items-center space-x-6">
              <div class="flex items-center space-x-2">
                <span class="text-sm text-gray-600">合计：</span>
                <span class="text-xl font-bold text-[#007029]">¥{{ totalPrice }}</span>
              </div>
              <button
                class="bg-[#007029] text-white px-8 py-2 rounded hover:bg-green-800 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                :disabled="checkoutLoading"
                @click="handleCheckout"
              >
                {{ checkoutLoading ? '结算中...' : '结算' }}
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <el-dialog v-model="addressDialogVisible" title="选择收货地址" width="600px">
      <div class="space-y-3">
        <div
          v-for="address in addressList"
          :key="address.id"
          class="border rounded-lg p-3 cursor-pointer transition-colors"
          :class="tempSelectedAddress && tempSelectedAddress.id === address.id ? 'border-[#007029] bg-green-50' : 'border-gray-200 hover:border-gray-300'"
          @click="selectAddress(address)"
        >
          <div class="flex items-center justify-between">
            <div class="flex items-center space-x-2">
              <div
                class="w-4 h-4 rounded-full border-2 flex items-center justify-center"
                :class="tempSelectedAddress && tempSelectedAddress.id === address.id ? 'border-[#007029]' : 'border-gray-300'"
              >
                <div
                  v-if="tempSelectedAddress && tempSelectedAddress.id === address.id"
                  class="w-2 h-2 rounded-full bg-[#007029]"
                ></div>
              </div>
              <span class="font-medium text-gray-800">{{ address.consignee }}</span>
              <span class="text-gray-600">{{ address.phone }}</span>
              <span
                v-if="address.isDefault === 1"
                class="text-green-700 text-xs font-medium bg-green-50 px-2 py-0.5 rounded"
              >
                默认
              </span>
            </div>
            <div class="flex items-center space-x-2">
              <button
                class="text-gray-500 hover:text-green-700 p-1"
                @click.stop="editAddress(address)"
              >
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M17 3a2.85 2.83 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5Z"/><path d="m15 5 4 4"/></svg>
              </button>
              <button
                class="text-gray-500 hover:text-red-600 p-1"
                @click.stop="deleteAddress(address.id)"
              >
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M3 6h18"/><path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6"/><path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2"/></svg>
              </button>
            </div>
          </div>
          <p class="text-sm text-gray-500 mt-1 ml-6">{{ address.province }} {{ address.city }} {{ address.district }} {{ address.addressDetail }}</p>
        </div>

        <button
          class="w-full border-2 border-dashed border-gray-300 rounded-lg p-3 text-gray-500 hover:border-[#007029] hover:text-[#007029] transition-colors"
          @click="addNewAddress"
        >
          + 添加新地址
        </button>
      </div>
      <template #footer>
        <el-button @click="addressDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmAddress">确认</el-button>
      </template>
    </el-dialog>

    <el-dialog
      :title="formAddress.id ? '编辑地址' : '添加地址'"
      v-model="formDialogVisible"
      width="500px"
    >
      <el-form :model="formAddress" label-width="80px">
        <el-form-item label="收货人">
          <el-input v-model="formAddress.consignee" placeholder="请输入收货人" />
        </el-form-item>
        <el-form-item label="手机号码">
          <el-input v-model="formAddress.phone" placeholder="请输入手机号码" />
        </el-form-item>
        <el-form-item label="省">
          <el-select v-model="formAddress.province" placeholder="请选择省份" @change="handleProvinceChange">
            <el-option v-for="item in provinces" :key="item.value" :label="item.label" :value="item.label" />
          </el-select>
        </el-form-item>
        <el-form-item label="市">
          <el-select v-model="formAddress.city" placeholder="请选择城市" @change="handleCityChange" :disabled="!formAddress.province">
            <el-option v-for="item in cityList" :key="item.value" :label="item.label" :value="item.label" />
          </el-select>
        </el-form-item>
        <el-form-item :label="formAddress.city && districtList.length === 0 ? '区/县（无）' : '区/县'">
          <el-select v-model="formAddress.district" :placeholder="formAddress.city && districtList.length === 0 ? '该地区无区县' : '请选择区县'" :disabled="!formAddress.city || districtList.length === 0">
            <el-option v-for="item in districtList" :key="item.value" :label="item.label" :value="item.label" />
          </el-select>
        </el-form-item>
        <el-form-item label="详细地址">
          <el-input v-model="formAddress.addressDetail" type="textarea" placeholder="请输入详细地址" />
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="isDefaultChecked">设为默认收货地址</el-checkbox>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="formDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveAddress">保存</el-button>
      </template>
    </el-dialog>
  </section>
</template>

<script setup>
import { ref, computed, onMounted, reactive } from 'vue'
import { apiClient } from '../api/apiService.js'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useStore } from 'vuex'
import { getImageUrl } from '../utils/imageUtils.js'
import { provinces, cities, districts } from '../data/areaData.js'

const store = useStore()
const cartList = ref([])
const checkoutLoading = ref(false)

const addressList = ref([])
const selectedAddress = ref(null)
const addressDialogVisible = ref(false)
const tempSelectedAddress = ref(null)

const formDialogVisible = ref(false)
const isDefaultChecked = ref(false)
const cityList = ref([])
const districtList = ref([])

const formAddress = reactive({
  id: null,
  consignee: '',
  phone: '',
  province: '',
  city: '',
  district: '',
  addressDetail: '',
  isDefault: 0
})

const totalPrice = computed(() => {
  if (!cartList.value || !Array.isArray(cartList.value)) return '0.00'
  return cartList.value.reduce((sum, item) => sum + item.price * item.count, 0).toFixed(2)
})

onMounted(async () => {
  await Promise.all([loadCartData(), loadAddresses()])
})

const loadCartData = async () => {
  try {
    const response = await apiClient.get('/cart/show', {
      headers: { Authorization: window.localStorage.token }
    })
    if (response.flag) {
      cartList.value = response.data || []
    } else {
      ElMessage.error(response.message || '加载购物车失败')
    }
  } catch (error) {
    console.error('加载购物车失败:', error)
    ElMessage.error('加载购物车失败')
  }
}

const loadAddresses = async () => {
  try {
    const response = await apiClient.get('/address/list', {
      headers: { Authorization: window.localStorage.token }
    })
    if (response.flag && response.data) {
      addressList.value = response.data
      const defaultAddr = addressList.value.find(a => a.isDefault === 1)
      selectedAddress.value = defaultAddr || addressList.value[0] || null
    }
  } catch (error) {
    console.error('加载地址失败:', error)
  }
}

const openAddressDialog = () => {
  tempSelectedAddress.value = selectedAddress.value ? { ...selectedAddress.value } : null
  addressDialogVisible.value = true
}

const selectAddress = (address) => {
  tempSelectedAddress.value = address ? { ...address } : null
}

const confirmAddress = () => {
  selectedAddress.value = tempSelectedAddress.value
  addressDialogVisible.value = false
}

const handleProvinceChange = (value) => {
  formAddress.city = ''
  formAddress.district = ''
  districtList.value = []
  const province = provinces.find(p => p.label === value)
  if (province) {
    cityList.value = cities[province.value] || []
  } else {
    cityList.value = []
  }
}

const handleCityChange = (value) => {
  formAddress.district = ''
  const province = provinces.find(p => p.label === formAddress.province)
  if (province) {
    const city = cities[province.value]?.find(c => c.label === value)
    if (city) {
      districtList.value = districts[city.value] || []
    } else {
      districtList.value = []
    }
  } else {
    districtList.value = []
  }
}

const addNewAddress = () => {
  formAddress.id = null
  formAddress.consignee = ''
  formAddress.phone = ''
  formAddress.province = ''
  formAddress.city = ''
  formAddress.district = ''
  formAddress.addressDetail = ''
  formAddress.isDefault = 0
  isDefaultChecked.value = false
  cityList.value = []
  districtList.value = []
  formDialogVisible.value = true
}

const editAddress = (address) => {
  formAddress.id = address.id
  formAddress.consignee = address.consignee
  formAddress.phone = address.phone
  formAddress.province = address.province || ''
  formAddress.city = address.city || ''
  formAddress.district = address.district || ''
  formAddress.addressDetail = address.addressDetail || ''
  formAddress.isDefault = address.isDefault
  isDefaultChecked.value = address.isDefault === 1

  cityList.value = []
  districtList.value = []

  if (formAddress.province) {
    const province = provinces.find(p => p.label === formAddress.province)
    if (province) {
      cityList.value = cities[province.value] || []
      if (formAddress.city) {
        const city = cities[province.value]?.find(c => c.label === formAddress.city)
        if (city) {
          districtList.value = districts[city.value] || []
        }
      }
    }
  }

  formDialogVisible.value = true
}

const saveAddress = async () => {
  formAddress.isDefault = isDefaultChecked.value ? 1 : 0

  if (!formAddress.consignee || !formAddress.consignee.trim()) {
    ElMessage.warning('请输入收货人姓名')
    return
  }
  if (formAddress.consignee.trim().length > 20) {
    ElMessage.warning('收货人姓名不能超过20个字符')
    return
  }
  const phoneRegex = /^1[3-9]\d{9}$/
  if (!formAddress.phone || !formAddress.phone.trim()) {
    ElMessage.warning('请输入手机号码')
    return
  }
  if (!phoneRegex.test(formAddress.phone.trim())) {
    ElMessage.warning('请输入正确的手机号码格式')
    return
  }
  if (!formAddress.province) {
    ElMessage.warning('请选择省份')
    return
  }
  if (!formAddress.city) {
    ElMessage.warning('请选择城市')
    return
  }
  if (!formAddress.district && districtList.value.length > 0) {
    ElMessage.warning('请选择区县')
    return
  }
  if (!formAddress.addressDetail || !formAddress.addressDetail.trim()) {
    ElMessage.warning('请输入详细地址')
    return
  }
  if (formAddress.addressDetail.trim().length > 200) {
    ElMessage.warning('详细地址不能超过200个字符')
    return
  }

  try {
    let response
    if (formAddress.id) {
      response = await apiClient.put('/address/update', {
        id: formAddress.id,
        consignee: formAddress.consignee.trim(),
        phone: formAddress.phone.trim(),
        province: formAddress.province,
        city: formAddress.city,
        district: formAddress.district,
        addressDetail: formAddress.addressDetail.trim(),
        isDefault: formAddress.isDefault
      }, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: window.localStorage.token
        }
      })
    } else {
      response = await apiClient.post('/address/add', {
        consignee: formAddress.consignee.trim(),
        phone: formAddress.phone.trim(),
        province: formAddress.province,
        city: formAddress.city,
        district: formAddress.district,
        addressDetail: formAddress.addressDetail.trim(),
        isDefault: formAddress.isDefault
      }, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: window.localStorage.token
        }
      })
    }

    if (response.flag) {
      ElMessage.success(formAddress.id ? '更新地址成功' : '添加地址成功')
      formDialogVisible.value = false
      await loadAddresses()
      if (formAddress.id) {
        const updated = addressList.value.find(a => a.id === formAddress.id)
        if (updated) selectAddress(updated)
      } else {
        const newest = addressList.value[addressList.value.length - 1]
        if (newest) selectAddress(newest)
      }
    } else {
      ElMessage.error(formAddress.id ? '更新地址失败' : '添加地址失败')
    }
  } catch (error) {
    console.error('保存地址失败:', error)
    ElMessage.error('保存地址失败')
  }
}

const deleteAddress = async (id) => {
  try {
    const response = await apiClient.delete(`/address/${id}`, {
      headers: {
        Authorization: window.localStorage.token,
        'Content-Type': 'application/json'
      }
    })
    if (response.flag) {
      ElMessage.success('删除地址成功')
      await loadAddresses()
      if (tempSelectedAddress.value && tempSelectedAddress.value.id === id) {
        tempSelectedAddress.value = null
      }
    } else {
      ElMessage.error('删除地址失败')
    }
  } catch (error) {
    console.error('删除地址失败:', error)
    ElMessage.error('删除地址失败')
  }
}

const handleCheckout = async () => {
  if (!selectedAddress.value) {
    ElMessage.warning('请先选择收货地址')
    return
  }
  if (cartList.value.length === 0) {
    ElMessage.warning('购物车为空，无法结算')
    return
  }

  try {
    await ElMessageBox.confirm(
      `确认结算 ¥${totalPrice.value} 的商品？`,
      '确认结算',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'info'
      }
    )
  } catch {
    return
  }

  checkoutLoading.value = true
  try {
    const response = await apiClient.post(
      `/purchase/commitOrder/${selectedAddress.value.id}/${totalPrice.value}`,
      cartList.value,
      {
        headers: {
          'Content-Type': 'application/json',
          Authorization: window.localStorage.token
        }
      }
    )
    if (response.flag) {
      ElMessage.success('结算成功')
      cartList.value = []
      selectedAddress.value = null
      await loadAddresses()
    } else {
      ElMessage.error(response.message || '结算失败')
    }
  } catch (error) {
    console.error('结算失败:', error)
    ElMessage.error('结算失败')
  } finally {
    checkoutLoading.value = false
  }
}

const changeCount = async (item, delta) => {
  const newCount = item.count + delta
  if (newCount < 1) return

  try {
    const response = await apiClient.put(`/cart/update/${item.shoppingId}/${newCount}`, null, {
      headers: { Authorization: window.localStorage.token }
    })
    if (response.flag) {
      item.count = newCount
    } else {
      ElMessage.error(response.message || '更新数量失败')
    }
  } catch (error) {
    console.error('更新数量失败:', error)
    ElMessage.error('更新数量失败')
  }
}

const confirmDelete = async (item) => {
  try {
    await ElMessageBox.confirm('确定要删除该商品吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })

    const response = await apiClient.delete(`/cart/delete/${item.shoppingId}`, {
      headers: { Authorization: window.localStorage.token }
    })
    if (response.flag) {
      ElMessage.success('删除成功')
      await loadCartData()
    } else {
      ElMessage.error(response.message || '删除失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
      ElMessage.error('删除失败')
    }
  }
}

const handleImageError = (e) => {
  e.target.src = 'data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><rect fill="%23eee" width="100" height="100"/><text x="50" y="55" text-anchor="middle" fill="%23aaa" font-size="14">暂无图片</text></svg>'
}
</script>

<style scoped>
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>