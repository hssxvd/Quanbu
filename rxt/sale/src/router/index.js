import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import FrontPage from '../views/FrontPage.vue'

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
        component: FrontPage,
      },
    ]
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
