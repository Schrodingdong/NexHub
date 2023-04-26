import '../LoginPage/Login';

import { Link } from 'react-router-dom';


const AddResource = () => {

  return (
    <div className='login-page'>
      <div className="login-card-container">
        <div className="login-card">
          <div className="login-card-header">
            <h1>Add Resource</h1>
          </div>
          <form className="login-card-form">
            <div  id='form-item'>
              <input type="text" placeholder="Resource" className="form-input" />
            </div>
            <div  id='form-item'>
              <input type="text" placeholder="Resource Description" className="form-input" />
            </div>
            <div  id='form-item'>
                <label for="pic-res"> Select File</label>
              <input type="file" placeholder="Resource Description"  id ='pic-res' className="form-input" />
            </div>
            
            <Link to='/userpage'><button type="submit" className='login-btn'>Update</button></Link>
          </form>
        
        </div>
    
      </div>
    </div>
  );

}
export default AddResource;
