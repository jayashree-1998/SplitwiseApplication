import axios from "axios";
import { IPAddress } from "../utils/constants";

const createGroupAPI = `http://${IPAddress}/group/create-group`;

const getMemberListByGroupIDAPI = (groupID) =>
  `http://${IPAddress}/group/get-all-users-by-group-id/${groupID}`;

const getGroupDetailAPI = (groupID) =>
  `http://${IPAddress}/group/get-group-detail/${groupID}`;

const addMemberToGroupAPI = `http://${IPAddress}/group/add-user-to-group`;

const deleteGroupAPI = `http://${IPAddress}/group/delete-group`;

async function createGroup(groupData) {
  const createGroupObject = {
    name: groupData.groupName,
    ownerID: groupData.ownerID,
  };
  console.log(createGroupObject);
  const responseData = await axios.post(createGroupAPI, createGroupObject);
  return responseData;
}

async function getUserListByGroupID(groupID) {
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

async function deleteGroup(group_ID, user_ID) {
  const deleteGroupBody = {
    groupID: group_ID,
    userID: user_ID,
  };
  const responseData = await axios.delete(deleteGroupAPI, {
    data: deleteGroupBody,
  });
  return responseData;
}

async function getGroupDetail(groupID) {
  const responseData = await axios.get(getGroupDetailAPI(groupID));
  return responseData;
}

export {
  createGroup,
  getUserListByGroupID,
  addMemberToGroup,
  deleteGroup,
  getGroupDetail,
};
