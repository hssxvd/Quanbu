import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import front from '../views/FrontPage.vue'
import loginpage from '../views/LoginPage.vue'
import registerpage from '../views/RegisterPage.vue'
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