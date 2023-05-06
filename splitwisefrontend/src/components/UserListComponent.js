import React, { useContext, useEffect, useState } from "react";
import {
  addMemberToGroup,
  deleteGroup,
  getMemberListByGroupID,
} from "../services/groupService";
import { toast } from "react-toastify";
import AddMemberToGroupModal from "./AddMemberToGroupModal";
import { GroupUserListContext } from "../context.js/GroupUserListContext";
import { GroupObjectContext } from "../context.js/GroupObjectContext";
import { UserObjectContext } from "../context.js/UserLoginResponseContext";

function UserDashboardSidebar() {
  const [addMemberClicked, setAddMemberClicked] = useState(false);
  // const [memberList, setMemberList] = useState([]);
  const [groupUserList, setGroupUserList] = useContext(GroupUserListContext);
  const [groupObject] = useContext(GroupObjectContext);
  const [userObject, setUserObject] = useContext(UserObjectContext);
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
    (async function getMemberList() {
      const responseData = await getMemberListByGroupID(groupObject.groupID);
      const memberListData = responseData.data;
      if (memberListData) {
        // setMemberList(memberListData.object.sort(sortComparator("name")));
        setGroupUserList(memberListData.object.sort(sortComparator("name")));
      } else {
        toast.error(memberListData.object);
      }
    })();
  }, [groupObject.groupID, refresh, setGroupUserList]);

  function openModal() {
    setAddMemberClicked(true);
  }

  function closeModal() {
    setAddMemberClicked(false);
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
      const responseData = await deleteGroup(
        groupObject.groupID,
        groupObject.ownerID
      );
      const data = responseData.data;
      if (data) {
        if (data.success === true) {
          //TODO: set deleted to true, use it to rerender userSidebar component
          oldGroupList = oldGroupList.filter((e, i) => {
            return groupObject.groupID !== e.groupID;
          });
          setUserObject((pv) => {
            return {
              ...pv,
              groupList: oldGroupList,
            };
          });
          toast.success(data.object);
        }
      }
    }
  }

  async function addMember() {
    if (email !== "") {
      const responseData = await addMemberToGroup(email, groupObject.groupID);
      const data = responseData.data;
      console.log(data);
      if (data) {
        if (data.success === true) {
          //get User List and set it
          setGroupUserList((pv) => {
            return [...pv, data.object];
          });
          closeModal();
          setRefresh((pv) => {
            return !pv;
          });
        } else {
          toast.error(data.object);
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
          backgroundColor: "#518495",
          display: "flex",
          flexDirection: "column",
        }}
      >
        <button className="button3" onClick={() => openModal()}>
          Add Memebers
        </button>
        {addMemberClicked && (
          <AddMemberToGroupModal
            closeModal={closeModal}
            modalHeading={"Add Members"}
            handleChange={handleChangeInAddMember}
            emailID={email}
            addMember={addMember}
          />
        )}
        <div style={{ flex: 1, flexDirection: "column", overflow: "auto" }}>
          {groupUserList.length !== 0 &&
            groupUserList.map((u, i) => {
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
                    }}
                  >
                    {u.name}
                  </label>
                </div>
              );
            })}
        </div>
        <div
          style={{
            display: "flex",
          }}
        >
          {groupObject.ownerID === userObject.user_id && (
            <button
              style={{
                flex: 1,
              }}
              className="button3"
              onClick={deleteGroupByOwner}
            >
              Delete Group
            </button>
          )}
        </div>
      </div>
    </div>
  );
}
export default UserDashboardSidebar;
