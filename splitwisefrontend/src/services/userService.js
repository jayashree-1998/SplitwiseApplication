import axios from "axios";
import { IPAddress } from "../utils/constants";

const getGroupListForUserAPI = (userID) =>
  `http://${IPAddress}/user/get-all-groups-by-user-id/${userID}`;

async function getGroupListForUserByID(userID) {
  const responseData = await axios.get(getGroupListForUserAPI(userID));
  return responseData;
}

export { getGroupListForUserByID };
