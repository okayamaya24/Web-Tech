<template>
    <div v-if="!gameScheduleAvailable" class="no-schedule">
        <h1>No upcoming game schedules at this time.</h1>
        <p>Please check again later!</p>
    </div>
    <div v-else>
    <h1>Upcoming Game Schedules</h1>
    <div class="controls">
    <input v-model="searchQuery" placeholder="Search by date, venue, or opponent..." />
    
    <select v-model="selectedSport">
      <option value="">All Sports</option>
      <option v-for="sport in uniqueSports" :key="sport" :value="sport">{{ formatSport(sport) }}</option>
    </select>

    <label for="sort">Sort by:</label>
    <select id="sort" v-model="sortBy">
    <option value="datetime">Date (Newest to Oldest)</option>
    <option value="venue">Venue</option>
    <option value="opponent">Opponent</option>
    </select>
    </div>
    <table>
        <div v-if="selectedGame" class="game-details">
        <h2>Game Details</h2>
        <p><strong>Sport:</strong> {{ formatSport(selectedGame.sport) }}</p>
        <p><strong>Date & Time:</strong> {{ formatDate(selectedGame.datetime) }}</p>
        <p><strong>Venue:</strong> {{ selectedGame.venue }}</p>
        <p><strong>Opponent:</strong> {{ selectedGame.opponent }}</p>
        <p><strong>Crew Positions:</strong></p>
        <ul>
        <li v-for="(role, index) in selectedGame.crew" :key="index">{{ role }}</li>
        </ul>
        <button @click="selectedGame = null">Close</button>
        </div>
      <thead>
        <tr>
          <th>Sport</th>
          <th>Date & Time</th>
          <th>Venue</th>
          <th>Opponent</th>
          <th>Crew Positions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(game, index) in filteredAndSortedGames"
        :key="index"
        @click="selectGame(game)"
        style="cursor: pointer; background-color: #f9f9f9;" >
          <td>{{ formatSport(game.sport) }}</td>
          <td>{{ formatDate(game.datetime) }}</td>
          <td>{{ game.venue }}</td>
          <td>{{ game.opponent }}</td>
          <td>
            <ul>
              <li v-for="role in game.crew" :key="role">{{ role }}</li>
            </ul>
          </td>
        </tr>
      </tbody>
    </table>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import axios from 'axios'

const gameSchedule = ref([])
const gameScheduleAvailable = ref(false)

const searchQuery = ref('')
const selectedSport = ref('')
const sortBy = ref('datetime')

const selectedGame = ref(null);

function selectGame(game) {
  selectedGame.value = game;
}

onMounted(async () => {
  try {
    const response = await axios.get('/api/schedule')
    if (Array.isArray(response.data) && response.data.length > 0) {
      gameSchedule.value = response.data
      gameScheduleAvailable.value = true
    }
  } catch (error) {
    console.error('Failed to load game schedule:', error)
  }
})

const uniqueSports = computed(() => {
  const sports = gameSchedule.value.map(g => g.sport)
  return [...new Set(sports)]
})

const filteredAndSortedGames = computed(() => {
  let filtered = gameSchedule.value;

  if (searchQuery.value) {
    const query = searchQuery.value.toLowerCase();
    filtered = filtered.filter(game =>
      game.venue.toLowerCase().includes(query) ||
      game.opponent.toLowerCase().includes(query)
    );
  }

  // Sport filter
  if (selectedSport.value) {
    filtered = filtered.filter(game => game.sport === selectedSport.value);
  }

    return [...filtered].sort((a, b) => {
    switch (sortBy.value) {
      case 'datetime':
        return new Date(b.datetime) - new Date(a.datetime);
      case 'venue':
        return a.venue.localeCompare(b.venue);
      case 'opponent':
        return a.opponent.localeCompare(b.opponent);
      default:
        return 0;
    }
  });
});

function formatDate(datetime) {
  const date = new Date(datetime)
  return date.toLocaleString(undefined, {
    weekday: 'short',
    year: 'numeric',
    month: 'short',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

function formatSport(sport) {
  return sport.replace(/-/g, ' ').replace(/\b\w/g, char => char.toUpperCase())
}
</script>

<style scoped>
.no-schedule {
  justify-content: center;
  align-items: center;
  height: 200px;
  max-width: 500px;
  margin: 2rem auto;
  padding: 2rem;
  background-color: #f8f8f8;
  border: 2px solid #ccc;
  border-radius: 12px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
  font-size: 1.2rem;
  color: #444;
}

.controls {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  margin-bottom: 1rem;
}

.controls input,
.controls select {
  padding: 0.5rem;
  font-size: 1rem;
}

.game-details {
  margin-top: 2rem;
  padding: 1rem;
  border: 2px solid #ccc;
  border-radius: 8px;
  background-color: #fdfdfd;
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
}
</style>