export function connectKrakenSocket(pairs, onMessage) {
    const ws = new WebSocket("wss://ws.kraken.com");

    ws.onopen = () => {
        ws.send(JSON.stringify({
            event: "subscribe",
            pair: pairs,
            subscription: { name: "ticker" }
        }));
    };

    ws.onmessage = (event) => {
        const data = JSON.parse(event.data);
        if (Array.isArray(data) && data.length > 1) {
            onMessage(data);
        }
    };

    return ws;
}