<template>
  <div class="invite-wrapper">
    <h2 class="title">Invite a New Crew Member</h2>

    <form class="invite-form" @submit.prevent="sendInvite">
      <input
        v-model="email"
        type="email"
        placeholder="Enter crew member's email"
        class="input"
        required
      />

      <button type="submit" :disabled="loading" class="btn">
        {{ loading ? 'Sending...' : 'Send Invitation' }}
      </button>

      <p v-if="message" :class="success ? 'success' : 'error'">
        {{ message }}
      </p>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const email = ref('')
const loading = ref(false)
const message = ref('')
const success = ref(false)

function sendInvite() {
  loading.value = true

  setTimeout(() => {
    message.value = `Invitation sent to ${email.value}!`
    success.value = true
    loading.value = false
    email.value = ''
  }, 1000)
}
</script>

<style scoped>
.invite-wrapper {
  max-width: 540px;
  margin: 3rem auto;
  padding: 2rem;
  border: 2px solid #4D1979;
  border-radius: 1rem;
  background-color: #faf7fc;
  box-shadow: 0 4px 14px rgba(0, 0, 0, 0.08);
  text-align: center;
}

.title {
  font-size: 1.8rem;
  font-weight: bold;
  margin-bottom: 1.5rem;
  color: #4D1979;
}

.invite-form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.input {
  padding: 0.75rem 1rem;
  border: 1px solid #ccc;
  border-radius: 0.5rem;
  font-size: 1rem;
  width: 90%;
}

.btn {
  padding: 0.75rem;
  background-color: #4D1979;
  color: white;
  font-weight: bold;
  border: none;
  border-radius: 0.5rem;
  cursor: pointer;
  transition: background-color 0.25s ease;
}

.btn:disabled {
  background-color: #a377c7;
  cursor: not-allowed;
}

.success {
  color: green;
  font-weight: 500;
}

.error {
  color: red;
  font-weight: 500;
}
</style>

