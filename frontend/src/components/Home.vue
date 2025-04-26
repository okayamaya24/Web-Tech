<template>
  <div class="welcome-box">
      <h1>Hello, Crew Member!</h1>

    <div class="notifications" v-if="notifications.length">
    <h2>Notifications:</h2>
    <ul>
      <li v-for="(note, index) in notifications" :key="index">
        {{ note.message }}
      </li>
    </ul>
  </div>

  <div v-else class="no-notifications">
    No new notifications.
  </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const notifications = ref([])

onMounted(() => {
fetch('/api/notifications')
  .then(res => res.json())
  .then(data => {
    notifications.value = data
  })
  .catch(err => {
    console.error('Failed to load notifications:', err)
  })
})
</script>

<style scoped>

.welcome-box {
justify-content: center;
align-items: center;
height: 200px;
max-width: 500px;
margin: 2rem auto;
padding: 2rem;
background-color: #f8f8f8;
border: 2px solid #ccc;
border-radius: 12px;
box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
text-align: center;
font-size: 1.2rem;
color: #444;
}
.notifications {
margin-top: 2rem;
text-align: left;
}

.notifications ul {
list-style-type: disc;
padding-left: 1.5rem;
}

.no-notifications {
margin-top: 2rem;
color: gray;
}
</style>