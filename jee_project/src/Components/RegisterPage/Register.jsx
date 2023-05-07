import React, { useState } from "react";
import {
  FaEnvelope,
  FaLock,
  FaFacebook,
  FaGoogle,
  FaApple,
  FaEyeSlash,
  FaEye,
} from "react-icons/fa";
import { Link, Navigate } from "react-router-dom";

import { isEmail } from "validator";

import axios from "axios";

import NavBar from "../Home/NavBar";

const required = (value) => {
  if (!value) {
    return (
      <div className="invalid-feedback d-block">This field is required!</div>
    );
  }
};

// const validateEmail = (value) => {
//   return !isEmail(value) ? (
//     <div className="invalid-feedback d-block">This is not a valid email.</div>
//   ) : null;
// };

const Register = () => {
  const [showPassword, setShowPassword] = useState(false);

  const navigate = useNavigate();

  const handleTogglePassword = () => {
    setShowPassword(!showPassword);
  };

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [nonValidElements, setNonValidElements] = useState([]);
  const [showValidationError, setShowValidationError] = useState(false);
  // onchange functions
  const onchangeEmail = (e) => {
    const email = e.target.value;
    setEmail(email);
  };
  const onChangefisrtName = (e) => {
    const firstName = e.target.value;
    setFirstName(firstName);
  };
  const onChangelastName = (e) => {
    const lastName = e.target.value;
    setLastName(lastName);
  };
  const onChangePassword = (e) => {
    const password = e.target.value;
    setPassword(password);
  };

  // validation functions
  const generateNonvalidElements = (typeElement, errMsg) => {
    const errorElement = {
      typeElement: typeElement,
      errMsg: errMsg,
    };
    return errorElement;
  };
  const validateEmail = (email) => {
    if (!isEmail(email)) {
      const el = generateNonvalidElements("Email", " is not valid");
      setNonValidElements((nonValidElements) => {
        nonValidElements.push(el);
        return nonValidElements;
      });
      return false;
6
    }
    return true;
  };
  const validateFirstName = (firstname) => {
    if (firstname.length < 3 || firstname.length > 20) {
      const el = generateNonvalidElements(
        "First Name",
        " must be between 3 and 20 characters"
      );
      setNonValidElements((nonValidElements) => {
        nonValidElements.push(el);
        return nonValidElements;
      });
      return false;
    }
    return true;
  };
  const validateLastName = (lastname) => {
    if (lastname.length < 3 || lastname.length > 20) {
      const el = generateNonvalidElements(
        "Last Name",
        " must be between 3 and 20 characters"
      );
      setNonValidElements((nonValidElements) => {
        nonValidElements.push(el);
        return nonValidElements;
      });
      return false;
    }
    return true;
  };
  const validatePassword = (password) => {
    if (password.length < 3 || password.length > 20) {
      const el = generateNonvalidElements(
        "Password",
        " must be between 3 and 20 characters"
      );
      setNonValidElements((nonValidElements) => {
        nonValidElements.push(el);
        return nonValidElements;
      });
      return false;
    }
    return true;
  };


  // on submit method
  const handleSubmit = (event) => {
    event.preventDefault();
    // validate Registration information
    setNonValidElements([]);
    validateEmail(email);
    validateFirstName(firstName);
    validateLastName(lastName);
    validatePassword(password);
    if (nonValidElements.length > 0) {
      setShowValidationError(true);
      return;
    }


    // if all good, send request
    setShowValidationError(false);
    const body = {
      firstName: firstName,
      lastName: lastName,
      email: email,
      password: password,
    };
    axios
      .post(
        `http://localhost:8080/authentication-service/api/v1/registration`,
        body,
        { withCredentials: true }
      )
      .then((response) => {
        console.log(response.status);
        if (response.status === 200) {
          Navigate("/login");
        }
      })
      .catch((error) => {
        alert(error);
        console.log(error);
      });
  };

  // styling
  const registrationValidationError = {
    backgroundColor: "#ffb3ad",
    padding: "1rem",
    borderRadius: "1rem",
  };
  return (
    <div className="login-page">
      <div>
        <NavBar />
      </div>
      <div className="login-card-container">
        <div className="login-card">
          <div className="login-card-header">
            <h1>Sign Up</h1>
            <div>Pool Your Resources: Join Our Community of Sharers</div>
          </div>
          {showValidationError && (
            <ul style={registrationValidationError}>
              {nonValidElements.map((el) => {
                return (
                  <li style={{ listStyle: "none" }} key={el.typeElement}>
                    <span style={{ color: "red" }}>{el.typeElement}</span>
                    {el.errMsg}
                  </li>
                );
              })}
            </ul>
          )}
          <form className="login-card-form" onSubmit={handleSubmit}>
            <div id="form-item">
              <input
                type="text"
                placeholder="First Name"
                className="form-input"
                name="firstName"
                value={firstName}
                onChange={onChangefisrtName}
                required
              />
              <input
                type="text"
                placeholder="Last Name"
                className="form-input"
                name="lastName"
                value={lastName}
                onChange={onChangelastName}
                required
              />
            </div>
            <div className="form-item">
              <i>
                <FaEnvelope
                  size={"1.3em"}
                  color="#37018e"
                  className="form-item-icon"
                />
              </i>
              <input
                type="text"
                placeholder="Enter Email"
                className="form-input"
                name="email"
                value={email}
                onChange={onchangeEmail}
                required
              />
            </div>
            <div className="form-item">
              <FaLock
                size={"1.3em"}
                color="#37018e"
                className="form-item-icon"
              />
              <input
                type={showPassword ? "text" : "password"}
                id="password"
                placeholder="Enter Password"
                className="form-input"
                name="password"
                value={password}
                onChange={onChangePassword}
                required
              />
              <i onClick={handleTogglePassword} className="form-item-icon-eye">
                {showPassword ? (
                  <FaEye size={"1.3em"} color="#37018e" />
                ) : (
                  <FaEyeSlash size={"1.3em"} color="#37018e" />
                )}
              </i>
            </div>
            <button type="submit" className="login-btn">
              Sign Up
            </button>
          </form>
          <div className="login-card-footer">
            Already have an account?{" "}
            <Link to="/login" className="new-acc">
              Sign In
            </Link>
          </div>
        </div>
        <div className="login-card-social">
          <div>Other Sign-Up Options</div>
          <div className="login-card-social-btns">
            <a href="/">
              <FaFacebook size={"2em"} />
            </a>
            <a href="/">
              <FaGoogle size={"2em"} />
            </a>
            <a href="/">
              <FaApple size={"2em"} />
            </a>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Register;
