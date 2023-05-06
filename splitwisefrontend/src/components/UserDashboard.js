import React, { useEffect, useState } from "react";
import UserDashboardSidebar from "./UserDashboardSidebar";
import Expense from "./Expense";

function UserDashboard() {
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
        <Expense />
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
        <UserDashboardSidebar />
      </div>
    </div>
  );
}
export default UserDashboard;
