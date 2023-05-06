import React, {  useState} from 'react';
import { FaEnvelope, FaLock, FaFacebook, FaGoogle, FaApple, FaEyeSlash, FaEye } from 'react-icons/fa';
import { Link, Navigate, useNavigate } from 'react-router-dom';
import { isEmail } from "validator";


import axios from 'axios';

import NavBar from '../Home/NavBar';


const required = (value) => {
  if (!value) {
    return (
      <div className="invalid-feedback d-block">
        This field is required!
      </div>
    );
  }
};

const validEmail = (value) => {
  if (!isEmail(value)) {
    return (
      <div className="invalid-feedback d-block">
        This is not a valid email.
      </div>
    );
  }
};

const vfirstName = (value) => {
  if (value.length < 3 || value.length > 20) {
    return (
      <div className="invalid-feedback d-block">
        The firstName must be between 3 and 20 characters.
      </div>
    );
  }
};
const vlastName = (value) => {
  if (value.length < 3 || value.length > 20) {
    return (
      <div className="invalid-feedback d-block">
        The lastName must be between 3 and 20 characters.
      </div>
    );
  }
};

const vpassword = (value) => {
  if (value.length < 6 || value.length > 40) {
    return (
      <div className="invalid-feedback d-block">
        The password must be between 6 and 40 characters.
      </div>
    );
  }
};


const Register = () => {


  const [showPassword, setShowPassword] = useState(false);

  const navigate = useNavigate();


  const handleTogglePassword = () => {
    setShowPassword(!showPassword);
  };
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');


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

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.post(
        `http://localhost:8080/authentication-service/api/v1/registration`,
        {
          firstName: firstName,
          lastName: lastName,
          email: email,
          password: password,
        },
        {
          withCredentials: true,
          headers: {
            'Access-Control-Allow-Origin': '*',
          },
        }
      );
      console.log(response.data);
      navigate('/login');
    } catch (error) {
      console.error(error);
    }
  };
  
  

  return (
    <div className='login-page'>
      <div>
        <NavBar />
      </div>
      <div className='login-card-container'>
        <div className='login-card'>
          <div className='login-card-header'>
            <h1>Sign Up</h1>
            <div>Pool Your Resources: Join Our Community of Sharers</div>
          </div>
          <form className='login-card-form'onSubmit={handleSubmit}>
            <div id='form-item'>
              <input
                type='text'
                placeholder='First Name'
                className='form-input'
                name='firstName'
                value={firstName}
                onChange={onChangefisrtName}
                validations={[required, vfirstName]}
              />
              <input
                type='text'
                placeholder='Last Name'
                className='form-input'
                name='lastName'
                value={lastName}
                onChange={onChangelastName}
                validations={[required, vlastName]}
              />
            </div>
            <div className='form-item'>
              <i>
                <FaEnvelope size={'1.3em'} color='#37018e' className='form-item-icon' />
              </i>
              <input
                type='text'
                placeholder='Enter Email'
                className='form-input'
                name='email'
                value={email}
                onChange={onchangeEmail}
                validations={[required, validEmail]}
              />
            </div>
            <div className='form-item'>
              <FaLock size={'1.3em'} color='#37018e' className='form-item-icon' />
              <input
                type={showPassword ? 'text' : 'password'}
                id='password'
                placeholder='Enter Password'
                className='form-input'
                name='password'
                value={password}
                onChange={onChangePassword}
                validations={[required, vpassword]}
              />
              <i onClick={handleTogglePassword} className='form-item-icon-eye'>
                {showPassword ? <FaEye size={'1.3em'} color='#37018e' /> : <FaEyeSlash size={'1.3em'} color='#37018e' />}
              </i>
            </div>
            <button type='submit'  className='login-btn'>
              Sign Up
            </button>
          </form>
          <div className="login-card-footer">
            Already have an account? <Link to='/login' className='new-acc'>Sign In</Link>
          </div>
        </div>
        <div className="login-card-social">
          <div>Other Sign-Up Options</div>
          <div className="login-card-social-btns">
            <a href="/"><FaFacebook size={'2em'} /></a>
            <a href="/"><FaGoogle size={'2em'} /></a>
            <a href="/"><FaApple size={'2em'} /></a>
          </div>
        </div>
      </div>
    </div>
  );

}

export default Register;