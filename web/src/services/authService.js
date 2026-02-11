const API_URL = "http://localhost:8080";

// Get token from localStorage
const getToken = () => {
  return localStorage.getItem("token");
};

// Register
export const register = async (data) => {
  try {
    const response = await fetch(`${API_URL}/auth/register`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || "Registration failed");
    }

    const user = await response.json();
    return user;
  } catch (error) {
    throw new Error(error.message);
  }
};

// Login
// Login - Try both email and username
export const login = async (data) => {
  try {
    const loginData = {
      username: data.email,  // Using email as username
      password: data.password
    };

    console.log("Sending login data:", JSON.stringify(loginData, null, 2));

    const response = await fetch(`${API_URL}/auth/login`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(loginData),
    });

    const responseText = await response.text();
    console.log("Raw response:", responseText);

    if (!response.ok) {
      const errorData = JSON.parse(responseText);
      console.log("Error response:", errorData);
      throw new Error(errorData.message || "Login failed");
    }

    const responseData = JSON.parse(responseText);
    
    localStorage.setItem("token", responseData.token);
    localStorage.setItem("user", JSON.stringify(responseData.user));

    return responseData;
  } catch (error) {
    console.error("Login error details:", error);
    throw new Error(error.message);
  }
};

// Get current user from /me endpoint
export const getCurrentUserFromAPI = async () => {
  try {
    const token = getToken();
    
    if (!token) {
      throw new Error("No token found");
    }

    const response = await fetch(`${API_URL}/auth/me`, {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    });

    if (!response.ok) {
      throw new Error("Failed to fetch user data");
    }

    const user = await response.json();
    
    // Update stored user info
    localStorage.setItem("user", JSON.stringify(user));
    
    return user;
  } catch (error) {
    // If token is invalid, clear everything
    logout();
    throw new Error(error.message);
  }
};

// Logout
export const logout = () => {
  localStorage.removeItem("token");
  localStorage.removeItem("user");
};

// Check if user is authenticated
export const isAuthenticated = () => {
  const token = getToken();
  return token !== null;
};

// Get current user from localStorage
export const getCurrentUser = () => {
  const user = localStorage.getItem("user");
  return user ? JSON.parse(user) : null;
};