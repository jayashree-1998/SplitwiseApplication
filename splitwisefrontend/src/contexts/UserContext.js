import React, { useState } from "react";
export const UserContext = React.createContext();

export function UserProvider({ children, data }) {
  const [userObject, setUserObject] = useState(data);
  return (
    <UserContext.Provider value={[userObject, setUserObject]}>
      {children}
    </UserContext.Provider>
  );
}
export default UserProvider;
