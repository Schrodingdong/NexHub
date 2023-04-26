import '../LoginPage/Login';

import { Link } from 'react-router-dom';
import { FaEnvelope , FaUpload } from 'react-icons/fa';

import './UpdateProfile.css'
const UpdateProfile = () => {

  return (
    <div className='login-page'>
      <div className="login-card-container">
        <div className="login-card">
          <div className="login-card-header">
            <h1>Update Profile</h1>
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
            <div className='update-image'>
                <div className="form-item">        
            <label forHtml="inputTag">
                Select Image
                <input id="inputTag" type="file"/>
                
                </label>
            </div>
            </div>
            
            <Link to='/userpage'><button type="submit" className='login-btn'>Update</button></Link>
          </form>
        
        </div>
    
      </div>
    </div>
  );

}
export default UpdateProfile;
