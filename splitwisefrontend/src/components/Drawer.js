import React, { useContext, useEffect, useState } from "react";
import CreateGroupModal from "./CreateGroupModal";
import { createGroup, getGroupDetail } from "../services/groupService";
import { toast } from "react-toastify";
import { getGroupListForUserByID } from "../services/userService";
import { useNavigate } from "react-router-dom";
import { UserContext } from "../contexts/UserContext";
import { GroupDetailContext } from "../contexts/GroupDetailContext";
import { COLOR } from "../utils/constants";
import Divider from "@mui/material/Divider";

const Drawer = ({ onGroupSelect }) => {
  const navigate = useNavigate();
  const [showAddGroupModal, setShowAddGroupModal] = useState(false);
  const [createGroupRequestBody, setCreateGroupRequestBody] = useState({
    groupName: "",
    ownerID: "",
  });
  const [userObject] = useContext(UserContext);
  const [selectedGroup, setSelectedGroup, groupObject, setGroupObject] =
    useContext(GroupDetailContext);
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
      const responseData = await getGroupListForUserByID(userObject.user_id);
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
    setShowAddGroupModal(true);
  }

  function closeModal() {
    setCreateGroupRequestBody({
      groupName: "",
      ownerID: "",
    });
    setShowAddGroupModal(false);
  }

  function logout() {
    const value = window.confirm("Are you sure");
    if (value) {
      navigate("/login");
    }
  }

  function handleChangeInCreateGroup(event) {
    const { name, value } = event.target;
    setCreateGroupRequestBody((pv) => {
      return {
        ...pv,
        ownerID: userObject.user_id,
      };
    });
    setCreateGroupRequestBody((pv) => {
      return {
        ...pv,
        [name]: value,
      };
    });
  }

  async function addGroup() {
    if (createGroupRequestBody.groupName !== "") {
      const responseData = await createGroup(createGroupRequestBody);
      const data = responseData.data;
      if (data) {
        if (data.success === true) {
          //get Group List and set it
          setGroupList((pv) => {
            return [...pv, data.object];
          });
          let group = {
            groupID: data.object.groupID,
            name: data.object.name,
            ownerID: data.object.ownerID,
            settled: false,
          };
          let userList = [
            {
              userID: userObject.ownerID,
              name: userObject.name,
              email: userObject.email,
              password: "",
              mobileNumber: userObject.mobileNumber,
            },
          ];
          setGroupObject(() => {
            return {
              group: group,
              expenseList: [],
              userList: userList,
            };
          });
          onGroupSelect(data.object.groupID);
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
        flex: 1,
        height: "100vh",
        width: "300px",
        backgroundColor: COLOR.secondaryColor,
        display: "flex",
        flexDirection: "column",
      }}
    >
      <label
        className="sidebar"
        style={{
          fontSize: "30px",
          padding: "8px 8px",
        }}
      >
        {userObject.name.substring(0, 20)}
      </label>
      <Divider
        style={{
          backgroundColor: COLOR.dividerColor,
        }}
      />
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
          Create Group
        </div>
      </button>

      <Divider
        variant="middle"
        style={{
          backgroundColor: COLOR.dividerColor,
        }}
      />

      {showAddGroupModal && (
        <CreateGroupModal
          closeModal={closeModal}
          modalHeading={"Create Group"}
          handleChange={handleChangeInCreateGroup}
          groupName={createGroupRequestBody.groupName}
          addGroup={addGroup}
        />
      )}
      <div
        style={{
          flex: 1,
          flexDirection: "column",
          overflow: "auto",
          marginTop: "12px",
        }}
      >
        {groupList.length !== 0 &&
          groupList.map((e, i) => {
            return (
              <div
                key={i}
                style={{
                  backgroundColor:
                    selectedGroup && selectedGroup === e.groupID
                      ? COLOR.primaryColor
                      : null,
                  display: "flex",
                  borderTopRightRadius: "8px",
                  borderBottomRightRadius: "8px",
                  marginRight: "8px",
                }}
              >
                <button
                  style={{
                    flex: 1,
                    color:
                      selectedGroup && selectedGroup === e.groupID
                        ? COLOR.white
                        : COLOR.tertiaryColor,
                    backgroundColor:
                      selectedGroup && selectedGroup === e.groupID
                        ? COLOR.primaryColor
                        : null,
                  }}
                  className="button3"
                  onClick={() => {
                    onGroupSelect(e.groupID.toString());
                  }}
                >
                  {e.name}
                </button>
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

      <button
        style={{ color: COLOR.primaryColor }}
        className="button3"
        onClick={() => logout()}
      >
        <div
          style={{
            backgroundColor: COLOR.white,
            borderRadius: "8px",
            padding: "12px 8px",
            borderColor: COLOR.secondaryColor,
          }}
        >
          Logout
        </div>
      </button>
    </div>
  );
};
export default Drawer;
