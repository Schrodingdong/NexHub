import LeftSide from '../LeftSide/LeftSide';
import NewResources from '../NewResources/NewResources';
import AppBar from '../AppBar/AppBar';
import FollowingResources from '../FollowingResources/FollowingResources';
import FollowingCard from '../FollowingCard/FollowingCard';
import { FaPen, FaUserPlus, FaUserTimes } from 'react-icons/fa';
import FollowersCard from '../FollowersCard/FollowerCard';
import { Link } from 'react-router-dom';
import { useState, useEffect } from 'react';
import axios from 'axios';
import MyResources from '../Myresource/Myresource';
import verifyJwtToken from "../../auth/verifyJwtToken";

import './profile.css';


const Profile = ({ isAuthenticated, userId }) => {
  useEffect(() => {
    verifyJwtToken(document.cookie).then((response) => {
      if (!response) {
        navigate("/login");
      }
    });
  }, []);

  const generateProfilePicture = (username) => {
    if (!username || username.length === 0) {
      return 'https://via.placeholder.com/150/CCCCCC/FFFFFF?text=?';
    }
    const color = Math.floor(Math.random() * 16777215).toString(16); // generate a random color
    const firstLetter = username.charAt(0).toUpperCase(); // get the first letter of the username
    return `https://via.placeholder.com/150/${color}/FFFFFF?text=${firstLetter}`; // return the URL of the image
  };
  
  const [following, setFollowing] = useState(false);
  const [users,setUsers]= useState("");

  useEffect(() => {
    const fetchUsers = async () => {
      const response = await axios.get(`http://localhost:8080/metadata-db-manager-service/users/get/id/4`);
      setUsers(response.data);
    };
    fetchUsers();
  }, [userId]);

  const handleFollowClick = async () => {
    const response = await axios.put(`http://localhost:8080/metadata-db-manager-service/users/2/follows/{followId}`);
    setFollowing(response.data);
  };

  const handleUnfollowClick = async () => {
    const response = await axios.delete(`http://localhost:8080/metadata-db-manager-service/users/2/unfollows/{followId}`);
    setFollowing(response.data);
  };




  return (
    <div className='user-page'>
      {isAuthenticated ? (
        <>
          <div className='user-header'>
            <AppBar />
          </div>
          <div className='user-profile'>
            <img src={generateProfilePicture(users.username)} alt={users.username} />
            <p>{users.username}</p>
            <div className='update-profile'>
              {userId !== 'currentUserId' && (
                <span>
                  {following ? (
                    <button onClick={handleUnfollowClick}>
                      <FaUserTimes size={'1.4rem'} /> 
                    </button>
                  ) : (
                    <button onClick={handleFollowClick}>
                      <FaUserPlus  size={'1.4rem'} /> 
                    </button>
                  )}
                </span>
              )}
              <Link to='/updateprofile'>
                <button><FaPen  size={'1.4rem'}  color='#000'/></button>
              </Link>
            </div>
          </div>
  
          <div className='user-profile-down'>
            <div className='user-info'>
              <div>
                <LeftSide />
              </div>
            </div>
            <div className='user-resource'>
              <div>
                <NewResources />
              </div>
              <div>
                <MyResources />
              </div>
            </div>
            <div className='user-following'>
              <FollowingCard />
              <FollowersCard />
            </div>
          </div>
        </>
      ) : (
        <p>Please log in to view this page.</p>
      )}

    </div>
  );
  
};

export default Profile;
