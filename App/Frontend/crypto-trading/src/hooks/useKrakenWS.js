import { useEffect, useState } from "react";
import { connectKrakenSocket } from "./krakenSocket";
import { defaultPairs } from "../config.js";

export default function useKrakenWS(pairs = defaultPairs) {
    const [cryptoData, setCryptoData] = useState([]);

    function onTickerEvent(data) {
        const cryptoSymbol = data.symbol;
        const cryptoPrice = data.ask;

        setCryptoData(prev => {
            const prevPrice = prev[cryptoSymbol]?.price ?? null;

            return {
                ...prev,
                [cryptoSymbol]: {
                    price: cryptoPrice,
                    lastPrice: prevPrice
                }
            };
        });
    }

    useEffect(() => {
        const ws = connectKrakenSocket(pairs, onTickerEvent);

        return () => {
            ws.close();
        };
    }, [pairs]);


    return cryptoData;
}