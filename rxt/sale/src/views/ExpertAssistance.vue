<template>
  <div class="min-h-screen bg-white">
    <section class="hero-section relative h-[400px] w-full">
      <div
        class="absolute inset-0 w-full h-full bg-cover bg-center"
        :style="{ backgroundImage: `url(${banner01})` }"
      >
        <div class="container mx-auto px-6 h-full flex items-center">
          <div class="max-w-2xl">
            <h1 class="text-5xl font-bold text-white mb-6">
              专业农业金融，助力乡村振兴
            </h1>
            <div
              class="bg-gradient-to-r from-[#007029] to-transparent p-4 rounded-lg inline-block"
            >
              <p class="text-xl text-white mb-0">
                提供专业的农业金融解决方案，支持农村经济发展
              </p>
            </div>
          </div>
        </div>
      </div>
    </section>

    <div class="container mx-auto px-4 py-8">
      <div class="flex border-b border-gray-200 mb-8">
        <button
          v-for="tab in tabs"
          :key="tab.key"
          @click="activeTab = tab.key"
          class="px-8 py-4 text-lg font-bold transition-colors relative border-b-2"
          :class="activeTab === tab.key ? 'text-green-800 border-green-800' : 'text-gray-500 border-transparent hover:text-green-700'"
        >
          {{ tab.label }}
        </button>
      </div>

      <div v-show="activeTab === 'knowledge'" class="space-y-6">
        <div
          v-for="(item, index) in knowledge"
          :key="index"
          class="flex items-start bg-white border border-gray-100 rounded-md overflow-hidden hover:shadow-md transition-shadow cursor-pointer"
          @click="viewKnowledgeDetail(item)"
        >
          <div class="w-48 h-40 flex-shrink-0">
            <img
              :src="getKnowledgeImg(item.picPath)"
              alt="知识图片"
              class="w-full h-full object-cover"
            />
          </div>
          <div class="flex-grow p-4">
            <h3 class="text-lg font-bold text-gray-800 mb-2">{{ item.title || '农业知识' }}</h3>
            <p class="text-sm text-gray-600 line-clamp-2">{{ item.content || '暂无内容' }}</p>
          </div>
        </div>
      </div>

      <div v-show="activeTab === 'qa'" class="space-y-6">
        <div class="bg-white border border-gray-100 rounded-md p-6">
          <h3 class="text-xl font-bold text-green-800 mb-4">农业问答</h3>
          <div class="flex gap-4">
            <input
              v-model="searchKeyword"
              type="text"
              placeholder="请输入问题..."
              class="flex-grow border border-gray-200 rounded-md px-4 py-2 focus:outline-none focus:border-green-800"
            />
            <button
              class="px-6 py-2 bg-green-800 text-white rounded-md hover:bg-green-900 transition-colors"
              @click="searchQues"
            >
              搜索
            </button>
          </div>
        </div>

        <div
          v-for="(faq, index) in quesAns"
          :key="index"
          class="bg-white border border-gray-100 rounded-md p-6 cursor-pointer hover:shadow-md transition-shadow"
          @click="viewQuestionDetail(faq)"
        >
          <div class="flex items-center justify-between">
            <div class="flex items-center gap-3">
              <span class="text-green-600 text-lg">Q</span>
              <span class="text-lg font-medium text-gray-800">{{ faq?.question || '问题标题' }}</span>
            </div>
            <button
              class="px-4 py-1 bg-green-800 text-white rounded-md text-sm hover:bg-green-900 transition-colors"
              @click.stop="openConsultDialog({realName: faq?.expertName || '专家'})"
            >
              立即咨询
            </button>
          </div>
          <div class="mt-3 pl-6">
            <p class="text-gray-600">{{ faq?.answer || '暂无回答' }}</p>
          </div>
        </div>
      </div>

      <div v-show="activeTab === 'experts'" class="space-y-6">
        <h3 class="text-xl font-bold text-green-800 mb-4">专家介绍</h3>
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-6">
          <div
            v-for="(expert, index) in experts"
            :key="index"
            class="bg-white rounded-lg shadow-md overflow-hidden border border-[rgba(0,112,41,0.3)]"
          >
            <div class="h-32 w-full flex items-center justify-center relative">
              <div
                class="absolute bottom-0 left-1/2 transform -translate-x-1/2 w-32 h-16 bg-[#007029] rounded-t-full"
                style="z-index: 1"
              ></div>
              <img
                :src="getExpertAvatar(expert.userName)"
                class="h-32 w-32 object-cover relative"
                style="z-index: 2"
              />
            </div>
            <div class="p-4">
              <div class="flex gap-2 mb-4">
                <button
                  class="text-center font-bold text-base py-1 px-2 rounded bg-green-800 text-white flex-1 hover:bg-green-900 transition-colors"
                  @click="openConsultDialog(expert)"
                >
                  直接咨询
                </button>
                <button
                  class="text-center font-bold text-base py-1 px-2 rounded bg-green-800 text-white flex-1 hover:bg-green-900 transition-colors"
                  @click="openReserveDialog(expert)"
                >
                  线下预约
                </button>
              </div>
              <div class="text-xs space-y-1">
                <div class="flex">
                  <span class="text-gray-400">专家姓名:</span>
                  <span class="font-medium ml-1">{{ expert.realName }}</span>
                </div>
                <div class="flex">
                  <span class="text-gray-400">专业:</span>
                  <span class="font-medium ml-1">{{ expert.profession }}</span>
                </div>
                <div class="flex">
                  <span class="text-gray-400">单位:</span>
                  <span class="font-medium ml-1">{{ expert.belong }}</span>
                </div>
                <div class="flex">
                  <span class="text-gray-400">联系电话:</span>
                  <span class="font-medium ml-1">{{ expert.phone }}</span>
                </div>
                <div class="flex">
                  <span class="text-gray-400">职称:</span>
                  <span class="font-medium ml-1">{{ expert.position }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <ExpertConsultDialog ref="consultDialog" :expertUserName="expertUserName" />
<ExpertReserveDialog ref="reserveDialog" :expertUserName="expertUserName" />
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { apiClient } from "../api/apiService.js";
import ExpertConsultDialog from "../components/ExpertConsultDialog.vue";
import ExpertReserveDialog from "../components/ExpertReserveDialog.vue";

const router = useRouter();
const route = useRoute();

const tabs = [
  { key: "knowledge", label: "农业知识" },
  { key: "qa", label: "农业问答" },
  { key: "experts", label: "专家介绍" },
];

const activeTab = ref("knowledge");
const expertUserName = ref("");
const searchKeyword = ref("");

const consultDialog = ref();
const reserveDialog = ref();

const experts = ref([]);
const quesAns = ref([]);
const knowledge = ref([]);

import expert01 from "@/assets/img/expert01.png";
import expert02 from "@/assets/img/expert02.png";
import expert03 from "@/assets/img/expert03.png";
import expert04 from "@/assets/img/expert04.png";
import expert05 from "@/assets/img/expert05.png";
import expert06 from "@/assets/img/expert06.png";
import expert07 from "@/assets/img/expert07.png";
import expert08 from "@/assets/img/expert08.png";
import expert09 from "@/assets/img/expert09.png";
import expert10 from "@/assets/img/expert10.png";
import expert11 from "@/assets/img/expert11.png";
import riceImg from "@/assets/img/rice.png";
import cornImg from "@/assets/img/jixinguo.jpg";
import banner01 from "@/assets/img/banner01.png";

const expertAvatars = [
  expert01,
  expert02,
  expert03,
  expert04,
  expert05,
  expert06,
  expert07,
  expert08,
  expert09,
  expert10,
  expert11,
];

const knowledgeImg = riceImg;

const getExpertAvatar = (userName) => {
  let hash = 0;
  for (let i = 0; i < (userName || '').length; i++) {
    hash = userName.charCodeAt(i) + ((hash << 5) - hash);
  }
  const index = Math.abs(hash) % expertAvatars.length;
  return expertAvatars[index] || expert01;
};

const openConsultDialog = (expert) => {
  expertUserName.value = expert.userName;
  consultDialog.value.open();
};

const openReserveDialog = (expert) => {
  expertUserName.value = expert.userName;
  reserveDialog.value.open();
};

const getKnowledgeImg = (picPath) => {
  if (!picPath) return knowledgeImg;
  if (picPath.startsWith('http')) return picPath;
  if (picPath.includes('/')) {
    return '/api/file/' + picPath;
  }
  return '/src/assets/img/' + picPath;
};

const viewKnowledgeDetail = (item) => {
  router.push(
    `/home/AgriKnlg?knowledgeId=${item.knowledgeId || item.id}&picPath=${item.picPath}&title=${item.title}&content=${item.content}&ownName=${item.ownName}&updateTime=${item.updateTime}`
  );
};

const viewQuestionDetail = (faq) => {
  router.push({
    path: '/home/question',
    query: {
      question: faq.question,
      answer: faq.answer,
      expertName: faq.expertName
    }
  });
};

const searchQues = () => {
  if (!searchKeyword.value) {
    loadQuesAns();
    return;
  }
  quesAns.value = quesAns.value.filter(item => 
    item.question && item.question.includes(searchKeyword.value)
  );
};

const selectExpert = async () => {
  try {
    const response = await apiClient.get("/question/findExpertUser/1");
    if (response && response.flag === true && response.data && response.data.list) {
      return response.data.list;
    }
    return [];
  } catch (error) {
    console.error("请求失败", error);
    throw error;
  }
};

const selectQuesAns = async () => {
  try {
    const response = await apiClient.get("/question/findAllQues/1");
    if (response && response.flag === true && response.data && response.data.list) {
      return response.data.list;
    }
    return [];
  } catch (error) {
    console.error("请求失败", error);
    throw error;
  }
};

const selectKnowledge = async () => {
  try {
    const response = await apiClient.get("/knowledge/1");
    console.log("ExpertAssistance selectKnowledge response:", response);
    console.log("response.data:", response?.data);
    console.log("response.data.list:", response?.data?.list);
    if (response && response.flag === true) {
      if (Array.isArray(response.data)) {
        return response.data;
      } else if (response.data && response.data.list) {
        return response.data.list;
      }
    }
    return [];
  } catch (error) {
    console.error("请求失败", error);
    throw error;
  }
};

const loadExperts = async () => {
  try {
    const expertdata = await selectExpert();
    if (expertdata && expertdata.length > 0) {
      experts.value = expertdata;
    } else {
      experts.value = getMockExperts();
    }
  } catch (e) {
    console.warn("专家数据加载失败，使用mock数据", e);
    experts.value = getMockExperts();
  }
};

const loadQuesAns = async () => {
  try {
    const questiondata = await selectQuesAns();
    if (questiondata && questiondata.length > 0) {
      quesAns.value = questiondata;
    } else {
      quesAns.value = getMockQuesAns();
    }
  } catch (e) {
    console.warn("问答数据加载失败，使用mock数据", e);
    quesAns.value = getMockQuesAns();
  }
};

const loadKnowledge = async () => {
  try {
    const knowledgedata = await selectKnowledge();
    if (knowledgedata && knowledgedata.length > 0) {
      knowledge.value = knowledgedata;
    } else {
      knowledge.value = getMockKnowledge();
    }
  } catch (e) {
    console.warn("农业知识数据加载失败，使用mock数据", e);
    knowledge.value = getMockKnowledge();
  }
};

const getMockExperts = () => [
  {
    id: 1,
    realName: "周强",
    profession: "农业经济学",
    belong: "山东农业大学",
    phone: "13592312577",
    position: "中级工程师",
  },
  {
    id: 2,
    realName: "吴昊",
    profession: "农业经济管理学",
    belong: "青岛农业大学",
    phone: "13592312576",
    position: "高级工程师",
  },
  {
    id: 3,
    realName: "李荣荣",
    profession: "农学",
    belong: "山东农业大学",
    phone: "13592312574",
    position: "中级工程师",
  },
  {
    id: 4,
    realName: "王玉婷",
    profession: "生物化学",
    belong: "青岛农业大学",
    phone: "13792312520",
    position: "中级工程师",
  },
  {
    id: 5,
    realName: "刘芳",
    profession: "农业经济管理学",
    belong: "山东农业大学",
    phone: "13792312549",
    position: "高级工程师",
  },
];

const getMockQuesAns = () => [
  {
    id: 1,
    expertName: "周强",
    question: "新疆哈密瓜甜度是否跟日照有关？",
    answer: "哈密瓜甜度跟充足的阳光和较大的昼夜温差有关。白天可以充分发挥光合作用，而夜晚的呼吸消耗较小，有利于糖分沉淀。",
  },
  {
    id: 2,
    expertName: "吴昊",
    question: "苹果树种植多久浇一次水比较好？",
    answer: "新栽苗木：定植后立即浇水，以后每隔5-7天浇1次，连浇3次，促进根系恢复。生长期：土壤干燥时（表土下5cm干燥）每周浇1次，保持土壤湿润但不积水。",
  },
  {
    id: 3,
    expertName: "李荣荣",
    question: "番茄种植时为什么会出现裂果？如何预防？",
    answer: "由于水分波动大（久旱后突然大量浇水或降雨），钙元素缺乏导致果皮韧性差。预防措施：保持土壤湿度稳定，避免忽干忽湿；补充钙肥。",
  },
];

const getMockKnowledge = () => [
  {
    id: 1,
    title: "水稻种植全过程",
    content: "水稻种植第一步：晒种 水稻种植第二步：选种 水稻种植第三步：整秧板 水稻种植第四步：播种 水稻种植第五步：插秧 水稻种植第六步：灌溉 水稻种植第七步：田间管理（施肥）",
    picPath: riceImg,
  },
  {
    id: 2,
    title: "玉米种植过程详解",
    content: "玉米一直都被誉为长寿食品，含有丰富的蛋白质、脂肪、维生素、微量元素、纤维素等，具有开发高营养、高生物学功能食品的巨大潜力。",
    picPath: cornImg,
  },
];

onMounted(() => {
  const activeSection = route.query.activeSection;
  if (activeSection === 'qa') {
    activeTab.value = 'qa';
  } else if (activeSection === 'experts') {
    activeTab.value = 'experts';
  } else {
    activeTab.value = 'knowledge';
  }

  loadExperts();
  loadQuesAns();
  loadKnowledge();
});
</script>