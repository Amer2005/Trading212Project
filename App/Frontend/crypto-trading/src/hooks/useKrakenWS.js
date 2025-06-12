import { useEffect, useState } from "react";
import { connectKrakenSocket } from "./krakenSocket";
const defaultPairs = [
    "BTC/USD",
    "ETH/USD",
    "USDT/USD",
    "XRP/USD",
    "BNB/USD",
    "SOL/USD",
    "USDC/USD",
    "DOGE/USD",
    "TRX/USD",
    "ADA/USD",
    "STETH/USD",
    "HYPE/USD",
    "WBTC/USD",
    "WSTETH/USD",
    "SUI/USD",
    "LINK/USD",
    "AVAX/USD",
    "XLM/USD",
    "BCH/USD"];

export default function useKrakenWS(pairs = defaultPairs) {
    const [cryptoData, setCryptoData] = useState([]);

    function onTickerEvent(data) {
        const cryptoSymbol = data.symbol;
        const cryptoPrice = data.ask;

        setCryptoData(prev => (
            {
                ...prev, 
                [cryptoSymbol]: cryptoPrice
            }
        ));
    }

    useEffect(() => {
        const ws = connectKrakenSocket(pairs, onTickerEvent);

        return () => {
            ws.close();
        };
    }, [pairs]);


    return cryptoData;
}