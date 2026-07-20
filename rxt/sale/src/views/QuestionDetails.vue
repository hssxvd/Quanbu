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
      <!-- 文章内容区域 (示例) -->
      <div class="mb-6 bg-white p-2 rounded-lg shadow-sm">
        <div class="flex items-start mb-4">
          <div
            class="w-6 h-6 rounded-tl-full rounded-tr-full rounded-bl-full bg-[#007029] flex items-center justify-center text-white font-bold mr-2 mb-3"
          >
            问
          </div>
          <h1 class="text-xl font-bold">{{question}}</h1>
        </div>
        <div class="text-gray-700 text-sm leading-relaxed">
          <div class="relative mb-4 pl-8 min-h-[2rem]">
            <div
              class="absolute left-0 top-0 w-5 h-5 rounded-tl-full rounded-tr-full rounded-bl-full bg-[#3CB371] flex items-center justify-center text-white font-bold text-l"
            >
              答
            </div>
            <p>
              {{answer}}
            </p>
          </div>
        </div>
      </div>

      <!-- 互动工具栏 -->
      <div  class="flex items-center justify-end text-sm border-b border-gray-100 text-gray-500 pb-4" >

        <!-- 查看次数 -->
        <div class="flex items-center mr-6">
          <eye-icon class="w-4 h-4 mr-1" />
          <span>{{ viewCount }}</span>
        </div>

        <!-- 收藏按钮 -->
        <button
          class="flex items-center mr-6 hover:text-gray-700 transition-colors"
          @click="toggleFavorite"
        >
          <star-icon
            class="w-4 h-4 mr-1"
            :class="{ 'fill-[#3CB371]': isFavorited }"
          />
          <span>收藏</span>
        </button>

        <!-- 点赞按钮 -->
        <button
          class="flex items-center hover:text-gray-700 transition-colors"
          :class="{ 'text-[#3CB371]': isLiked }"
          @click="toggleLike"
        >
          <thumbs-up
            class="w-4 h-4 mr-1"
            :class="{ 'fill-[#3CB371]': isLiked }"
          />
          <span>{{ likeText }}</span>
        </button>
      </div>
      <!-- 相关问题 -->
       <div class="ml-8 mt-8 pt-5">
  <h2 class="text-xl mb-4 text-gray-500 font-bold">相关问题</h2>
  <ul class="list-none">
    <li v-for="(question, index) in relatedQuestions" :key="index" class="mb-2 pl-4 relative">
      <a :href="question.link" class="text-gray-500 text-base hover:text-[#007029] hover:underline">
        {{ question.title }}
      </a>
      <span class="absolute left-0 top-2 w-2 h-2 bg-[#007029] rounded-full"></span>
    </li>
  </ul>
</div>
    </div>
  </section>
</template>

<script setup>
import { ref, computed } from "vue";
import { EyeIcon, StarIcon, ThumbsUp } from "lucide-vue-next";
import { useRoute, useRouter } from "vue-router";
import { onMounted } from "vue";
import { apiClient } from "../api/apiService.js";
import { ElMessage } from 'element-plus';

const route = useRoute();
const router = useRouter();

const relatedQuestions = ref([
  {
    title: '草莓杂草如何防治？',
    link: '#'
  },
  {
    title: '水稻减产问题（如穗小、空秕粒多）？',
    link: '#'
  },
  {
    title: '北方草莓种植品种有哪些？',
    link: '#'
  },
  {
    title: '白菜病虫问题如何防范（如软腐病、菜青虫）？',
    link: '#'
  },
  {
    title: '番石榴种植方法是什么？',
    link: '#'
  }
]);

// 文章数据
const viewCount = ref(47);

// 互动状态
const isLiked = ref(false);
const isFavorited = ref(false);
const likeCount = ref(0);

// 计算属性：点赞文本
const likeText = computed(() => {
  return isLiked.value ? `点赞(${likeCount.value})` : "点赞";
});

// 切换收藏状态
const toggleFavorite = () => {
  isFavorited.value = !isFavorited.value;

  // 这里可以添加与后端交互的代码
  if (isFavorited.value) {
    // 发送收藏请求
    console.log("文章已收藏");
  } else {
    // 发送取消收藏请求
    console.log("取消收藏");
  }
};

// 切换点赞状态
const toggleLike = () => {
  if (!isLiked.value) {
    likeCount.value++;
  } else {
    likeCount.value = Math.max(0, likeCount.value - 1);
  }

  isLiked.value = !isLiked.value;

  // 这里可以添加与后端交互的代码
  if (isLiked.value) {
    // 发送点赞请求
    console.log("文章已点赞");
  } else {
    // 发送取消点赞请求
    console.log("取消点赞");
  }
};

let question = ref();
let answer = ref();
let expertName = ref();

onMounted(async () => {
  question.value = route.query.question;
  answer.value = route.query.answer;
  expertName.value = route.query.expertName;
});

const goBack = () => {
  // 返回时携带当前页面状态（需配合路由的state配置）
  router.back();
};

</script>