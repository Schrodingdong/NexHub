import LeftSide from '../LeftSide/LeftSide';
import NewResources from '../NewResources/NewResources';
import AppBar from '../AppBar/AppBar';
import FollowingResources from '../FollowingResources/FollowingResources';
import FollowingCard from '../FollowingCard/FollowingCard';
import { FaPen } from 'react-icons/fa';
import FollowersCard from '../FollowersCard/FollowerCard'


import './profile.css'
import profileimg from '../../images/profile.webp'



const Profile = ({ userId }) => {
  // const [resources, setResources] = useState([]);
  // const [userInfo, setUserInfo] = useState({});

  // useEffect(() => {
  //   const fetchUserInfo = async () => {
  //     const response = await axios.get(`/api/users/${userId}`);
  //     setUserInfo(response.data);
  //   };
  //   fetchUserInfo();
  // }, []);

  // useEffect(() => {

  //   const fetchResources = async () => {
  //     const response = await axios.get(`/api/resources/${userId}`);
  //     setResources(response.data);
  //   };
  //   fetchResources();
  // }, [userId]);

  return (
    <div className='user-page'>
      <div className='user-header'><AppBar /></div>
      <div className='user-profile'>
        <img src={profileimg} alt='imgprfl' ></img>
        <p>Full <span>Name</span></p>
        <a href='/updateprofile'><FaPen/></a>
        </div>
        {/* 
        <img>{userInfo.image}</img>
        <p>First Name: {userInfo.firstname} <span>Last Name: {userInfo.lastname}</span></p>
        <p>Email: {userInfo.email}</p> */}
  
        <div className='user-profile-down'>
      <div className='user-info'>
        <div><LeftSide/></div>
        </div>
          <div className='user-resource'>
          <div><NewResources/></div>
          <div>
            <FollowingResources/>
            
          </div>
         
            
              {/* 
        <img>{userInfo.image}</img>
        <p>First Name: {userInfo.firstname} <span>Last Name: {userInfo.lastname}</span></p>
       */}

            
       {/*  {resources.map((resource) => (
          <div key={resource.id}> 
           <img src={resource.image} alt={resource.title} />
            <h2>{resource.title}</h2>
            <p>{resource.content}</p> 
          </div>
        ))} */} 

        </div>
        <div className='user-following'><FollowingCard/>
        <FollowersCard/></div>
        </div>
        </div>
      

  );
};

export default Profile;