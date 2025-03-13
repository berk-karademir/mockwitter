import axios from "axios";

const baseURL = "http://localhost:3000";

export const api = axios.create({
  baseURL,
});

export const apiWithCredentials = axios.create({
  baseURL: 'http://localhost:3000', // Backend API'nin base URL'si
  withCredentials: true, // Kimlik bilgileriyle istek gönderiyorsanız
});

