<template>
  <div class="crew-assignment">
    <h1>Schedule Crew</h1>

    <div v-if="games.length">
      <label>Select a Game:</label>
      <select v-model="selectedGameId">
        <option disabled value="">Select a game</option>
        <option v-for="game in games" :key="game.id" :value="game.id">
          {{ game.sport }} vs {{ game.opponent }} ({{ formatDate(game.gameDate) }})
        </option>
      </select>
    </div>

    <div v-if="selectedGame">
      <h2>Assign Crew for {{ selectedGame.sport }} vs {{ selectedGame.opponent }}</h2>

      <div v-for="position in crewPositions" :key="position">
        <label>{{ position }}:</label>
        <select v-model="assignments[position]">
          <option disabled value="">Select crew member</option>
          <option v-for="member in availableCrew" :key="member.id" :value="member.id">
            {{ member.name }} ({{ member.qualifications.join(', ') }})
          </option>
        </select>
      </div>

      <div class="actions">
        <button @click="saveDraft">Save Draft</button>
        <button @click="finalizeAssignments">Finalize Assignments</button>
      </div>

      <div v-if="validationErrors.length">
        <ul>
          <li v-for="(error, index) in validationErrors" :key="index" class="error">{{ error }}</li>
        </ul>
      </div>
    </div>

    <div v-else>
    No games available to schedule crews. 🎉
  </div>

  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue';

const games = ref([]);
const selectedGameId = ref('');
const crewPositions = ref(['Referee', 'Scorekeeper', 'Timer']);
const assignments = ref({});
const availableCrew = ref([]);
const validationErrors = ref([]);


const fetchGames = async () => {
const response = await fetch('/api/schedule');
const data = await response.json();
games.value = data;
};

const fetchAvailableCrew = async (gameId) => {
const response = await fetch(`/api/available-crew/${gameId}`);
const data = await response.json();
availableCrew.value = data;
};

const selectedGame = computed(() => {
return games.value.find(g => g.id === selectedGameId.value);
});

watch(selectedGameId, (newGameId) => {
if (newGameId) {
  fetchAvailableCrew(newGameId);
  assignments.value = {};
}
});

const saveDraft = async () => {
try {
  await fetch(`/api/crew-assignments/draft/${selectedGameId.value}`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(assignments.value)
  });
  alert('Draft saved successfully!');
} catch (error) {
  console.error('Error saving draft:', error);
  alert('Failed to save draft.');
}
};


const validateAssignments = () => {
validationErrors.value = [];

for (const position of crewPositions.value) {
  if (!assignments.value[position]) {
    validationErrors.value.push(`No crew member assigned for ${position}.`);
  }
}



return validationErrors.value.length === 0;
};


const finalizeAssignments = async () => {
if (!validateAssignments()) {
  return;
}

try {
  await fetch(`/api/crew-assignments/finalize/${selectedGameId.value}`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(assignments.value)
  });

  await fetch(`/api/notifications/crew-assigned/${selectedGameId.value}`, {
    method: 'POST'
  });

  alert('Crew assignments finalized and notifications sent!');
} catch (error) {
  console.error('Error finalizing assignments:', error);
  alert('Failed to finalize assignments.');
}
};

onMounted(fetchGames);
</script>

<style scoped>
.crew-assignment {
padding: 2rem;
max-width: 800px;
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

h2 {
margin-top: 2rem;
margin-bottom: 1rem;
font-size: 1.5rem;
color: #333;
}

form {
display: flex;
flex-direction: column;
gap: 1rem;
background-color: white;
padding: 1.5rem;
border-radius: 8px;
box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
}

label {
font-weight: bold;
color: #555;
}

select {
padding: 0.75rem;
border: 1px solid #ccc;
border-radius: 8px;
font-size: 1rem;
width: 100%;
background-color: #fff;
transition: border-color 0.3s, box-shadow 0.3s;
}

select:focus {
border-color: #4D1979;
box-shadow: 0 0 8px rgba(123, 0, 255, 0.3);
outline: none;
}

button {
padding: 0.75rem;
border: none;
background-color: #4D1979;
color: white;
border-radius: 8px;
font-size: 1rem;
font-weight: bold;
cursor: pointer;
transition: background-color 0.3s, transform 0.2s;
}

button:hover {
background-color: #3a125e;
transform: translateY(-2px);
}

button:active {
transform: translateY(2px);
}

.actions {
display: flex;
gap: 1rem;
justify-content: center;
margin-top: 1rem;
}

.error {
color: red;
font-style: italic;
}

div > div:last-child {
text-align: center;
font-style: italic;
color: #777;
margin-top: 2rem;
}
</style>
