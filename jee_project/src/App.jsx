import Home from './Components/Home/Home.jsx';
import Footer from './Components/Home/Footer.jsx';
import About from './Components/Home/About.jsx';
import Login from './Components/LoginPage/Login.jsx'
import Register from './Components/RegisterPage/Register.jsx';
import UserPage from './Components/UserPage/User.jsx'
import ProfilePage from './Components/ProfilePage/profile.jsx'
import UpdateProfile from './Components/UpdateProfile/UpdateProfile.jsx';
import AddResource from './Components/AddResource/AddResource.jsx';
import { BrowserRouter, Route ,Routes} from 'react-router-dom';


const App = () => {
  return( 
    <BrowserRouter basename="/">
      <div>
        <Routes>
          <Route exact path="/login" element={<Login />} />
          <Route exact path="/register" element={<Register />} />
          <Route exact path="/userpage" element={<UserPage />} />
          <Route exact path="/profilepage" element={<ProfilePage />} />
          <Route exact path='/about' element={<About />}/>
          <Route exact path='/footer' element={<Footer />}/>
          <Route exact path='/updateprofile' element={<UpdateProfile />} />
          <Route exact path='/addresource' element={<AddResource />} />
          <Route exact path='/' element={<Home />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
