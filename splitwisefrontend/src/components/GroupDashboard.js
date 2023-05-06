import React, { useContext, useEffect } from "react";
import UserListComponent from "./UserListComponent";
import { getGroupDetail } from "../services/groupService";
import { GroupDetailContext } from "../contexts/GroupDetailContext";

function GroupDashboard() {
  return (
    <div style={{ display: "flex", flexDirection: "row" }}>
      <div
        style={{
          flex: 7,
          height: "100vh",
          width: "300px",
          backgroundColor: "white",
          display: "flex",
          flexDirection: "row",
        }}
      >
        {/* <ExpenseComponent /> */}
      </div>

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
        <UserListComponent />
      </div>
    </div>
  );
}
export default GroupDashboard;
