import React, { useState } from 'react';
import './AppBar.css';
import { FaSearch } from 'react-icons/fa';
import { Link } from 'react-router-dom';
import usersData from '../../data/users.json';
import UserProfile from '../ProfilePage/profile';
import profile_img from '../../images/profile.webp';
import axios from 'axios';

const AppBar = () => {
  const [searchResults, setSearchResults] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [showResults, setShowResults] = useState(false);

  const handleSearch = (event) => {
    event.preventDefault();
    axios.get("http://localhost:8080/metadata-db-manager-service/users/get/all")
    const filteredUsers = usersData.users.filter((user) =>
      user.name.toLowerCase().includes(searchQuery.toLowerCase())
    );
    setSearchResults(filteredUsers);
    setShowResults(true);
  };

  const handleInputChange = (event) => {
    setSearchQuery(event.target.value);
    if (event.target.value === '') {
      setShowResults(false);
    }
  };

  return (
    <div className='user-page-header'>
      <div className='user-page-logo'>
        <h2>
          Nex<span>Hub</span>
        </h2>
      </div>
      <div className='user-page-search'>
        <form onSubmit={handleSearch} data-test id='search-form'>
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
              <div key={user.id} onClick={() => <UserProfile id={user.id} />}>
                {user.name}
                {user.profilePicture}
              </div>
            ))}
          </div>
        </form>
      </div>
      <div className='user-page-info'>
        <Link to='/profilepage'>
          <img src={profile_img} alt='profile' />
        </Link>
      </div>
    </div>
  );
};

export default AppBar;
