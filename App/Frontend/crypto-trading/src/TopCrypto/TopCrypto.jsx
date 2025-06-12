import styles from './TopCrypto.module.css'
import useKrakenWS from '../hooks/useKrakenWS'
import { symbolNames } from '../config'

function TopCrypto(props) {
    const cryptoData = props.cryptoData;

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