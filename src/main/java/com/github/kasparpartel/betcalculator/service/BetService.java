package com.github.kasparpartel.betcalculator.service;

import com.github.kasparpartel.betcalculator.model.Bet;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface BetService {

    CompletableFuture<Bet> saveBet(Bet bet);

    List<Bet> getAllBets();
}
