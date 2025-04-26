import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    redirect: '/register'
  },
  {
  path: '/register',
  name: 'RegisterCrewMember',
  component: () => import('../components/RegisterCrewMember.vue'),
  props: route => ({ token: route.query.token })
},

  { path: '/view-profile/:email?',
    name: 'viewCrewProfile', 
    component: () => import('../components/ViewCrewProfile.vue'),
    props: true
  },

  {
    path: '/admin',
    name: 'AdminView',
    component: () => import('../views/AdminView.vue')
  },

  {
    path: '/login',
    name: 'login',
    component: () => import('../components/Login.vue')
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
    component: () => import('../components/GameForm.vue')
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
  },
  {
    path: '/dashboard',
    name: 'dashboard',
    component: () => import('../components/Dashboard.vue')
  },
  {
    path: '/scheduleCrew',
    name: 'scheduleCrew',
    component: () => import('../components/CrewAssignment.vue')
   },
   {
    path: '/deleteCrew',
    name: 'deleteCrew',
    component: () => import('../components/AdminDeleteCrew.vue')
   },
   {
    path: '/viewCrew',
    name: 'viewCrew',
    component: () => import('../components/AdminCrewList.vue')
   }
  
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router

