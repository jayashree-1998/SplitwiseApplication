import React from "react";
import { useLocation } from "react-router-dom";
import MainDashboard from "../components/MainDashboard";
import UserProvider from "../contexts/UserContext";
import GroupDetailProvider from "../contexts/GroupDetailContext";

function MainPage() {
  const locationState = useLocation().state;

  return (
    <div>
      <UserProvider data={locationState.loginResponse}>
        <GroupDetailProvider>
          <MainDashboard />
        </GroupDetailProvider>
      </UserProvider>
    </div>
  );
}

export default MainPage;
