import React from "react";
import Dashboard from "../components/Dashboard";
import { useLocation } from "react-router-dom";
import GroupObjectProvider from "../context.js/GroupObjectContext";
import UserObjectProvider from "../context.js/UserObjectContext";
import GroupUserListContext, {
  GroupUserListProvider,
} from "../context.js/GroupUserListContext";

function MainPage() {
  const locationState = useLocation().state;

  return (
    <div>
      <GroupObjectProvider>
        <GroupUserListProvider>
          <UserObjectProvider>
            <Dashboard userObj={locationState.userObj} />
          </UserObjectProvider>
        </GroupUserListProvider>
      </GroupObjectProvider>
    </div>
  );
}

export default MainPage;
