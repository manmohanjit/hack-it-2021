import axios from 'axios';

const api = axios.create({
  baseURL: process.env.API_URL || "http://localhost:8080/api/",
  timeout: 1000,
});

api.interceptors.response.use(function (response) {
  return response.data || {};
}, function (error) {
  if(error?.response) {
    return Promise.reject({
      code: error.response.status,
      status: error.response?.data?.status,
      message: error.response?.data?.message,
      errors: error.response?.data?.errors,
    });
  }

  return Promise.reject(error);
});

export default api;
