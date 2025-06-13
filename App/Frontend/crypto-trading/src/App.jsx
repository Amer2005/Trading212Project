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
import Holdings from './Holdings/Holdings.jsx'
import ViewTransactions from './Transaction/ViewTransactions.jsx'
import { getCookie } from './services/cookieService.js';
import { API_BASE_URL } from './config.js'


function App() {
  const cryptoData = useKrakenWS();
  const [user, setUser] = useState(null);
  const [loggedIn, setloggedIn] = useState(false);

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

            <Route path="/login" element={<Login fetchUser={fetchUser} />} />

            <Route path="/register" element={<Register fetchUser={fetchUser} />} />

            <Route path="/holdings" element={<Holdings isLoggedIn={loggedIn} cryptoData={cryptoData} />} />
            
            <Route path="/transactions" element={<ViewTransactions isLoggedIn={loggedIn} cryptoData={cryptoData} />} />
            
            <Route path="/transaction" element={<Transaction
              cryptoData={cryptoData} isLoggedIn={loggedIn} user={user} fetchUser={fetchUser} />} />

            <Route path="/profile" element={<Profile isLoggedIn={loggedIn} user={user} fetchUser={fetchUser} />} />

            <Route path="*" element={<div>404 Not Found</div>} />

          </Route>
        </Routes>
      </BrowserRouter>

      <Footer />
    </>

  );

}

export default App
