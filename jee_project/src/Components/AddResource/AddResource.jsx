import { useEffect } from "react";
import "../LoginPage/Login";

import { Link, useNavigate } from "react-router-dom";
import verifyJwtToken from "../../auth/verifyJwtToken";

const AddResource = () => {
  const navigate = useNavigate();
  useEffect(() => {
    verifyJwtToken(document.cookie).then((response) => {
      if (!response) {
        navigate("/login");
      }
    });
  }, []);
  return (
    <div className="login-page">
      <div className="login-card-container">
        <div className="login-card">
          <div className="login-card-header">
            <h1>Add Resource</h1>
          </div>
          <form className="login-card-form">
            <div id="form-item">
              <input
                type="text"
                placeholder="Resource Title"
                className="form-input"
              />
            </div>
            <div id="form-item">
              <input
                type="text"
                placeholder="Resource Description"
                className="form-input"
              />
            </div>
            <div id="form-item">
              <label for="file" class="form-input-file">
                Choose file
              </label>
              <input id="file" class="form-input" type="file" />
            </div>

            <Link to="/userpage">
              <button type="submit" className="login-btn">
                Add
              </button>
            </Link>
          </form>
        </div>
      </div>
    </div>
  );
};
export default AddResource;
