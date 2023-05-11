import { useEffect, useState } from "react";
import "../LoginPage/Login";
import { Link, useNavigate } from "react-router-dom";
import verifyJwtToken from "../../auth/verifyJwtToken";
import {
  getMailFromCookie,
  getUserIdFromCookie,
  initializeAxios,
} from "../../api/springApi";

const AddResource = () => {
  const navigate = useNavigate();
  useEffect(() => {
    verifyJwtToken(document.cookie).then((response) => {
      if (!response) {
        navigate("/login");
      }
    });
  }, []);

  const [myUserData, setMyUserData] = useState({});
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [file, setFile] = useState("");
  const [visibility, setVisibility] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(file);
    if (description === "" || file === "" || visibility === "") {
      alert("Please fill all the fields");
      return;
    }

    const { resourceService } = initializeAxios();
    // send file to backend
    const formdata = new FormData();
    formdata.append("file", file);
    resourceService
      .post(
        `/resource/upload?userMail=${getMailFromCookie()}&resourceDescription=${description}&resourceVisibility=${visibility}`,
        formdata,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      )
      .then((res) => {
        if (res.status === 200) {
          alert("Resource added successfully");
          navigate("/userPage");
        }
      });
  };

  useEffect(() => {
    const { metadataService } = initializeAxios();
    const userId = getUserIdFromCookie();
    metadataService.get(`/users/get/id/${userId}`).then((res) => {
      if (res.data != null) setMyUserData(res.data);
    });
  }, []);

  return (
    <div className="login-page">
      <div className="login-card-container">
        <div className="login-card">
          <div className="login-card-header">
            <h1>Add Resource</h1>
          </div>

          <form className="login-card-form" onSubmit={handleSubmit}>
            {/* <div id="form-item">
              <input
                type="text"
                placeholder="Resource Title"
                className="form-input"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
              />
            </div> */}
            <div id="form-item">
              <input
                type="text"
                placeholder="Resource Description"
                className="form-input"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
              />
            </div>
            <div id="form-item">
              <select
                value={visibility}
                onChange={(e) => setVisibility(e.target.value)}
              >
                <option value="" defaultChecked>
                  select an option..
                </option>
                <option value="PUBLIC">Public</option>
                <option value="PRIVATE">Private</option>
              </select>
            </div>
            <div id="form-item">
              <label htmlFor="file" className="form-input-file">
                Choose file
              </label>
              <input
                id="file"
                className="form-input"
                type="file"
                onChange={(e) => setFile(e.target.files[0])}
              />
            </div>
            <button type="submit" className="login-btn">
              Add
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};
export default AddResource;
