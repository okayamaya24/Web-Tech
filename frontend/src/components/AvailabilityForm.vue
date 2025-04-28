<template>
  <div class="availability">
    <h1>Submit Availability</h1>

    <div v-if="loading">Loading games...</div>

    <div v-else>
      <table>
        <thead>
          <tr>
            <th>Sport</th>
            <th>Game Date</th>
            <th>Game Time</th>
            <th>Venue</th>
            <th>Opponent</th>
            <th>Available?</th>
            <th>Comment</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(game, index) in games" :key="game.id">
            <td>{{ game.sport }}</td>
            <td>{{ formatDate(game.date) }}</td>
            <td>{{ game.time }}</td>
            <td>{{ game.venue }}</td>
            <td>{{ game.opponent }}</td>
            <td>
              <select v-model="formData[game.id].available" required>
                <option value="">Select</option>
                <option :value="true">Yes</option>
                <option :value="false">No</option>
              </select>
            </td>
            <td>
              <input
                type="text"
                v-model="formData[game.id].comment"
                placeholder="Optional comment"
              />
            </td>
          </tr>
        </tbody>
      </table>

      <div style="margin-top: 2rem; text-align: center;">
        <button @click="submitAllAvailability">Submit Availability</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

const games = ref([]);
const formData = ref({});
const loading = ref(true);

const fetchGames = async () => {
  try {
    const response = await fetch('http://localhost:8080/api/games');
    const data = await response.json();
    games.value = data;

    // Initialize formData for each game
    data.forEach((game) => {
      formData.value[game.id] = {
        available: '',
        comment: ''
      };
    });
  } catch (error) {
    console.error('Error fetching games:', error);
  } finally {
    loading.value = false;
  }
};

const formatDate = (dateStr) => {
  if (!dateStr) return '';
  const options = { year: 'numeric', month: 'short', day: 'numeric' };
  return new Date(dateStr).toLocaleDateString(undefined, options);
};

const submitAllAvailability = async () => {
  try {
    const userEmail = localStorage.getItem('userEmail');
    if (!userEmail) {
      alert('Please log in first!');
      return;
    }

    // Prepare all filled availability
    const promises = [];

    for (const gameId in formData.value) {
      const entry = formData.value[gameId];
      if (entry.available !== '') {
        const payload = {
          available: entry.available,
          comment: entry.comment
        };

        const request = fetch(`http://localhost:8080/api/availability/${gameId}?userEmail=${encodeURIComponent(userEmail)}`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(payload)
        });

        promises.push(request);
      }
    }

    await Promise.all(promises);

    alert('Availability submitted successfully!');
    window.location.reload(); // Refresh to clear form
  } catch (error) {
    console.error('Error submitting availability:', error);
    alert('Something went wrong.');
  }
};

onMounted(fetchGames);
</script>
 
<style scoped>
div {
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

select, textarea, input[type="text"] {
  padding: 0.75rem;
  border: 1px solid #ccc;
  border-radius: 8px;
  font-size: 1rem;
  width: 95%; /* 🔥 wider now */
  background-color: #fff;
  transition: border-color 0.3s, box-shadow 0.3s;
}

select:focus, textarea:focus, input[type="text"]:focus {
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

div > div:last-child {
text-align: center;
font-style: italic;
color: #777;
margin-top: 2rem;
}
</style>
