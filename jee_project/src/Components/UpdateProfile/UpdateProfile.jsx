import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { FaEnvelope } from 'react-icons/fa';
import verifyJwtToken from "../../auth/verifyJwtToken";

const UpdateProfile = () => {
  const [username, setUsername] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const navigate =  useNavigate()

  const handleSubmit = async (event) => {

    const navigate = useNavigate();
    useEffect(() => {
      verifyJwtToken(document.cookie).then((response) => {
        if (!response) {
          navigate("/login");
        }
      });

    }, []);
    event.preventDefault();
    try {
      const response = await axios.put('http://localhost:8080/metadata-db-manager-service/users/update/id/4', {
        username: username,
        firstName:firstName,
         lastName: lastName
      });
      console.log(response.data);
      navigate('/profilepage')
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className='login-page'>
      <div className='login-card-container'>
        <div className='login-card'>
          <div className='login-card-header'>
            <h1>Update Profile</h1>
          </div>
          <form className='login-card-form' onSubmit={handleSubmit}>
            <div>
              <input type='text' placeholder='First Name' className='form-input' value={firstName} onChange={(e) => setFirstName(e.target.value)} />
            </div>
            <div className='form-item'>
              <input type='text' placeholder='Last  Name' className='form-input' value={lastName} onChange={(e) => setLastName(e.target.value)} />
            </div>
            <div className='form-item'>
              <input type='text' placeholder='Username' className='form-input' value={username} onChange={(e) => setUsername(e.target.value)} />
            </div>

            <button type='submit' className='login-btn'>
              Update
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};
export default UpdateProfile;
