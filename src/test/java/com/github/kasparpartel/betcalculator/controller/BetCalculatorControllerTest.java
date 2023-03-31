package com.github.kasparpartel.betcalculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kasparpartel.betcalculator.dto.BetDto;
import com.github.kasparpartel.betcalculator.model.Bet;
import com.github.kasparpartel.betcalculator.service.BetService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BetCalculatorControllerTest {
    @Mock
    private BetService betService;

    private Bet bet;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // For some reason JUnit5 @InjectMocks doesn't auto-inject to final fields - itc. BetService
        BetCalculatorController betCalculatorController = new BetCalculatorController(betService);

        bet = new Bet(30, 75.0F);
        mockMvc = MockMvcBuilders.standaloneSetup(betCalculatorController).build();
    }

    @AfterEach
    void tearDown() {
        bet = null;
    }

    @Test
    public void postMappingOfBet() throws Exception {
        BetDto betDto = new BetDto(30, 75.0F);

        given(betService.saveBet(any())).willReturn(bet);

        mockMvc.perform(post("/api/v1/bets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(betDto)))
                .andExpect(status().isCreated());

        verify(betService, times(1)).saveBet(any());
    }

    @Test
    public void getMappingOfBet() throws Exception {
        List<Bet> betList = List.of(bet);

        given(betService.getAllBets()).willReturn(betList);

        mockMvc.perform(get("/api/v1/bets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        verify(betService, times(1)).getAllBets();
    }
}