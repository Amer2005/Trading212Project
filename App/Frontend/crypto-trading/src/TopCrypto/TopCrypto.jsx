import styles from './TopCrypto.module.css'
import { symbolNames } from '../config'
import { Link } from 'react-router-dom'

function TopCrypto(props) {
    const cryptoData = props.cryptoData;

    const cryptoList = Object.keys(cryptoData).map((symbolKey) => {
        const symbol = symbolKey.split("/")[0];
        const name = symbolNames[symbol] || symbol;
        const price = cryptoData[symbolKey];

        const buypage = "/Transaction?type=BUY&symbol=" + symbolKey;
        const sellpage = "/Transaction?type=SELL&symbol=" + symbolKey;

        return (<>
            <tr key={symbol}>
                <td>{name}</td>
                <td>{symbol}</td>
                <td>${price}</td>
                <td>
                    <Link className={styles.buybtn} to={buypage}>Buy</Link>
                    <Link className={styles.sellbtn} to={sellpage}>Sell</Link>
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