import axios from "axios";
import { IPAddress } from "../utils/constants";

const loginAPI = `http://${IPAddress}/user/login`;

const registerAPI = `http://${IPAddress}/user/register-user`;

async function login(loginData) {
  const loginObject = {
    email: loginData.email,
    password: loginData.password,
  };
  const responseData = await axios.post(loginAPI, loginObject);
  return responseData;
}

async function register(registerData) {
  const registrationObject = {
    name: registerData.name,
    email: registerData.email,
    password: registerData.password,
    mobileNumber: registerData.mobileNumber,
  };
  const responseData = await axios.post(registerAPI, registrationObject);
  return responseData;
}

export { login, register };
