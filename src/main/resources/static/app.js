let stompClient = null;
const formEl = document.querySelector(".form");
const connectEl = document.querySelector(".connect");
const disconnectEl = document.querySelector(".disconnect");

function setConnected(connected) {
    const betsDiv = document.querySelector(".bets");

    if (connected) {
        connectEl.disabled = true;
        disconnectEl.disabled = false;
        betsDiv.hidden = false;
    } else {
        disconnectEl.disabled = true;
        betsDiv.hidden = true;
    }
}

function connect() {
    const socket = new SockJS('/ws-endpoint');

    stompClient = Stomp.over(socket);
    stompClient.connect({}, frame => {
        setConnected(true);

        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/bets', bet => {
            createBet(JSON.parse(bet.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }

    setConnected(false);

    console.log("Disconnected");
}

function sendBet() {
    const userNumber = document.querySelector("#userNumber").value;
    const betAmount = document.querySelector("#betAmount").value;

    try {
        stompClient.send("/app/addBet", {}, JSON.stringify({"userNumber": userNumber, "betAmount": betAmount}));
    } catch (e) {
        console.log(e)
    }
}

function createBet(bet) {
    const betEl = document.createElement("div");
    betEl.className = "bet ";
    bet.win ? betEl.classList.add("won") : betEl.classList.add("lost")

    const textNodes = [
        document.createTextNode(`user number: ${bet.userNumber}`),
        document.createTextNode(`winning number: ${bet.winningNumber}`),
        document.createTextNode(`bet amount: ${bet.betAmount}`),
        document.createTextNode(`won amount: ${bet.wonAmount}`)
    ]

    for (const text of textNodes) {
        const el = document.createElement("p");
        el.append(text)
        betEl.append(el)
    }

    document.querySelector(".bets").appendChild(betEl);
}


formEl.addEventListener("submit", (e) => {
    e.preventDefault();
    sendBet();

    document.querySelector("#userNumber").value = null
    document.querySelector("#betAmount").value = null
});

connectEl.addEventListener("click", () => {
    connect();
})
disconnectEl.addEventListener("click", () => {
    disconnect();
})
