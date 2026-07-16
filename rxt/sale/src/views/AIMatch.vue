<template>
  <section class="py-12 bg-white border-t-2 border-gray-300">
    <div class="container w-2/3 mx-auto">
      <!-- Back Button -->
      <div class="mb-6">
        <button class="flex items-center text-gray-500 hover:text-gray-700" @click="goBack">
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
      <!-- 融资意向 -->
      <div class="bg-white rounded-md shadow-sm mb-8">
        <div class="flex justify-between items-center p-4 border-b relative">
          <div
            class="absolute bottom-0 left-0 w-[30px] h-[3px] bg-[#007029]"
          ></div>
          <div class="custom-border">
            <h2 class="text-lg font-bold text-gray-800">融资意向</h2>
            <p class="text-xs text-gray-500">Financing Intent</p>
          </div>
          <button
            class="text-[#007029] hover:text-green-500"
            @click="
              originalProfile = { ...userProfile };
              showEditModal = true;
            "
          >
            编辑
          </button>
        </div>

        <div class="p-6">
          <div
            v-if="userProfile && userProfile.name"
            class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6"
          >
            <div class="flex flex-col">
              <span class="text-gray-500 text-sm mb-1">姓名</span>
              <span class="font-medium">{{ userProfile.name }}</span>
            </div>
            <div class="flex flex-col">
              <span class="text-gray-500 text-sm mb-1">联系方式</span>
              <span class="font-medium">{{ userProfile.phone }}</span>
            </div>
            <div class="flex flex-col">
              <span class="text-gray-500 text-sm mb-1">地址</span>
              <span class="font-medium">{{ userProfile.address }}</span>
            </div>
            <div class="flex flex-col">
              <span class="text-gray-500 text-sm mb-1">意向贷款金额（万）</span>
              <span class="font-medium">{{ userProfile.amount }}</span>
            </div>
            <div class="flex flex-col">
              <span class="text-gray-500 text-sm mb-1">用途</span>
              <span class="font-medium">{{ userProfile.application }}</span>
            </div>
            <div class="flex flex-col">
              <span class="text-gray-500 text-sm mb-1">农作物</span>
              <span class="font-medium">{{ userProfile.crops }}</span>
            </div>
            <div class="flex flex-col">
              <span class="text-gray-500 text-sm mb-1">面积（亩）</span>
              <span class="font-medium">{{ userProfile.area }}</span>
            </div>
            <div class="flex flex-col">
              <span class="text-gray-500 text-sm mb-1">贷款期限（月）</span>
              <span class="font-medium">{{ userProfile.term }}</span>
            </div>
          </div>
          <div v-else class="flex justify-center">
            <button
              class="px-6 py-3 bg-[#007029] text-white rounded-lg hover:bg-green-600"
              @click="showEditModal = true"
            >
              添加融资意向
            </button>
          </div>
        </div>
      </div>
      <!-- 推荐联合贷款人 -->
      <div class="bg-white rounded-md shadow-sm">
        <div class="flex justify-between items-center p-4 border-b relative">
          <div
            class="absolute bottom-0 left-0 w-[30px] h-[3px] bg-[#007029]"
          ></div>
          <div>
            <h2 class="text-lg font-bold text-gray-800">推荐联合贷款人</h2>
            <p class="text-xs text-gray-500">Joint Lender</p>
          </div>
        </div>

        <div class="p-6">
          <!-- 推荐人列表 -->
          <div
            v-for="(user, index) in recommendedUsers"
            :key="index"
            class="bg-green-50 rounded-lg p-4 mb-4"
          >
            <div class="flex flex-col md:flex-row gap-6">
              <div class="flex-shrink-0 flex justify-center">
                <div
                  class="relative w-20 h-20 rounded-full overflow-hidden shadow-md bg-[#007029]"
                >
                  <img
                    :src="getAvatarSrc(user.avatar)"
                    alt="User"
                    class="absolute inset-0 w-full h-full object-cover"
                  />
                </div>
              </div>

              <div
                class="flex-grow grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4"
              >
                <div class="flex flex-col">
                  <span class="text-gray-500 text-sm mb-1">姓名</span>
                  <span class="font-medium">{{ user.name }}</span>
                </div>
                <div class="flex flex-col">
                  <span class="text-gray-500 text-sm mb-1">联系方式</span>
                  <span class="font-medium">{{ user.phone }}</span>
                </div>
                <div class="flex flex-col">
                  <span class="text-gray-500 text-sm mb-1">地址</span>
                  <span class="font-medium">{{ user.address }}</span>
                </div>

                <div class="flex flex-col">
                  <span class="text-gray-500 text-sm mb-1">农作物</span>
                  <span class="font-medium">{{ user.crops }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Edit Modal -->
    <div
      v-if="showEditModal"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
    >
      <div class="bg-white rounded-lg w-2/3 max-w-4xl">
        <div class="p-4 border-b">
          <h3 class="text-lg font-bold">融资意向编辑</h3>
        </div>
        <div class="p-6 grid grid-cols-2 gap-6">
          <!-- Left Column -->
          <div class="space-y-4">
            <div class="flex flex-col">
              <label class="text-gray-500 text-sm mb-1">姓名</label>
              <input v-model="userProfile.name" class="border rounded p-2" />
            </div>
            <div class="flex flex-col">
              <label class="text-gray-500 text-sm mb-1">联系方式</label>
              <input v-model="userProfile.phone" class="border rounded p-2" />
            </div>
            <div class="flex flex-col">
              <label class="text-gray-500 text-sm mb-1">地址</label>
              <input v-model="userProfile.address" class="border rounded p-2" />
            </div>
            <div class="flex flex-col">
              <label class="text-gray-500 text-sm mb-1"
                >意向贷款金额（万）</label
              >
              <input v-model="userProfile.amount" class="border rounded p-2" />
            </div>
          </div>

          <!-- Right Column -->
          <div class="space-y-4">
            <div class="flex flex-col">
              <label class="text-gray-500 text-sm mb-1">用途</label>
              <input
                v-model="userProfile.application"
                class="border rounded p-2"
              />
            </div>
            <div class="flex flex-col">
              <label class="text-gray-500 text-sm mb-1">农作物</label>
              <input v-model="userProfile.crops" class="border rounded p-2" />
            </div>
            <div class="flex flex-col">
              <label class="text-gray-500 text-sm mb-1">面积（亩）</label>
              <input v-model="userProfile.area" class="border rounded p-2" />
            </div>
            <div class="flex flex-col">
              <label class="text-gray-500 text-sm mb-1">贷款期限（月）</label>
              <input v-model="userProfile.term" class="border rounded p-2" />
            </div>
          </div>
        </div>
        <div class="p-4 border-t flex justify-end space-x-4">
          <button
            class="px-4 py-2 text-gray-500 hover:text-gray-700"
            @click="
              userProfile = { ...originalProfile };
              showEditModal = false;
            "
          >
            取消
          </button>
          <button
            class="px-4 py-2 bg-[#007029] text-white rounded hover:bg-green-600"
            @click="
              saveData();
              showEditModal = false;
            "
          >
            保存
          </button>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { UserIcon } from "lucide-vue-next";
import { ref, onMounted, computed } from "vue";
import riceImg from "@/assets/img/rice.png";
import expert01 from "@/assets/img/expert01.png";
import expert02 from "@/assets/img/expert02.png";
import expert03 from "@/assets/img/expert03.png";
import expert04 from "@/assets/img/expert04.png";
import expert05 from "@/assets/img/expert05.png";
import { apiClient } from "../api/apiService.js";
import { ElMessage } from "element-plus";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { Base64 } from "js-base64";

const avatarMap = {
  'expert01.png': expert01,
  'expert02.png': expert02,
  'expert03.png': expert03,
  'expert04.png': expert04,
  'expert05.png': expert05,
};

const getAvatarSrc = (avatar) => {
  if (!avatar) return expert01;
  if (avatarMap[avatar]) return avatarMap[avatar];
  return `${store.state.imgShowRoad}/file/avatar/${avatar}`;
};

const store = useStore();
const router = useRouter();

const showEditModal = ref(false);
const originalProfile = ref({});

// 可以在这里添加更多的数据和逻辑
const userProfile = ref({
  username: "",
  name: "",
  phone: "",
  address: "",
  application: "",
  amount: "",
  crops: "",
  area: "",
  term: "",
});

const recommendedUsers = ref([]);

onMounted(async () => {
  try {
    const intentionData = await selectIntention();
    if (intentionData) {
      userProfile.value = {
        username: intentionData.userName,
        name: intentionData.realName,
        phone: intentionData.phone,
        address: intentionData.address,
        application: intentionData.application,
        amount: intentionData.amount,
        crops: intentionData.item,
        area: intentionData.area,
        term: intentionData.repaymentPeriod,
      };
    }
  } catch (e) {
    console.warn('融资意向数据加载失败，使用mock数据', e);
  }

  try {
    await editRecommned();
  } catch (e) {
    console.warn('推荐用户数据加载失败', e);
  }
});

const editRecommned = async () => {
  try {
    const recommnedData = await selectRecommned();

    if (recommnedData && recommnedData.length > 0) {
      const grouped = {};
      recommnedData.forEach((item) => {
        const key = `${item.realName}-${item.phone}`;
        if (!grouped[key]) {
          grouped[key] = {
            name: item.realName,
            phone: item.phone,
            address: item.address,
            avatar: item.avatar,
            crops: item.item,
          };
        }
      });
      recommendedUsers.value = Object.values(grouped);
    } else {
      throw new Error('No data');
    }
  } catch (e) {
    console.warn('推荐用户数据加载失败，使用mock数据', e);
    recommendedUsers.value = [
      {
        name: '李明贵',
        phone: '13521347658',
        address: '山东省威海市',
        avatar: 'expert03.png',
        crops: '玉米',
      },
      {
        name: '张文斌',
        phone: '13523652222',
        address: '山东省威海市',
        avatar: 'expert04.png',
        crops: '黄瓜制品',
      },
    ];
  }
};

// 获取登陆用户融资意向
const selectIntention = async () => {
  try {
    const response = await apiClient.get("/finance/selectIntentionByName");
    if (response.flag == true) {
      return response.data[0];
    } else {
      ElMessage.error(response.message);
    }
  } catch (error) {
    console.error("请求失败", error);
    throw error;
  }
};

const selectRecommned = async () => {
  try {
    const response = await apiClient.get("/finance/selectRecommendByName");
    if (response.flag == true) {
      return response.data;
    } else {
      ElMessage.error(response.message);
    }
  } catch (error) {
    console.error("请求失败", error);
    throw error;
  }
};

// 保存融资意向
const saveData = async () => {
  try {
    const param = ref({
      realName: userProfile.value.name,
      amount: userProfile.value.amount,
      application: userProfile.value.application,
      item: userProfile.value.crops,
      repaymentPeriod: userProfile.value.term,
      address: userProfile.value.address,
      area: userProfile.value.area,
      phone: userProfile.value.phone,
    });

    if (userProfile.value.username != "") {
      const response = await apiClient.put(
        `/finance/updateIntentionByName`,
        param.value
      );
      if (response.flag == true) {
        recommendedUsers.value = [];
        editRecommned();
      } else {
        ElMessage.error(response.data);
      }
    } else {
      const response = await apiClient.post(
        `finance/insertIntentionByName`,
        param.value
      );
      if (response.flag == true) {
        recommendedUsers.value = [];
        editRecommned();
      } else {
        ElMessage.error(response.data);
      }
    }
  } catch (error) {
    console.error("请求失败", error);
    throw error;
  }
};

const goBack = () => {
  // 返回时携带当前页面状态（需配合路由的state配置）
  router.back();
};
</script>

<style>
@import "tailwindcss/base";
@import "tailwindcss/components";
@import "tailwindcss/utilities";

/* 自定义样式 */
.container {
  max-width: 1280px;
}

/* 绿色主题颜色 */
:root {
  --color-green-50: #f0fdf4;
  --color-green-100: #dcfce7;
  --color-green-200: #bbf7d0;
  --color-green-300: #86efac;
  --color-green-400: #4ade80;
  --color-green-500: #22c55e;
  --color-green-600: #16a34a;
  --color-green-700: #15803d;
  --color-green-800: #166534;
  --color-green-900: #14532d;
  --color-green-950: #052e16;
}

button {
  transition: all 0.2s ease;
}
</style>