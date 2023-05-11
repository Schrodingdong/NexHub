import React, { useState, useEffect } from "react";
import "./AppBar.css";
import { FaSearch } from "react-icons/fa";
import { Link, Navigate } from "react-router-dom";
import UserProfile from "../ProfilePage/profile";
import profile_img from "../../images/profile.webp";
import axios from "axios";
import generateProfilePicture from "../../api/generateProfilePicture";
import SearchResult from "./SearchResult";

const AppBar = ({ userId, allUsers }) => {
  const [searchResults, setSearchResults] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [showResults, setShowResults] = useState(false);

  const handleInputChange = (event) => {
    const query = event.target.value;
    setSearchQuery(query);
    if (query === "") {
      setShowResults(false);
      setSearchResults([]);
    } else {
      if (allUsers === undefined) return;
      const filteredList = allUsers.filter((user) =>
        user.username.toLowerCase().includes(query.toLowerCase())
      );
      setSearchResults(filteredList);
      setShowResults(true);
    }
  };
  const [users, setUsers] = useState("");

  return (
    <div className="user-page-header">
      <div className="user-page-logo">
        <Link to="/" style={{ textDecoration: "none" }}>
          <h2>
            Nex<span>Hub</span>
          </h2>
        </Link>
      </div>
      <div className="user-page-search">
        <form data-test id="search-form">
          <input
            type="text"
            placeholder="Search users..."
            value={searchQuery}
            onChange={handleInputChange}
          />
          <button type="submit">
            <i>
              <FaSearch size={"1.3rem"} color="#00000030" />
            </i>
          </button>
          <div
            className="search-results"
            style={{ display: showResults ? "block" : "none" }}
          >
            {searchResults.map((user) => (
              <SearchResult user={user} />
            ))}
          </div>
        </form>
      </div>
    </div>
  );
};

export default AppBar;
