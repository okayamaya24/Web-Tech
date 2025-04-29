// src/apis/axios.js
import axios from 'axios';

const instance = axios.create({
  baseURL: 'https://frogcrew-backend-2025-emasdvebbbgkbpfy.eastus-01.azurewebsites.net', // YOUR backend URL
  withCredentials: true, // if you use cookies/session
});

export default instance;
