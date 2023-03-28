package com.github.kasparpartel.betcalculator.repository;

import com.github.kasparpartel.betcalculator.model.Bet;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Data
@Repository
public class BetRepository {

    public List<Bet> bets = new ArrayList<>();

    public void add(Bet bet) {
        this.bets.add(bet);
    }
}
