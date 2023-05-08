import './FollowingCard.css'
import { FaUserTimes } from 'react-icons/fa';
import { Link } from 'react-router-dom';
 import { useEffect, useState } from "react";
import axios from "axios"; 
import FollowingModal from '../FollowingList/FollowingList';

const FollowingCard = ({ userId }) => {

  const generateProfilePicture = (username) => {
    const color = Math.floor(Math.random() * 16777215).toString(16); // generate a random color
    const firstLetter = username.charAt(0).toUpperCase(); // get the first letter of the username
    return `https://via.placeholder.com/150/${color}/FFFFFF?text=${firstLetter}`; // return the URL of the image
  };

  const [following, setFollowing] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  useEffect(() => {
    const fetchFollowing = async () => {
      const response = await axios.get(`http://localhost:8080/metadata-db-manager-service/users/get/${userId}/following`);
      setFollowing(response.data);
    };

    fetchFollowing();
  }, [userId]);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };


  return (
    <div className='following-card'>
      <Link to ='#' onClick={openModal}><h3>Following</h3><br/></Link>
      <div className='following-card-body'>
      {following.slice(0, 5).map((following) => (
        <div key={following.id} className='following-card-div'>
          <img src={generateProfilePicture(following.username)} alt={following.username} />
          <Link to="/profilepage"><span>{`${following.username}`}</span></Link>
          <div className='unfollow'><button ><FaUserTimes size={'1rem'}/></button></div>
        </div>
      ))}</div>
      <FollowingModal
        following={following}
        isOpen={isModalOpen}
        onClose={closeModal}
      />
    </div>
  );
};
export default FollowingCard;
