//crew views game schedule
<template>
    <div v-if="!gameScheduleAvailable">
        <h1>No upcoming game schedules at this time.</h1>
        <p>Please check again later!</p>
    </div>
    <div v-else>

    </div>
</template>

<script setup>
import { ref } from 'vue';

const gameScheduleAvailable = ref(false)


</script>

<style scoped>

</style>