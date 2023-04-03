package com.github.kasparpartel.betcalculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kasparpartel.betcalculator.dto.BetRequestDto;
import com.github.kasparpartel.betcalculator.dto.BetResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BetCalculatorControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void test1thousandRequests_whenFinished_printOutRTP() throws Exception {
        float betAmount = 1_000_00F; // if player bets 100 each time - 100 * 1000 = 1000000
        float wonAmount = 0.0F;

        for (int i = 0; i < 1_000; i++) {
            int userNumber = ThreadLocalRandom.current().nextInt(1, 101);
            BetRequestDto betRequestDto = new BetRequestDto(userNumber, 100.0F);

            MvcResult result = mockMvc.perform(post("/api/v1/bets")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(betRequestDto)))
                    .andExpect(status().isCreated())
                    .andReturn();

            BetResponseDto bet = objectMapper.readValue(result.getResponse().getContentAsByteArray(),
                    BetResponseDto.class);

            wonAmount += bet.getWonAmount();
        }

        String rtp = ((int) ((wonAmount / betAmount) * 100)) + "%";

        System.out.printf("Bet amount: %s, Won amount: %s, RTP: %s\n", String.format("%.2f", betAmount),
                String.format("%.2f", wonAmount), rtp);
    }

    @Test
    void test100thousandRequests_whenFinished_printOutRTP() throws Exception {
        float betAmount = 1_000_000_0F; // if player bets 100 each time - 100 * 100000 = 10000000
        float wonAmount = 0.0F;

        for (int i = 0; i < 1_000_00; i++) {
            int userNumber = ThreadLocalRandom.current().nextInt(1, 101);
            BetRequestDto betRequestDto = new BetRequestDto(userNumber, 100.0F);

            MvcResult result = mockMvc.perform(post("/api/v1/bets")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(betRequestDto)))
                    .andExpect(status().isCreated())
                    .andReturn();

            BetResponseDto bet = objectMapper.readValue(result.getResponse().getContentAsByteArray(),
                    BetResponseDto.class);

            wonAmount += bet.getWonAmount();
        }

        String rtp = ((int) ((wonAmount / betAmount) * 100)) + "%";

        System.out.printf("Bet amount: %s, Won amount: %s, RTP: %s\n", String.format("%.2f", betAmount),
                String.format("%.2f", wonAmount), rtp);
    }

    @Test
    void test1millionRequests_whenFinished_printOutRTP() throws Exception {
        float betAmount = 1_000_000_00F; // if player bets 100 each time - 100 * 1000000 = 100000000
        float wonAmount = 0.0F;

        for (int i = 0; i < 1_000_000; i++) {
            int userNumber = ThreadLocalRandom.current().nextInt(1, 101);
            BetRequestDto betRequestDto = new BetRequestDto(userNumber, 100.0F);

            MvcResult result = mockMvc.perform(post("/api/v1/bets")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(betRequestDto)))
                    .andExpect(status().isCreated())
                    .andReturn();

            BetResponseDto bet = objectMapper.readValue(result.getResponse().getContentAsByteArray(),
                    BetResponseDto.class);

            wonAmount += bet.getWonAmount();
        }

        String rtp = ((int) ((wonAmount / betAmount) * 100)) + "%";

        System.out.printf("Bet amount: %s, Won amount: %s, RTP: %s\n", String.format("%.2f", betAmount),
                String.format("%.2f", wonAmount), rtp);
    }
}
