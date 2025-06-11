import { useEffect, useState } from "react";
import { connectKrakenSocket } from "./krakenSocket";
import { getAssetNames } from '../services/krakenRest';

const defaultPairs = [
  "XBT/USD",    
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
    const [cryptoData, setCryptoData] = useState({});

    useEffect(() => {
        const ws = connectKrakenSocket(pairs, (data) => {
            const [, ticker, , pair] = data;
            const price = ticker.c[0]; // Last trade price

            setCryptoData(prev => ({
                ...prev,
                [pair]: price
            }));
        });

        return () => {
            ws.close();
        };
    }, [pairs]);

    return cryptoData;
}