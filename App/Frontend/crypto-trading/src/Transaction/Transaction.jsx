function Transaction(props) {
    return (<div className="crypto-transaction-wrapper">
        <h2 className="crypto-transaction-title">Buy/Sell Crypto</h2>

        <form className="crypto-transaction-form">
            <div className="crypto-transaction-type-group">
                <span className="crypto-transaction-label">Transaction Type</span>
                <div className="crypto-transaction-toggle-container">
                    <button type="button" className="crypto-transaction-toggle-btn crypto-transaction-toggle-active">Buy</button>
                    <button type="button" className="crypto-transaction-toggle-btn">Sell</button>
                </div>
            </div>

            <div className="crypto-transaction-input-group">
                <label htmlFor="crypto-symbol" className="crypto-transaction-label">Cryptocurrency</label>
                <select id="crypto-symbol" className="crypto-transaction-select">
                    <option value="">Select a coin</option>
                    <option value="BTC">Bitcoin (BTC)</option>
                    <option value="ETH">Ethereum (ETH)</option>
                    <option value="SOL">Solana (SOL)</option>
                    <option value="ADA">Cardano (ADA)</option>
                </select>
            </div>

            <div className="crypto-transaction-input-group">
                <label htmlFor="crypto-amount" className="crypto-transaction-label">Amount</label>
                <input
                    type="number"
                    id="crypto-amount"
                    className="crypto-transaction-input"
                    placeholder="0.00"
                    step="0.000001"
                />
            </div>

            <button type="submit" className="crypto-transaction-submit">
                Confirm Transaction
            </button>
        </form>
    </div>
    );
}

export default Transaction