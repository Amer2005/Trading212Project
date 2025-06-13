import React, { useEffect, useState } from 'react'
import { symbolNames } from '../config'
import { getCookie } from '../services/cookieService.js'
import { API_BASE_URL } from '../config.js'

function Holdings(props) {
  if (props.isLoggedIn === false) {
    return;
  }

  const cryptoData = props.cryptoData;

  const [holdings, setHoldings] = useState({ holdings: [{ id: "none" }] });

  const fetchHoldings = async () => {
    try {
      const sessionId = getCookie('SESSIONID');

      if (!sessionId) {
        return;
      }

      const response = await fetch(API_BASE_URL + '/user/holdings', {
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
        const holdingsData = await response.json();

        setHoldings(holdingsData);
      }
      else {
        return;
      }
    } catch (error) {
      console.log(error);
    }
  };


  useEffect(() => {
    fetchHoldings();
  }, []);

  return (<>
    <table>
      <thead>
        <tr>
          <th>Name</th>
          <th>Symbol</th>
          <th>Amount</th>
          <th>Price</th>
          <th>Total</th>
        </tr>
      </thead>
      <tbody>
        {holdings.holdings.map((holding) => {
          const symbol = holding.symbol;
          const name = symbolNames[symbol] || symbol;
          const priceRaw = cryptoData?.[symbol + '/USD']?.price || 'N/A';
          const price = priceRaw === 'N/A' ? 0 : priceRaw.toFixed(3);
          const total = priceRaw === 'N/A' ? 0 : (priceRaw * holding.amount).toFixed(3);
          return (
            <tr key={holding.id}>
              <td>{name}</td>
              <td>{symbol}</td>
              <td>{holding.amount}</td>
              <td>{price}$</td>
              <td>{total}$</td>
            </tr>
          );
        })}
      </tbody>
    </table>
  </>)
}

export default Holdings