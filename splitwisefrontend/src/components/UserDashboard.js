import React from "react";

function UserDashboard() {
  return (
    <div style={{ display: "flex", flexDirection: "row" }}>
      <div
        style={{
          flex: 8,
          height: "100vh",
          width: "300px",
          backgroundColor: "white",
          display: "flex",
          flexDirection: "column",
        }}
      ></div>

      <div
        style={{
          flex: 3,
          height: "100vh",
          width: "300px",
          backgroundColor: "#518495",
          display: "flex",
          flexDirection: "column",
        }}
      ></div>
    </div>
  );
}
export default UserDashboard;
