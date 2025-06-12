import { useState } from 'react';
import { login } from '../services/authService'
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useNavigate } from 'react-router-dom';

function Login(props) {
  const [username, setusername] = useState('');
  const [password, setPassword] = useState('');

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!username || !password) {
      toast.error('Please enter both username and password.');
      return;
    }

    try {
      const result = await login(username, password);

      if (result.status === true) {
        toast.success('Login successful!');

        await props.fetchUser();

        navigate('/');
      } else {
        toast.error(result.errorMessage || 'Login failed. Please try again.');
      }
    } catch (err) {
      toast.error('An unexpected error occurred. Please try again later.');
    }
  };

  return (
    <div className="login-container">
      <div className="login-box">
        <h2>Login</h2>
        <form onSubmit={handleSubmit} className="login-form">
          <div>
            <label htmlFor="username">Username</label>
            <input
              id="username"
              type="text"
              value={username}
              onChange={(e) => setusername(e.target.value)}
            />
          </div>

          <div>
            <label htmlFor="password">Password</label>
            <input
              id="password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>

          <button type="submit" className="login-button">
            Log In
          </button>

          <ToastContainer />
        </form>
      </div>
    </div>
  );
}

export default Login;