import axios from "axios";

export const getTokenFromCookie = () => {
  let token = document.cookie.split(";").find((item) => item.includes("token")).split("=")[1];
  if (token === undefined) {
    console.error("No token found");
    return -1;
  }
  return token;
}
export const getMailFromCookie = () => {
  let mail = document.cookie.split(";").find((item) => item.includes("mail")).split("=")[1];
  return mail;
}
export const getUserIdFromCookie = () => {
  let userId = document.cookie.split(";").find((item) => item.includes("userId")).split("=")[1];
  return userId;
}



let metadataService;
let authenticationService;
let resourceService;
export const initializeAxios = () => {
  if (document.cookie === "") {
    console.error("No cookie found");
    return;
  }
  metadataService = axios.create({
    baseURL: "http://localhost:8080/metadata-db-manager-service",
    headers: {'Authorization': 'Bearer ' + getTokenFromCookie()}
  })
  authenticationService = axios.create({
    baseURL: "http://localhost:8080/authentication-service",
    headers: {'Authorization': 'Bearer ' + getTokenFromCookie()}
  })
  resourceService = axios.create({
    baseURL: "http://localhost:8080/resource-manager-service",
    headers: {'Authorization': 'Bearer ' + getTokenFromCookie()}
  })
  console.info("Axios initialized");
  return {
    metadataService: metadataService,
    authenticationService: authenticationService,
    resourceService: resourceService
  }
}


export const getUserIdFromMail = async (mail) => {
  let res = await metadataService.get(`/users/get/mail/${mail}`);
  return res.data.userId;
}

export const getUserFollowers = async (userId) => {
  let followers = await metadataService.get(`/users/get/${userId}/followers`);
  return followers.data;
}
export const getUserFollowing = async (userId) => {
  let followings = await metadataService.get(`/users/get/${userId}/following`);
  return followings.data;
}