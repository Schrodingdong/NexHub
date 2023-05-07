import { Link } from "react-router-dom";
import React from "react";
import "./Home.css";

const NavBar = () => {
  return (
    <div className="NavBar">
      <nav>
        <h2 className="logo">
          Nex<span className="logo-2">Hub</span>
        </h2>
        <ul>
          <li>
            <Link to="/">Home</Link>
          </li>
          <li>
            <Link to="/#about">About</Link>
          </li>
          <li>
            <Link to="/#footer">Contact</Link>
          </li>
        </ul>
        <Link to="/login">
          <button type="button" className="button-login">
            Login
          </button>
        </Link>
      </nav>
    </div>
  );
};

export default NavBar;
