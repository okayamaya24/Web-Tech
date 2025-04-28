<template>
    <div class="dashboard-layout" v-if="roleReady">
      <nav>
        <router-link
    v-for="link in navLinks"
    :key="link.name"
    :to="{ name: link.name }"
    class="nav-link"
    active-class="active-link"
    exact-active-class="active-link"
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
      Loading Admin Dashboard...
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
    userRole.value = storedRole || 'crewMember' // fallback
    roleReady.value = true
  })
  
  const navLinks = computed(() => {
    return [
      { name: 'AdminView', label: 'Admin Home' },
      { name: 'inviteCrewMember', label: 'Invite Crew Member' },
      { name: 'createGameSchedule', label: 'Create Game Schedule' },
      { name: 'manageGameSchedule', label: 'Add New Games' },
      { name: 'deleteCrew', label: 'Delete Crew Members' },
      { name: 'scheduleCrew', label: 'Schedule Crew' },
      { name: 'viewCrew', label: 'View Crew Members' }
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
    min-height: 100vh;
  }
  
  nav {
    display: flex;
    justify-content: center;
    gap: 2rem;
    padding: 1.5rem;
    background-color: #4D1979;
    border-radius: 8px;
  }
  
  .nav-link {
    text-decoration: none;
    color: white;
    font-weight: 500;
    transition: 0.3s ease;
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
