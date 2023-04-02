package com.github.kasparpartel.betcalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class BetResponseDto {
    private Long id;

    @Range(min = 1, max = 100)
    private int winningNumber;

    @NonNull
    @Range(min = 1, max = 100)
    private Integer userNumber;

    @NonNull
    @Positive
    private Float betAmount;

    private boolean isWin;

    @PositiveOrZero
    private float wonAmount;
}
