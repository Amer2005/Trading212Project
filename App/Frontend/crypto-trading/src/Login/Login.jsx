import { useState } from 'react';
import './Login.css';
import { login } from '../services/loginService'

function Login() {
  const [username, setusername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!username || !password) {
      setError('Please enter both username and password.');
      return;
    }

    const result = login(username, password);

    console.log('Logging in with:', result);
    setError('');
  };

  return (
    <div className="login-container">
      <div className="login-box">
        <h2>Login</h2>

        {error && <div className="login-error">{error}</div>}

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
        </form>
      </div>
    </div>
  );
}

export default Login;