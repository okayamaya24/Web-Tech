import { createApp } from 'vue'
//import App from './App.vue'
import router from './router'
//import App from './components/ViewGameSchedule.vue'
//import App from './components/AdminAddsGames.vue'
//import App from './components/Login.vue'
import App from './components/Dashboard.vue'

createApp(App).use(router).mount('#app')