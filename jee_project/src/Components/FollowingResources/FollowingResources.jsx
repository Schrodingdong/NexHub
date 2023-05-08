import './FollowingResources.css'
import { useEffect, useState } from "react";
import axios from "axios";
import prflimg from '../../images/profile.webp';

const FollowingResources = () => {




    
  const [followingPosts, setFollowingPosts] = useState([]);
  const [users, setUsers] = useState([]);

  useEffect(() => {
    const fetchFollowingPosts = async () => {
      const response = await axios.get('http://localhost:8080/metadata-db-manager-service/res/get/all');
      setFollowingPosts(response.data);
    };

    fetchFollowingPosts();
  }, []);

  useEffect(() => {
    const fetchUsers = async () => {
      const response = await axios.get('http://localhost:8080/metadata-db-manager-service/users/get/id/5');
      setUsers(response.data);
    };

    fetchUsers();
  }, []);

  return (
    <div>
      {followingPosts.map((post) => (
        <div className='home-page-resources' key={post.resourceId}>
          <div className='resource-user-profile'>
          <img  alt={users.username} />
            <span>{users.username}</span>
          </div>
          <div className='resource-user-info'>
            <h4>{post.resourceName}</h4>
            <p>{post.resourceDescription}</p>
            <img  alt='file' />
          </div>
        </div>
      ))}
    </div>
  );
};

export default FollowingResources;
