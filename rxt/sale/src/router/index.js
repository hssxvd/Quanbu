import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import front from '../views/FrontPage.vue'
import loginpage from '../views/LoginPage.vue'
import registerpage from '../views/RegisterPage.vue'
import FinancingList from '../views/FinancingList.vue'
import financing from '../views/FinancingPage.vue'
import AImatch from '../views/AIMatch.vue'
import PersonalInfo from '../views/PersonalInfo.vue'
import MyMsg from '../components/MyMsg.vue'
import MyAddress from '../components/MyAddress.vue'
import MyPassword from '../components/MyPassword.vue'
import MyExpertMsg from '../components/MyExpertMsg.vue'
import ExpertAssistance from '../views/ExpertAssistance.vue'
import AgriculturalKnowledge from '../views/AgriculturalKnowledge.vue'
import Question from '../views/QuestionDetails.vue'


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
      {
        path: 'expertassist',
        component: ExpertAssistance
      },
      {
        path: 'AgriKnlg',
        component: AgriculturalKnowledge
      },
      {
        path: 'question',
        component: Question
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
  {
    path: '/personal',
    component: PersonalInfo,
    children: [
      {
        path: '',
        redirect: 'mymsg'
      },
      {
        path: 'mymsg',
        component: MyMsg
      },
      {
        path: 'myadd',
        component: MyAddress
      },
      {
        path: 'mypwd',
        component: MyPassword
      },
      {
        path: 'myexpertmsg',
        component: MyExpertMsg
      }
    ]
  },
]
const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router