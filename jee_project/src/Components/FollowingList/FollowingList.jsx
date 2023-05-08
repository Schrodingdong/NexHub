import { useState } from "react";
import Modal from "react-modal";
import '../FollowersList/FollowersList.css'
import { FaWindowClose, FaUserTimes } from "react-icons/fa";
import { Link } from "react-router-dom";

const FollowingModal = ({ following, isOpen, onClose }) => {

    const generateProfilePicture = (username) => {
        const color = Math.floor(Math.random() * 16777215).toString(16); // generate a random color
        const firstLetter = username.charAt(0).toUpperCase(); // get the first letter of the username
        return `https://via.placeholder.com/150/${color}/FFFFFF?text=${firstLetter}`; // return the URL of the image
      };
  return (
    <Modal className='modal' isOpen={isOpen} onRequestClose={onClose}>
      <div className='modal-header'><h2>Following</h2><button onClick={onClose}><FaWindowClose size={'1.5rem'}/></button></div>
        <div className="modal-body">{following.map((following) => (
          <div key={following.id} className="follower">
          <img src={generateProfilePicture(following.username)} alt={following.username} />
          <Link to="/profilepage"><span>{`${following.username}`}</span></Link>
        </div>
        ))}</div>
      
    </Modal>
  );
};

export default FollowingModal;
