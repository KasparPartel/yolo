package com.github.kasparpartel.betcalculator.service;

import com.github.kasparpartel.betcalculator.model.Bet;

import java.util.List;

public interface BetService {

    Bet saveBet(Bet bet);

    List<Bet> getAllBets();
}
