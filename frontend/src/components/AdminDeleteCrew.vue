<template>
    <div class="delete-crew-container">
      <h2> Delete a Crew Member</h2>
  
      <table v-if="crewList.length > 0" class="crew-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Role</th>
            <th>Qualified Position</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="member in crewList" :key="member.id">
            <td>{{ member.firstName }} {{ member.lastName }}</td>
            <td>{{ member.email }}</td>
            <td>{{ member.role }}</td>
            <td>{{ member.qualifiedPosition }}</td>
            <td>
              <button class="delete-btn" @click="deleteCrew(member.id)">Delete</button>
            </td>
          </tr>
        </tbody>
      </table>
  
      <p v-else class="empty">No crew members to display.</p>
    </div>
  </template>
  
  <script setup>
  import { onMounted, ref } from 'vue';
  import axios from 'axios';
  
  const crewList = ref([]);
  
  async function loadCrew() {
    try {
      const response = await axios.get('http://localhost:8080/api/crew-members');
      crewList.value = response.data;
    } catch (error) {
      console.error('Error fetching crew members:', error);
    }
  }
  
  async function deleteCrew(id) {
    const confirmed = confirm('Are you sure you want to delete this crew member?');
  
    if (!confirmed) return;
  
    try {
      await axios.delete(`http://localhost:8080/api/crew-members/${id}`);
      crewList.value = crewList.value.filter(member => member.id !== id);
      alert('Crew member deleted successfully.');
    } catch (error) {
      console.error('Error deleting crew member:', error);
      alert('Failed to delete crew member.');
    }
  }
  
  onMounted(loadCrew);
  </script>
  
  <style scoped>
  .delete-crew-container {
    max-width: 900px;
    margin: 0 auto;
    padding: 2rem;
    text-align: center;
  }
  
  .crew-table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 1rem;
  }
  
  .crew-table th,
  .crew-table td {
    padding: 0.75rem;
    border: 1px solid #ccc;
  }
  
  .delete-btn {
    background-color: #c62828;
    color: white;
    padding: 0.5rem 0.75rem;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    font-weight: bold;
    transition: background-color 0.3s ease;
  }
  
  .delete-btn:hover {
    background-color: #a32020;
  }
  
  .empty {
    margin-top: 2rem;
    color: #555;
  }
  </style>
