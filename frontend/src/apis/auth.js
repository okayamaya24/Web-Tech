import axios from 'axios'
import { ref } from 'vue'

const isAuthenticated = ref(false)

const login = async (email, password) => {
    try {
        //  Send a real POST request to Spring Boot
        const response = await axios.post('http://localhost:8080/api/auth/login', {
            email: email,
            password: password
        });

        console.log('Login response:', response.data);

        //  If successful:
        isAuthenticated.value = true;
        localStorage.setItem('userEmail', email);
    } catch (error) {
        console.error('Login failed:', error);
        throw error; // So the Login.vue page knows login failed
    }
}

const logout = async () => {
    isAuthenticated.value = false;
    localStorage.removeItem('userEmail'); // Clear saved email
}

export { isAuthenticated, login, logout }
