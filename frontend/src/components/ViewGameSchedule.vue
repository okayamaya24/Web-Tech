<template>
  <div class="schedule">
      <table border="solid">
          <thead>
              <tr>
                  <th>Sport Type</th>
                  <th>Game Date and Time</th>
                  <th>Venue</th>
                  <th>Opponent (If Applicable)</th>
                  <th>Required Crew Positions</th>
              </tr>
          </thead>
          <tbody>
              <tr v-for="(game, index) in games" :key="index">
                  <td>
                       <select name="sports" id="sports" v-model="game.sport" :class=" { invalid: invalidIndices.includes(index) && !game.sport.trim() }">
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
                  </td>
                  <td>
                      <input type="datetime-local" v-model="game.datetime" required :class="{ invalid: invalidIndices.includes(index) && !game.datetime }"/>
                  </td>
                  <td>
                      <input type="text" v-model="game.venue" required placeholder="Venue" :class="{ invalid: invalidIndices.includes(index) && !game.venue.trim() }"/>
                  </td>
                  <td>
                      <input type="text" v-model="game.opponent" placeholder="If Not Applicable Enter N/A" required  :class="{ invalid: invalidIndices.includes(index) && !game.venue.trim() }"/>
                  </td>
                  <td>
                      <div :class="{ invalid: invalidIndices.includes(index) && game.crew.length === 0 }" class="crew-checkboxes">
                        <div class="checkbox-group">
                          <input type="checkbox" id="assisant-director" name="assistant-director" value="Assistant Director" v-model="game.crew" />
                        <label for="assistant-director">Assistant Director</label>
                        </div>
                        <div class="checkbox-group">
                          <input type="checkbox" id="assistant-producer" name="assistant-producer" value="Assistant Producer" v-model="game.crew"/>
                      <label for="assistant-producer">Assistant Producer</label>
                        </div>
                        <div class="checkbox-group">
                          <input type="checkbox" id="audio-a1" name="audio-a1" value="Audio A1" v-model="game.crew"/>
                      <label for="audio-a1">Audio, A1</label> 
                        </div>
                        <div class="checkbox-group">
                          <input type="checkbox" id="audio-assistant" name="audio-assistant" value="Audio Assistant A2" v-model="game.crew"/>
                      <label for="audio-assistant">Audio Assistant, A2</label>
                        </div>
                        <div class="checkbox-group">
                          <input type="checkbox" id="bug-operator" name="bug-operator" value="Bug Operator" v-model="game.crew"/>
                      <label for="bug-operator">Bug Operator</label>
                        </div>
                        <div class="checkbox-group">
                          <input type="checkbox" id="camera-fixed" name="camera-fixed" value="Camera Fixed" v-model="game.crew"/>
                      <label for="camera-fixed">Camera Fixed</label>
                        </div>
                        <div class="checkbox-group">
                          <input type="checkbox" id="camera-handheld" name="camera-handheld" value="Camera Handheld" v-model="game.crew"/>
                      <label for="camera-handheld">Camera Handheld</label>
                        </div>
                        <div class="checkbox-group">
                          <input type="checkbox" id="camera-steadicam" name="camera-steadicam" value="Camera Steadicam" v-model="game.crew"/>
                      <label for="camera-steadicam">Camera Steadicam</label>
                        </div>
                        <div class="checkbox-group">
                          <input type="checkbox" id="director" name="director" value="Director" v-model="game.crew"/>
                      <label for="director">Director</label>
                        </div>
                        <div class="checkbox-group">
                          <input type="checkbox" id="eic" name="eic" value="Engineer in Charge (EIC)" v-model="game.crew"/>
                      <label for="eic">Engineer in Charge (EIC)</label>
                        </div>
                        <div class="checkbox-group">
                          <input type="checkbox" id="eng2" name="eng2" value="ENG 2" v-model="game.crew"/>
                      <label for="eng2">ENG 2</label>
                        </div>
                        <div class="checkbox-group">
                          <input type="checkbox" id="evs-replay-lead" name="evs-replay-lead" value="EVS Replay Lead" v-model="game.crew"/>
                      <label for="evs-replay-lead">EVS Replay Lead</label>
                        </div>
                        <div class="checkbox-group">
                          <input type="checkbox" id="evs-replay-ro" name="evs-replay-ro" value="EVS Replay R/O" v-model="game.crew"/>
                      <label for="evs-replay-ro">EVS Replay R/O</label>
                        </div>
                        <div class="checkbox-group">
                          <input type="checkbox" id="graphics-operator" name="graphics-operator" value="Graphic Operator" v-model="game.crew"/>
                      <label for="graphics-operator">Graphics Operator</label>
                        </div>
                        <div class="checkbox-group">
                          <input type="checkbox" id="producer" name="producer" value="Producer" v-model="game.crew"/>
                      <label for="producer">Producer</label>
                        </div>
                        <div class="checkbox-group">
                          <input type="checkbox" id="technical-director" name="technical-director" value="Technical Director" v-model="game.crew"/>
                      <label for="technical-director">Technical Director</label>
                        </div>
                        <div class="checkbox-group">
                          <input type="checkbox" id="timeout-coordinator" name="timeout-coordinator" value="Timeout Coordinator" v-model="game.crew"/>
                      <label for="timeout-coordinator">Timeout Coordinator</label>
                        </div>
                        <div class="checkbox-group">
                          <input type="checkbox" id="video-operator" name="video-operator" value="Video Operator" v-model="game.crew"/>
                      <label for="video-operator">Video Operator</label>
                        </div>
                        <div class="checkbox-group">
                          <input type="checkbox" id="sport-broadcaster" name="sport-broadcaster" value="Sport Broadcaster" v-model="game.crew"/>
                      <label for="sport-broadcaster">Sport Broadcaster</label>
                        </div>
                        <div class="checkbox-group">
                          <input type="checkbox" id="paid-freelancer" name="paid-freelancer" value="Paid Freelancer" v-model="game.crew"/>
                      <label for="paid-freelancer">Paid Freelancer</label>
                        </div>
                        <div class="checkbox-group">
                          <input type="checkbox" id="paid-student" name="paid-student" value="Paid Student" v-model="game.crew"/>
                      <label for="paid-student">Paid Student</label>
                        </div>
                      </div>

                  </td>
                  <td>
                      <button @click="removeGame(index)" v-if="games.length > 1">Remove</button>
                  </td>
              </tr>
          </tbody>
      </table>

  <div style="margin-top: 1rem;">
    <button @click="addGame">Add New Game</button>
    <button @click="saveSchedule">Save Schedule</button>
  </div>
</div>

</template>

<script setup>

import { reactive, ref, computed } from 'vue'

const games = reactive([
{
  sport: '',
  datetime: '',
  venue: '',
  opponent: '',
  crew: []
}
])

const submitted = ref(false)

const invalidIndices = computed(() => {
if (!submitted.value) return []

const hasAtLeastOneValid = games.some(
g => g.sport.trim() && g.datetime && g.venue.trim() && g.crew.length
)

if (hasAtLeastOneValid) return []

return games
.map((g, i) => (!g.sport.trim() || !g.datetime || !g.venue.trim() || g.crew.length === 0) ? i : null)
.filter(i => i !== null)
})

function addGame() {
games.push({
  sport: '',
  datetime: '',
  venue: '',
  opponent: '',
  crew: []
})
}

function removeGame(index) {
games.splice(index, 1)
}

function saveSchedule() {
  submitted.value = true

const validGames = games.filter(
  (g) =>
    g.sport.trim() &&
    g.datetime &&
    g.venue.trim() &&
    g.crew.trim()
)


if (validGames.length === 0) {
  alert('Please enter at least one complete valid game entry before saving. Check for invalid entries.')
  return
}


fetch('/api/schedule', {
method: 'POST',
headers: {
  'Content-Type': 'application/json'
},
body: JSON.stringify(validGames)
})
.then(response => {
if (!response.ok) {
  throw new Error('Failed to save schedule');
}
return response.json();
})
.then(data => {
alert('Schedule saved!');
console.log('Saved schedule data:', data);
})
.catch(error => {
console.error('Error saving schedule:', error);
alert('Something went wrong. Try again!');
});
} 

</script>

<style scoped>

.schedule {
  max-width: 1000px;
  margin: 2rem auto;
  padding: 1rem;
}

table {
  width: 100%;
  border-collapse: collapse;
  background-color: white;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-radius: 12px;
  overflow: hidden;
}

th,
td {
  padding: 1rem;
  text-align: left;
  border-bottom: 1px solid #ddd;
  vertical-align: top;
}

th {
  background-color: #4D1979;
  color: white;
  font-weight: 600;
}

select,
input[type="text"],
input[type="datetime-local"] {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 1rem;
}

input[type="checkbox"] {
  margin-right: 0.5rem;
}

label {
  margin-right: 1rem;
  font-size: 0.95rem;
}

button {
  padding: 0.6rem 1.2rem;
  margin-right: 1rem;
  border: none;
  border-radius: 8px;
  background-color: #4D1979;
  color: white;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #6d2c91;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.crew-checkboxes {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 0.5rem;
  max-height: 200px;
  overflow-y: auto;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 6px;
  background-color: #f9f9f9;
}

.checkbox-group {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  white-space: nowrap;
}


div[style*="margin-top"] {
  text-align: center;
  margin-top: 2rem !important;
}

.invalid {
  border: 2px dashed red !important;
  border-radius: 6px;
}
</style>