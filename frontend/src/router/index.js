import { createRouter, createWebHistory } from 'vue-router'
import ViewCrewProfile from '../components/ViewCrewProfile.vue'
import RegisterCrewMember from '../components/RegisterCrewMember.vue'
import AdminView from '../views/AdminView.vue';
// Add other imports as needed

const routes = [
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
  
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
