import './FollowingResources.css'
/* import { useEffect, useState } from "react";
import axios from "axios"; */
import prflimg from '../../images/profile.webp';
import rscimg from '../../images/postpic.webp';

const FollowingResources = ({ userId }) => {
  /* const [followingPosts, setFollowingPosts] = useState([]);

  useEffect(() => {
    const fetchFollowingPosts = async () => {
      const response = await axios.get(`/api/users/${userId}/following/resources`);
      setFollowingPosts(response.data);
    };

    fetchFollowingPosts();
  }, [userId]);
 */
  return (
    <div  className='home-page-resources'>
      <div className='resource-user-profile'>
      <img src={prflimg} alt='user profile picture'></img><span>Full Name</span></div>
      
      <div className='resource-user-info'>
      <p>Resource Description</p>
      <img src = {rscimg}></img></div>
      
      {/* {followi
      ngPosts.map((post) => (
        <div key={post.id}>
          <img src={post.image} alt={post.title} />
          <h3>{post.title}</h3>
        </div>
      ))} */}
    </div>
  );
};
export default FollowingResources;