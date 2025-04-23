<template>
    <div class=".dashboard-layout">
        <nav>
        <router-link :to="{name: 'viewGameSchedule'}">View Game Schedule</router-link>
        <router-link :to="{name: 'crew'}">View Crew List</router-link>
        <router-link :to="{name: 'availability'}">Availability</router-link>
        </nav>
        <router-view></router-view>
    </div>
</template>

<script setup>

</script>

<style scoped>

.dashboard-layout {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    
  }
  
nav {
        display: flex;
        justify-content: center;
        gap: 2rem;
        padding: 1.5rem;
        background-color: #f5f5f5;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);

        .router-link-active {
            background-color: purple;
            color: #fff;
            border-color: purple;
            border-radius: 6px;
            box-shadow: 0 6px 12px rgba(0, 0,0, 0.2);
            transform: translateY(-2px);
        }
    }
</style>