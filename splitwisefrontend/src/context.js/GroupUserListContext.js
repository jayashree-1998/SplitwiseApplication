import React, { useState } from "react";
export const GroupUserListContext = React.createContext();

export function GroupUserListProvider({ children }) {
  const [groupUserList, setGroupUserList] = useState([]);
  return (
    <GroupUserListContext.Provider value={[groupUserList, setGroupUserList]}>
      {children}
    </GroupUserListContext.Provider>
  );
}
export default GroupUserListProvider;
