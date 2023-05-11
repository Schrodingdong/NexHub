import "./FollowingCard.css";
import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import FollowingModal from "../FollowingList/FollowingList";
import FollowingCardElement from "./FollowingCardElement";

const FollowingCard = ({ userId, followingList }) => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [following, setFollowing] = useState(followingList);
  useEffect(() => {
    setFollowing(followingList);
  }, [followingList]);
  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  return (
    <div className="following-card">
      <Link to="#" onClick={openModal}>
        <h3>Following</h3>
        <br />
      </Link>
      <div className="following-card-body">
        {following.length === 0 ? (
          <p>Nothing to show</p>
        ) : (
          following
            .slice(0, 5)
            .map((following) => (
              <FollowingCardElement
                key={following.userId}
                following={following}
                setFollowingList={setFollowing}
              />
            ))
        )}
      </div>
      <FollowingModal
        following={following}
        isOpen={isModalOpen}
        onClose={closeModal}
      />
    </div>
  );
};
export default FollowingCard;
