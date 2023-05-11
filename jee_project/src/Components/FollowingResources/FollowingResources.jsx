import "./FollowingResources.css";
import { useEffect, useState } from "react";
import axios from "axios";
import prflimg from "../../images/profile.webp";
import generateProfilePicture from "../../api/generateProfilePicture";
import { Link } from "react-router-dom";
import { FaUserTimes } from "react-icons/fa";
import FollowingResourceCard from "./FollowingResourceCard";

const FollowingResources = ({
  followingResourceList,
  followingList,
  myUserData,
}) => {
  const [followingPosts, setFollowingPosts] = useState([]);
  const [following, setFollowing] = useState([]);
  const [users, setUsers] = useState([]);

  useEffect(() => {
    setFollowingPosts(followingResourceList);
    console.log(followingResourceList);
  }, [followingResourceList]);

  useEffect(() => {
    setFollowing(followingList);
  }, [followingList]);

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
      {followingPosts.map((resource) => (
        <FollowingResourceCard
          resource={resource}
          myUserData={myUserData}
          following={following}
        />
      ))}
    </div>
  );
};

export default FollowingResources;
