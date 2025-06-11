import styles from './TopCrypto.module.css'
import useKrakenWS from '../hooks/useKrakenWS'

function TopCrypto(props) {

    const symbolNames = {
        XBT: "Bitcoin",
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

    const sortedData = Object.entries(cryptoData).sort(([, priceA], [, priceB]) => {
        return parseFloat(priceB) - parseFloat(priceA);
    });

    const cryptosList = sortedData.map(([pair, price]) => {
        const symbol = pair.split("/")[0];
        const name = symbolNames[symbol] || symbol;

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
    }
    )

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
                    {cryptosList}
                </tbody>
            </table>
        </>
    );
}

export default TopCrypto