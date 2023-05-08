import React, { useContext } from "react";
import Drawer from "./Drawer";
import GroupDashboard from "./GroupDashboard";
import { GroupDetailContext } from "../contexts/GroupDetailContext";

function MainDashboard() {
  const [selectedGroup, setSelectedGroup, groupObject, setGroupObject] =
    useContext(GroupDetailContext);

  function onGroupSelect(groupID) {
    setSelectedGroup(groupID);
  }

  return (
    <div
      style={{
        display: "flex",
        height: "100vh",
        flexDirection: "row",
        // alignItems: "stretch",
      }}
    >
      <div
        style={{
          flex: 1,
          backgroundColor: "white",
          display: "flex",
          flexDirection: "column",
        }}
      >
        <Drawer onGroupSelect={onGroupSelect} />
      </div>

      <div
        style={{
          flex: 5,
          backgroundColor: "white",
          display: "flex",
          flexDirection: "column",
        }}
      >
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
