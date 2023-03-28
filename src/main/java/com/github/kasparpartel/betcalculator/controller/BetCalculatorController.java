package com.github.kasparpartel.betcalculator.controller;

import com.github.kasparpartel.betcalculator.dto.BetDto;
import com.github.kasparpartel.betcalculator.model.Bet;
import com.github.kasparpartel.betcalculator.service.BetService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bets")
public class BetCalculatorController {

    private final BetService betService;

    public BetCalculatorController(BetService betService) {
        this.betService = betService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BetDto create(@Valid @RequestBody BetDto betDto) {
        Bet bet = new Bet();
        bet.setUserNumber(betDto.getUserNumber());
        bet.setBetAmount(betDto.getBetAmount());
        Bet savedBet = betService.add(bet);

        return new BetDto(
                savedBet.getId(),
                savedBet.getWinningNumber(),
                savedBet.getUserNumber(),
                savedBet.getBetAmount(),
                savedBet.isWin(),
                savedBet.getWonAmount()
        );
    }

    @GetMapping
    public List<Bet> getAll() {
        return betService.getAll();
    }
}
