import axios from 'axios'
import { ref } from 'vue'

const isAuthenticated = ref(false)

const login = async (email, password) => {
  try {
    const response = await axios.post('https://frogcrew-backend-2025.azurewebsites.net/api/auth/login', { 
      email: email,
      password: password
    });

    console.log('Login response:', response.data);

    // Save user info from backend
    localStorage.setItem('userEmail', response.data.email);
    localStorage.setItem('userRole', response.data.role);

    isAuthenticated.value = true;
  } catch (error) {
    console.error('Login failed:', error);
    throw error; // So Login.vue can handle it
  }
}

const logout = async () => {
  isAuthenticated.value = false;
  localStorage.removeItem('userEmail');
  localStorage.removeItem('userRole');
}

export { isAuthenticated, login, logout }
