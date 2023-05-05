import React, { useContext, useEffect, useState } from "react";
import {
  addMemberToGroup,
  getMemberListByGroupID,
} from "../services/groupService";
import { toast } from "react-toastify";
import AddMemberToGroupModal from "./AddMemberToGroupModal";
import { GroupObjectContext } from "../context.js/GroupObjectContext";
import { UserObjectContext } from "../context.js/UserObjectContext";

function UserDashboardSidebar() {
  const [addMemberClicked, setAddMemberClicked] = useState(false);
  const [memberList, setMemberList] = useState([]);
  const [groupObject] = useContext(GroupObjectContext);
  const [userObject, setUserObject] = useContext(UserObjectContext);
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
        setMemberList(memberListData.object.sort(sortComparator("name")));
      } else {
        toast.error(memberListData.object);
      }
    })();
  }, [groupObject.groupID, refresh]);

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

  async function deleteGroup() {}

  async function addMember() {
    if (email !== "") {
      const responseData = await addMemberToGroup(email, groupObject.groupID);
      const data = responseData.data;
      console.log(data);
      if (data) {
        if (data.success === true) {
          //get User List and set it
          setMemberList((pv) => {
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
          {memberList.length !== 0 &&
            memberList.map((u, i) => {
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
              onClick={deleteGroup}
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
