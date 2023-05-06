import React, { useContext, useEffect, useState } from "react";
import CreateGroupModal from "./CreateGroupModal";
import { createGroup } from "../services/groupService";
import { toast } from "react-toastify";
import { getGroupListForUserByID } from "../services/userService";
import { useNavigate } from "react-router-dom";
import { UserObjectContext } from "../context.js/UserObjectContext";

const UserSidebar = ({ userObj, onGroupSelect }) => {
  const navigate = useNavigate();
  const [addGroupClicked, setAddGroupClicked] = useState(false);
  const [createGroupObj, setCreateGroupObj] = useState({
    groupName: "",
    ownerID: "",
  });
  const [userObject] = useContext(UserObjectContext);
  const [groupList, setGroupList] = useState([]);

  const sortComparator = (prop) => (a, b) => {
    if (a[prop] > b[prop]) {
      return 1;
    } else if (a[prop] < b[prop]) {
      return -1;
    }
    return 0;
  };

  useEffect(() => {
    (async function getGroupList() {
      if (userObject.user_id === null) return;
      const responseData = await getGroupListForUserByID(userObj.user_id);
      const groupListData = responseData.data;
      console.log(groupListData);
      if (groupListData) {
        setGroupList(groupListData.object.sort(sortComparator("name")));
      } else {
        toast.error(groupListData.object);
      }
    })();
  }, [userObject]);

  function openModal() {
    setAddGroupClicked(true);
  }

  function closeModal() {
    setCreateGroupObj({
      groupName: "",
      ownerID: "",
    });
    setAddGroupClicked(false);
  }

  function logout() {
    const value = window.confirm("Are you sure");
    if (value) {
      navigate("/login");
    }
  }

  function handleChangeInCreateGroup(event) {
    const { name, value } = event.target;
    setCreateGroupObj((pv) => {
      return {
        ...pv,
        ownerID: userObject.user_id,
      };
    });
    setCreateGroupObj((pv) => {
      return {
        ...pv,
        [name]: value,
      };
    });
  }

  async function addGroup() {
    if (createGroupObj.groupName !== "") {
      const responseData = await createGroup(createGroupObj);
      const data = responseData.data;
      console.log(data);
      if (data) {
        if (data.success === true) {
          //get Group List and set it
          setGroupList((pv) => {
            return [...pv, data.object];
          });
          closeModal();
        } else {
          toast.error(data.object);
        }
      } else {
        console.log("No data returned");
      }
    } else {
      alert("Group name is empty");
    }
  }

  return (
    <div
      style={{
        flex: 2,
        height: "100vh",
        width: "300px",
        backgroundColor: "#518495",
        display: "flex",
        flexDirection: "column",
      }}
    >
      <label
        className="sidebar"
        style={{ fontSize: "20px", padding: "8px 8px" }}
      >
        {userObject.name}
      </label>
      <button className="button3" onClick={() => openModal()}>
        Create Group
      </button>
      {addGroupClicked && (
        <CreateGroupModal
          closeModal={closeModal}
          modalHeading={"Create Group"}
          handleChange={handleChangeInCreateGroup}
          groupName={createGroupObj.groupName}
          addGroup={addGroup}
        />
      )}
      <div style={{ flex: 1, flexDirection: "column", overflow: "auto" }}>
        {groupList.length !== 0 &&
          groupList.map((g, i) => {
            return (
              <div
                key={i}
                style={{
                  display: "flex",
                }}
              >
                <button
                  style={{
                    flex: 1,
                  }}
                  className="button3"
                  onClick={() => onGroupSelect(g)}
                >
                  {g.name}
                </button>
              </div>
            );
          })}
      </div>
      <div
        style={{
          display: "flex",
        }}
      >
        <button
          style={{
            flex: 1,
          }}
          className="button3"
          onClick={logout}
        >
          Logout
        </button>
      </div>
    </div>
  );
};
export default UserSidebar;
