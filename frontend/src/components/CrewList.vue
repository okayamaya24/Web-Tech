<template>
    <div class="p-4">
      <h1 class="text-2xl font-bold mb-4">Crew List</h1>
  
      <div class="mb-6">
        <label class="block mb-2 font-semibold">Select Game:</label>
        <select v-model="selectedGameId" class="border rounded p-2 w-full" @change="selectGame">
          <option value="" disabled>Select a game</option>
          <option v-for="game in games" :key="game.id" :value="game.id">
            {{ game.sport }} vs {{ game.opponent }} ({{ formatDate(game.gameDate) }})
          </option>
        </select>
      </div>
  
      <div v-if="selectedGame" class="overflow-x-auto">
        <table class="min-w-full bg-white border border-gray-300">
          <thead class="bg-gray-100">
            <tr>
              <th class="py-2 px-4 border-b">Sport</th>
              <th class="py-2 px-4 border-b">Opponent</th>
              <th class="py-2 px-4 border-b">Game Date</th>
              <th class="py-2 px-4 border-b">Game Time</th>
              <th class="py-2 px-4 border-b">Position</th>
              <th class="py-2 px-4 border-b">Name</th>
              <th class="py-2 px-4 border-b">Report Time</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(crew, index) in selectedGame.crew" :key="index">
              <td class="py-2 px-4 border-b">{{ selectedGame.sport }}</td>
              <td class="py-2 px-4 border-b">{{ selectedGame.opponent }}</td>
              <td class="py-2 px-4 border-b">{{ formatDate(selectedGame.gameDate) }}</td>
              <td class="py-2 px-4 border-b">{{ formatTime(selectedGame.gameTime) }}</td>
              <td class="py-2 px-4 border-b">{{ crew.position }}</td>
              <td class="py-2 px-4 border-b">{{ crew.name }}</td>
              <td class="py-2 px-4 border-b">{{ formatDateTime(crew.reportTime) }}</td>
            </tr>
          </tbody>
        </table>
      </div>
  
      <div v-else class="text-gray-500">
        Please select a game to view the crew list.
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  
  const games = ref([]);
  const selectedGameId = ref('');
  const selectedGame = ref(null);
  
  const fetchGames = async () => {
    try {
      const response = await fetch('/api/schedule');
      const data = await response.json();
      games.value = data;
    } catch (error) {
      console.error('Error fetching games:', error);
    }
  };
  
  const selectGame = () => {
    selectedGame.value = games.value.find(game => game.id === selectedGameId.value);
  };
  
  const formatDate = (dateStr) => {
    const options = { year: 'numeric', month: 'short', day: 'numeric' };
    return new Date(dateStr).toLocaleDateString(undefined, options);
  };
  
  const formatTime = (timeStr) => {
    const options = { hour: '2-digit', minute: '2-digit' };
    return new Date(timeStr).toLocaleTimeString(undefined, options);
  };
  
  const formatDateTime = (dateStr) => {
    const options = { year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit' };
    return new Date(dateStr).toLocaleString(undefined, options);
  };
  
  onMounted(fetchGames);
  </script>
  
  <style scoped>
  /* Optional table styling */
  </style>