import React, { useState } from "react";
export const GroupObjectContext = React.createContext({});

export function GroupObjectProvider({ children }) {
  const [groupObject, setGroupObject] = useState([]);
  return (
    <GroupObjectContext.Provider value={[groupObject, setGroupObject]}>
      {children}
    </GroupObjectContext.Provider>
  );
}
export default GroupObjectProvider;
