import Home from './Components/Home/Home.jsx';
import Footer from './Components/Home/Footer.jsx';
import About from './Components/Home/About.jsx';
import Login from './Components/LoginPage/Login.jsx'
import Register from './Components/RegisterPage/Register.jsx';
import UserPage from './Components/UserPage/User.jsx'
import ProfilePage from './Components/ProfilePage/profile.jsx'
import UpdateProfile from './Components/UpdateProfile/UpdateProfile.jsx';
import AddResource from './Components/AddResource/AddResource.jsx';
import { BrowserRouter as Router, Route ,Switch} from 'react-router-dom';


const App = () => {
  return( 
   <Router>
        <div>
                <Switch>
                <Route exact path="/login" component={Login} />
                <Route exact path="/register" component={Register} />
                <Route exact path="/userpage" component={UserPage} />
                <Route exact path="/profilepage" component={ProfilePage} />
                <Route exact path='/about' component={About}/>
                <Route exact path='/footer' component={Footer}/>
                <Route exact path='/updateprofile' component={UpdateProfile}/>
                <Route exact path='/addresource' component={AddResource}/>
                <Route exact path='/' component={Home}/>
                </Switch>
        </div>
        </Router>

       

);

}

export default App