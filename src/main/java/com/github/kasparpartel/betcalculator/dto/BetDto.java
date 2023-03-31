package com.github.kasparpartel.betcalculator.dto;

import com.github.kasparpartel.betcalculator.model.Bet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Positive;


@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class BetDto {
    private Long id;
    private int winningNumber;

    @NonNull
    @Range(min = 1, max = 100)
    private Integer userNumber;

    @NonNull
    @Positive
    private Float betAmount;

    private boolean isWin;
    private float wonAmount;

    public Bet toEntity() {
        return new Bet(this.getUserNumber(), this.getBetAmount());
    }
}
