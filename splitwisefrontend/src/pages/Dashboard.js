import React, { useEffect, useState } from "react";
import UserDashboard from "../components/UserDashboard";
import { useLocation } from "react-router-dom";
import UserSidebar from "../components/UserSidebar";
import { getGroupListForUser } from "../services/userService";

function Dashboard() {
  const locationState = useLocation().state;
  const [groupSelected, setGroupSelected] = useState(false);
  const [groupList, setGroupList] = useState([]);

  useEffect(() => {
    setGroupList(locationState.userObj.groupList);
  }, []);

  return (
    <div style={{ display: "flex", flexDirection: "row" }}>
      <div
        style={{
          flex: 2,
          height: "100vh",
          width: "300px",
          backgroundColor: "white",
          display: "flex",
          flexDirection: "column",
        }}
      >
        {/* Render user sidebar component */}
        <UserSidebar
          userGroupList={groupList}
          userObj={locationState.userObj}
        />
      </div>

      <div
        style={{
          flex: 8,
          height: "100vh",
          width: "300px",
          backgroundColor: "white",
          display: "flex",
          flexDirection: "column",
        }}
      >
        {/* based on the condition, i.e group selected or not, render the component. */}
        {groupSelected && <UserDashboard />}
      </div>
    </div>
  );
}

export default Dashboard;
