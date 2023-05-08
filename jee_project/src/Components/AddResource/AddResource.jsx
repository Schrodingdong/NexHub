
import { useEffect } from "react";
import "../LoginPage/Login";


import { Link, useNavigate } from "react-router-dom";
import verifyJwtToken from "../../auth/verifyJwtToken";

const AddResource = () => {

  const navigate = useNavigate();
  useEffect(() => {
    verifyJwtToken(document.cookie).then((response) => {
      if (!response) {
        navigate("/login");
      }
    });
  }, []);


  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [file, setFile] = useState('');
  const [visibility,setVisibility] = useState('')



  const handleSubmit = async (e) => {
    e.preventDefault();

    const data = {
      resourceName: title,
      resourceDescription: description,
      resourceBucketId: file,
      resourceVisibility : visibility
    };

    try {
      // Make a POST request to the API to add the resource

      const res = await axios.post(`http://localhost:8080/metadata-db-manager-service/res/add/${userId}`, data);

      // Redirect the user to their user page after the resource has been added
      navigate('/userpage');
    } catch (err) {
      console.error(err);
    }
  };


  return (
    <div className="login-page">
      <div className="login-card-container">
        <div className="login-card">
          <div className="login-card-header">
            <h1>Add Resource</h1>
          </div>

          <form className="login-card-form">
            <div id="form-item">

              <input
                type="text"
                placeholder="Resource Title"
                className="form-input"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
              />
            </div>
            <div id='form-item'>
              <input
                type="text"
                placeholder="Resource Description"
                className="form-input"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
              />
            </div>
            <div id='form-item'>
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
            <button type="submit" className='login-btn'>Add</button>
          </form>
        </div>
      </div>
    </div>
  );
};
export default AddResource;
