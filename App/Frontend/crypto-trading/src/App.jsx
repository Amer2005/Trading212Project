import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Header from './Header.jsx'
import Footer from './Footer.jsx'
import TopCrypto from './TopCrypto/TopCrypto.jsx'
import Login from './Login/Login.jsx'

function App() {



  return (
    <>
      <Header isLoggedIn={false} />

      <Router>
        <Routes>
          <Route path="*" element={
            <>
              <TopCrypto />
            </>
          } />

          <Route path="/login" element={
            <>
              <Login/>
            </>
          } />
        </Routes>
      </Router>

      <Footer />
    </>

  );

}

export default App
