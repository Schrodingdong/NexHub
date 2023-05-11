import React from "react";
import { FaUserCircle, FaHome, FaUsers } from "react-icons/fa";
import { FiLogOut } from "react-icons/fi";
import "./LeftSide.css";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { getUserIdFromCookie } from "../../api/springApi";

const LeftSide = () => {
  const navigate = useNavigate();
  const userId = getUserIdFromCookie();
  const handleLogout = () => {
    // Remove the authentication token from local storage
    document.cookie = "token=;expires=Thu, 01 Jan 1970 00:00:00 UTC;path=/;";
    // Redirect to the login page
    navigate("/login");
  };

  return (
    <div className="user-page-left">
      <div className="user-page-card">
        <Link to={`/profilepage/${userId}`}>
          <button type="submit">
            <i>
              <FaUserCircle size={"1.2rem"} />
            </i>
            Profile
          </button>
        </Link>
        <Link to="/userpage">
          <button type="submit">
            <i>
              <FaHome size={"1.2rem"} />
            </i>
            Home
          </button>
        </Link>
        <button onClick={handleLogout} type="submit">
          <i>
            <FiLogOut size={"1.2rem"} />
          </i>
          Log out
        </button>
      </div>
    </div>
  );
};

export default LeftSide;
