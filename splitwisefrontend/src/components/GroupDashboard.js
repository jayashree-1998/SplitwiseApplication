import React, { useContext, useEffect } from "react";
import UserListComponent from "./UserListComponent";

import ExpenseComponent from "./ExpenseComponent";
import { GroupDetailContext } from "../contexts/GroupDetailContext";
import { getGroupDetail } from "../services/groupService";

function GroupDashboard() {
  const [selectedGroup, setSelectedGroup, groupObject, setGroupObject] =
    useContext(GroupDetailContext);

  useEffect(() => {
    (async () => {
      const responseData = await getGroupDetail(selectedGroup);
      if (responseData.data.success === true) {
        // set selected group id in context
        setGroupObject(responseData.data.object);
      }
    })();
  }, [selectedGroup, setGroupObject]);

  return (
    <div
      style={{
        display: "flex",
        flexDirection: "row",
      }}
    >
      <div
        style={{
          flex: 5,
          backgroundColor: "white",
          display: "flex",
          flexDirection: "row",
          height: "100vh",
          overflowY: "scroll",
        }}
      >
        {groupObject && <ExpenseComponent />}
      </div>

      <div
        style={{
          flex: 2,
          backgroundColor: "#306eb1",
          display: "flex",
          flexDirection: "column",
        }}
      >
        {groupObject && <UserListComponent />}
      </div>
    </div>
  );
}
export default GroupDashboard;
