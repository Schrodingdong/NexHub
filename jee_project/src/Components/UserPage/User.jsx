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
import axios from "axios";
import {
  getUserFollowers,
  getUserFollowing,
  getUserIdFromCookie,
  initializeAxios,
} from "../../api/springApi";
import { useState } from "react";

const User = () => {
  const [userId, setUserId] = useState(-1);
  const [myUserData, setMyUserData] = useState({});
  const [followers, setFollowers] = useState([]);
  const [following, setFollowing] = useState([]);
  const [resourceList, setResourceList] = useState([]);
  const [allUsers, setAllUsers] = useState([]);
  const [userIdListForResource, setUserIdListForResource] = useState([]);
  const navigate = useNavigate();
  useEffect(() => {
    verifyJwtToken(document.cookie).then((res) => {
      if (!res) {
        navigate("/login");
      } else {
        setUserId(getUserIdFromCookie());
        const { metadataService } = initializeAxios();
        metadataService.get("/users/get/all").then((res) => {
          if (res.data.length > 0) setAllUsers(res.data);
        });
      }
    });
  }, []);

  useEffect(() => {
    const { metadataService } = initializeAxios();
    metadataService.get(`/users/get/${userId}/followers`).then((res) => {
      if (res.data.length > 0) setFollowers(res.data);
    });
    metadataService.get(`/users/get/${userId}/following`).then((res) => {
      if (res.data.length > 0) setFollowing(res.data);
    });
    metadataService.get(`/users/get/id/${userId}`).then((res) => {
      if (res.data != null) setMyUserData(res.data);
    });
  }, [userId]);

  useEffect(() => {
    const { metadataService } = initializeAxios();
    // get resource Ids
    const userIdsToSeeResource = [userId, ...following.map((el) => el.userId)];
    console.log("ids for resource : " + userIdsToSeeResource);

    let resourceListToAppend = [];
    userIdsToSeeResource.forEach((id) => {
      metadataService
        .get(`/res/get/public-from-user/${id}`)
        .then((res) => {
          if (res.data.length > 0) {
            console.log("resources for " + id + " : " + res.data);
            resourceListToAppend.push(...res.data);
          }
        })
        .finally(() => {
          setResourceList(resourceListToAppend);
        });
    });
  }, [following, userId]);

  return (
    <div className="user-page">
      <AppBar allUsers={allUsers} userId={userId} />
      <div className="user-page-down">
        <div className="left">
          <LeftSide />
        </div>
        <div className="resource">
          <div>
            <NewRessources />
          </div>
          <div>
            <FollowingResources
              followingResourceList={resourceList}
              followingList={following}
              myUserData={myUserData}
            />
          </div>
        </div>
        <div className="right">
          <FollowingCard className="right" followingList={following} />
          <FollowerCard className="right" followerList={followers} />
        </div>
      </div>
    </div>
  );
};

export default User;
