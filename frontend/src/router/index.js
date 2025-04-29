import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    redirect: '/register'
  },
  {
    path: '/register',
    name: 'RegisterCrewMember',
    component: () => import('@/components/RegisterCrewMember.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/components/Login.vue')
  },
  {
    path: '/admin',
    component: () => import('@/components/AdminDashboard.vue'), //  Admin
    children: [
      { path: '', name: 'AdminView', component: () => import('@/views/AdminView.vue') },
      { path: 'inviteCrewMember', name: 'inviteCrewMember', component: () => import('@/components/AdminInviteForm.vue') },
      { path: 'createGameSchedule', name: 'createGameSchedule', component: () => import('@/components/ViewGameSchedule.vue') },
      { path: 'manageGameSchedule', name: 'manageGameSchedule', component: () => import('@/components/AdminAddsGames.vue') },
      { path: 'deleteCrew', name: 'deleteCrew', component: () => import('@/components/AdminDeleteCrew.vue') },
      { path: 'viewCrew', name: 'viewCrew', component: () => import('@/components/AdminCrewList.vue') },
      { path: 'scheduleCrew', name: 'scheduleCrew', component: () => import('@/components/CrewAssignment.vue') }
    ]
  },
  {
    path: '/crew',
    component: () => import('@/components/CrewDashboard.vue'), // Crew
    children: [
      { path: '', name: 'home', component: () => import('@/components/Home.vue') },
      { path: 'viewGameSchedule', name: 'viewGameSchedule', component: () => import('@/components/GameForm.vue') },
      { path: 'crew', name: 'crew', component: () => import('@/components/CrewList.vue') },
      { path: 'availability', name: 'availability', component: () => import('@/components/AvailabilityForm.vue') },
      { path: 'profile/:email', name: 'viewCrewProfile', component: () => import('@/views/ViewCrewProfile.vue') }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
