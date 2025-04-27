<template>
  <DashboardLayout v-if="showDashboard" :userRole="userRole" />
  <router-view v-else />
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import DashboardLayout from './components/Dashboard.vue'

const route = useRoute()

const showDashboard = computed(() => {
  // render dashboard layout on everything except login/register
  const name = route.name || ''
  return !['login', 'register'].includes(route.name)
})

const userRole = localStorage.getItem('userRole') || 'crew'
</script>
