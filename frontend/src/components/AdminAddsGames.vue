<template>
  <div class="schedule">
    <div class="header-actions">
      <h2>Current Game Schedule</h2>
      <div class="action-buttons">
        <button class="purple-btn" @click="openModal">Add New Game</button>
      </div>
    </div>

    <table>
      <thead>
        <tr>
          <th>Sport</th>
          <th>Date</th>
          <th>Time</th>
          <th>Venue</th>
          <th>Opponent</th>
          <th>Crew Positions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(game, index) in existingGames" :key="index">
          <td>{{ game.sport }}</td>
          <td>{{ game.date }}</td>
          <td>{{ game.time }}</td>
          <td>{{ game.venue }}</td>
          <td>{{ game.opponent }}</td>
          <td>{{ game.crewPositions?.join(', ') }}</td>
        </tr>
      </tbody>
    </table>

    <!-- Modal Popup -->
    <div v-if="showModal" class="modal-overlay">
      <div class="modal-content">
        <h2>Add New Game(s)</h2>

        <div v-for="(game, index) in newGames" :key="index" class="game-inputs">
          <label>Sport:</label>
          <select v-model="game.sport">
            <option value="">--</option>
            <option value="baseball">Baseball</option>
            <option value="football">Football</option>
            <option value="soccer">Soccer</option>
            <option value="">--</option>
                          <option value="baseball">Baseball</option>
                          <option value="basketball-men">Basketball, Men's</option>
                          <option value="basketball-women">Basketball, Women's</option>
                          <option value="beach-volleyball">Beach Volleyball</option>
                          <option value="cc-men">Cross Country, Men's</option>
                          <option value="cc-women">Cross Country, Women's</option>
                          <option value="equestrian">Equestrian</option>
                          <option value="football">Football</option>
                          <option value="golf-men">Golf, Men's</option>
                          <option value="golf-women">Golf, Women's</option>
                          <option value="indoor-track-men">Indoor Track and Field, Men's</option>
                          <option value="indoor-track-women">Indoor Track and Field, Women's</option>
                          <option value="outdoor-track-men">Outdoor Track and Field, Men's </option>
                          <option value="outdoor-track-women">Outdoor Track and Field, Women's</option>
                          <option value="rifle">Rifle</option>
                          <option value="soccer">Soccer</option>
                          <option value="swimming-men">Swimming and Diving, Men's</option>
                          <option value="swimming-women">Swimming and Diving, Women's</option>
                          <option value="tennis-men">Tennis, Men's</option>
                          <option value="tennis-women">Tennis, Women's</option>
                          <option value="triathlon">Triathlon</option>
                          <option value="volleyball">Volleyball</option>
          </select>

          <label>Date & Time:</label>
          <input type="datetime-local" v-model="game.datetime" />

          <label>Venue:</label>
<select v-model="game.venue">
  <option disabled value="">Select Venue</option>
  <option>Amon G. Carter Stadium</option>
  <option>Schollmaier Arena</option>
  <option>Lupton Baseball Stadium</option>
  <option>Garvey-Rosenthal Soccer Stadium</option>
  <option>University Recreation Center</option>
  <option>Beyer Track</option>
  <option>TCU Tennis Center</option>
  <option>Schaefer Success Center</option>
  <option>Frog Alley</option>
  <option>Lowdon Track and Field Complex</option>
  <option>Sue & Edgar Wachenheim Courts</option>
</select>

          <label>Opponent (If any):</label>
          <input type="text" v-model="game.opponent" placeholder="Opponent" />

          <label>Required Crew Positions:</label>
          <div class="crew-checkboxes">
            <label v-for="(role, i) in crewRoles" :key="i">
              <input type="checkbox" :value="role" v-model="game.crew" />
              {{ role }}
            </label>
          </div>

          <button @click="removeGame(index)">Remove</button>
          <hr />
        </div>

        <div style="margin-top: 1rem">
          <button @click="addGame">Add New Game</button>
          <button @click="saveGames">Save New Games</button>
          <button @click="showModal = false">Close</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const existingGames = ref([])
const newGames = ref([])
const submitted = ref(false)
const showModal = ref(false)

const crewRoles = [
  'Assistant Director', 'Assistant Producer', 'Audio A1', 'Audio Assistant A2',
  'Bug Operator', 'Camera Fixed', 'Camera Handheld', 'Camera Steadicam',
  'Director', 'Engineer in Charge (EIC)', 'ENG 2', 'EVS Replay Lead',
  'EVS Replay R/O', 'Graphic Operator', 'Producer', 'Technical Director',
  'Timeout Coordinator', 'Video Operator', 'Sport Broadcaster',
  'Paid Freelancer', 'Paid Student'
]

onMounted(() => {
  fetch('http://localhost:8080/api/games')
    .then(res => res.json())
    .then(data => {
      existingGames.value = data
    })
    .catch(err => {
      console.error('Failed to fetch games:', err)
    })
})

function openModal() {
  newGames.value = []
  addGame()
  showModal.value = true
}

function addGame() {
  newGames.value.push({
    sport: '',
    datetime: '',
    venue: '',
    opponent: '',
    crew: []
  })
}

function removeGame(index) {
  newGames.value.splice(index, 1)
}

function saveGames() {
  submitted.value = true

  const validGames = newGames.value.filter(g =>
    g.sport.trim() && g.datetime && g.venue.trim() && g.crew.length > 0
  )

  if (validGames.length === 0) {
    alert('Please enter valid games before saving.')
    return
  }

  const postData = validGames.map(game => {
    const [date, time] = game.datetime.split('T')
    return {
      sport: game.sport.trim(),
      date,
      time,
      venue: game.venue.trim(),
      opponent: game.opponent.trim(),
      crewPositions: game.crew
    }
  })

  fetch('http://localhost:8080/api/games', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(postData)
  })
    .then(res => {
      if (!res.ok) throw new Error('Failed to save games')
      return res.json()
    })
    .then(() => {
      alert('Games added successfully!')
      newGames.value = []
      submitted.value = false
      showModal.value = false
      return fetch('http://localhost:8080/api/games')
    })
    .then(res => res.json())
    .then(data => {
      existingGames.value = data
    })
    .catch(err => {
      console.error('Error adding games:', err)
      alert('Error adding games.')
    })
}
</script>

<style scoped>
.schedule {
  max-width: 1000px;
  margin: 2rem auto;
  padding: 1rem;
}

.purple-btn {
  background-color: #4D1979;
  color: white;
  font-weight: bold;
  padding: 0.6rem 1.2rem;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}

.purple-btn:hover {
  background-color: #6d2c91;
}

.modal-overlay {
  position: fixed;
  inset: 0;
  background-color: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}

.modal-content {
  background: white;
  border-radius: 1rem;
  padding: 2rem;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
  width: 90%;
  max-width: 800px;
}

th,
td {
  padding: 0.75rem;
  border: 1px solid #ccc;
  text-align: left;
}

.game-inputs {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.crew-checkboxes {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
  gap: 0.5rem 1rem;
  max-height: 200px;
  overflow-y: auto;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 6px;
  background-color: #f9f9f9;
}
</style>
