<template>
  <div class="invite-form">
    <h2>Invite a New Crew Member</h2>

    <input
      v-model="email"
      type="email"
      placeholder="Enter crew member's email"
      class="input"
    />

    <button @click="sendInvite" class="btn">Send Invitation</button>

    <p v-if="message" :class="{ success: success, error: !success }">
      {{ message }}
    </p>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';

const email = ref('');
const message = ref('');
const success = ref(false);

async function sendInvite() {
  message.value = '';
  success.value = false;

  if (!email.value.includes('@')) {
    message.value = 'Please enter a valid email address.';
    return;
  }

  try {
    const response = await axios.post(
      `http://localhost:8080/api/invitations?email=${email.value}`,
      {}
    );
    success.value = true;
    message.value = 'Invitation sent successfully!';
    email.value = ''; // Clear input
  } catch (error) {
    success.value = false;
    console.error('Error sending invitation:', error);
    message.value =
      error.response?.data || 'Failed to send invitation. Please try again.';
  }
}
</script>

<style scoped>
.invite-form {
  max-width: 540px; /* was 480px */
  margin: 3rem auto;
  padding: 2.5rem;
  border: 2px solid #4D1979;
  border-radius: 1rem;
  box-shadow: 0 4px 14px rgba(0, 0, 0, 0.08);
  font-family: 'Segoe UI', sans-serif;
  background-color: #faf7fc;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.title {
  font-size: 1.8rem;
  margin-bottom: 1.5rem;
  color: #4D1979;
  font-weight: 700;
  text-align: center;
}

.logo {
  width: 80px;
  margin-bottom: 1rem;
}

.input {
  width: 100%;
  padding: 0.75rem 1rem;
  margin-bottom: 1.2rem;
  border: 1px solid #ccc;
  border-radius: 0.5rem;
  font-size: 1rem;
  outline-color: #4D1979;
}

.btn {
  width: 100%;
  padding: 0.75rem;
  background-color: #4D1979;
  color: white;
  font-weight: bold;
  font-size: 1rem;
  border: none;
  border-radius: 0.5rem;
  cursor: pointer;
  transition: background-color 0.25s ease;
}

.btn:hover {
  background-color: #3a0f60;
}

.success {
  color: green;
  margin-top: 0.75rem;
  text-align: center;
  font-weight: 500;
}

.error {
  color: red;
  margin-top: 0.75rem;
  text-align: center;
  font-weight: 500;
}
</style>
