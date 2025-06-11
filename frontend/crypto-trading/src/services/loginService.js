import { API_BASE_URL } from '../config.js';

export async function login(username, password) {
  console.log("LOGGIN");

  const res = await fetch(`${API_BASE_URL}/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password }),
  });
  
  const result = await res.json();
  
  return result; 
}