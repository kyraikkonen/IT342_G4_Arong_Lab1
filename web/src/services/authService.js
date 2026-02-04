const API_URL = "http://localhost:8080/auth"; // Spring Boot backend

// Login
export const login = async (data) => {
  try {
    const response = await fetch(`${API_URL}/login`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data), // { username, password } or { email, password }
    });

    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || "Login failed");
    }

    const user = await response.json();

    // You can store user info or a JWT if your backend provides one
    localStorage.setItem("user", JSON.stringify(user));

    return user;
  } catch (error) {
    throw new Error(error.message);
  }
};

// Register
export const register = async (data) => {
  try {
    const response = await fetch(`${API_URL}/register`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data), // { username, email, password }
    });

    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || "Registration failed");
    }

    const user = await response.json();

    // Optionally log in immediately after register
    localStorage.setItem("user", JSON.stringify(user));

    return user;
  } catch (error) {
    throw new Error(error.message);
  }
};

// Logout
export const logout = () => {
  localStorage.removeItem("user");
};

// Check if authenticated
export const isAuthenticated = () => {
  return !!localStorage.getItem("user");
};
