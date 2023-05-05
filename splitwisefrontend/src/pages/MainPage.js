import React from "react";
import Dashboard from "../components/Dashboard";
import { useLocation } from "react-router-dom";
import GroupObjectProvider from "../context.js/GroupObjectContext";
import UserObjectProvider from "../context.js/UserObjectContext";

function MainPage() {
  const locationState = useLocation().state;

  return (
    <div>
      <GroupObjectProvider>
        <UserObjectProvider>
          <Dashboard userObj={locationState.userObj} />
        </UserObjectProvider>
      </GroupObjectProvider>
    </div>
  );
}

export default MainPage;
