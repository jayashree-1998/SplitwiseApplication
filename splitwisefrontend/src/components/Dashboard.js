import React, { useContext, useEffect, useState } from "react";
import UserDashboard from "./UserDashboard";
import UserSidebar from "./UserSidebar";
import { UserObjectContext } from "../context.js/UserObjectContext";
import { GroupObjectContext } from "../context.js/GroupObjectContext";

function Dashboard({ userObj }) {
  const [groupSelected, setGroupSelected] = useState(false);
  const [__, setGroupObject] = useContext(GroupObjectContext);
  const [userObject, setUserObject] = useContext(UserObjectContext);
  useEffect(() => {
    setUserObject(userObj);
  }, []);

  useEffect(() => {
    if (groupSelected) {
      setGroupSelected(false);
    }
  }, [userObject]);

  function onGroupSelect(g) {
    setGroupObject(g);

    setGroupSelected((pv) => {
      return true;
    });
  }

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
        <UserSidebar userObj={userObj} onGroupSelect={onGroupSelect} />
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
        {groupSelected ? (
          <UserDashboard />
        ) : (
          <div>Please select/create a group</div>
        )}
      </div>
    </div>
  );
}

export default Dashboard;
