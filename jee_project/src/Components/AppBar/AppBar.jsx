import React, { useState } from 'react';
import './AppBar.css';
import { FaSearch } from 'react-icons/fa';
import { Link } from 'react-router-dom';
import usersData from '../../data/users.json';
import UserProfile from '../ProfilePage/profile';
import profile_img from '../../images/profile.webp';

const AppBar = () => {
  const [searchResults, setSearchResults] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');

  const handleSearch = (event) => {
    event.preventDefault();
    // simulate API call to fetch search results
    const filteredUsers = usersData.users.filter((user) =>
      user.name.toLowerCase().includes(searchQuery.toLowerCase())
    );
    setSearchResults(filteredUsers);
  };

  const handleInputChange = (event) => {
    setSearchQuery(event.target.value);
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
          <div className='search-users'>
          {searchResults.map((user) => (
            <div  key={user.id} onClick={() => <UserProfile id={user.id} />}>
              {user.name}
            </div>
          ))}</div>
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


