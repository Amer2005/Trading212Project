import { useEffect, useState } from "react";
import { connectKrakenSocket } from "./krakenSocket";
import { defaultPairs } from "../config.js";

export default function useKrakenWS(pairs = defaultPairs) {
    const [cryptoData, setCryptoData] = useState([]);

    function onTickerEvent(data) {
        const cryptoSymbol = data.symbol;
        const cryptoPrice = data.ask;
        const cryptoVolume = data.volume;

        setCryptoData(prev => {


            const prevPrice = prev[cryptoSymbol]?.price ?? null;

            const updated = {
                ...prev,
                [cryptoSymbol]: {
                    price: cryptoPrice,
                    lastPrice: prevPrice,
                    volume: cryptoVolume
                }
            };

            const sortedData = Object.entries(updated)
                .sort((a, b) => -(b[1].volume - a[1].volume)) // Compare volumes
                .reduce((acc, [key, value]) => {
                    acc[key] = value;
                    return acc;
                }, {});

            return sortedData;
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