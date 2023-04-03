package com.github.kasparpartel.betcalculator.dto;

import com.github.kasparpartel.betcalculator.model.Bet;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Positive;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class BetRequestDto {
    @NonNull
    @Range(min = 1, max = 100)
    private Integer userNumber;

    @NonNull
    @Positive
    private Float betAmount;

    public Bet toEntity() {
        return new Bet(this.getUserNumber(), this.getBetAmount());
    }

}
