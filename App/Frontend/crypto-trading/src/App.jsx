import { BrowserRouter, Routes, Route, useLocation} from 'react-router-dom';
import React, { useEffect } from 'react';
import useKrakenWS from './hooks/useKrakenWS'
import Layout from './Layout.jsx'
import Footer from './Footer.jsx'
import TopCrypto from './TopCrypto/TopCrypto.jsx'
import Login from './Login/Login.jsx'
import Register from './Register/Register.jsx'

function App() {
  const cryptoData = useKrakenWS();

  return (
    <>

      <BrowserRouter>
        <Routes>
          <Route element={<Layout isLoggedIn={false} />}>

            <Route path="/" element={<TopCrypto cryptoData={cryptoData} />} />

            <Route path="/login" element={<Login />} />
            <Route path="/Register" element={<Register />} />

            <Route path="*" element={<div>404 Not Found</div>} />

          </Route>
        </Routes>
      </BrowserRouter>

      <Footer />
    </>

  );

}

export default App
