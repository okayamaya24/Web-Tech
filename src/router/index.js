import { createRouter, createWebHistory } from 'vue-router'
import ViewCrewProfile from '../components/ViewCrewProfile.vue'
import RegisterCrewMember from '../components/RegisterCrewMember.vue'
// Add other imports as needed

const routes = [
  { path: '/', 
    name: 'Register',
    component: RegisterCrewMember 
  },

  { path: '/crew/:id',
    name: 'ViewCrewProfile', 
    component: ViewCrewProfile
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router