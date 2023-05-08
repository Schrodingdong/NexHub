import React, { useState,useEffect } from 'react';
import './AppBar.css';
import { FaSearch } from 'react-icons/fa';
import { Link } from 'react-router-dom';
import UserProfile from '../ProfilePage/profile';
import profile_img from '../../images/profile.webp';
import axios from 'axios';

const AppBar = ({userId}) => {

  const generateProfilePicture = (username) => {
    const color = Math.floor(Math.random() * 16777215).toString(16); // generate a random color
    const firstLetter = username ? username.charAt(0).toUpperCase() : ''; // check if username is defined before calling the charAt method
    return `https://via.placeholder.com/150/${color}/FFFFFF?text=${firstLetter}`; // return the URL of the image
  };
  

  const [searchResults, setSearchResults] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [showResults, setShowResults] = useState(false);

  const handleInputChange = (event) => {
    const query = event.target.value;
    setSearchQuery(query);
    if (query === '') {
      setShowResults(false);
      setSearchResults([]);
    } else {
      axios
        .get('http://localhost:8080/metadata-db-manager-service/users/get/all')
        .then((response) => {
          const filteredUsers = response.data.filter((user) =>
            user.username.toLowerCase().includes(query.toLowerCase())
          );
          setSearchResults(filteredUsers);
          setShowResults(true);
        });
    }
  };
  const [users,setUsers]= useState("");

  useEffect(() => {
    const fetchUsers = async () => {
      const response = await axios.get(`http://localhost:8080/metadata-db-manager-service/users/get/id/5`);
      setUsers(response.data);
    };
    fetchUsers();
  }, [userId]);

  

  return (
    <div className='user-page-header'>
      <div className='user-page-logo'>
        <h2>
          Nex<span>Hub</span>
        </h2>
      </div>
      <div className='user-page-search'>
        <form data-test id='search-form'>
          <input
            type='text'
            placeholder='Search users...'
            value={searchQuery}
            onChange={handleInputChange}
          />
          <button type='submit'>
            <i>
              <FaSearch size={'1.3rem'} color='#00000030' />
            </i>
          </button>
          <div className='search-results' style={{ display: showResults ? 'block' : 'none' }}>
            {searchResults.map((user) => (
              <div key={user.userId}  className='users-search'>
                <img src={generateProfilePicture(user.username)} alt={user.username} />
                <button onClick={() => <UserProfile id={user.userId} />}>{user.username}</button>
              </div>
            ))}
          </div>
        </form>
      </div>

    </div>
  );
};

export default AppBar;
