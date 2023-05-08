import { useState , useEffect } from "react";
import axios from "axios";
import { Link } from 'react-router-dom';
import Modal from "react-modal";
import FollowersModal from '../FollowersList/FollowersList'

const FollowerCard = ({ userId }) => {

  const generateProfilePicture = (username) => {
    const color = Math.floor(Math.random() * 16777215).toString(16); // generate a random color
    const firstLetter = username.charAt(0).toUpperCase(); // get the first letter of the username
    return `https://via.placeholder.com/150/${color}/FFFFFF?text=${firstLetter}`; // return the URL of the image
  };
  const [followers, setFollowers] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);

  useEffect(() => {
    const fetchFollowers = async () => {
      const response = await axios.get(`http://localhost:8080/metadata-db-manager-service/users/get/${userId}/followers`);
      setFollowers(response.data);
    };

    fetchFollowers();
  }, [userId]);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  return (
    <div className='following-card'>
      <Link to ='#' onClick={openModal}><h3>Followers</h3><br/></Link>
      <div className='following-card-body'>{followers.slice(0, 5).map((follower) => (
        <div key={follower.id} className='following-card-div'>
          <img src={generateProfilePicture(follower.username)} alt={follower.username} />
          <Link to="/profilepage"><span>{`${follower.username}`}</span></Link>
        </div>
      ))}</div>
      <FollowersModal
        followers={followers}
        isOpen={isModalOpen}
        onClose={closeModal}
      />
    </div>
  );
};

export default FollowerCard;
