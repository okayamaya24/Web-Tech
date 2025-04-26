<template>
  <div class="admin-view">
    <h1>Admin Dashboard</h1>

    <!-- Invitation Form (Use Case 14) -->
    <section class="section">
      <AdminInviteForm />
    </section>

    <!-- Crew List Placeholder (Use Case 16, optional layout only) -->
    <section class="section">
      <h2>Crew Members</h2>
      <p>This section will show a list of invited or registered crew members.</p>
      <!-- Future: Replace with <ul> or table of members from API -->
    </section>
  </div>
</template>

<script setup>
import AdminInviteForm from '../components/AdminInviteForm.vue'
</script>

<style scoped>
.admin-view {
  max-width: 900px;
  margin: 0 auto;
  padding: 2rem;
  font-family: 'Segoe UI', sans-serif;
}

h1 {
  text-align: center;
  color: #4D1979;
  margin-bottom: 2rem;
}

.section {
  margin-top: 2rem;
  background-color: #f9f7fb;
  border: 2px solid #e0d4ef;
  padding: 2rem;
  border-radius: 1rem;
  box-shadow: 0 4px 14px rgba(0, 0, 0, 0.06);
}
</style>
