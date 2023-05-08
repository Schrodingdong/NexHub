import React from "react";

import { FaFile, FaImage, FaVideo, FaLink, FaPlus } from "react-icons/fa";

import "./NewResources.css";
import { Link, useNavigate } from "react-router-dom";
import { useEffect } from "react";
import verifyJwtToken from "../../auth/verifyJwtToken";

const NewResources = () => {
  const navigate = useNavigate();
  useEffect(() => {
    if (!verifyJwtToken(document.cookie)) {
      navigate("/login");
    }
  }, []);
  return (
    <div className="resource-page">
      <div className="resource-page-header">
        <Link>
          <button>
            <FaFile size={"1.5rem"} color="#ffffff" />
          </button>
        </Link>
        <button>
          <FaImage size={"1.5rem"} color="#ffffff" />
        </button>
        <button>
          <FaVideo size={"1.5rem"} color="#ffffff" />
        </button>
        <button>
          <FaLink size={"1.5rem"} color="#ffffff" />
        </button>
        <Link to="/addresource">
          <button>
            <FaPlus size={"1.5rem"} color="#ffffff" />
          </button>
        </Link>
      </div>
      <div></div>
    </div>
  );
};

export default NewResources;
