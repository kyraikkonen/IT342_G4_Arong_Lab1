import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { login } from "../services/authService.js";

function Login() {
  const [identifier, setIdentifier] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    if (!identifier || !password) {
      setError("All fields are required");
      return;
    }

    try {
      await login({ identifier, password });
      navigate("/dashboard");
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="card">
      <h2>Login</h2>

      {error && <div className="error">{error}</div>}

      <input
        placeholder="Username or Email"
        value={identifier}
        onChange={(e) => setIdentifier(e.target.value)}
      />

      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />

      <button onClick={handleLogin}>Login</button>

      <div className="link">
        <Link to="/register">Create an account</Link>
      </div>
    </div>
  );
}

export default Login;
