import React, { useState } from "react";
export const GroupDetailContext = React.createContext();

export function GroupDetailProvider({ children }) {
  const [groupObject, setGroupObject] = useState(null);
  const [selectedGroup, setSelectedGroup] = useState(null);
  return (
    <GroupDetailContext.Provider
      value={[selectedGroup, setSelectedGroup, groupObject, setGroupObject]}
    >
      {children}
    </GroupDetailContext.Provider>
  );
}
export default GroupDetailProvider;
