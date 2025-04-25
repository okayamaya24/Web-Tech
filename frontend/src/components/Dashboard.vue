<template>
    <div class="dashboard-layout">
        <nav>
        <router-link 
        v-for="link in navLinks"
        :key="link.name"
        :to="{name: link.name}">
            {{ link.label }}
        </router-link>
        <!-- Profile Icon -->
      <span class="profile-icon" @click="goToProfile">
        <i class="fas fa-user-circle"></i>
      </span>
    </nav>
        <router-view></router-view>
    </div>
</template>

<script setup>
import { computed } from 'vue'
import { defineProps } from 'vue'
import { useRouter } from 'vue-router'

const props = defineProps({
  userRole: {
    type: String,
    required: true,
  },
})

const navLinks = computed(() => {
  if (props.userRole === 'admin') {
    return [
      { name: 'adminView', label: 'Admin Home' },
      { name: 'inviteCrewMember', label: 'Invite Crew Member'},
      { name: 'createGameSchedule', label: 'Create Game Schedule' },
      { name: 'manageGameSchedule', label: 'Manage Game Schedule' },
      { name: 'manageCrew', label: 'Manage Crew Members' },
      { name: 'scheduleCrew', label: 'Schedule Crew' },
    ]
  } else {
    // crew member
    return [
      { name: 'home', label: 'Home' },
      { name: 'viewGameSchedule', label: 'View Game Schedule' },
      { name: 'crew', label: 'View Crew List' },
      { name: 'availability', label: 'Availability' },
    ]
  }
})

function goToProfile() {
  const email = localStorage.getItem('userEmail')
  if (email) {
    router.push({ name: 'ViewProfile', params: { email } })
  } else {
    alert("You're not logged in.")
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
        justify-content: center;
        gap: 2rem;
        padding: 1.5rem;
        background-color: #4D1979;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);

        .router-link-active {
            background-color: white;
            color: black;
            border-color: black;
            border-radius: 6px;
            box-shadow: 0 6px 12px rgba(0, 0,0, 0.2);
            transform: translateY(-2px);
        }
    }

nav a {
  text-decoration: none;
  color: #ffffff;
  font-weight: 500;
  transition: color 0.3s ease;
}

    .router-link-active {
  background-color: white;
  color: black;
  border-color: black;
  border-radius: 6px;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
  transform: translateY(-2px);
}

.profile-icon {
  color: white;
  font-size: 1.5rem;
  cursor: pointer;
  margin-left: auto;
  display: flex;
  align-items: center;
}
</style>
