<template>
  <h1>Create a Crew Member Profile</h1>
  Details
  <div>
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
        <label for="qualifiedPos">Qualified Position:</label>
        <input type="radio" id="sport-broadcasters" name="qualifiedPos" required value="Sport Broadcaster" v-model="formData.qualifiedPos">
        <label for="sport-broadcasters">Sport Broadcaster</label> 
        <input type="radio" id="paid-freelancers" name="qualifiedPos" required value="Paid Freelancer" v-model="formData.qualifiedPos">
        <label for="paid-freelancers">Paid Freelancer</label>
        <input type="radio" id="paid-students" name="qualifiedPos" required value="Paid Student" v-model="formData.qualifiedPos">
        <label for="paid-students">Paid Student</label> <br />
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

// need to implement logic of an already existing user here
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
  .invalid {
  border: 2px dashed red;
}

.invalid-radio {
  border: 2px dashed red;
  padding: 10px;
}
</style>