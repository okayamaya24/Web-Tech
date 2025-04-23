import { createRouter, createWebHistory } from 'vue-router'
import ViewCrewProfile from '../components/ViewCrewProfile.vue'
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
  {
    path: '/dashboard',
    name: 'dashboard',
    component: () => import('../components/Dashboard.vue')
   },
  {
    path: '/availability',
    name: 'availability',
    component: () => import('../components/AvailabilityForm.vue')
  },
  {
    path: '/crew',
    name: 'crew',
    component: () => import('../components/CrewList.vue')
  },
  {
    path: '/viewGameSchedule',
    name: 'viewGameSchedule',
    compoenent: () => import('../components/GameForm.vue')
  },
  {
    path: '/home',
    name: 'home',
    component: () => import('../components/Home.vue'),
  },
  {
    path: '/createGameSchedule',
    name: 'createGameSchedule',
    component: () => import('../components/ViewGameSchedule.vue')
  },
  {
    path: '/manageGameSchedule',
    name: 'manageGameSchedule',
    component: () => import('../components/AdminAddsGames.vue')
  },
  {
    path: '/inviteCrewMember',
    name: 'inviteCrewMember',
    component: () => import('../components/AdminInviteForm.vue')
  }
  
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
