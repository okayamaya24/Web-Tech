<template>
  <DashboardLayout v-if="showDashboard" :userRole="userRole" />
  <router-view v-else />
</template>

<script setup>

</script>

<style scoped>

</style>
