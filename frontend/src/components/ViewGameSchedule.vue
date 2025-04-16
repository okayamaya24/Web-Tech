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
                        <input type="text" v-model="game.sport" required placeholder="Sport of Game" :class="{ invalid: submitted && !game.sport.trim() }"/>
                    </td>
                    <td>
                        <input type="datetime-local" v-model="game.datetime" required :class="{ invalid: submitted && !game.datetime }"/>
                    </td>
                    <td>
                        <input type="text" v-model="game.venue" required placeholder="Venue" :class="{ invalid: submitted && !game.venue.trim() }"/>
                    </td>
                    <td>
                        <input type="text" v-model="game.opponent" placeholder="If Not Applicable Enter N/A" />
                    </td>
                    <td>
                        <input type="text" v-model="game.crew" required placeholder="List Required Crew Positions" :class="{ invalid: submitted && !game.crew.trim() }" />
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

import { reactive, ref } from 'vue'

const games = reactive([
  {
    sport: '',
    datetime: '',
    venue: '',
    opponent: '',
    crew: ''
  }
])

const submitted = ref(false)

function addGame() {
  games.push({
    sport: '',
    datetime: '',
    venue: '',
    opponent: '',
    crew: ''
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

  //should replace this with logic to connect with the backend
  console.log('Saving schedule:', validGames)
  alert('Schedule saved!')
} 

</script>

<style scoped>

    .invalid {
        border: 2px dashed red;
    }

    .schedule {
        max-width: 100%;
    }
</style>
