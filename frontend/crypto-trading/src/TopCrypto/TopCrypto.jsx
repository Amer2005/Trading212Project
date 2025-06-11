import styles from './TopCrypto.module.css'
import useKrakenWS from '../hooks/useKrakenWS'

function TopCrypto(props) {

    const symbolNames = {
        BTC: "Bitcoin",
        ETH: "Ethereum",
        USDT: "Tether",
        XRP: "XRP",
        BNB: "BNB",
        SOL: "Solana",
        USDC: "USD Coin",
        DOGE: "Dogecoin",
        TRX: "TRON",
        ADA: "Cardano",
        STETH: "Lido Staked Ether",
        HYPE: "Hyperliquid",
        WBTC: "Wrapped Bitcoin",
        WSTETH: "Wrapped stETH",
        SUI: "Sui",
        LINK: "Chainlink",
        AVAX: "Avalanche",
        XLM: "Stellar",
        BCH: "Bitcoin Cash",
        LEO: "LEO Token"
    };

    const cryptoData = useKrakenWS();

    const cryptoList = Object.keys(cryptoData).map((symbolKey) => {
        const symbol = symbolKey.split("/")[0];
        const name = symbolNames[symbol] || symbol;
        const price = cryptoData[symbolKey];
        
        return (<>
            <tr key={symbol}>
                <td>{name}</td>
                <td>{symbol}</td>
                <td>${price}</td>
                <td>
                    <button className={styles.buybtn}>Buy</button>
                    <button className={styles.sellbtn}>Sell</button>
                </td>
            </tr>
        </>);
    })

    return (
        <>
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Symbol</th>
                        <th>Price</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {cryptoList}
                </tbody>
            </table>
        </>
    );
}

export default TopCrypto