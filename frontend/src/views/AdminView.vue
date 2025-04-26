<template>
    <div class="admin-view">
      <h1>Welcome, Admin</h1>

      <div v-if="notifications.length > 0" class="notifications">
      <h2>Notifications</h2>
      <ul>
        <li v-for="(notification, index) in notifications" :key="index">
          {{ notification.message }}
        </li>
      </ul>
    </div>
    <div v-else>
      No notifications yet.
    </div>
      
    </div>
  </template>
  
  <script setup>
import { ref, onMounted } from 'vue';

const notifications = ref([]);

const fetchNotifications = async () => {
  try {
    const response = await fetch('/api/notifications');
    const data = await response.json();
    notifications.value = data;
  } catch (error) {
    console.error('Error fetching notifications:', error);
  }
};

onMounted(() => {
  fetchNotifications();

  setInterval(fetchNotifications, 10000);
});
  </script>

  <style>
  .admin-view {
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
  </style>

