<template>
    <div v-if="roleReady" class="dashboard-layout">
      <nav>
        <router-link
  v-for="link in navLinks"
  :key="link.name"
  :to="{ name: link.name }"
  class="nav-link"
  :class="{ 'active-link': $route.name === link.name }"
>
  {{ link.label }}
</router-link>
  
        <span class="profile-icon" @click="goToProfile">
          <i class="fas fa-user-circle"></i>
        </span>
      </nav>
  
      <router-view />
    </div>
  
    <div v-else class="loading-screen">
      Loading your dashboard...
    </div>
  </template>
  
  <script setup>
  import { ref, computed, onMounted } from 'vue'
  import { useRouter } from 'vue-router'
  
  const router = useRouter()
  
  const userRole = ref('')
  const roleReady = ref(false)
  
  onMounted(() => {
    const storedRole = localStorage.getItem('userRole')
    userRole.value = storedRole || 'crewMember'  // Default to crewMember
    roleReady.value = true
  })
  
  const navLinks = computed(() => {
    return [
      { name: 'home', label: 'Home' },
      { name: 'viewGameSchedule', label: 'View Game Schedule' },
      { name: 'crew', label: 'View Crew List' },
      { name: 'availability', label: 'Submit Availability' },
    ]
  })
  
  function goToProfile() {
    const email = localStorage.getItem('userEmail')
    if (email) {
      router.push({ name: 'viewCrewProfile', params: { email } })
    } else {
      router.push('/login')
    }
  }
  </script>
  
  <style scoped>
  .dashboard-layout {
    display: flex;
    flex-direction: column;
    gap: 1rem;
  }
  
  nav {
    display: flex;
    justify-content: space-between;
    gap: 1rem 2rem;
    padding: 1.5rem;
    background-color: #4D1979;
    border-radius: 8px;
  }
  
  .nav-link {
    text-decoration: none;
    color: white;
    font-weight: 500;
    transition: 0.3s ease;
    flex-grow: 1;
  }
  
  .active-link {
    background-color: white;
    color: #4D1979;
    padding: 0.5rem 1rem;
    border-radius: 8px;
  }
  
  .profile-icon {
    color: white;
    font-size: 1.8rem;
    cursor: pointer;
    margin-left: auto;
    display: flex;
    align-items: center;
  }
  
  .loading-screen {
    text-align: center;
    margin-top: 5rem;
    font-size: 1.5rem;
    color: #777;
  }
  </style>
