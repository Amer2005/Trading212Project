import React, { useEffect, useState } from 'react'
import { symbolNames } from '../config.js'
import { getCookie } from '../services/cookieService.js'
import { API_BASE_URL } from '../config.js'

function ViewTransactions(props) {
  if (props.isLoggedIn === false) {
    return;
  }

  const cryptoData = props.cryptoData;

  const [transactions, setTransactions] = useState({ transactions: [{ id: "none" }] });

  const fetchTransactions = async () => {
    try {
      const sessionId = getCookie('SESSIONID');

      if (!sessionId) {
        return;
      }

      const response = await fetch(API_BASE_URL + '/user/transactions', {
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
        const transactionssData = await response.json();

        setTransactions(transactionssData);
      }
      else {
        return;
      }
    } catch (error) {
      console.log(error);
    }
  };


  useEffect(() => {
    fetchTransactions();
  }, []);

  transactions.transactions.sort((a, b) => new Date(b.transactionTime) - new Date(a.transactionTime));

  return (<>
    <table>
      <thead>
        <tr>
          <th>Name</th>
          <th>Symbol</th>
          <th>Type</th>
          <th>Amount</th>
          <th>Transaction Price</th>
          <th>Total</th>
          <th>Profit</th>
          <th>Transaction Time (UTC)</th>
        </tr>
      </thead>
      <tbody>
        {transactions.transactions.map((transaction) => {
          const symbol = transaction.symbol;
          const name = symbolNames[symbol] || symbol;
          const type = transaction.type;
          const price = transaction.price;
          const total = transaction.total;

          const priceRaw = cryptoData?.[symbol + '/USD']?.price || 'N/A';

          if (priceRaw == 'N/A') {
            return (
              <tr key={transaction.id}>
                <td>{name}</td>
                <td>{symbol}</td>
                {
                  type === 'BUY' ?
                    <td className='buy-text'>{type}</td> :
                    <td className='sell-text'>{type}</td>
                }
                <td>{transaction.amount}</td>
                <td>{price}$</td>
                <td>{total}$</td>
                <td></td>
                <td>{transaction.transactionTime}</td>
              </tr>
            );
          }

          const currentPrice = priceRaw.toFixed(4);
          const currentTotal = (priceRaw * transaction.amount);
          let profit = (total - currentTotal).toFixed(4);

          if(type == 'BUY')
          {
              profit = -profit;
          }
          
          return (
              <tr key={transaction.id}>
                <td>{name}</td>
                <td>{symbol}</td>
                <td>{type}</td>
                <td>{transaction.amount}</td>
                <td>{price}$</td>
                <td>{total}$</td>
                {
                  profit >= 0 ?
                    <td className='green-text'>{profit}</td> :
                    <td className='red-text'>{profit}</td>
                }
                <td>{transaction.transactionTime}</td>
              </tr>
            );

        })}
      </tbody>
    </table>
  </>)
}

export default ViewTransactions