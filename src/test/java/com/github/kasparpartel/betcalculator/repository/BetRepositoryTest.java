package com.github.kasparpartel.betcalculator.repository;

import com.github.kasparpartel.betcalculator.model.Bet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class BetRepositoryTest {
    @Autowired
    private BetRepository betRepository;
    private Bet bet;

    @BeforeEach
    public void setUp() {
        bet = new Bet(50, 40.5F);
    }

    @AfterEach
    public void tearDown() {
        betRepository.deleteAll();
        bet = null;
    }

    @Test
    public void givenBetToAdd_shouldReturnAddedBet() {
        betRepository.save(bet);
        Bet fetchedBet = betRepository.findById(bet.getId()).get();

        assertEquals(bet.getId(), fetchedBet.getId());
    }

    @Test
    public void givenGetAllBets_shouldReturnListOfAllBets() {
        Bet bet1 = new Bet(80, 100.5F);
        Bet bet2 = new Bet(50, 70.5F);
        betRepository.save(bet1);
        betRepository.save(bet2);

        List<Bet> betList = betRepository.findAll();
        assertEquals(50, betList.get(1).getUserNumber());
    }

    @Test
    public void givenIdToDelete_shouldDeleteBetOfThatId() {
        Bet bet2 = new Bet(30, 40.5F);
        betRepository.save(bet);
        betRepository.save(bet2);

        betRepository.deleteById(bet.getId());
        Optional<Bet> optional = betRepository.findById(bet.getId());

        assertEquals(Optional.empty(), optional);
    }

    @Test
    public void givenIdNotInRepository_shouldReturnNull() {
        Bet bet2 = betRepository.save(bet);
        Optional<Bet> optional = betRepository.findById(bet2.getId()+1);
        assertFalse(optional.isPresent());
    }
}