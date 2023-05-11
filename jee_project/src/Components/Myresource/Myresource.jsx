import "../FollowingResources/FollowingResources.css";
import { useEffect, useState } from "react";
import axios from "axios";
import { getMailFromCookie, initializeAxios } from "../../api/springApi";
import MyResourceCard from "./MyResourceCard";

const MyResources = ({ userResources }) => {
  const wrapperStyle = {
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    justifyContent: "center",
    width: "100%",
    gap: "1rem",
    padding: "2rem",
  };
  return (
    <div style={wrapperStyle}>
      {userResources.map((resource) => (
        <MyResourceCard resource={resource} />
      ))}
    </div>
  );
};

export default MyResources;
