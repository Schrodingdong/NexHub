import axios from "axios";

const verifyJwtToken = async (cookie) => {
  if (!cookie) return false;
  // call the auth api
  try{
    // extract the token from the cookie
    const token = cookie.split("=")[1];
    const res = await axios.get(
      "http://localhost:8080/authentication-service/validateToken?token=" + token
    )
    return res.status === 200;
  } catch (err) {
    console.log(err);
    return false;
  }
  
}

export default verifyJwtToken;