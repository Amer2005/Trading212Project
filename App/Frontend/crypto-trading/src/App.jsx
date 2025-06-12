import { BrowserRouter, Routes, Route } from 'react-router-dom'
import React, { useEffect, useState } from 'react';
import useKrakenWS from './hooks/useKrakenWS'
import Layout from './Layout.jsx'
import Footer from './Footer.jsx'
import TopCrypto from './TopCrypto/TopCrypto.jsx'
import Login from './Login/Login.jsx'
import Register from './Register/Register.jsx'
import Profile from './Profile/Profile.jsx';
import Transaction from './Transaction/Transaction.jsx'

import { API_BASE_URL } from './config.js'


function App() {
  const cryptoData = useKrakenWS();
  const [user, setUser] = useState(null);
  const [loggedIn, setloggedIn] = useState(false);

  const getCookie = (name) => {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
  };

  const fetchUser = async () => {
    try {
      

      const sessionId = getCookie('SESSIONID');
      if (!sessionId) {
        setUser(null);
        setloggedIn(false);
        return;
      }
      const response = await fetch(API_BASE_URL + '/user/get', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json'
        },
        body: JSON.stringify({
          session: sessionId
        }),
        credentials: 'include'
      });

      if (response.ok) {
        const userData = await response.json();

        setUser(userData);
        setloggedIn(true);
      }
      else {
        setUser(null);
        setloggedIn(false);
      }
    } catch (error) {
    }
  };

  useEffect(() => {
    fetchUser();
  }, []);

  return (
    <>

      <BrowserRouter>
        <Routes>
          <Route element={<Layout isLoggedIn={loggedIn} user={user} />}>

            <Route path="/" element={<TopCrypto isLoggedIn={loggedIn} cryptoData={cryptoData} />} />

            <Route path="/login" element={<Login fetchUser={fetchUser}/>} />
            
            <Route path="/Register" element={<Login fetchUser={fetchUser}/>} />

            <Route path="/Transaction" element={<Transaction 
            cryptoData={cryptoData} isLoggedIn={loggedIn} user={user} fetchUser={fetchUser}/>} />

            <Route path="/Profile" element={<Profile isLoggedIn={loggedIn} user={user} fetchUser={fetchUser}/>} />

            <Route path="*" element={<div>404 Not Found</div>} />

          </Route>
        </Routes>
      </BrowserRouter>

      <Footer />
    </>

  );

}

export default App
