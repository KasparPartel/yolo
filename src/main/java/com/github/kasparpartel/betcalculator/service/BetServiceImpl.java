package com.github.kasparpartel.betcalculator.service;

import com.github.kasparpartel.betcalculator.model.Bet;
import com.github.kasparpartel.betcalculator.repository.BetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BetServiceImpl implements BetService {
    private final BetRepository repository;

    public BetServiceImpl(BetRepository repository) {
        this.repository = repository;
    }

    @Override
    public Bet add(Bet bet) {
        if (bet.isWinning()) {
            bet.calculateWinnings();
        }
        repository.add(bet);
        return bet;
    }

    @Override
    public List<Bet> getAll() {
        return repository.getBets();
    }
}
