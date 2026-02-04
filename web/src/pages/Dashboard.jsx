import { useNavigate } from "react-router-dom";
import { logout, isAuthenticated } from "../services/authService.js";
import { useEffect } from "react";

function Dashboard() {
  const navigate = useNavigate();

  useEffect(() => {
    if (!isAuthenticated()) {
      navigate("/");
    }
  }, [navigate]);

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  const goToProfile = () => {
    navigate("/profile"); // make sure you have a Profile.jsx route
  };

  return (
    <div className="dashboard">
      <h2>Welcome, John Doe!</h2>
      <p style={{ textAlign: "center", color: "#555", marginBottom: "20px" }}>
        Here's a quick overview of your account.
      </p>

      <div className="dashboard-cards">
        <div className="card-small">
          <h3>Username</h3>
          <p>johndoe</p>
        </div>

        <div className="card-small">
          <h3>Email</h3>
          <p>johndoe@email.com</p>
        </div>
      </div>

        <button className="logout" onClick={handleLogout}>
          Logout
        </button>
      </div>
  );
}

export default Dashboard;
