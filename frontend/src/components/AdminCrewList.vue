<template>
  <div class="manage-crew">
    <h1>Manage Crew Members</h1>

    <div v-if="crewMembers.length > 0" class="crew-list">
      <div v-for="(member, index) in crewMembers" :key="index" class="crew-card">
        <p><strong>Name:</strong> {{ member.firstName }} {{ member.lastName }}</p>
        <p><strong>Email:</strong> {{ member.email }}</p>
        <p><strong>Role:</strong> {{ member.role }}</p>
        <button @click="deleteCrewMember(index)">Delete</button>
      </div>
    </div>

    <div v-else class="empty-message">
      <p>No crew members available.</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

// Fake crew member data for now
const crewMembers = ref([
  { firstName: 'John', lastName: 'Doe', email: 'john.doe@example.com', role: 'Sport Broadcaster' },
  { firstName: 'Jane', lastName: 'Smith', email: 'jane.smith@example.com', role: 'Paid Freelancer' },
])

function deleteCrewMember(index) {
  if (confirm('Are you sure you want to delete this crew member?')) {
    crewMembers.value.splice(index, 1)
  }
}
</script>

<style scoped>
.manage-crew {
  max-width: 800px;
  margin: 2rem auto;
  padding: 2rem;
  background-color: #faf7fc;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.crew-list {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
}

.crew-card {
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 1rem;
  box-shadow: 0 2px 6px rgba(0,0,0,0.08);
}

.crew-card p {
  margin: 0.5rem 0;
}

button {
  margin-top: 0.5rem;
  background-color: #e63946;
  color: white;
  border: none;
  padding: 0.6rem 1rem;
  border-radius: 6px;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #b71c1c;
}

.empty-message {
  text-align: center;
  margin-top: 2rem;
  font-size: 1.2rem;
  color: #777;
}
</style>
