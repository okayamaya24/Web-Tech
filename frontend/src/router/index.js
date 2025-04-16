import { createRouter, createWebHistory } from 'vue-router'
import ViewCrewProfile from '../components/ViewCrewProfile.vue'
import RegisterCrewMember from '../components/RegisterCrewMember.vue'
import AdminView from '../views/AdminView.vue';
import Login from '../components/Login.vue';
// Add other imports as needed

const routes = [
  {
    path: '/',
    redirect: '/register'
  },
  {
  path: '/register',
  name: 'RegisterCrewMember',
  component: () => import('../components/RegisterCrewMember.vue'), // or '../views/' if it moved
  props: route => ({ token: route.query.token })
},

  { path: '/crew/:id',
    name: 'ViewCrewProfile', 
    component: ViewCrewProfile
  },

  {
    path: '/admin',
    name: 'AdminView',
    component: AdminView,
  },

  {
    path: '/login',
    name: 'login',
    component: Login
  },
  
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
