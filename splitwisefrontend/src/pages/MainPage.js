import React from "react";
import { useLocation } from "react-router-dom";
import MainDashboard from "../components/MainDashboard";

function MainPage() {
  const locationState = useLocation().state;

  return (
    <div>
      <MainDashboard />
    </div>
  );
}

export default MainPage;
