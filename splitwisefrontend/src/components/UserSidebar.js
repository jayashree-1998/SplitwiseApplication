import React from "react";
const UserSidebar = ({ userGroupList, userObj }) => {
  return (
    <div
      style={{
        flex: 2,
        height: "100vh",
        width: "300px",
        backgroundColor: "#518495",
        display: "flex",
        flexDirection: "column",
      }}
    >
      <label className="sidebar" style={{ fontSize: "20px" }}>
        {userObj.name}
      </label>
      <button className="button3">Add Group</button>
      {userGroupList.length !== 0 && (
        <label className="sidebar">Group List</label>
      )}
      {userGroupList.length !== 0 &&
        userGroupList.map((g, i) => {
          return <button className="button3">{g.name}</button>;
        })}
    </div>
  );
};
export default UserSidebar;
