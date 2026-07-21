<template>
  <div class="min-h-screen bg-gray-50">
    <section class="hero-section relative h-[400px] w-full">
      <div
        class="absolute inset-0 w-full h-full bg-cover bg-center"
        :style="{ backgroundImage: `url(${currentBanner})` }"
      >
        <div class="container mx-auto px-6 h-full flex items-center">
          <div class="max-w-2xl">
            <h1 class="text-5xl font-bold text-white mb-6">
              {{ articleData.title }}
            </h1>
            <div
              class="bg-gradient-to-r from-[#007029] to-transparent p-4 rounded-lg inline-block"
            >
              <p class="text-xl text-white mb-0">
                {{ articleData.subtitle }}
              </p>
            </div>
          </div>
        </div>
      </div>
    </section>

    <section class="py-12 bg-white">
      <div class="container mx-auto px-4 max-w-4xl">
        <div class="bg-white rounded-lg shadow-md overflow-hidden">
          <div class="p-8">
            <div class="flex items-center gap-4 mb-6 text-gray-500 text-sm">
              <span>📅 发布时间：{{ articleData.date }}</span>
              <span>📁 分类：{{ articleData.category }}</span>
            </div>

            <div class="prose prose-green max-w-none text-gray-700 leading-relaxed">
              <h2 class="text-2xl font-bold text-green-800 mb-4">{{ articleData.section1.title }}</h2>
              <p class="mb-6">{{ articleData.section1.content }}</p>
              
              <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
                <div v-for="(item, index) in articleData.section1.cards" :key="index" 
                     class="bg-green-50 p-6 rounded-lg text-center">
                  <div class="text-4xl mb-3">{{ item.icon }}</div>
                  <h3 class="font-bold text-green-800 mb-2">{{ item.title }}</h3>
                  <p class="text-sm text-gray-600">{{ item.desc }}</p>
                </div>
              </div>

              <h2 class="text-2xl font-bold text-green-800 mb-4">{{ articleData.section2.title }}</h2>
              <p class="mb-6">{{ articleData.section2.content }}</p>

              <div v-if="articleData.section2.list" class="mb-8">
                <ul class="space-y-3">
                  <li v-for="(item, index) in articleData.section2.list" :key="index" 
                      class="flex items-start">
                    <span class="text-green-600 mr-3">✓</span>
                    <span>{{ item }}</span>
                  </li>
                </ul>
              </div>

              <h2 class="text-2xl font-bold text-green-800 mb-4">{{ articleData.section3.title }}</h2>
              <p class="mb-6">{{ articleData.section3.content }}</p>

              <div v-if="articleData.section3.stats" class="bg-green-50 p-6 rounded-lg mb-8">
                <div class="grid grid-cols-2 md:grid-cols-4 gap-4 text-center">
                  <div v-for="(stat, index) in articleData.section3.stats" :key="index">
                    <div class="text-3xl font-bold text-green-800">{{ stat.value }}</div>
                    <div class="text-sm text-gray-600">{{ stat.label }}</div>
                  </div>
                </div>
              </div>

              <div class="bg-green-700 text-white p-6 rounded-lg">
                <h3 class="font-bold text-lg mb-3">💡 核心价值</h3>
                <p>{{ articleData.coreValue }}</p>
              </div>
            </div>
          </div>
        </div>

        <div class="mt-8 flex justify-center">
          <button
            class="px-8 py-3 bg-green-800 text-white rounded-full hover:bg-green-900 transition-colors"
            @click="goBack"
          >
            返回首页
          </button>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import banner01 from "@/assets/img/banner01.png";
import banner02 from "@/assets/img/banner02.png";
import banner03 from "@/assets/img/banner03.png";

const router = useRouter();
const route = useRoute();

const articleId = ref(1);

onMounted(() => {
  const id = parseInt(route.query.id);
  if (!isNaN(id)) {
    articleId.value = id;
  }
});

const currentBanner = computed(() => {
  const banners = [banner01, banner02, banner03];
  return banners[articleId.value - 1] || banner01;
});

const articleData = computed(() => {
  const articles = {
    1: {
      title: "助力农业发展，普惠金融服务",
      subtitle: "运用科技手段，提供智能化金融服务，助力农业现代化",
      date: "2026-07-21",
      category: "金融服务",
      section1: {
        title: "什么是普惠金融服务？",
        content: "普惠金融是指以可负担的成本为有金融服务需求的社会各阶层和群体提供适当、有效的金融服务，重点服务对象包括小微企业、农民、城镇低收入人群等弱势群体。在农业领域，普惠金融服务旨在解决农户融资难、融资贵的问题，助力农业产业发展。",
        cards: [
          { icon: "💰", title: "低息贷款", desc: "专属农业金融产品，利率优惠" },
          { icon: "⚡", title: "快速审批", desc: "线上申请，极速放款" },
          { icon: "📱", title: "便捷服务", desc: "全程线上办理" }
        ]
      },
      section2: {
        title: "普惠金融服务的特点",
        content: "融销通平台提供的普惠金融服务具有以下特点：",
        list: [
          "覆盖范围广：服务全国各地农户和农业企业",
          "申请门槛低：简化申请流程，降低准入条件",
          "产品种类多：满足不同农业生产场景的融资需求",
          "服务效率高：线上审核，快速放款",
          "利率优惠：享受农业专项优惠利率"
        ]
      },
      section3: {
        title: "普惠金融服务的意义",
        content: "普惠金融服务对于推动农业发展具有重要意义：",
        stats: [
          { value: "50万+", label: "服务农户" },
          { value: "10亿+", label: "放款金额" },
          { value: "98%", label: "满意度" },
          { value: "3天", label: "平均放款" }
        ]
      },
      coreValue: "普惠金融服务不仅为农户提供了资金支持，更重要的是通过金融手段激活了农业生产活力，促进了农村经济的发展，助力乡村振兴战略的实施。"
    },
    2: {
      title: "创新金融科技，服务三农发展",
      subtitle: "汇聚农业专家智慧，为您的农业生产保驾护航",
      date: "2026-07-21",
      category: "科技服务",
      section1: {
        title: "金融科技如何服务三农？",
        content: "金融科技是指利用科技手段创新金融服务模式，提高金融服务效率。在三农领域，金融科技通过大数据、人工智能、云计算等技术，为农业生产提供智能化解决方案，包括智能风控、精准匹配、在线咨询等服务。",
        cards: [
          { icon: "👨‍🔬", title: "专家咨询", desc: "一对一专家指导" },
          { icon: "📊", title: "智能匹配", desc: "精准匹配金融产品" },
          { icon: "💬", title: "在线问答", desc: "实时解答农业问题" }
        ]
      },
      section2: {
        title: "金融科技服务内容",
        content: "融销通平台整合金融科技与农业专家资源，提供以下服务：",
        list: [
          "农业专家在线咨询：汇聚全国各地农业专家，提供一对一技术指导",
          "智能融资匹配：根据农户需求，智能推荐最适合的金融产品",
          "农业知识分享：丰富的农业技术文章和视频教程",
          "在线问答社区：与其他农户交流经验，解答疑惑",
          "线下服务预约：预约专家实地指导服务"
        ]
      },
      section3: {
        title: "专家团队实力",
        content: "我们的专家团队由来自各大农业院校和科研机构的专业人士组成：",
        stats: [
          { value: "100+", label: "专家数量" },
          { value: "30+", label: "专业领域" },
          { value: "10年+", label: "平均经验" },
          { value: "5000+", label: "咨询服务" }
        ]
      },
      coreValue: "创新金融科技服务，让农业专家的智慧触手可及，帮助农户解决生产中遇到的技术难题，提升农业生产效率和质量。"
    },
    3: {
      title: "专业农业金融，助力乡村振兴",
      subtitle: "提供专业的农业金融解决方案，支持农村经济发展",
      date: "2026-07-21",
      category: "乡村振兴",
      section1: {
        title: "乡村振兴与农业金融",
        content: "乡村振兴战略是国家重要发展战略，农业金融是乡村振兴的重要支撑。融销通平台通过提供专业的农业金融服务，支持农村产业发展、促进农民增收、推动农村基础设施建设。",
        cards: [
          { icon: "🌾", title: "农产品交易", desc: "线上销售渠道" },
          { icon: "🏡", title: "农村建设", desc: "支持乡村发展" },
          { icon: "👨‍👩‍👧", title: "农民增收", desc: "助力脱贫致富" }
        ]
      },
      section2: {
        title: "农业金融服务乡村振兴",
        content: "融销通平台在乡村振兴中发挥着重要作用：",
        list: [
          "支持特色农业发展：为地方特色农产品提供销售渠道",
          "促进农村电商发展：搭建农产品线上交易平台",
          "助力农村基础设施建设：提供专项贷款支持",
          "培育新型农业经营主体：支持家庭农场、合作社发展",
          "推动农业产业升级：引导农业向现代化、规模化发展"
        ]
      },
      section3: {
        title: "乡村振兴成果",
        content: "通过我们的服务，已经取得了显著的成效：",
        stats: [
          { value: "1000+", label: "合作村镇" },
          { value: "50亿+", label: "带动产值" },
          { value: "10万+", label: "就业岗位" },
          { value: "95%", label: "脱贫率" }
        ]
      },
      coreValue: "专业农业金融服务，不仅是资金的支持，更是对乡村振兴战略的深度参与，通过金融手段激发农村活力，实现农业强、农村美、农民富的目标。"
    }
  };
  return articles[articleId.value] || articles[1];
});

const goBack = () => {
  router.push('/home').catch((err) => err);
};
</script>
