import Header from './Header.jsx'
import Footer from './Footer.jsx'
import TopCrypto from './TopCrypto/TopCrypto.jsx'

function App() {

  return (
    <>
      <Header isLoggedIn={true} balance={10000}/>
      <TopCrypto/>
      <Footer/>
    </>
  );

}

export default App
