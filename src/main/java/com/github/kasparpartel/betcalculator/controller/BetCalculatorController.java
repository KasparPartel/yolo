package com.github.kasparpartel.betcalculator.controller;

import com.github.kasparpartel.betcalculator.model.Bet;
import com.github.kasparpartel.betcalculator.service.BetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bets")
public class BetCalculatorController {

    private final BetService betService;

    public BetCalculatorController(BetService betService) {
        this.betService = betService;
    }

    @PostMapping
    public Bet create(@RequestBody Bet bet) {
        return betService.add(bet);
    }

    @GetMapping
    public List<Bet> getAll() {
        return betService.getAll();
    }
}
