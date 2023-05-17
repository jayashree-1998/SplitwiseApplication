import React, { useContext } from "react";
import Drawer from "./Drawer";
import GroupDashboard from "./GroupDashboard";
import { GroupDetailContext } from "../contexts/GroupDetailContext";
import { COLOR } from "../utils/constants";

function MainDashboard() {
  const [selectedGroup, setSelectedGroup, groupObject, setGroupObject] =
    useContext(GroupDetailContext);

  function onGroupSelect(groupID) {
    console.log("on:", groupID);
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
          <div
            style={{
              flex: 1,
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
              fontSize: "20px",
              color: COLOR.gray,
            }}
          >
            Please Select/Create a Group
          </div>
        )}
      </div>
    </div>
  );
}

export default MainDashboard;
