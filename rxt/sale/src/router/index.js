import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import front from '../views/FrontPage.vue'
import loginpage from '../views/LoginPage.vue'
import registerpage from '../views/RegisterPage.vue'
import FinancingList from '../views/FinancingList.vue'
import financing from '../views/FinancingPage.vue'
import AImatch from '../views/AIMatch.vue'
// import MyAddress from '../components/MyAddress.vue'


const routes = [
  {
     path: '/',
    redirect: '/home'
  },
  {
    path: '/home',
    component: Home,
    children: [
      {
        path: '',
        component: front,
      },
      {
        path: 'financing',
        component: financing
      },
      {
        path: 'aimatch',
        component: AImatch
      },
      {
        path: 'financinglist',
        component: FinancingList
      },
    ]
  },
  {
     path: '/login',
    component: loginpage,
  },
  {
     path: '/register',
    component: registerpage,
  },
]
const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router 