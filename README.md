# YOLO Bet Calculator
## Project info
- Requests using rest api or websocket
- Server runs on port `:8080`
- User provided number must be in range of **1-100**
- Simple web client for sending websocket requests - `localhost:8080`

### Request JSON object
```
{  
    "userNumber": integer,  
    "betAmount": float  
} 
```
### Response JSON object
```
{  
    "id": integer,
    "winningNumber": integer,
    "userNumber": integer,
    "betAmount": float,
    "win": boolean,
    "wonAmount": float
}
```

### Rest api endpoints
- #### POST single bet - ```/api/v1/bets``` 
- #### GET all bets - ```/api/v1/bets```

### Websocket endpoints
- #### CONNECT - ```/ws-endpoint```
- #### SUBSCRIPTION POOL - ```/topic/bets```
- #### CREATE BET - ```/app/addBet```

### Needs fixing:
- 1 million requests in 24 threads test fails as it is not implemented correctly