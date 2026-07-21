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
import MyKnowledge from '../components/MyKnowledge.vue'
import MyQuestion from '../components/MyQuestion.vue'
import MyAppointment from '../components/MyAppointment.vue'
import MyGoods from '../components/MyGoods.vue'
import MyRequest from '../components/MyRequest.vue'
import ExpertAssistance from '../views/ExpertAssistance.vue'
import AgriculturalKnowledge from '../views/AgriculturalKnowledge.vue'
import Question from '../views/QuestionDetails.vue'
import AgriPro from '../views/AgriculturalProduct.vue'
import ShopCart from '../views/ShoppingCart.vue'
import BannerArticle from '../views/BannerArticle.vue'
import AboutUs from '../views/AboutUs.vue'
import ContactUs from '../views/ContactUs.vue'
import ServiceTerms from '../views/ServiceTerms.vue'
import PrivacyPolicy from '../views/PrivacyPolicy.vue'
import GoodsDetail from '../views/GoodsDetail.vue'
import MyUserManagement from '../components/MyUserManagement.vue'
import MyFinancing from '../components/MyFinancing.vue'
import MyGoodsManagement from '../components/MyGoodsManagement.vue'
import MySellOrder from '../components/MySellOrder.vue'
import MyBuyOrder from '../components/MyBuyOrder.vue'
import Personal from '../views/PersonalInfo.vue'


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
        path: 'agripro',
        component: AgriPro
      },
      {
        path: 'shopcart',
        component: ShopCart
      },
      {
        path: 'bannerarticle',
        component: BannerArticle
      },
      {
        path: 'AgriKnlg',
        component: AgriculturalKnowledge
      },
      {
        path: 'question',
        component: Question
      },
      {
        path: 'about',
        component: AboutUs
      },
      {
        path: 'contact',
        component: ContactUs
      },
      {
        path: 'terms',
        component: ServiceTerms
      },
      {
        path: 'privacy',
        component: PrivacyPolicy
      },
      {
        path: 'goodsDetail',
        component: GoodsDetail
      },
      {
        path: 'personal',
        component: Personal,
        children: [
          {
            path: '',
            component: MyMsg
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
          },
          {
            path: 'myknowledge',
            component: MyKnowledge
          },
          {
            path: 'myquestion',
            component: MyQuestion
          },
          {
            path: 'myappointment',
            component: MyAppointment
          },
          {
            path: 'products',
            component: MyGoods
          },
          {
            path: 'requests',
            component: MyRequest
          },
          {
            path: 'myfinancing',
            component: MyFinancing
          },
          {
            path: 'mygoodsmng',
            component: MyGoodsManagement
          },
          {
            path: 'mysell',
            component: MySellOrder
          },
          {
            path: 'mybuy',
            component: MyBuyOrder
          },
        ]
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
      },
      {
        path: 'myknowledge',
        component: MyKnowledge
      },
      {
        path: 'myquestion',
        component: MyQuestion
      },
      {
        path: 'myappointment',
        component: MyAppointment
      },
      {
        path: 'products',
        component: MyGoods
      },
      {
        path: 'requests',
        component: MyRequest
      },
      {
        path: 'myusermng',
        component: MyUserManagement
      },
      {
        path: 'myfinancing',
        component: MyFinancing
      },
      {
        path: 'mygoodsmng',
        component: MyGoodsManagement
      },
      {
        path: 'mysell',
        component: MySellOrder
      },
      {
        path: 'mybuy',
        component: MyBuyOrder
      }
    ]
  },
]
const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router