import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import '@fortawesome/fontawesome-free/css/all.min.css';
//import App from './components/ViewGameSchedule.vue'
//import App from './components/AdminAddsGames.vue'
//import App from './components/Login.vue'
//import App from './components/Dashboard.vue'
//import App from './components/RegisterCrewMember.vue'
//import App from './components/CrewAssignment.vue'
//import App from './components/CrewDashboard.vue'
//import App from './components/AvailabilityForm.vue'

createApp(App).use(router).mount('#app')
