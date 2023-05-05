import axios from "axios";

const getGroupListForUserAPI = (userID) =>
  `http://localhost:9081/user/get-all-groups-by-user-id/${userID}`;

async function getGroupListForUserByID(userID) {
  const responseData = await axios.get(getGroupListForUserAPI(userID));
  return responseData;
}

export { getGroupListForUserByID };
