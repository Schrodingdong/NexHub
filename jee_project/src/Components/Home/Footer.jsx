import React from 'react'
import './Home.css'
import { FaFacebook, FaLinkedin, FaTwitter, FaInstagram } from 'react-icons/fa';

const Footer = () => {
  return (
    <div className='footer'>
      <div className="footer-content">
            <h3>NexHub</h3>
            <p>Empowering learning for everyone.</p>
            <ul class="socials">
                <li><a href="/"><i><FaFacebook/></i></a></li>
                <li><a href="/"><i><FaInstagram/></i></a></li>
                <li><a href="/"><i><FaLinkedin/></i></a></li>
                <li><a href="/"><i><FaTwitter/></i></a></li>
            </ul>
        </div>
        <div className="footer-bottom">
            <p>copyright &copy;2023 NexHub. designed by <span>Ilham Oumaima Hamza</span></p>
        </div>
    </div>
  )
}

export default Footer