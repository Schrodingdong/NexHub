import '../LoginPage/Login';
import NavBar from '../Home/NavBar';
import { Link } from 'react-router-dom';

import { useState } from 'react';
import { FaEnvelope, FaLock, FaFacebook, FaGoogle, FaApple, FaEyeSlash, FaEye , FaUser} from 'react-icons/fa';

const Register = () => {
  const [showPassword, setShowPassword] = useState(false);

  const handleTogglePassword = () => {
    setShowPassword(!showPassword);
  };

  return (
    <div className='login-page'>
      <div>
        <NavBar />
      </div>
      <div className="login-card-container">
        <div className="login-card">
          <div className="login-card-header">
            <h1>Sign Up</h1>
            <div>Pool Your Resources: Join Our Community of Sharers</div>
          </div>
          <form className="login-card-form">
            <div  id='form-item'>
              <input type="text" placeholder="First Name" className="form-input" />
              <input type="text" placeholder="Last Name" className="form-input" />
            </div>
            <div className="form-item">
              <i><FaEnvelope size={'1.3em'} color='#37018e' className='form-item-icon'/></i>
              <input type="text" placeholder="Enter Email" className="form-input" />
            </div>
            <div className="form-item">
              <FaLock size={'1.3em'} color='#37018e' className='form-item-icon' />
              <input type={showPassword ? 'text' : 'password'} id='password' placeholder="Enter Password" className="form-input" />
              <i onClick={handleTogglePassword} className='form-item-icon-eye'>
                {showPassword ? <FaEye size={'1.3em'} color='#37018e'  /> : <FaEyeSlash size={'1.3em'} color='#37018e' />}
              </i>
            </div>
            <Link to='/userpage'><button type="submit" className='login-btn'>Sign Up</button></Link>
          </form>
          <div className="login-card-footer">
            Already have an account? <Link to='/login' className='new-acc'>Sign In</Link>
          </div>
        </div>
        <div className="login-card-social">
          <div>Other Sign-Up Options</div>
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
export default Register;
