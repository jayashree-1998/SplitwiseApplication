import React, { useContext, useEffect } from "react";
import Drawer from "./Drawer";
import GroupDashboard from "./GroupDashboard";
import { GroupDetailContext } from "../contexts/GroupDetailContext";
import { getGroupDetail } from "../services/groupService";

function MainDashboard() {
  const [selectedGroup, setSelectedGroup] = useContext(GroupDetailContext);

  function onGroupSelect(groupID) {
    setSelectedGroup(groupID);
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
        <Drawer onGroupSelect={onGroupSelect} />
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
        {selectedGroup ? (
          <GroupDashboard />
        ) : (
          <div>Please select/create a group</div>
        )}
      </div>
    </div>
  );
}

export default MainDashboard;
