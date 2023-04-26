import './Login.css';
import NavBar from '../Home/NavBar';
import { Link } from 'react-router-dom';
import { useState } from 'react';
import { FaEnvelope, FaLock, FaFacebook, FaGoogle, FaApple, FaEyeSlash, FaEye } from 'react-icons/fa';

const LoginPage = () => {
  const [showPassword, setShowPassword] = useState(false);

  const handleTogglePassword = () => {
    setShowPassword(!showPassword);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Add your logic for form submission here
  };

  return (
    <div className='login-page'>
      <NavBar />
      <div className="login-card-container">
        <div className="login-card">

          <div className="login-card-header">
            <h1>Sign In</h1>
            <div>Please login to use the platform</div>
          </div>
          <form className="login-card-form" onSubmit={handleSubmit}>
            <div className="form-item">
              <i><FaEnvelope size={'1.3em'} color='#37018e' className='form-item-icon'/></i>
              <input type="email" placeholder="Enter Email" className="form-input" required />
            </div>
            <div className="form-item">
              <FaLock size={'1.3em'} color='#37018e' className='form-item-icon' />
              <input type={showPassword ? 'text' : 'password'} id='password' placeholder="Enter Password" className="form-input" required />
              <i onClick={handleTogglePassword} className='form-item-icon-eye'>
                {showPassword ? <FaEye size={'1.3em'} color='#37018e'  /> : <FaEyeSlash size={'1.3em'} color='#37018e' />}
              </i>
            </div>
            <Link to ='/userpage'><button type="submit" className='login-btn'>Sign In</button></Link>
          </form>
          <div className="login-card-footer">
            Don't have an account? <Link to='/register' className='new-acc'>Create a free account.</Link>
          </div>
        </div>
        <div className="login-card-social">
          <div>Other Sign-In Options</div>
          <div className="login-card-social-btns">
            <a href="/"><FaFacebook size={'2em'} /></a>
            <a href="/"><FaGoogle size={'2em'} /></a>
            <a href="/"><FaApple size={'2em'} /></a>
          </div>
        </div>
      </div>
    </div>
  );
}

export default LoginPage;
