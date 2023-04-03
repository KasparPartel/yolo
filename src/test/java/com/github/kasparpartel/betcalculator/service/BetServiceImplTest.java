package com.github.kasparpartel.betcalculator.service;

import com.github.kasparpartel.betcalculator.model.Bet;
import com.github.kasparpartel.betcalculator.repository.BetRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class BetServiceTest {

    @Mock
    private BetRepository betRepository;

    @InjectMocks
    private BetServiceImpl betService;

    private Bet bet;

    @BeforeEach
    void setUp() {
        bet = new Bet(30, 75.0F);
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("JUnit test for saveBet method")
    @Test
    void givenBetObject_whenSaveBet_thenReturnBetObject() {
        given(betRepository.save(bet)).willReturn(bet);

        CompletableFuture<Bet> savedBet = betService.saveBet(bet);

        assertNotNull(savedBet);
    }

    @DisplayName("JUnit test for findAllBets method")
    @Test
    void givenFindAllBets_whenGetAllBets_thenReturnListOfAllBets() {
        Bet bet1 = new Bet(75, 30.0F);

        given(betRepository.findAll()).willReturn(List.of(bet, bet1));

        List<Bet> betList = betService.getAllBets();

        assertNotNull(betList);
        assertThat(betList.size()).isEqualTo(2);
    }
}