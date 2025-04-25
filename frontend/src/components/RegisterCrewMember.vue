<template>
  <div class="page-background">
    <div class="form-container">
      <h1>Create a Crew Member Profile</h1>
      <p class="subtitle">Details</p>
      <form v-if="!isConfirming" @submit.prevent="confirmInput" name="detailsForm">
      <label for="firstName">First Name:</label>
      <input type="text" id="firstName" name="firstName" required v-model="formData.firstName" placeholder="'John'" @blur="touched.firstName = true" :class="{ 'invalid': touched.firstName && !formData.firstName }">
      <label for="lastName">Last Name:</label>
      <input type="text" id="lastName" name="lastName" required v-model="formData.lastName" placeholder="'Doe'" @blur="touched.lastName = true" :class="{ 'invalid': touched.lastName && !formData.lastName }"> <br />
      <label for="email">Email:</label>
      <input type="email" id="email" name="email" required v-model="formData.email" placeholder="'johndoe@email.com'" @blur="touched.email = true" :class="{ 'invalid': touched.email && !formData.email }">
      <label for="phone">Phone Number:</label>
      <input type="tel" id="phone" name="phone" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" required v-model="formData.phone" placeholder="Format: 123-456-7890" @blur="touched.phone = true" :class="{ 'invalid': touched.phone && !formData.phone }"> <br />
      <label for="password">Password:</label>
      <input type="password" id="password" name="password" required v-model="formData.password" placeholder="Enter Your Password Here" @blur="touched.password = true" :class="{ 'invalid': touched.password && !formData.password }">
      <label for="role">Role:</label>
      <input type="text" id="role" name="role" required v-model="formData.role" placeholder="Enter Your Role Here" @blur="touched.role = true" :class="{ 'invalid': touched.role && !formData.role }"> <br />
      <div :class="{ 'invalid-radio': touched.qualifiedPos && !formData.qualifiedPos }" @blur="touched.qualifiedPos = true" tabindex="0">
  <label>Qualified Position:</label>
  <div class="radio-group">
    <label class="radio-option">
      <input type="radio" name="qualifiedPos" required value="Sport Broadcaster" v-model="formData.qualifiedPos">
      Sport Broadcaster
    </label>
    <label class="radio-option">
      <input type="radio" name="qualifiedPos" required value="Paid Freelancer" v-model="formData.qualifiedPos">
      Paid Freelancer
    </label>
    <label class="radio-option">
      <input type="radio" name="qualifiedPos" required value="Paid Student" v-model="formData.qualifiedPos">
      Paid Student
    </label>
  </div>
</div>
      <input type="submit" value="Submit">
    </form>

    <div v-if="isConfirming">
    <h3>Confirm Your Information</h3>
    <p>First Name: {{ formData.firstName }}</p>
    <p>Last Name: {{ formData.lastName }}</p>
    <p>Email: {{ formData.email }}</p>
    <p>Phone Number: {{ formData.phone }}</p>
    <p>Password: {{ formData.password }}</p>
    <p>Role: {{ formData.role }}</p>
    <p>Qualified Position: {{ formData.qualifiedPos }}</p>

    <button @click="editInput">Edit</button>
    <button @click="submitForm">Submit</button>
    </div>
  </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import {useRouter} from 'vue-router';
import axios from 'axios';

const router = useRouter();

const formData = reactive({
  firstName: '',
  lastName: '',
  email: '',
  phone: '',
  password: '',
  role: '',
  qualifiedPos: ''
});

const touched = reactive({
  firstName: false,
  lastName: false,
  email: false,
  phone: false,
  password: false,
  role: false,
  qualifiedPos: false
});

const isConfirming = ref(false);

function confirmInput() {
  isConfirming.value = true;
}

function editInput() {
  isConfirming.value = false;
}

 async function submitForm() {
  const crewMemberData = {
    firstName: formData.firstName,
    lastName: formData.lastName,
    email: formData.email,
    phoneNumber: formData.phone,
    password: formData.password,
    role: formData.role,
    qualifiedPosition: formData.qualifiedPos
  };

  try {
    const response = await axios.post('http://localhost:8080/api/crew-members/register', crewMemberData);
    console.log('Crew member registered:', response.data);
    alert('Registration successful!');

    
    Object.keys(formData).forEach(key => formData[key] = '');
    isConfirming.value = false;

    router.push('/login');
  } catch (error) {
    const errorMsg = error.response?.data?.message || error.response?.data || '';

    if (errorMsg.includes('already exists')) {
      const goToLogin = confirm('An account with this email already exists. Would you like to go to the login page?');
      if (goToLogin) {
        router.push('/login');
      }
    } else {
      console.error('Error during registration:', errorMsg || error.message);
      alert('Registration failed. Please try again.');
    }
  }
}
</script>

<style scoped>
.page-background {
  background-color: #4D1979;
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 2rem;
}

.form-container {
  background-color: white;
  padding: 2rem;
  border-radius: 1rem;
  max-width: 600px;
  width: 100%;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
}

h1 {
  color: #4D1979;
  margin-bottom: 0.5rem;
}

.subtitle {
  margin-bottom: 1.5rem;
  font-weight: bold;
}

label {
  display: block;
  margin: 0.5rem 0 0.25rem;
  color: #333;
}

input[type="text"],
input[type="email"],
input[type="tel"],
input[type="password"],
input[type="submit"] {
  width: 100%;
  padding: 0.5rem;
  margin-bottom: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
}

input[type="submit"],
button {
  background-color: #4D1979;
  color: white;
  border: none;
  padding: 0.7rem 1.2rem;
  border-radius: 5px;
  cursor: pointer;
  margin-right: 0.5rem;
}

input[type="submit"]:hover,
button:hover {
  background-color: #6E2599;
}

.invalid {
  border: 2px dashed red;
}

.invalid-radio {
  border: 2px dashed red;
  padding: 10px;
}

input[type="radio"] {
  margin-right: 5px;
}

.radio-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  margin-top: 0.5rem;
}

.radio-option {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}
</style>
