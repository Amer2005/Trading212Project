import {API_BASE_URL} from "../config.js"

function Profile(props) {

  if (!props.isLoggedIn) {
    return;
  }

  let userData = props.user;

  const resetProfile = async () => {
    console.log("resseting");
    const res = await fetch(API_BASE_URL + '/reset', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify({ "session": userData.session }),
    });

    props.fetchUser();

    return;
  };

  return (
    <div className="profile-container">
      <h1>User Profile</h1>

      <div className="profile-details">
        <div className="profile-field">
          <span className="field-label">User ID:</span>
          <span className="field-value">{userData.id}</span>
        </div>

        <div className="profile-field">
          <span className="field-label">Username:</span>
          <span className="field-value">{userData.username}</span>
        </div>

        <div className="profile-field">
          <span className="field-label">Balance:</span>
          <span className="field-value">{userData.balance}$</span>
        </div>
      </div>

      <button 
        onClick={resetProfile}
        className="reset-button"
      >
        Reset Profile
      </button>
    </div>);
}

export default Profile;