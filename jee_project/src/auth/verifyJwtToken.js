import axios from "axios";

const verifyJwtToken = async (cookie) => {
  if (!cookie.includes("token")) return false;
  try{
    let token = cookie.split(";").find((item) => item.includes("token")).split("=")[1];
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