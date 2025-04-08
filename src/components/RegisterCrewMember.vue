<template>
  <h1>Create a Crew Member Profile</h1>
  Details
  <div>
  <form name="detailsForm">
      <label for="firstName">First Name:</label>
      <input type="text" id="firstName" name="firstName" required>
      <label for="lastName">Last Name:</label>
      <input type="text" id="lastName" name="lastName" required> <br />
      <label for="email">Email:</label>
      <input type="email" id="email" name="email" required>
      <label for="phone">Phone Number:</label>
      <input type="tel" id="phone" name="phone" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" required> <br />
      <label for="password">Password:</label>
      <input type="password" id="password" name="password" required>
      <label for="role">Role:</label>
      <input type="text" id="role" name="role" required> <br />
      <label for="qualifiedPos">Qualified Position:</label>
      <input type="text" id="qualifiedPos" name="qualifiedPos" required> <br />
      <input type="submit" value="Submit">
  </form>
  </div>

</template>
<script setup>




</script>


<style scoped>
input:invalid {
border: 2px dashed red;
}
</style>
