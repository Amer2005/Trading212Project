import { symbolNames } from '../config'
import { useState } from 'react';
import { ToastContainer, toast } from 'react-toastify';
import { API_BASE_URL } from "../config.js"
import { useLocation } from 'react-router-dom';


function Transaction(props) {

    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const transactionType = searchParams.get('type');
    const defaultSymbol = searchParams.get('symbol');

    if (!props.isLoggedIn) {
        return;
    }

    let userData = props.user;
    const cryptoData = props.cryptoData;
    let defautlPrice = cryptoData[defaultSymbol];

    const [selectedType, setSelectedType] = useState(transactionType)
    const [selectedCrypto, setSelectedCrypto] = useState(defaultSymbol);
    const [selectedCryptoPrice, setSelectedCryptoPrice] = useState(defautlPrice);
    const [amount, setAmount] = useState(0);
    const [total, setTotal] = useState(0);


    const cryptoList = Object.keys(cryptoData).map((symbolKey) => {
        const symbol = symbolKey.split("/")[0];
        const name = symbolNames[symbol] || symbol;
        const price = cryptoData[symbolKey];

        return (<>
            <option value={symbolKey}>{name}: {price}$</option>
        </>);
    });

    function handleSelectTypeChange(e) {
        let value = e.target.value;

        setSelectedType(value);
    }

    function handleSelectChange(e) {
        let value = e.target.value;

        if (cryptoData[value] === null) {
            return;
        }

        let price = cryptoData[value];

        setSelectedCryptoPrice(price);
        setSelectedCrypto(value);
    }

    function handleAmountChange(e) {
        let value = e.target.value;

        if(value < 0)
        {
            value = 0;
        }

        setAmount(value);
        setTotal(selectedCryptoPrice * value);
    }

    const createTransaction = async () => {
        try {
            const sentData = {
                "userSession": userData.session,
                "type": selectedType,
                "symbol": selectedCrypto.split("/")[0],
                "amount": amount
            };

            const result = await fetch(API_BASE_URL + '/buy', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
                body: JSON.stringify(sentData),
            });

            const resultData = await result.json();

            if (resultData.status === true) {
                toast.success('Transaction done!');

                await props.fetchUser();
            } else {
                toast.error(resultData.errorMessage || 'Transaction failed');
            }
        } catch (err) {
            toast.error('An unexpected error occurred. Please try again later.');
        }


        return;
    };

    return (<div className="crypto-transaction-wrapper">
        <h2 className="crypto-transaction-title">Buy/Sell Crypto</h2>

        <div className="crypto-transaction-form">
            <div className="crypto-transaction-type-group">
                <span className="crypto-transaction-label">Transaction Type</span>



                <div className="crypto-transaction-toggle-container">
                    <select value={selectedType} onChange={handleSelectTypeChange} id="crypto-symbol" className="crypto-transaction-select">
                        <option value="BUY">Buy</option>
                        <option value="SELL">Sell</option>
                    </select>
                </div>
            </div>

            <div className="crypto-transaction-input-group">
                <label htmlFor="crypto-symbol" className="crypto-transaction-label">Cryptocurrency</label>
                <select
                    value={selectedCrypto}
                    id="crypto-symbol"
                    className="crypto-transaction-select"
                    onChange={handleSelectChange}
                >
                    {cryptoList}
                </select>
            </div>

            <div className="crypto-transaction-input-group">
                <p>Price: {selectedCryptoPrice}$</p>
            </div>

            <div className="crypto-transaction-input-group">
                <label htmlFor="crypto-amount" className="crypto-transaction-label">Amount</label>
                <input
                    value={amount}
                    type="number"
                    id="crypto-amount"
                    className="crypto-transaction-input"
                    placeholder="0.00"
                    onChange={handleAmountChange}
                />
            </div>

            <div className="crypto-transaction-input-group">
                <p>Total: {total}$</p>
            </div>

            <button onClick={createTransaction} className="crypto-transaction-submit">
                Confirm Transaction
            </button>

            <ToastContainer />
        </div>
    </div>
    );
}

export default Transaction