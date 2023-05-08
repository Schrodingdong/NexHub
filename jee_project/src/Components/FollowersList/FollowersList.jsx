import { useState } from "react";
import Modal from "react-modal";
import './FollowersList.css'
import { FaWindowClose, Fa } from "react-icons/fa";
import { Link } from "react-router-dom";

const FollowersModal = ({ followers, isOpen, onClose }) => {
  
  const generateProfilePicture = (username) => {
    const color = Math.floor(Math.random() * 16777215).toString(16); // generate a random color
    const firstLetter = username.charAt(0).toUpperCase(); // get the first letter of the username
    return `https://via.placeholder.com/150/${color}/FFFFFF?text=${firstLetter}`; // return the URL of the image
  };
  
  return (
    <Modal className='modal' isOpen={isOpen} onRequestClose={onClose}>
      <dib className='modal-header'><h2>Followers</h2><button onClick={onClose}><FaWindowClose size={'1.5rem'}/></button></dib>
        <div className="modal-body">{followers.map((follower) => (
          <div key={follower.id} className="follower">
          <img src={generateProfilePicture(follower.username)} alt={follower.username} />
          <Link to="/profilepage"><span>{`${follower.username}`}</span></Link>
        </div>
        ))}</div>
      
    </Modal>
  );
};

export default FollowersModal;
