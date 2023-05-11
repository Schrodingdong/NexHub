const generateProfilePicture = (username) => {
  // const color = Math.floor(Math.random() * 16777215).toString(16); // generate a random color
  const firstLetter = username.charAt(0).toUpperCase(); // get the first letter of the username
  return `https://via.placeholder.com/150/000000/FFFFFF?text=${firstLetter}`; // return the URL of the image
}

export default generateProfilePicture;
