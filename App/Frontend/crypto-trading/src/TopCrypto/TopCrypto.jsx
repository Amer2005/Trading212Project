import styles from './TopCrypto.module.css'
import { symbolNames } from '../config'
import { Link } from 'react-router-dom'

function reset_animation(elementName, elementIdInner, change) {
    var el = document.getElementById(elementName);

    if (el === null)
        return;

    var lastChange = document.getElementById(elementIdInner);

    if(lastChange != null && lastChange.innerHTML == change)
    {
        return;
    }

    el.style.animation = 'none';
    el.offsetHeight;
    el.style.animation = null;
}

function TopCrypto(props) {
    const cryptoData = props.cryptoData;

    const cryptoList = Object.keys(cryptoData).map((symbolKey) => {
        const symbol = symbolKey.split("/")[0];
        const name = symbolNames[symbol] || symbol;
        const price = cryptoData[symbolKey].price;
        const lastPrice = cryptoData[symbolKey].lastPrice;
        const isProfit = price >= lastPrice;
        const change = (price - lastPrice).toFixed(4);
        const elementId = symbol + 'id';
        const elementIdInner = symbol + 'innerId';

        const buypage = "/transaction?type=BUY&symbol=" + symbolKey;
        const sellpage = "/transaction?type=SELL&symbol=" + symbolKey;

        reset_animation(elementId, elementIdInner, change);

        if (change == 0 || change == price.toFixed(4)) {

            return (<>
                <tr key={symbol}>
                    <td>{name}</td>
                    <td>{symbol}</td>
                        <td id={elementId}>{price}$
                        </td>

                    {props.isLoggedIn ?
                        <td>
                            <Link className={styles.buybtn} to={buypage}>Buy</Link>
                            <Link className={styles.sellbtn} to={sellpage}>Sell</Link>
                        </td>
                        :
                        <></>}
                </tr>
            </>)
        }

        return (<>
            <tr key={symbol}>
                <td>{name}</td>
                <td>{symbol}</td>
                {isProfit ?
                    <td id={elementId} className={styles.profit}>{price}$
                        <span className={styles.change}>
                            <span hidden id={elementIdInner}>{change}</span></span>
                    </td>
                    :
                    <td id={elementId} className={styles.loss}>{price}$
                        <span className={styles.change}>
                            <span hidden id={elementIdInner}>{change}</span></span>
                    </td>}

                {props.isLoggedIn ?
                    <td>
                        <Link className={styles.buybtn} to={buypage}>Buy</Link>
                        <Link className={styles.sellbtn} to={sellpage}>Sell</Link>
                    </td>
                    :
                    <></>}
            </tr>
        </>)
    })


    return (
        <>
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Symbol</th>
                        <th className='price-header'>Price</th>
                        {props.isLoggedIn ? <th>Actions</th> : ''}
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