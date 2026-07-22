<template>
  <div class="min-h-screen bg-white">
    <div class="container mx-auto px-4 py-8">
      <button
        class="text-gray-600 hover:text-green-800 mb-6 flex items-center"
        @click="goBack"
      >
        <span class="text-lg mr-2">←</span>
        返回
      </button>

      <div class="max-w-3xl mx-auto">
        <h1 class="text-2xl font-bold text-gray-800 mb-4">{{ knowledge.title }}</h1>
        <p class="text-gray-500 text-sm mb-6">发布时间：{{ knowledge.updateTime }}</p>

        <div class="mb-8">
          <img
            :src="getImageUrl(knowledge.picPath)"
            alt="知识图片"
            class="w-full h-64 object-cover rounded-md"
          />
        </div>

        <div class="prose prose-green max-w-none mb-8">
          <p class="text-gray-700 leading-relaxed whitespace-pre-wrap">{{ knowledge.content }}</p>
        </div>

        <div class="border-t border-gray-200 pt-8">
          <h3 class="text-xl font-bold text-green-800 mb-4">评论 ({{ comments.length }})</h3>

          <div
          v-for="(comment, index) in comments"
          :key="index"
          class="bg-gray-50 rounded-md p-4 mb-4"
        >
          <div class="flex items-center justify-between mb-2">
            <span class="font-medium text-gray-800">{{ comment.own_name || comment.ownName || '用户' }}</span>
            <span class="text-sm text-gray-500">{{ comment.create_time || comment.createTime }}</span>
          </div>
          <p class="text-gray-600">{{ comment.content }}</p>
        </div>

          <div v-if="comments.length === 0" class="text-center py-8 text-gray-400">
            暂无评论，快来发表第一条评论吧！
          </div>

          <div class="mt-6">
            <div class="flex gap-4">
              <textarea
                v-model="newComment"
                placeholder="发表您的看法..."
                class="flex-grow border border-gray-200 rounded-md px-4 py-3 focus:outline-none focus:border-green-800"
                rows="3"
              ></textarea>
              <button
                class="px-6 py-3 bg-green-800 text-white rounded-md hover:bg-green-900 transition-colors"
                @click="submitComment"
              >
                发表
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { apiClient } from "../api/apiService.js";
import { ElMessage } from "element-plus";
import { useStore } from "vuex";

const router = useRouter();
const route = useRoute();
const store = useStore();

const knowledge = ref({
  title: "",
  content: "",
  picPath: "",
  updateTime: "",
  ownName: "",
});

const comments = ref([]);
const newComment = ref("");

const goBack = () => {
  router.back();
};

const getImageUrl = (picPath) => {
  if (!picPath) return "";
  if (picPath.startsWith("http")) return picPath;
  if (picPath.includes('/')) {
    return '/api/file/' + picPath;
  }
  return '/src/assets/img/' + picPath;
};

const getKnowledgeById = async (knowledgeId) => {
  try {
    const response = await apiClient.get(`/knowledge/selectById/${knowledgeId}`);
    return response;
  } catch (error) {
    console.error("获取知识详情失败", error);
    throw error;
  }
};

const getComments = async (knowledgeId) => {
  try {
    const url = `/discuss/${knowledgeId}`;
    console.log("getComments URL:", url);
    console.log("getComments called with knowledgeId:", knowledgeId);
    console.log("typeof knowledgeId:", typeof knowledgeId);
    const response = await apiClient.get(url);
    console.log("getComments response:", response);
    console.log("response.flag:", response?.flag);
    console.log("response.data:", response?.data);
    if (response && response.flag === true) {
      return response.data || [];
    }
    return [];
  } catch (error) {
    console.error("获取评论失败", error);
    throw error;
  }
};

const getUsernameFromToken = () => {
  const token = window.localStorage.token;
  if (!token) return "";
  try {
    const [, payload] = token.split('.');
    const decoded = atob(payload);
    const { username } = JSON.parse(decoded);
    return username || "";
  } catch (e) {
    console.error("解析token失败", e);
    return "";
  }
};

const addComment = async (knowledgeId, content) => {
  try {
    const nickname = store.state.loginUserNickname;
    const username = getUsernameFromToken();
    const response = await apiClient.post(
      `/discuss`,
      {
        knowledgeId: parseInt(knowledgeId),
        content,
        ownName: nickname || username || "匿名用户",
      }
    );
    return response;
  } catch (error) {
    console.error("发表评论失败", error);
    throw error;
  }
};

const submitComment = async () => {
  if (!newComment.value.trim()) {
    ElMessage.error("请输入评论内容");
    return;
  }
  if (!window.localStorage.token) {
    ElMessage.error("请先登录");
    router.push("/login");
    return;
  }
  try {
    console.log("submitComment knowledgeId:", route.query.knowledgeId);
    console.log("submitComment content:", newComment.value);
    console.log("submitComment ownName:", store.state.loginUserNickname);
    const result = await addComment(route.query.knowledgeId, newComment.value);
    console.log("submitComment result:", result);
    if (result && result.flag !== false) {
      ElMessage.success("评论成功");
      newComment.value = "";
      await loadComments();
    } else {
      ElMessage.error("评论失败");
    }
  } catch (error) {
    console.error("submitComment error:", error);
    ElMessage.error("评论失败，请重试");
  }
};

const loadComments = async () => {
  try {
    const knowledgeId = route.query.knowledgeId;
    if (!knowledgeId) {
      console.warn("knowledgeId 参数为空");
      comments.value = [];
      return;
    }
    const commentData = await getComments(knowledgeId);
    if (commentData && commentData.length > 0) {
      comments.value = commentData;
    } else {
      comments.value = [];
    }
  } catch (e) {
    console.warn("评论数据加载失败", e);
    comments.value = [];
  }
};

onMounted(async () => {
  console.log("route.query:", route.query);
  console.log("route.query.knowledgeId:", route.query.knowledgeId);
  
  const knowledgeId = route.query.knowledgeId;
  if (knowledgeId) {
    try {
      const response = await getKnowledgeById(knowledgeId);
      if (response && response.flag === true && response.data) {
        knowledge.value = {
          title: response.data.title || route.query.title || "农业知识",
          content: response.data.content || route.query.content || "暂无内容",
          picPath: response.data.picPath || route.query.picPath || "",
          updateTime: response.data.updateTime || route.query.updateTime || "",
          ownName: response.data.ownName || route.query.ownName || "",
        };
      } else {
        knowledge.value = {
          title: route.query.title || "农业知识",
          content: route.query.content || "暂无内容",
          picPath: route.query.picPath || "",
          updateTime: route.query.updateTime || "",
          ownName: route.query.ownName || "",
        };
      }
    } catch (error) {
      console.error("获取知识详情失败", error);
      knowledge.value = {
        title: route.query.title || "农业知识",
        content: route.query.content || "暂无内容",
        picPath: route.query.picPath || "",
        updateTime: route.query.updateTime || "",
        ownName: route.query.ownName || "",
      };
    }
  } else {
    knowledge.value = {
      title: route.query.title || "农业知识",
      content: route.query.content || "暂无内容",
      picPath: route.query.picPath || "",
      updateTime: route.query.updateTime || "",
      ownName: route.query.ownName || "",
    };
  }

  loadComments();
});
</script>