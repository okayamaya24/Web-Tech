<template>
  <div class="profile-page">
    <h1>My Profile</h1>
    <div v-if="crewMember">
      <p><strong>First Name:</strong> {{ crewMember.firstName }}</p>
      <p><strong>Last Name:</strong> {{ crewMember.lastName }}</p>
      <p><strong>Email:</strong> {{ crewMember.email }}</p>
      <p><strong>Phone:</strong> {{ crewMember.phoneNumber }}</p>
      <p><strong>Role:</strong> {{ crewMember.role }}</p>
      <p><strong>Qualified Position:</strong> {{ crewMember.qualifiedPosition }}</p>
    </div>
    <div v-else>
      <p>Loading profile...</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';

const crewMember = ref(null);
const route = useRoute();

onMounted(async () => {
  const email = route.params.email || localStorage.getItem('userEmail');
  if (email) {
    try {
      const response = await axios.get(`http://localhost:8080/api/crew-members/${email}`);
      crewMember.value = response.data;
    } catch (error) {
      console.error('Error fetching crew member profile:', error);
    }
  }
});
</script>

<style scoped>
.profile-page {
  max-width: 600px;
  margin: auto;
  padding: 2rem;
  background: white;
  border-radius: 1rem;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
}
h1 {
  color: #4D1979;
}
</style>
