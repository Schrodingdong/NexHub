import { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import Modal from "react-modal";
import FollowersModal from "../FollowersList/FollowersList";
import generateProfilePicture from "../../api/generateProfilePicture";

const FollowerCard = ({ userId, followerList }) => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  return (
    <div className="following-card">
      <Link to="#" onClick={openModal}>
        <h3>Followers</h3>
        <br />
      </Link>
      <div className="following-card-body">
        {followerList.length === 0 ? (
          <p>Nothing to see</p>
        ) : (
          followerList.slice(0, 5).map((follower) => (
            <div key={follower.id} className="following-card-div">
              <img
                src={generateProfilePicture(follower.username)}
                alt={follower.username}
              />
              <Link to="/profilepage">
                <span>{`${follower.username}`}</span>
              </Link>
            </div>
          ))
          // <p>content</p>
        )}
      </div>
      <FollowersModal
        followers={followerList}
        isOpen={isModalOpen}
        onClose={closeModal}
      />
    </div>
  );
};

export default FollowerCard;
