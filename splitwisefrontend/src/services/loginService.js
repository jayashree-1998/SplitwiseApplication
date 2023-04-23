import axios from "axios";

const loginAPI = `http://localhost:9081/api/user/login`;

const registerAPI = `http://localhost:9081/api/user/register-user`;

async function login(loginData) {
  const responseData = await axios.post(loginAPI, loginData);
  return responseData;
}

async function register(registerData) {
  const responseData = await axios.post(registerAPI, registerData);
  return responseData;
}

export { login, register };
