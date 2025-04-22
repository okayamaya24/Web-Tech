<template>
    <div>
        <table border="solid">
            <thead>
                <tr>
                    <th>Sport</th>
                    <th>Game Date and Time</th>
                    <th>Venue</th>
                    <th>Opponent (If Applicable)</th>
                    <th>Required Crew Positions</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(game, index) in games" :key="index">
                        <template v-if="game?.isNew">
                            <td v-if="game && game.isNew">
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
                            <div :class="{ invalid: invalidIndices.includes(index) && game.crew.length === 0 }">
                                <input type="checkbox" id="assisant-director" name="assistant-director" value="Assistant Director" v-model="game.crew" />
                                <label for="assistant-director">Assistant Director</label>
                                <input type="checkbox" id="assistant-producer" name="assistant-producer" value="Assistant Producer" v-model="game.crew"/>
                                <label for="assistant-producer">Assistant Producer</label>
                                <input type="checkbox" id="audio-a1" name="audio-a1" value="Audio A1" v-model="game.crew"/>
                                <label for="audio-a1">Audio, A1</label> <br />
                                <input type="checkbox" id="audio-assistant" name="audio-assistant" value="Audio Assistant A2" v-model="game.crew"/>
                                <label for="audio-assistant">Audio Assistant, A2</label>
                                <input type="checkbox" id="bug-operator" name="bug-operator" value="Bug Operator" v-model="game.crew"/>
                                <label for="bug-operator">Bug Operator</label>
                                <input type="checkbox" id="camera-fixed" name="camera-fixed" value="Camera Fixed" v-model="game.crew"/>
                                <label for="camera-fixed">Camera Fixed</label> <br />
                                <input type="checkbox" id="camera-handheld" name="camera-handheld" value="Camera Handheld" v-model="game.crew"/>
                                <label for="camera-handheld">Camera Handheld</label>
                                <input type="checkbox" id="camera-steadicam" name="camera-steadicam" value="Camera Steadicam" v-model="game.crew"/>
                                <label for="camera-steadicam">Camera Steadicam</label>
                                <input type="checkbox" id="director" name="director" value="Director" v-model="game.crew"/>
                                <label for="director">Director</label> <br />
                                <input type="checkbox" id="eic" name="eic" value="Engineer in Charge (EIC)" v-model="game.crew"/>
                                <label for="eic">Engineer in Charge (EIC)</label>
                                <input type="checkbox" id="eng2" name="eng2" value="ENG 2" v-model="game.crew"/>
                                <label for="eng2">ENG 2</label>
                                <input type="checkbox" id="evs-replay-lead" name="evs-replay-lead" value="EVS Replay Lead" v-model="game.crew"/>
                                <label for="evs-replay-lead">EVS Replay Lead</label> <br />
                                <input type="checkbox" id="evs-replay-ro" name="evs-replay-ro" value="EVS Replay R/O" v-model="game.crew"/>
                                <label for="evs-replay-ro">EVS Replay R/O</label>
                                <input type="checkbox" id="graphics-operator" name="graphics-operator" value="Graphic Operator" v-model="game.crew"/>
                                <label for="graphics-operator">Graphics Operator</label>
                                <input type="checkbox" id="producer" name="producer" value="Producer" v-model="game.crew"/>
                                <label for="producer">Producer</label> <br />
                                <input type="checkbox" id="technical-director" name="technical-director" value="Technical Director" v-model="game.crew"/>
                                <label for="technical-director">Technical Director</label>
                                <input type="checkbox" id="timeout-coordinator" name="timeout-coordinator" value="Timeout Coordinator" v-model="game.crew"/>
                                <label for="timeout-coordinator">Timeout Coordinator</label>
                                <input type="checkbox" id="video-operator" name="video-operator" value="Video Operator" v-model="game.crew"/>
                                <label for="video-operator">Video Operator</label> <br />
                                <input type="checkbox" id="sport-broadcaster" name="sport-broadcaster" value="Sport Broadcaster" v-model="game.crew"/>
                                <label for="sport-broadcaster">Sport Broadcaster</label>
                                <input type="checkbox" id="paid-freelancer" name="paid-freelancer" value="Paid Freelancer" v-model="game.crew"/>
                                <label for="paid-freelancer">Paid Freelancer</label>
                                <input type="checkbox" id="paid-student" name="paid-student" value="Paid Student" v-model="game.crew"/>
                                <label for="paid-student">Paid Student</label>
                            </div>
                        </td>
                        </template>
                        <template v-else>
                            <td>{{ game.sport }}</td>
                            <td>{{ game.datetime }}</td>
                            <td>{{ game.venue }}</td>
                            <td>{{ game.opponent }}</td>
                            <td>{{ game.crew }}</td>
                        </template>
                </tr>
            </tbody>
        </table>
    </div>
    <button @click="addGame">Add New Game</button>
    <button @click="saveGames">Save New Game Schedule</button>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'

const games = ref([])
const submitted = ref(false)

const invalidIndices = computed(() => {
  if (!submitted.value) return []

  const gameList = games.value // <- fix: dereference the ref

  const hasAtLeastOneValid = gameList.some(
    g => g.sport.trim() && g.datetime && g.venue.trim() && g.opponent.trim() && g.crew.length
  )

  if (hasAtLeastOneValid) return []

  return gameList
    .map((g, i) => (!g.sport.trim() || !g.datetime || !g.venue.trim() || !g.opponent.trim() || g.crew.length === 0) ? i : null)
    .filter(i => i !== null)
})

onMounted(() => {
  fetch('/api/schedule')
    .then(res => res.json())
    .then(data => {
      games.value = data.map(g => ({ ...g, isNew: false })) // mark existing games
    })
    .catch(err => {
      console.error('Failed to load schedule:', err)
    })
})

function addGame() {
  games.value.push({
    sport: '',
    datetime: '',
    venue: '',
    opponent: '',
    crew: [],
    isNew: true
  })
}

function saveGames() {
  submitted.value = true; // this ensures invalids are highlighted

  const newGames = games.value
    .map((game, index) => ({ game, index }))
    .filter(({ game }) =>
      game.isNew &&
      game.sport.trim() &&
      game.datetime &&
      game.venue.trim() &&
      game.crew.length
    )

  if (!newGames.length) {
    alert('Please enter valid game entries before saving.')
    return
  }

  const postData = newGames.map(({ game }) => ({
    sport: game.sport.trim(),
    datetime: game.datetime,
    venue: game.venue.trim(),
    opponent: game.opponent.trim(),
    crew: game.crew
  }))

  fetch('/api/schedule/add', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(postData)
  })
    .then(res => {
      if (!res.ok) throw new Error('Failed to save new games')
      return res.json()
    })
    .then(() => {
      alert('New games added successfully!')

      // mark them as non-editable
      newGames.forEach(({ index }) => {
        games.value[index].isNew = false
      })

      submitted.value = false // reset validation
    })
    .catch(err => {
      console.error(err)
      alert('Error adding games.')
    })
}
</script>

<style scoped>
.invalid {
      border: 2px dashed red;
  }
</style>