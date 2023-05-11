import { useNavigate } from "react-router-dom";
import generateProfilePicture from "../../api/generateProfilePicture";

const SearchResult = ({ user }) => {
  const navigate = useNavigate();
  const goToProfile = () => {
    console.log("navigated");
    navigate(`/profilepage/${user.userId}`);
  };

  return (
    <div key={user.userId} className="users-search">
      <img src={generateProfilePicture(user.username)} alt={user.username} />
      <button onClick={goToProfile}>{user.username}</button>
    </div>
  );
};

export default SearchResult;
