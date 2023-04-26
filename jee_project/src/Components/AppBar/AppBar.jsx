import React from 'react'
import './AppBar.css'
import { FaSearch} from 'react-icons/fa';
import { Link } from 'react-router-dom/cjs/react-router-dom.min';

import  profile_img from '../../images/profile.webp';

const AppBar = () => {
  return (
    <div className='user-page-header'>
      <div className='user-page-logo'>
      <h2 >Nex<span>Hub</span></h2>
      </div>
      <div className='user-page-search'>
        <form>
        <input type='text' placeholder='Search'/>
        <button type='submit'><i><FaSearch size={'1.3rem'} color='#00000030'/></i></button>
        </form>
      </div>
    <div className='user-page-info'>
    <Link to ='/profilepage'><img src={profile_img} alt='profile'></img></Link>
    </div>
    </div>
  )
}

export default AppBar