import '../FollowingCard/FollowingCard.css'
//import { FaMinus } from 'react-icons/fa';
import prflimg from '../../images/profile.webp'
/* import { useEffect, useState } from "react";
import axios from "axios"; */
import { Link } from 'react-router-dom';

const FollowerCard = ({ userId }) => {
  /* const [following, setFollowing] = useState([]);

  useEffect(() => {
    const fetchFollowing = async () => {
      const response = await axios.get(`/api/users/${userId}/following`);
      setFollowing(response.data);
    };

    fetchFollowing();
  }, [userId]);
 */

  return (
    <div className='following-card'>
         
        <Link to ='#'><h3>Followers</h3><br/></Link>
          <div>
            <img src={prflimg} alt='profile '></img>
            <span> Full Name</span>
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
export default FollowerCard;
