package com.github.kasparpartel.betcalculator.controller;

import com.github.kasparpartel.betcalculator.dto.BetRequestDto;
import com.github.kasparpartel.betcalculator.dto.BetResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BetCalculatorWebsocketControllerTest {

    @LocalServerPort
    private Integer port;
    private String URL;

    private WebSocketStompClient webSocketStompClient;

    private CompletableFuture<BetResponseDto> completableFuture;

    @BeforeEach
    void setUp() {
        webSocketStompClient = new WebSocketStompClient(new SockJsClient(
                createTransportClient())
        );
        URL = String.format("ws://localhost:%d/ws-endpoint", port);
        completableFuture = new CompletableFuture<>();
    }

    @Test
    void verifyBetIsReceived() throws Exception {
        BetRequestDto betRequestDto = new BetRequestDto(50, 50.0F);

        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSession session = webSocketStompClient.connect(URL, new StompSessionHandlerAdapter() {
        }).get(1, TimeUnit.SECONDS);

        session.subscribe("/topic/bets", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return BetResponseDto.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                completableFuture.complete((BetResponseDto) payload);
            }
        });

        session.send("/app/addBet", betRequestDto);

        BetResponseDto betResponseDto = completableFuture.get();

        assertNotNull(betResponseDto);
    }

    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }
}