package com.github.kasparpartel.betcalculator.controller;

import com.github.kasparpartel.betcalculator.dto.BetRequestDto;
import com.github.kasparpartel.betcalculator.dto.BetResponseDto;
import com.github.kasparpartel.betcalculator.model.Bet;
import com.github.kasparpartel.betcalculator.service.BetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/bets",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class BetCalculatorController {

    private final BetService betService;

    public BetCalculatorController(BetService betService) {
        this.betService = betService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BetResponseDto create(@Valid @RequestBody BetRequestDto betRequestDto) throws ExecutionException,
            InterruptedException {
        CompletableFuture<Bet> savedBet = betService.saveBet(betRequestDto.toEntity());
        return savedBet.get().toDto();
    }

    @GetMapping
    public List<BetResponseDto> getAll() {
        return betService.getAllBets()
                .stream()
                .map(Bet::toDto)
                .collect(Collectors.toList());
    }
}
