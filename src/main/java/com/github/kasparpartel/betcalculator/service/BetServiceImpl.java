package com.github.kasparpartel.betcalculator.service;

import com.github.kasparpartel.betcalculator.model.Bet;
import com.github.kasparpartel.betcalculator.repository.BetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class BetServiceImpl implements BetService {
    private final BetRepository repository;

    public BetServiceImpl(BetRepository repository) {
        this.repository = repository;
    }

//    private final Logger logger = LoggerFactory.getLogger(BetService.class);

    @Override
    @Async
    public CompletableFuture<Bet> saveBet(Bet bet) {
//        logger.info("saving bet: " + Thread.currentThread().getName());
        bet.setWin(bet.winning());
        bet.calculateWinnings();
        repository.save(bet);
        return CompletableFuture.completedFuture(bet);
    }

    @Override
    public List<Bet> getAllBets() {
        return repository.findAll();
    }
}
