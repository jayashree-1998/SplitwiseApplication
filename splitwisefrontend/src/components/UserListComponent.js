import React, { useContext, useEffect, useState } from "react";
import {
  addMemberToGroup,
  deleteGroup,
  getGroupDetail,
  getUserListByGroupID,
} from "../services/groupService";
import { toast } from "react-toastify";
import AddMemberToGroupModal from "./AddMemberToGroupModal";
import { UserContext } from "../contexts/UserContext";
import { GroupDetailContext } from "../contexts/GroupDetailContext";
import { COLOR } from "../utils/constants";
import { Divider } from "@mui/material";

function UserListComponent() {
  const [showAddUserModal, setShowAddUserModal] = useState(false);
  const [selectedGroup, setSelectedGroup, groupObject, setGroupObject] =
    useContext(GroupDetailContext);
  const [userObject, setUserObject] = useContext(UserContext);

  const [userList, setUserList] = useState([]);

  let oldGroupList = userObject.groupList;

  const [email, setEmail] = useState("");
  const [refresh, setRefresh] = useState(false);

  const sortComparator = (prop) => (a, b) => {
    if (a[prop] > b[prop]) {
      return 1;
    } else if (a[prop] < b[prop]) {
      return -1;
    }
    return 0;
  };

  useEffect(() => {
    if (groupObject) {
      (async function getMemberList() {
        const responseData = await getUserListByGroupID(
          groupObject.group.groupID
        );
        const memberListData = responseData.data;
        if (memberListData) {
          setUserList(memberListData.object.sort(sortComparator("name")));
        } else {
          toast.error(memberListData.object);
        }
      })();
    }
  }, [groupObject, refresh]);

  function openModal() {
    setShowAddUserModal(true);
  }

  function closeModal() {
    setShowAddUserModal(false);
  }

  function handleChangeInAddMember(event) {
    const { name, value } = event.target;
    setEmail(value);
  }

  async function deleteGroupByOwner() {
    const value = window.confirm(
      "Deleting group will also delete all the expenses"
    );
    if (value) {
      console.log(groupObject.group.groupID);
      const responseData = await deleteGroup(
        groupObject.group.groupID,
        groupObject.group.ownerID
      );
      const data = responseData.data;
      if (data) {
        if (data.success === true) {
          setSelectedGroup(null);
          oldGroupList = oldGroupList.filter((e, i) => {
            return groupObject.group.groupID !== e.groupID;
          });
          setGroupObject(null);
          setUserObject((pv) => {
            return {
              ...pv,
              groupList: oldGroupList,
            };
          });
          setRefresh((pv) => {
            return !pv;
          });
          toast.success(data.object);
        }
      }
    }
  }

  async function addMember() {
    if (email !== "") {
      const responseData = await addMemberToGroup(
        email,
        groupObject.group.groupID
      );
      const data = responseData.data;
      console.log(data);
      if (data) {
        if (data.success === true) {
          //get User List and set it
          setUserList((pv) => {
            return [...pv, data.object];
          });
          // also update the group object
          // this is so that on add expense new added users can appear in paid and owe modal
          setGroupObject((pv) => {
            return {
              ...pv,
              userList: data.object,
            };
          });
          closeModal();
          setRefresh((pv) => {
            return !pv;
          });
        } else {
          toast.error(data.object);
          oldGroupList = oldGroupList.filter((e, i) => {
            return groupObject.group.groupID !== e.groupID;
          });
          setUserObject((pv) => {
            return {
              ...pv,
              groupList: oldGroupList,
            };
          });
          setSelectedGroup(null);
          setGroupObject(null);
          closeModal();
        }
      } else {
        console.log("No data returned");
      }
    } else {
      alert("EmailID is empty");
    }
  }
  return (
    <div style={{ display: "flex", flexDirection: "row" }}>
      <div
        style={{
          flex: 3,
          height: "100vh",
          width: "300px",
          background: COLOR.secondaryColor,
          display: "flex",
          flexDirection: "column",
        }}
      >
        <button
          style={{ color: COLOR.primaryColor }}
          className="button3"
          onClick={() => openModal()}
        >
          <div
            style={{
              backgroundColor: COLOR.white,
              borderRadius: "8px",
              padding: "12px 8px",
              borderColor: COLOR.secondaryColor,
            }}
          >
            Add Member
          </div>
        </button>
        {showAddUserModal && (
          <AddMemberToGroupModal
            closeModal={closeModal}
            modalHeading={"Add Member"}
            handleChange={handleChangeInAddMember}
            emailID={email}
            addMember={addMember}
          />
        )}
        <Divider
          variant="middle"
          style={{
            backgroundColor: COLOR.dividerColor,
          }}
        />
        <div style={{ flex: 1, flexDirection: "column", overflow: "auto" }}>
          {userList.length !== 0 &&
            userList.map((u, i) => {
              return (
                <div
                  key={i}
                  style={{
                    display: "flex",
                  }}
                >
                  <label
                    className="sidebar"
                    style={{
                      flex: 1,
                      fontSize: "20px",
                      padding: "8px 8px",
                      color: COLOR.tertiaryColor,
                    }}
                  >
                    {u.name}
                  </label>
                </div>
              );
            })}
        </div>
        <Divider
          variant="middle"
          style={{
            backgroundColor: COLOR.dividerColor,
          }}
        />
        <div
          style={{
            display: "flex",
          }}
        >
          {groupObject && groupObject.group.ownerID === userObject.user_id && (
            <button
              style={{ color: COLOR.primaryColor, flex: 1 }}
              className="button3"
              onClick={() => deleteGroupByOwner()}
            >
              <div
                style={{
                  backgroundColor: COLOR.white,
                  borderRadius: "8px",
                  padding: "12px 8px",
                  borderColor: COLOR.secondaryColor,
                }}
              >
                Delete Group
              </div>
            </button>
          )}
        </div>
      </div>
    </div>
  );
}
export default UserListComponent;
