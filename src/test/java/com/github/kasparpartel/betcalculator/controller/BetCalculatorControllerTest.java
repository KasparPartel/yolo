package com.github.kasparpartel.betcalculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kasparpartel.betcalculator.dto.BetDto;
import com.github.kasparpartel.betcalculator.model.Bet;
import com.github.kasparpartel.betcalculator.service.BetService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BetCalculatorControllerTest {
    @Mock
    private BetService betService;

    @InjectMocks
    private BetCalculatorController betCalculatorController;

    private Bet bet;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        bet = new Bet(30, 75.0F);
        mockMvc = MockMvcBuilders.standaloneSetup(betCalculatorController).build();
    }

    @AfterEach
    void tearDown() {
        bet = null;
    }

    @Test
    public void postMappingOfProduct() throws Exception {
        BetDto betDto = new BetDto(30, 75.0F);

        given(betService.saveBet(any())).willReturn(bet);

        mockMvc.perform(post("/api/v1/bets").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(betDto)))
                .andExpect(status().isCreated());

        verify(betService, times(1)).saveBet(any());
    }
}