import React from 'react'
import {FaUserCircle,FaHome,FaUsers} from 'react-icons/fa';
import { FiLogOut} from 'react-icons/fi';
import './LeftSide.css'
import { Link } from 'react-router-dom';



const LeftSide = () => {
  return (
    <div className='user-page-left'>
        <div className='user-page-card'>
         <Link to ='/profilepage'><button type='submit'><i><FaUserCircle size={'1.2rem'}/></i> Profile</button></Link>
          <Link to='/userpage'><button type='submit'><i><FaHome size={'1.2rem'} /></i> Home</button></Link>
          <button type='submit' ><i><FiLogOut size={'1.2rem'}/></i> Log out</button>
      </div>
    </div>
  )
}

export default LeftSide