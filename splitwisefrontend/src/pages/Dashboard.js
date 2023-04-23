import React from "react";

function Dashboard() {
  return (
    <div style={{ display: "flex", flexDirection: "row" }}>
      <div
        style={{
          flex: 2,
          height: "100vh",
          width: "300px",
          backgroundColor: "#518495",
          display: "flex",
          flexDirection: "column",
        }}
      ></div>
      <div style={{ flex: 6 }}></div>
      <div
        style={{
          flex: 2,
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

export default Dashboard;
