import { useState } from "react";
import Modal from "react-modal";
import "../FollowersList/FollowersList.css";
import { FaWindowClose, FaUserTimes } from "react-icons/fa";
import { Link } from "react-router-dom";
import generateProfilePicture from "../../api/generateProfilePicture";

const FollowingModal = ({ following, isOpen, onClose }) => {
  return (
    <Modal className="modal" isOpen={isOpen} onRequestClose={onClose}>
      <div className="modal-header">
        <h2>Following</h2>
        <button onClick={onClose}>
          <FaWindowClose size={"1.5rem"} />
        </button>
      </div>
      <div className="modal-body">
        {following.map((following) => (
          <div key={following.id} className="follower">
            <img
              src={generateProfilePicture(following.username)}
              alt={following.username}
            />
            <Link to="/profilepage">
              <span>{`${following.username}`}</span>
            </Link>
          </div>
        ))}
      </div>
    </Modal>
  );
};

export default FollowingModal;
