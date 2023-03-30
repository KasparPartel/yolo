package com.github.kasparpartel.betcalculator.repository;

import com.github.kasparpartel.betcalculator.model.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {

}
