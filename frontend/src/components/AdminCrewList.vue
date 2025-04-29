<template>
  <div class="admin-crew-list">
    <h1>View Crew Members</h1>

    <table v-if="crewMembers.length">
      <thead>
        <tr>
          <th>First Name</th>
          <th>Last Name</th>
          <th>Email</th>
          <th>Phone Number</th>
          <th>Role</th>
          <th>Qualified Position</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="member in crewMembers" :key="member.id">
          <td>{{ member.firstName }}</td>
          <td>{{ member.lastName }}</td>
          <td>{{ member.email }}</td>
          <td>{{ member.phoneNumber }}</td>
          <td>{{ member.role }}</td>
          <td>{{ member.qualifiedPosition }}</td>
        </tr>
      </tbody>
    </table>

    <div v-else>
      No crew members found.
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const crewMembers = ref([])

const fetchCrewMembers = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/crew-members')
    crewMembers.value = response.data
    console.log('Fetched Crew Members:', crewMembers.value) 
  } catch (error) {
    console.error('Error fetching crew members:', error)
  }
}

onMounted(fetchCrewMembers)
</script>

<style scoped>
.admin-crew-list {
  padding: 2rem;
  max-width: 1000px;
  margin: 0 auto;
  background-color: #fafafa;
  border-radius: 10px;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 1rem;
  background-color: white;
  border-radius: 8px;
  overflow: hidden;
}

th, td {
  padding: 1rem;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

thead {
  background-color: #4D1979;
  color: white;
}

tbody tr:hover {
  background-color: #f2f2f2;
}
</style>
