package com.github.kasparpartel.betcalculator.controller;

import com.github.kasparpartel.betcalculator.dto.BetRequestDto;
import com.github.kasparpartel.betcalculator.dto.BetResponseDto;
import com.github.kasparpartel.betcalculator.model.Bet;
import com.github.kasparpartel.betcalculator.service.BetService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller
public class BetCalculatorWebsocketController {

    private final BetService betService;

    public BetCalculatorWebsocketController(BetService betService) {
        this.betService = betService;
    }

    @MessageMapping("/addBet")
    @SendTo("/topic/bets")
    public BetResponseDto create(BetRequestDto betRequestDto) throws ExecutionException, InterruptedException {
        CompletableFuture<Bet> savedBet = betService.saveBet(betRequestDto.toEntity());
        return savedBet.get().toDto();
    }
}
