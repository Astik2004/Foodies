import axios from "axios";

const API_URL = "https://foodies-api-cl26.onrender.com/api";

export const registerUser = async (data) => {
    return await axios.post(`${API_URL}/register`, data);
};

export const login = async (data) => {
    return await axios.post(`${API_URL}/login`, data);
};
