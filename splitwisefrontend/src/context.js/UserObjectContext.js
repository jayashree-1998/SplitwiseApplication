import React, { useState } from "react";
export const UserObjectContext = React.createContext({});

export function UserObjectProvider({ children }) {
  const [userObject, setUserObject] = useState({});
  return (
    <UserObjectContext.Provider value={[userObject, setUserObject]}>
      {children}
    </UserObjectContext.Provider>
  );
}
export default UserObjectProvider;
