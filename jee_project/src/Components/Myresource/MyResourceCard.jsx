import { getMailFromCookie, initializeAxios } from "../../api/springApi";

const MyResourceCard = ({ resource }) => {
  const downloadHandler = () => {
    const { resourceService } = initializeAxios();
    const email = getMailFromCookie();
    resourceService
      .get(
        `/resource/download?resourceId=${resource.resourceBucketId}&userMail=${email}&downloadName=${resource.resourceName}`,
        { responseType: "blob" }
      )
      .then((res) => {
        const url = window.URL.createObjectURL(new Blob([res.data]));
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", resource.resourceName);
        document.body.appendChild(link);
        link.click();

        document.body.removeChild(link);
        URL.revokeObjectURL(url);
      });
  };

  return (
    <div className="home-page-resources" key={resource.resourceId}>
      <div className="resource-user-info">
        <h4>{resource.resourceName}</h4>
        <p>{resource.resourceDescription}</p>
        <button className="download-button" onClick={downloadHandler}>
          Download File
        </button>
      </div>
    </div>
  );
};

export default MyResourceCard;
