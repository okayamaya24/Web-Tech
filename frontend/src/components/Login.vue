<template>
    <div class="login-container">
        <h2>Login</h2>
        <form @submit.prevent="handleLogin" class="login-form">
            <div class="form-group">
               <label for="email">Email</label>
                <input type="email" id="email" name="email" v-model="email" required placeholder="Enter your email">
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" v-model="password" required placeholder="Enter your password">
            </div>
            <button type="submit" class="login-button">Login</button>
        </form>
    </div>
</template>

<script setup>
import { ref } from 'vue'
import { login } from '@/apis/auth'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()
    
const email = ref('')
const password = ref('')

async function handleLogin() {
    try {
       await login(email.value, password.value);
        //redirect to the page the user originally wanted to visit, or fallback to home
        const redirectPath = route.query.redirect || { name: 'home' }
        router.replace(redirectPath)
    } catch (error) {
        console.error(error)
    }
}
</script>

<style scoped>
    .login-container {
    padding: 1.5rem;
    background-color: #f5f5f5;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    max-width: 400px;
    margin: 2rem auto;

    h2 {
        margin-bottom: 1rem;
        font-size: 1.8rem;
        font-weight: bold;
        color: #333;
        text-align: center;
    }


    .login-form {
        display: flex;
        flex-direction: column;
        gap: 1rem;

        .form-group {
            display: flex;
            flex-direction: column;

            label {
                margin-bottom: 0.5rem;
                font-size: 1rem;
                color: #555;
            }

            input {
                padding: 0.5rem;
                border: 1px solid #ddd;
                border-radius: 6px;
                background-color: #fff;
                transition: all 0.3s ease;

                &:focus {
                    border-color: #4D1979;
                    box-shadow: 0 0 4px rgba(123, 0, 255, 0.5);
                    outline: none;
                }
            }
        }

        .login-button {
            padding: 0.75rem;
            border: none;
            border-radius: 6px;
            background-color: #4D1979;
            color: #fff;
            font-size: 1rem;
            font-weight: bold;
            cursor: pointer;
            transition: all 0.3s ease;

            &:hover {
                background-color: #4D1979;
                box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
                transform: translateY(-3px);
            }

            &:active {
                transform: translateY(3px);
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }
        }
    }
}
</style>
