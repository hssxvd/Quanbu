<template>
  <div v-if="total > pageSize" class="flex items-center justify-between mt-6">
    <!-- 分页信息 -->
    <div class="text-sm text-gray-600">
      当前 {{ (currentPage - 1) * pageSize + 1 }}-{{ Math.min(currentPage * pageSize, total) }} 条 / 共 {{ total }} 条
    </div>

    <!-- 分页控件 -->
    <div class="flex items-center space-x-2">


      <!-- 页码导航 -->
      <div class="flex space-x-1">
        <button
          @click="goToPage(1)"
          :disabled="currentPage === 1"
          class="p-2 rounded disabled:opacity-30"
          aria-label="第一页"
        >
          &laquo;
        </button>
        <button
          @click="goToPage(currentPage - 1)"
          :disabled="currentPage === 1"
          class="p-2 rounded disabled:opacity-30"
          aria-label="上一页"
        >
          &lsaquo;
        </button>

        <template v-for="page in visiblePages" :key="page">
          <button
            v-if="typeof page === 'number'"
            @click="goToPage(page)"
            :class="{
              'bg-blue-500 text-white': currentPage === page,
              'hover:bg-gray-100': currentPage !== page
            }"
            class="w-10 h-10 rounded transition-colors"
          >
            {{ page }}
          </button>
          <span v-else class="flex items-end px-2">{{ page }}</span>
        </template>

        <button
          @click="goToPage(currentPage + 1)"
          :disabled="currentPage === pageCount"
          class="p-2 rounded disabled:opacity-30"
          aria-label="下一页"
        >
          &rsaquo;
        </button>
        <button
          @click="goToPage(pageCount)"
          :disabled="currentPage === pageCount"
          class="p-2 rounded disabled:opacity-30"
          aria-label="最后一页"
        >
          &raquo;
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, ref, watch } from 'vue'

const props = defineProps({
  currentPage: { type: Number, required: true },
  pageSize: { type: Number, default: 10 },
  total: { type: Number, required: true },
  pageRange: { type: Number, default: 5 }, // 显示页码数量
})

const emit = defineEmits(['update:currentPage', 'update:pageSize', 'page-change'])

const localPageSize = ref(props.pageSize)

// 计算总页数
const pageCount = computed(() => Math.ceil(props.total / localPageSize.value))

// 生成显示的页码数组（带省略号）
const visiblePages = computed(() => {
  const range = props.pageRange
  const current = props.currentPage
  const total = pageCount.value
  const pages = []

  if (total <= range + 4) {
    // 显示所有页码
    for (let i = 1; i <= total; i++) pages.push(i)
  } else {
    // 带省略号的页码
    const leftBound = current - Math.floor(range / 2)
    const rightBound = current + Math.floor(range / 2)
    
    if (leftBound > 1) pages.push(1)
    if (leftBound > 2) pages.push('...')
    
    let start = Math.max(1, leftBound)
    let end = Math.min(total, rightBound)
    
    for (let i = start; i <= end; i++) pages.push(i)
    
    if (end < total - 1) pages.push('...')
    if (end < total) pages.push(total)
  }

  return pages
})

// 切换页码
const goToPage = (page) => {
  if (page < 1 || page > pageCount.value || page === props.currentPage) return
  emit('update:currentPage', page)
  emit('page-change', { page, pageSize: localPageSize.value })
}

// 切换每页条数
const handlePageSizeChange = () => {
  emit('update:pageSize', localPageSize.value)
  emit('page-change', { page: 1, pageSize: localPageSize.value })
}

// 监听外部pageSize变化
watch(() => props.pageSize, (newVal) => {
  localPageSize.value = newVal
})
</script>