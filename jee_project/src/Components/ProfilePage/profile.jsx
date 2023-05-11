import LeftSide from "../LeftSide/LeftSide";
import NewResources from "../NewResources/NewResources";
import AppBar from "../AppBar/AppBar";
import FollowingResources from "../FollowingResources/FollowingResources";
import FollowingCard from "../FollowingCard/FollowingCard";
import { FaPen, FaUserPlus, FaUserTimes, FaPlus } from "react-icons/fa";
import FollowersCard from "../FollowersCard/FollowerCard";
import { Link, useNavigate, useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";
import MyResources from "../Myresource/Myresource";
import verifyJwtToken from "../../auth/verifyJwtToken";

import "./profile.css";
import { getUserIdFromCookie, initializeAxios } from "../../api/springApi";
import generateProfilePicture from "../../api/generateProfilePicture";

const Profile = () => {
  const [users, setUsers] = useState("");
  const { userId } = useParams();
  const [followers, setFollowers] = useState([]);
  const [following, setFollowing] = useState([]);
  const [userInformation, setUserInformation] = useState({});
  const [myUserInformation, setMyUserInformation] = useState({});
  const [userResourceList, setUserResourceList] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    verifyJwtToken(document.cookie).then((response) => {
      if (!response) {
        navigate("/login");
      }
    });
  }, []);

  useEffect(() => {
    const { metadataService } = initializeAxios();
    metadataService.get(`/res/get/all-from-user/${userId}`).then((res) => {
      if (res.data.length > 0) setUserResourceList(res.data);
    });
    metadataService.get(`/users/get/id/${userId}`).then((res) => {
      console.log(res.data);
      if (res.data != null) setUserInformation(res.data);
    });
    if (userId !== getUserIdFromCookie()) {
      metadataService
        .get(`/users/get/id/${getUserIdFromCookie()}`)
        .then((res) => {
          console.log(res.data);
          if (res.data != null) setMyUserInformation(res.data);
        });
    }

    metadataService.get(`/users/get/${userId}/followers`).then((res) => {
      if (res.data.length > 0) setFollowers(res.data);
    });
    metadataService.get(`/users/get/${userId}/following`).then((res) => {
      if (res.data.length > 0) setFollowing(res.data);
    });
  }, [userId]);

  const [img, setImg] = useState("a");
  useEffect(() => {
    if (userInformation.firstName === undefined) return;
    setImg(
      generateProfilePicture(
        userInformation.firstName + userInformation.lastName
      )
    );
  }, [userInformation]);

  const unfollowHandler = () => {
    const { metadataService } = initializeAxios();
    const username = userInformation.firstName + userInformation.lastName;
    metadataService
      .delete(`/users/${getUserIdFromCookie()}/unfollows/${userId}`)
      .then((res) => {
        alert("successfully unfollowed " + username);
        setFollowers(
          followers.filter(
            (following) => following.userId !== getUserIdFromCookie()
          )
        );
        window.location.reload();
      });
  };

  const followHandler = () => {
    const { metadataService } = initializeAxios();
    const username = userInformation.firstName + userInformation.lastName;
    metadataService
      .put(`/users/${getUserIdFromCookie()}/follows/${userId}`)
      .then((res) => {
        alert("successfully followed " + username);
        setFollowers((prev) => {
          return [...prev, myUserInformation];
        });
        window.location.reload();
      });
  };

  const infoWrapper = {
    display: "flex",
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",
    gap: "1rem",
    marginTop: "1rem",
  };
  return (
    <div className="user-page">
      <div className="user-header">
        <AppBar />
      </div>
      <div className="user-profile">
        <img
          src={img}
          alt={userInformation.firstName + userInformation.lastName}
        />
        <h1>{userInformation.firstName + " " + userInformation.lastName}</h1>
        <div className="update-profile">
          {/* {userId !== "currentUserId" && (
            <span>
              {following ? (
                <button onClick={handleUnfollowClick}>
                  <FaUserTimes size={"1.4rem"} />
                </button>
              ) : (
                <button onClick={handleFollowClick}>
                  <FaUserPlus size={"1.4rem"} />
                </button>
              )}
            </span>
          )} */}
          {userId === getUserIdFromCookie() ? (
            <Link to="/updateprofile">
              <button>
                <FaPen size={"1.4rem"} color="#fff" />
              </button>
            </Link>
          ) : (
            // check if user is already following
            <>
              {followers.filter((el) => el.userId == getUserIdFromCookie())
                .length === 0 ? (
                <>
                  <button onClick={followHandler}>
                    <FaUserPlus size={"1.4rem"} color="#fff" />
                  </button>
                  <span>Follow User</span>
                </>
              ) : (
                <>
                  <button onClick={unfollowHandler}>
                    <FaUserTimes size={"1.4rem"} color="#fff" />
                  </button>
                  <span>Unfollow User</span>
                </>
              )}
            </>
          )}
        </div>
      </div>

      <div className="user-profile-down">
        <div className="user-info">
          <div>
            <LeftSide />
          </div>
        </div>
        <div className="user-resource">
          {userId === getUserIdFromCookie() ? (
            <div>
              <NewResources />
            </div>
          ) : (
            <div className="resource-page-header">
              <h2>{userInformation.firstName}'s resources</h2>
            </div>
          )}
          <div>
            {followers.filter((el) => el.userId == getUserIdFromCookie())
              .length == 0 ? (
              <div
                style={{
                  display: "flex",
                  justifyContent: "center",
                  padding: "2rem",
                }}
              >
                <h3>You have to follow the user to see his resources</h3>
              </div>
            ) : (
              <MyResources userResources={userResourceList} />
            )}
          </div>
        </div>
        <div className="user-following">
          <FollowingCard followingList={following} />
          <FollowersCard followerList={followers} />
        </div>
      </div>
    </div>
  );
};

export default Profile;
