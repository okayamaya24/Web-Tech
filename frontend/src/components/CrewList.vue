<template>
  <div>
    <h1>Crew List</h1>

    <div>
      <label>Select Game:</label>
      <select v-model="selectedGameId" @change="selectGame">
        <option value="" disabled>Select a game</option>
        <option v-for="game in games" :key="game.id" :value="game.id">
          {{ game.sport }} vs {{ game.opponent }} ({{ formatDate(game.gameDate) }})
        </option>
      </select>
    </div>

    <div v-if="selectedGame">
      <table>
        <thead>
          <tr>
            <th>Sport</th>
            <th>Opponent</th>
            <th>Game Date</th>
            <th>Game Time</th>
            <th>Position</th>
            <th>Name</th>
            <th>Report Time</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(crew, index) in selectedGame.crew" :key="index">
            <td>{{ selectedGame.sport }}</td>
            <td>{{ selectedGame.opponent }}</td>
            <td>{{ formatDate(selectedGame.gameDate) }}</td>
            <td>{{ formatTime(selectedGame.gameTime) }}</td>
            <td>{{ crew.position }}</td>
            <td>{{ crew.name }}</td>
            <td>{{ formatDateTime(crew.reportTime) }}</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div v-else>
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
div {
padding: 2rem;
max-width: 900px;
margin: 0 auto;
background-color: #f9f9f9;
border-radius: 10px;
box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
}

h1 {
text-align: center;
margin-bottom: 2rem;
color: #4D1979;
font-size: 2rem;
}

label {
display: block;
margin-bottom: 0.5rem;
font-weight: bold;
color: #333;
}

select {
width: 100%;
padding: 0.75rem;
border: 1px solid #ccc;
border-radius: 8px;
font-size: 1rem;
margin-bottom: 2rem;
background-color: #fff;
transition: border-color 0.3s, box-shadow 0.3s;
}

select:focus {
border-color: #4D1979;
box-shadow: 0 0 8px rgba(123, 0, 255, 0.3);
outline: none;
}

table {
width: 100%;
border-collapse: collapse;
margin-top: 1rem;
background-color: #fff;
border-radius: 8px;
overflow: hidden;
box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

thead {
background-color: #4D1979;
color: white;
}

thead th {
padding: 1rem;
text-align: left;
font-weight: bold;
}

tbody td {
padding: 1rem;
border-bottom: 1px solid #eee;
}

tbody tr:hover {
background-color: #f2f2f2;
}

div > div:last-child {
text-align: center;
font-style: italic;
color: #777;
margin-top: 2rem;
}
</style>