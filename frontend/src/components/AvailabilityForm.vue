<template>
    <div>
      <h1>Submit Availability</h1>
  
      <div v-if="unsubmittedGames.length > 0">
        <div v-for="game in unsubmittedGames" :key="game.id">
          <h2>{{ game.sport }} vs {{ game.opponent }} ({{ formatDate(game.gameDate) }})</h2>
  
          <form @submit.prevent="submitAvailability(game.id)">
            <div>
              <label>Available:</label>
              <select v-model="formData[game.id].available" required>
                <option value="">Select availability</option>
                <option :value="true">Yes</option>
                <option :value="false">No</option>
              </select>
            </div>
  
            <div>
              <label >Comment (Optional):</label>
              <textarea v-model="formData[game.id].comment" rows="3" placeholder="Any notes..."></textarea>
            </div>
  
            <button type="submit">
              Submit Availability
            </button>
          </form>
        </div>
      </div>
  
      <div v-else>
        You have submitted availability for all games. 🎉
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted, computed } from 'vue';
  
  const games = ref([]);
  const submittedAvailability = ref([]);
  const formData = ref({});
  
  const fetchGames = async () => {
    try {
      const response = await fetch('/api/schedule');
      const data = await response.json();
      games.value = data;
  

      const availabilityResponse = await fetch('/api/availability');
      const availabilityData = await availabilityResponse.json();
      submittedAvailability.value = availabilityData.map(item => item.gameId);
  
      
      games.value.forEach(game => {
        if (!submittedAvailability.value.includes(game.id)) {
          formData.value[game.id] = {
            available: '',
            comment: ''
          };
        }
      });
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };
  
  const submitAvailability = async (gameId) => {
    try {
      const payload = {
        available: formData.value[gameId].available,
        comment: formData.value[gameId].comment
      };
  
      await fetch(`/api/availability/${gameId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
      });
  
      alert('Availability submitted successfully!');
      delete formData.value[gameId];
      games.value = games.value.filter(game => game.id !== gameId);
  
    } catch (error) {
      console.error('Error submitting availability:', error);
      alert('Failed to submit availability.');
    }
  };
  
  const formatDate = (dateStr) => {
    const options = { year: 'numeric', month: 'short', day: 'numeric' };
    return new Date(dateStr).toLocaleDateString(undefined, options);
  };
  
  onMounted(fetchGames);
  
  const unsubmittedGames = computed(() => {
    return games.value.filter(game => !submittedAvailability.value.includes(game.id));
  });
  </script>
  
  <style scoped>
  
  </style>