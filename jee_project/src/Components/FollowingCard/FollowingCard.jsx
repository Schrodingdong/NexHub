import './FollowingCard.css'
import { FaMinus } from 'react-icons/fa';
import prflimg from '../../images/profile.webp'
import { Link } from 'react-router-dom';
/* import { useEffect, useState } from "react";
import axios from "axios"; */

const FollowingCard = ({ userId }) => {
  /* const [following, setFollowing] = useState([]);

  useEffect(() => {
    const fetchFollowing = async () => {
      const response = await axios.get(`/api/users/${userId}/following`);
      setFollowing(response.data);
    };

    fetchFollowing();
  }, [userId]);

  const handleUnfollow = async (user) => {
    await axios.post(`/api/users/${userId}/unfollow`, { userId: user.id });
    setFollowing((prevFollowing) =>
      prevFollowing.filter((u) => u.id !== user.id)
    );
  }; */

  return (
    <div className='following-card'>
      <Link to ='#'><h3>Following</h3><br/></Link>
          <div>
            <img src={prflimg} alt='profile '></img>
            <span> Username</span>
            <button><i><FaMinus/></i></button>
          </div>
        {/* {following.map((user) => (
          <li key={user.id}>
            <div className="user-info">
              <img src={user.picturePath} alt={user.firstName} />
              <div>
                <p>{`${user.firstName} ${user.lastName}`}</p>
                <button onClick={() => handleUnfollow(user)}>Unfollow</button>
              </div>
            </div>
          </li>
        ))} */}
    </div>
  );
};
export default FollowingCard;
