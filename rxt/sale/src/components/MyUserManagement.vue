<template>
  <!-- 主内容区 -->
  <div class="mx-auto px-10">
    <!-- 顶部发布按钮 -->
    <div class="mb-4">
      <button
        @click="addUser()" 
        class="flex items-center space-x-2 bg-[#007029] hover:bg-green-800 text-white font-medium py-2 px-4 rounded-md focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-offset-2"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="16"
          height="16"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="2"
          stroke-linecap="round"
          stroke-linejoin="round"
          class="lucide lucide-plus"
        >
          <path d="M5 12h14" />
          <path d="M12 5v14" />
        </svg>
        <span>添加用户</span>
      </button>
    </div>
    <div class="space-y-2">
      <!-- 数据表格 -->
      <div class="bg-white rounded-lg shadow-sm overflow-hidden">
        <div class="overflow-x-auto">
          <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-50">
              <tr>
                <th
                  scope="col"
                  class="px-3 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                >
                  用户名
                </th>
                <th
                  scope="col"
                  class="px-3 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                >
                  昵称
                </th>
                <th
                  scope="col"
                  class="px-3 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                >
                  身份证号
                </th>
                <th
                  scope="col"
                  class="px-3 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                >
                  角色
                </th>
                <th
                  scope="col"
                  class="px-3 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                >
                  电话
                </th>
                <th
                  scope="col"
                  class="px-3 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                >
                  地址
                </th>
                <th
                  scope="col"
                  class="px-3 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                >
                  操作
                </th>
              </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
              <tr
                v-for="(user, index) in users"
                :key="index"
                class="hover:bg-gray-50"
              >
                <td class="px-3 py-4 whitespace-nowrap">
                  <div class="text-sm font-medium text-gray-900">
                    {{ user.userName }}
                  </div>
                </td>
                <td class="px-3 py-4 whitespace-nowrap">
                  <div class="text-sm text-gray-900">{{ user.nickName }}</div>
                </td>
                <td class="px-3 py-4 whitespace-nowrap">
                  <div class="text-sm text-gray-500">
                    {{ user.identityNum }}
                  </div>
                </td>
                <td class="px-3 py-4 whitespace-nowrap">
                  <span :class="getRoleBadgeClass(user.role)">
                    {{ getRoleText(user.role) }}
                  </span>
                </td>
                <td class="px-3 py-4 whitespace-nowrap">
                  <div class="text-sm text-gray-500">{{ user.phone }}</div>
                </td>
                <td class="px-3 py-4">
                  <div class="text-sm text-gray-500 truncate max-w-xs">
                    {{ user.address }}
                  </div>
                </td>
                <td
                  v-if="user.role != 'admin'"
                  class="px-3 py-4 whitespace-nowrap text-sm font-medium"
                >
                  <div class="flex space-x-2">
                    <button
                      @click="editUser(user)"
                      class="text-indigo-600 hover:text-indigo-900"
                    >
                      编辑
                    </button>
                    <button
                      @click="confirmDelete(user)"
                      class="text-red-600 hover:text-red-900"
                    >
                      删除
                    </button>
                  </div>
                </td>
              </tr>
              <tr v-if="users.length === 0">
                <td colspan="7" class="px-3 py-10 text-center text-gray-500">
                  暂无数据
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <!-- 分页 -->
        <div class="flex justify-center items-center mt-8 text-sm">
          <Pagination
            :current-page="pagination.currentPage"
            :page-size="pagination.pageSize"
            :total="pagination.total"
            @update:current-page="setCurrentPage"
            @update:page-size="setPageSize"
            @page-change="loadData"
            class="mt-6"
          />
        </div>
      </div>
    </div>

    <!-- 编辑/添加模态框 -->
    <div
      v-if="showEditModal || showAddModal"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
    >
      <div class="bg-white rounded-lg p-6 w-full max-w-2xl">
        <div class="flex justify-between items-center mb-4">
          <h2 class="text-xl font-bold text-gray-800">
            {{ showEditModal ? "编辑用户" : "新增用户" }}
          </h2>
          <button @click="closeModal" class="text-gray-500 hover:text-gray-700">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              class="h-6 w-6"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M6 18L18 6M6 6l12 12"
              />
            </svg>
          </button>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1"
              >用户名 <span class="text-red-500">*</span></label
            >
            <input
              v-model="currentUser.userName"
              :readonly="showEditModal"
              type="text"
              class="w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1"
              >昵称 <span class="text-red-500">*</span></label
            >
            <input
              v-model="currentUser.nickName"
              type="text"
              class="w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1"
              >身份证号 <span class="text-red-500">*</span></label
            >
            <input
              v-model="currentUser.identityNum"
              type="text"
              class="w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1"
              >角色 <span class="text-red-500">*</span></label
            >
            <select
              v-model="currentUser.role"
              class="w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent"
            >
              <option value="user">农户</option>
              <option value="expert">专家</option>
            </select>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1"
              >电话 <span class="text-red-500">*</span></label
            >
            <input
              v-model="currentUser.phone"
              type="text"
              class="w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent"
            />
          </div>

          <div class="md:col-span-2">
            <label class="block text-sm font-medium text-gray-700 mb-1"
              >地址</label
            >
            <input
              v-model="currentUser.address"
              type="text"
              class="w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent"
            />
          </div>

          <div class="md:col-span-2">
            <label class="block text-sm font-medium text-gray-700 mb-1"
              >密码 <span class="text-red-500">*</span></label
            >
            <input
              v-model="currentUser.password"
              type="password"
              class="w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent"
            />
          </div>
        </div>

        <div class="flex justify-end space-x-3 mt-6">
          <button
            @click="closeModal"
            class="px-4 py-2 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50"
          >
            取消
          </button>
          <button
            @click="saveUser"
            class="px-4 py-2 bg-green-600 text-white rounded-md hover:bg-green-700"
          >
            保存
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref, reactive, computed } from "vue";
import { onMounted } from "vue";
import { defineComponent } from "vue";
import { apiClient } from "../api/apiService.js";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import { useStore } from "vuex";
import Pagination from "../components/Pagination.vue";

const store = useStore();
const router = useRouter();

// 搜索和筛选
const searchQuery = ref("");
const filters = reactive({
  role: "",
});

// 模态框状态
const showEditModal = ref(false);
const showAddModal = ref(false);

const currentItem = ref();
const currentUser = reactive({
  userName: "",
  nickName: "",
  identityNum: "",
  role: "",
  phone: "",
  address: "",
  password: "",
});

// 分页状态
const pagination = ref({
  currentPage: 1,
  pageSize: 15,
  total: 0,
});

//用户列表数据
const users = ref([]);

// 后端请求数据数据
const selectData = async ({ page }) => {
  try {
    //检索URL
    const url = ref(`/user/search/${page}?pageSize=${pagination.value.pageSize}`);

    const response = await apiClient.get(url.value, {
      headers: {
        Authorization: window.localStorage.token,
      },
    });
    if (response.flag) {
      return response.data;
    } else {
      ElMessage.error(response.data);
    }
  } catch (error) {
    console.error("请求失败", error);
    throw error;
  }
};

// 加载数据
const loadData = async ({ page }) => {
  try {
    const res = await selectData({ page });
    users.value = res.list;
    pagination.value.total = res.total;
  } catch (error) {
    console.error("数据加载失败:", error);
  }
};

// 更新当前页
const setCurrentPage = (page) => {
  pagination.value.currentPage = page;
};

// 更新每页条数
const setPageSize = (size) => {
  pagination.value.pageSize = size;
};

// 初始化加载
onMounted(() => {
  loadData({
    page: pagination.value.currentPage,
  });
});

// 获取角色文本
const getRoleText = (role) => {
  const roleMap = {
    admin: "管理员",
    user: "农户",
    expert: "专家",
  };
  return roleMap[role] || role;
};

// 获取角色标签样式
const getRoleBadgeClass = (role) => {
  const baseClass =
    "px-2 py-1 inline-flex text-xs leading-5 font-semibold rounded-full";
  const roleClassMap = {
    admin: `${baseClass} bg-purple-100 text-purple-800`,
    user: `${baseClass} bg-green-100 text-green-800`,
    expert: `${baseClass} bg-blue-100 text-blue-800`,
  };
  return roleClassMap[role] || `${baseClass} bg-gray-100 text-gray-800`;
};

// 编辑用户
const editUser = (user) => {
  Object.assign(currentUser, JSON.parse(JSON.stringify(user)));
  currentItem.value = user;
  showEditModal.value = true;
};

const addUser =() => {
  showAddModal.value = true;
};

// 确认删除
const confirmDelete = async(user) => {
  try{
    if (confirm("确定要删除用户吗？")) {
      //删除
      const response = await apiClient.delete(`/user/${user.userName}`, {
        headers: {
          Authorization: window.localStorage.token,
        }
      });
      console.log('Delete response:', response); // Debug logging
      if (response.flag == true) {
        ElMessage.success("删除用户成功。");
      }
      else {
        ElMessage.error(response.data);
      }

      //重新加载商品
      pagination.value.currentPage = 1;
      loadData({
        page: pagination.value.currentPage,
      });
    }
  }  catch (error) {
    console.error('删除失败，请重试:', error);
  }
};

// 保存用户
const saveUser = async () => {
  // 表单验证
  if (!currentUser.userName ||!currentUser.nickName|| !currentUser.password||!currentUser.phone||!currentUser.identityNum||!currentUser.address) {
    alert("请填写必填字段");
    return;
  }

  // 验证身份证号格式
  const idCardRegex = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
  if (currentUser.identityNum && !idCardRegex.test(currentUser.identityNum)) {
    alert("请输入正确的身份证号码");
    return;
  }

  // 验证手机号格式
  const phoneRegex = /^1[3-9]\d{9}$/;
  if (currentUser.phone && !phoneRegex.test(currentUser.phone)) {
    alert("请输入正确的手机号码");
    return;
  }
  try {
    if (showEditModal.value) {
      // 更新现有用户
      const param = ref({
        userName: currentUser.userName,
        nickName: currentUser.nickName,
        identityNum: currentUser.identityNum,
        role: currentUser.role,
        phone: currentUser.phone,
        address: currentUser.address,
        password: currentUser.password,
      });
      if (currentUser.password == currentItem.value.password) {
        param.value.password = null;
      }
      const responseOrder = await apiClient.put(
        `/user/${currentUser.userName}`,
        param.value,
        {
          headers: {
            Authorization: window.localStorage.token,
          },
        }
      );

      if (responseOrder.flag) {
        ElMessage.success("修改成功");
      } else {
        ElMessage.error(responseOrder.data);
      }
    } else {
      //添加用户
      const param = ref({
        userName: currentUser.userName,
        nickName: currentUser.nickName,
        identityNum: currentUser.identityNum,
        role: currentUser.role,
        phone: currentUser.phone,
        address: currentUser.address,
        password: currentUser.password,
      });
      const responseOrder = await apiClient.post(
        `/user/add`,
        param.value,
        {
          headers: {
            Authorization: window.localStorage.token,
          },
        }
      );

      if (responseOrder.flag) {
        ElMessage.success("添加成功");
      } else {
        ElMessage.error(responseOrder.data);
      }
    }

    pagination.value.currentPage = 1;
    loadData({
    page: pagination.value.currentPage,
  });

    closeModal();
  } catch {}
};

// 关闭模态框
const closeModal = () => {
  showAddModal.value = false;
  showEditModal.value = false;
  Object.assign(currentUser, {
    userName: "",
    nickName: "",
    identityNum: "",
    role: "",
    phone: "",
    address: "",
    password: "",
  });
};
</script>