import React from "react";
import LeftSide from "../LeftSide/LeftSide";
import NewRessources from "../NewResources/NewResources";
import AppBar from "../AppBar/AppBar";
import FollowingResources from "../FollowingResources/FollowingResources";
import "./User.css";
import FollowingCard from "../FollowingCard/FollowingCard";
import FollowerCard from "../FollowersCard/FollowerCard";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";
import verifyJwtToken from "../../auth/verifyJwtToken";

const User = () => {
  const navigate = useNavigate();
  useEffect(() => {
    console.log("cookie : " + document.cookie);
    verifyJwtToken(document.cookie).then((res) => {
      if (!res) {
        console.log("user");
        navigate("/login");
      }
    });
  }, []);
  return (
    <div className="user-page">
      <div className="user-header">
        <AppBar />
      </div>
      <div className="user-page-down">
        <div className="left">
          <LeftSide />
        </div>
        <div className="resource">
          <div>
            <NewRessources />
          </div>
          <div>
            <FollowingResources />
          </div>{" "}
        </div>
        <div className="right">
          <FollowingCard className="right" />
          <FollowerCard className="right" />
        </div>
      </div>
    </div>
  );
};

export default User;
