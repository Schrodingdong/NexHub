import generateProfilePicture from "../../api/generateProfilePicture";
import { Link } from "react-router-dom";
import { getUserIdFromCookie, initializeAxios } from "../../api/springApi";
import { FaUserTimes } from "react-icons/fa";
import { useState } from "react";

const FollowingCardElement = ({ following, setFollowingList }) => {
  const [userId, setUserId] = useState(following.userId);
  const [username, setUsername] = useState(following.username);
  const [firstName, setFirstName] = useState(following.firstName);
  const [lastName, setLastName] = useState(following.lastName);
  const [email, setEmail] = useState(following.email);
  const [buckedId, setBuckedId] = useState(following.buckedId);

  const unfollowHandler = () => {
    const { metadataService } = initializeAxios();
    let myUserId = getUserIdFromCookie();
    metadataService
      .delete(`/users/${myUserId}/unfollows/${userId}`)
      .then((res) => {
        alert("successfully unfollowed" + username);
        setFollowingList((prev) => {
          return prev.filter((following) => following.userId !== userId);
        });
        window.location.reload();
      });
  };
  return (
    <div key={following.id} className="following-card-div">
      <img
        src={generateProfilePicture(following.username)}
        alt={following.username}
      />
      <Link to={`/profilepage/${following.userId}`}>
        <span>{`${following.username}`}</span>
      </Link>
      <div className="unfollow">
        <button onClick={unfollowHandler}>
          <FaUserTimes size={"1rem"} />
        </button>
      </div>
    </div>
  );
};
export default FollowingCardElement;
