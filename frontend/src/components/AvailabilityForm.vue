<template>
  <div>
    <h1>Submit Availability</h1>

    <div v-if="loading">
      Loading games...
    </div>

    <div v-else-if="unsubmittedGames.length > 0">
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
const loading = ref(true); // <-- new

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
  } finally {
    loading.value = false; // <-- when fetching is done
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

    await sendNotification(gameId, payload.available, payload.comment);

    alert('Availability submitted successfully!');
    delete formData.value[gameId];
    games.value = games.value.filter(game => game.id !== gameId);
  } catch (error) {
    console.error('Error submitting availability:', error);
    alert('Failed to submit availability.');
  }
};

const sendNotification = async (gameId, available, comment) => {
  try {
    await fetch('/api/notifications', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        gameId,
        available,
        comment,
        message: `New availability submitted for Game ID: ${gameId}. Available: ${available ? 'Yes' : 'No'}. Comment: ${comment || 'None'}`
      })
    });
  } catch (error) {
    console.error('Error sending notification:', error);
  }
};
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

select, textarea {
padding: 0.75rem;
border: 1px solid #ccc;
border-radius: 8px;
font-size: 1rem;
width: 100%;
background-color: #fff;
transition: border-color 0.3s, box-shadow 0.3s;
}

select:focus, textarea:focus {
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