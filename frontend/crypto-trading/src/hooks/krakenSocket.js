export function connectKrakenSocket(pairs, onTickerEventMethod) {
    const ws = new WebSocket("wss://ws.kraken.com/v2");

    ws.onopen = () => {
        ws.send(JSON.stringify({
            method: "subscribe",
            params: {
                channel: "ticker",
                symbol: pairs
            }
        }));
    };

    ws.onmessage = (event) => {
        const data = JSON.parse(event.data);

        if(data.channel === "ticker") {
            onTickerEventMethod(data.data[0]);
        }
    };

    return ws;
}