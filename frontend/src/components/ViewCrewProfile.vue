<template>
  <div class="profile-container">
    <h2>Crew Member Profile</h2>

    <div v-if="crew">
      <div v-if="!isEditing">
        <p><strong>Name:</strong> {{ crew.firstName }} {{ crew.lastName }}</p>
        <p><strong>Email:</strong> {{ crew.email }}</p>
        <p><strong>Phone:</strong> {{ crew.phoneNumber }}</p>
        <p><strong>Role:</strong> {{ crew.role }}</p>
        <p><strong>Qualified Position:</strong> {{ crew.qualifiedPosition }}</p>
        <button @click="toggleEdit">Edit</button>
      </div>

      <div v-else>
        <label>First Name: <input v-model="form.firstName" /></label>
        <label>Last Name: <input v-model="form.lastName" /></label>
        <label>Email: <input v-model="form.email" disabled /></label>
        <label>Phone: <input v-model="form.phoneNumber" /></label>
        <label>Role: <input v-model="form.role" /></label>
        <label>Qualified Position: <input v-model="form.qualifiedPosition" /></label>
        <button @click="saveChanges">Save</button>
        <button @click="toggleEdit">Cancel</button>
      </div>
    </div>

    <div v-else>
      <p>Loading crew data...</p>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const crew = ref(null)
const isEditing = ref(false)
const form = reactive({
  firstName: '',
  lastName: '',
  email: '',
  phoneNumber: '',
  role: '',
  qualifiedPosition: ''
})

const router = useRouter()

function toggleEdit() {
  if (crew.value && !isEditing.value) {
    Object.assign(form, crew.value)
  }
  isEditing.value = !isEditing.value
}

async function saveChanges() {
  try {
    await axios.put(`http://localhost:8080/api/crew-members/${form.email}`, form)
    crew.value = { ...form }
    isEditing.value = false
    alert('Profile updated successfully!')
  } catch (err) {
    console.error('Failed to save changes:', err)
    alert('Could not save changes.')
  }
}

onMounted(async () => {
  const email = localStorage.getItem('userEmail')

  if (!email) {
    alert('You are not logged in.')
    router.push('/login')
    return
  }

  try {
    const res = await axios.get(`http://localhost:8080/api/crew-members/${email}`)
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

p, label {
  font-size: 1.1rem;
  margin: 0.5rem 0;
  display: block;
}

input {
  margin-left: 0.5rem;
  padding: 0.3rem;
  font-size: 1rem;
  border-radius: 5px;
  border: 1px solid #ccc;
}
button {
  margin: 0.5rem 0.5rem 0 0;
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 6px;
  background-color: #4D1979;
  color: white;
  font-weight: bold;
  cursor: pointer;
}
</style>
