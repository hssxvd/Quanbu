import { createStore } from 'vuex'

export default createStore({
  state: () => ({
    role: localStorage.getItem('role') ? JSON.parse(localStorage.getItem('role')) : [],
    detailData: [],
    orderId: 0,
    changedOrderId: 0,
    changedKnowledgeId: 0,
    user: '',
    loginUserNickname: '',
    loginUserAvatar: '',
    activeIndex: '1',
    publishActiveIndex: '1',
    userActiveIndex: '1-1',
    token: localStorage.getItem('token') ? localStorage.getItem('token') : '',
    paymentInfo: '',
    imgShowRoad: import.meta.env.VITE_APP_Address,  // 注意环境变量访问方式变化
    fileUploadRoad: import.meta.env.VITE_APP_Address,
    mutiFile: '',
  }),
  getters: {
    getStorage(state) {
      if (!state.token) {
        state.token = localStorage.getItem('token')
        return state.token
      }
      return state.token
    },
    isExpert(state) {
      return state.role.some(item => item.authority === "expert")
    },
    isAdmin(state) {
      return state.role.some(item => item.authority === "admin")
    },
  },
  mutations: {
    updateOrderId(state, value) {
      state.orderId = value
    },
    updateDetailData(state, value) {
      state.detailData = value
      console.log('state.detailData', state.detailData)
    },
    updateChangedOrderId(state, value) {
      state.changedOrderId = value
    },
    updateChangedKnowledgeId(state, value) {
      state.changedKnowledgeId = value
    },
    updateLoginUserNickname(state, value) {
      console.log('state.loginUserNickname', value, state.loginUserNickname)
      state.loginUserNickname = value
    },
    updateLoginUserAvatar(state, value) {
      state.loginUserAvatar = value
    },
    updateRole(state, value) {
      state.role = value
      localStorage.setItem('role', JSON.stringify(value))
    },
    updatePaymentInfo(state, value) {
      state.paymentInfo = value
    },
    updateActiveIndex(state, value) {
      state.activeIndex = value
    },
    updatePublishActiveIndex(state, value) {
      state.publishActiveIndex = value
    },
    updateUserActiveIndex(state, value) {
      state.userActiveIndex = value
    },
    setToken(state, value) {
      state.token = value
      localStorage.setItem('token', value)
    },
    removeStorage(state) {
      state.token = ''
      state.role = []
      localStorage.removeItem('token')
      localStorage.removeItem('role')
    }
  },
  actions: {
    // 可以在这里添加异步操作
  },
  modules: {
    // 可以在这里添加模块
  }
})
