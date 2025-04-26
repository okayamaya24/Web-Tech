import { createRouter, createWebHistory } from 'vue-router'
import ViewCrewProfile from '../components/ViewCrewProfile.vue'
import RegisterCrewMember from '../components/RegisterCrewMember.vue'
import AdminInviteForm from '../components/AdminInviteForm.vue'
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

  { 
    path: '/view-profile/:email',
    name: 'ViewCrewProfile', 
    component: () => import('../components/ViewCrewProfile.vue'),
    props: true
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
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// Role-based navigation guard
router.beforeEach((to, from, next) => {
  const userRole = localStorage.getItem('userRole')

  // Admin-only route protection
  const adminOnlyRoutes = ['AdminView', 'inviteCrewMember', 'createGameSchedule', 'manageGameSchedule']

  if (adminOnlyRoutes.includes(to.name) && userRole !== 'admin') {
    alert("Access denied. Admins only.");
    return next('/home')
  }

  next()
})

export default router
