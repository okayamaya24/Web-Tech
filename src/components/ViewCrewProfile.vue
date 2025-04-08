<template>
    <div class="profile-container">
      <h2>Crew Member Profile</h2>
  
      <div v-if="crew">
        <p><strong>Name:</strong> {{ crew.firstName }} {{ crew.lastName }}</p>
        <p><strong>Email:</strong> {{ crew.email }}</p>
        <p><strong>Phone:</strong> {{ crew.phoneNumber }}</p>
        <p><strong>Role:</strong> {{ crew.role }}</p>
        <p><strong>Qualified Position:</strong> {{ crew.qualifiedPosition }}</p>
      </div>
  
      <div v-else>
        <p>Loading crew data...</p>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import { useRoute } from 'vue-router'
  import axios from 'axios'
  
  const crew = ref(null)
  const route = useRoute()
  
  onMounted(async () => {
    const id = route.params.id
    try {
      const res = await axios.get(`http://localhost:8080/api/crew/${id}`)
      crew.value = res.data
    } catch (err) {
      console.error('Error loading crew member:', err)
      alert('Could not load crew member profile.')
    }
  })
  </script>
  
  <style scoped>
  .profile-container {
    max-width: 600px;
    margin: 2rem auto;
    padding: 2rem;
    background-color: #f6f6f6;
    border-radius: 12px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  }
  
  h2 {
    margin-bottom: 1rem;
    color: #293840;
  }
  
  p {
    font-size: 1.1rem;
    margin: 0.5rem 0;
  }
  </style>