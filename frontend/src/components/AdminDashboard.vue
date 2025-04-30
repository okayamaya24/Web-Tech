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
  
        <button class="logout-button" @click="handleLogout">Logout</button>
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
  import { logout } from '@/apis/auth'
  
  const router = useRouter()
  const userRole = ref('')
  const roleReady = ref(false)

  const handleLogout = async () => {
    await logout()
    router.replace('/login')
}
  
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
  const role = localStorage.getItem('userRole');
  const email = localStorage.getItem('userEmail');

  if (role === 'crewMember' && email) {
    router.push({ name: 'viewCrewProfile', params: { email } });
  } else if (role === 'admin') {
    // Optional: Show toast or tooltip instead of redirect
    alert('Admin profiles are not available.');
  } else {
    // Just in case fallback
    alert('Unable to access profile.');
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

  .logout-button {
  background-color: transparent;
  color: white;
  border: 2px solid white;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
}

.logout-button:hover {
  background-color: white;
  color: #4D1979;
}
  </style>
