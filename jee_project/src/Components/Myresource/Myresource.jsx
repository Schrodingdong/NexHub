import '../FollowingResources/FollowingResources.css'
import { useEffect, useState } from "react";
import axios from "axios";

const MyResources = ({userId}) => {




    
  const [myposts, setMyposts] = useState([]);
  const [users, setUsers] = useState([]);

  useEffect(() => {
    const fetchMyPosts = async () => {
      const response = await axios.get(`http://localhost:8080/metadata-db-manager-service/res/get/all-from-user/${userId}`,
   );
      setMyposts(response.data);
    };

    fetchMyPosts();
  }, []);

  useEffect(() => {
    const fetchUsers = async () => {
      const response = await axios.get(`http://localhost:8080/metadata-db-manager-service/users/get/id/${userId}`,
   );
      setUsers(response.data);
    };

    fetchUsers();
  }, []);

  return (
    <div>
      {myposts.map((post) => (
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

export default MyResources;
