import axios from "axios";
import { IPAddress } from "../utils/constants";

const createGroupAPI = `http://${IPAddress}/group/create-group`;
const getMemberListByGroupIDAPI = (groupID) =>
  `http://${IPAddress}/group/get-all-users-by-group-id/${groupID}`;

const addMemberToGroupAPI = `http://${IPAddress}/group/add-user-to-group`;

async function createGroup(groupData) {
  const createGroupObject = {
    name: groupData.groupName,
    ownerID: groupData.ownerID,
  };
  console.log(createGroupObject);
  const responseData = await axios.post(createGroupAPI, createGroupObject);
  return responseData;
}

async function getMemberListByGroupID(groupID) {
  const responseData = await axios.get(getMemberListByGroupIDAPI(groupID));
  return responseData;
}

async function addMemberToGroup(email_id, group_id) {
  const addMemberToGroupObject = {
    emailID: email_id,
    groupID: group_id,
  };
  console.log(addMemberToGroupObject);
  const responseData = await axios.post(
    addMemberToGroupAPI,
    addMemberToGroupObject
  );
  return responseData;
}

export { createGroup, getMemberListByGroupID, addMemberToGroup };
