package com.github.kasparpartel.betcalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Positive;


@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public BetDto(Integer userNumber, Float betAmount) {
        this.userNumber = userNumber;
        this.betAmount = betAmount;
    }
}
