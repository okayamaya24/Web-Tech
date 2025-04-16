<template>
  <div class="profile-view">
    <h1>Crew Member Profile</h1>

    <div class="input-section">
      <label for="email">Enter Email to View Profile:</label>
      <input v-model="email" type="email" placeholder="john.doe@example.com" />
      <button @click="fetchProfile">View Profile</button>
    </div>

    <!-- Loading -->
    <p v-if="loading">Loading profile...</p>

    <!-- Profile Display -->
    <div v-if="profile && !loading" class="profile-card">
      <h3>{{ profile.firstName }} {{ profile.lastName }}</h3>
      <p><strong>Email:</strong> {{ profile.email }}</p>
      <p><strong>Phone Number:</strong> {{ profile.phoneNumber }}</p>
      <p><strong>Role:</strong> {{ profile.role }}</p>
      <p><strong>Qualified Position:</strong> {{ profile.qualifiedPosition }}</p>
    </div>

    <!-- Error Message -->
    <p v-if="error" class="error">{{ error }}</p>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';

const email = ref('');
const profile = ref(null);
const error = ref('');
const loading = ref(false); // track loading state

async function fetchProfile() {
  error.value = '';
  profile.value = null;
  loading.value = true;

  try {
    const response = await axios.get(`http://localhost:8080/api/crew-members/${email.value}`);
    profile.value = response.data;
  } catch (err) {
    if (err.response?.status === 404) {
      error.value = 'No profile found for this email.';
    } else {
      error.value = 'Error retrieving profile.';
      console.error(err);
    }
  } finally {
    loading.value = false;
  }
}
</script>

<style scoped>
.profile-view {
  max-width: 600px;
  margin: 0 auto;
  padding: 2rem;
}

.input-section {
  margin-bottom: 1.5rem;
}

input {
  padding: 0.5rem;
  margin-right: 0.5rem;
}

.profile-card {
  border: 1px solid #ccc;
  padding: 1rem;
  border-radius: 10px;
}

.error {
  color: red;
  margin-top: 1rem;
}
</style>
