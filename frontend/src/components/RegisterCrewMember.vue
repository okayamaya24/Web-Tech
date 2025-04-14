<template>
  <h1>Create a Crew Member Profile</h1>
  Details
  <div>
  <form v-if="!isConfirming" @submit.prevent="confirmInput" name="detailsForm">
      <label for="firstName">First Name:</label>
      <input type="text" id="firstName" name="firstName" required v-model="formData.firstName">
      <label for="lastName">Last Name:</label>
      <input type="text" id="lastName" name="lastName" required v-model="formData.lastName"> <br />
      <label for="email">Email:</label>
      <input type="email" id="email" name="email" required v-model="formData.email">
      <label for="phone">Phone Number:</label>
      <input type="tel" id="phone" name="phone" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" required v-model="formData.phone" placeholder="Format: 123-456-7890"> <br />
      <label for="password">Password:</label>
      <input type="password" id="password" name="password" required v-model="formData.password">
      <label for="role">Role:</label>
      <input type="text" id="role" name="role" required v-model="formData.role"> <br />
      <label for="qualifiedPos">Qualified Position:</label>
      <input type="radio" id="sport-broadcasters" name="qualifiedPos" required value="Sport Broadcaster" v-model="formData.qualifiedPos">
      <label for="sport-broadcasters">Sport Broadcaster</label> 
      <input type="radio" id="paid-freelancers" name="qualifiedPos" required value="Paid Freelancer" v-model="formData.qualifiedPos">
      <label for="paid-freelancers">Paid Freelancer</label>
      <input type="radio" id="paid-students" name="qualifiedPos" required value="Paid Student" v-model="formData.qualifiedPos">
      <label for="paid-students">Paid Student</label> <br />
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
</template>

<script setup>
import { reactive, ref } from 'vue';
import axios from 'axios';

const formData = reactive({
  firstName: '',
  lastName: '',
  email: '',
  phone: '',
  password: '',
  role: '',
  qualifiedPos: ''
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
     const respone = await axios.post('http://localhost:8080/api/crew-members/register', crewMemberData);
    console.log('Crew member registered:', response.data);
     alert('Registration successful!');
      } catch (error) {
     console.error('Error during registration:', error.response?.data || error.message);
    alert('Registration failed. Please try again.');
  }
}
</script>

<style scoped>
input:invalid {
border: 2px dashed red;
}
</style>
