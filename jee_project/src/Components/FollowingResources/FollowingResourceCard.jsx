import generateProfilePicture from "../../api/generateProfilePicture";
import { initializeAxios } from "../../api/springApi";

const FollowingResourceCard = ({ resource, myUserData, following }) => {
  const downloadHandler = () => {
    const { resourceService, metadataService } = initializeAxios();

    metadataService
      .get(`/users/get/id/${resource.resourceHolderId}`)
      .then((res) => {
        const linkedMail = res.data.email;

        resourceService
          .get(
            `/resource/download?resourceId=${resource.resourceBucketId}&userMail=${linkedMail}&downloadName=${resource.resourceName}`,
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
      });
  };

  return (
    <div className="home-page-resources" key={resource.resourceId}>
      <div className="resource-user-profile">
        <img
          src={generateProfilePicture(
            resource.resourceHolderId === myUserData.userId
              ? myUserData.firstName + myUserData.lastName
              : following
                  .filter((el) => el.userId === resource.resourceHolderId)
                  .map((el) => {
                    return el.username;
                  })[0]
          )}
        />
        <span>
          {resource.resourceHolderId === myUserData.userId
            ? myUserData.firstName + myUserData.lastName
            : following
                .filter(
                  (el) =>
                    el.userId === resource.resourceHolderId ||
                    el.userId === myUserData.userId
                )
                .map((el) => el.firstName + el.lastName)}
        </span>
      </div>
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

export default FollowingResourceCard;
