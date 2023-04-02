package com.github.kasparpartel.betcalculator.model;

import com.github.kasparpartel.betcalculator.dto.BetResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "bets")
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Range(min = 1, max = 100)
    private final int winningNumber = ThreadLocalRandom.current().nextInt(1, 101);

    @NonNull
    @Range(min = 1, max = 100)
    private Integer userNumber;

    @NonNull
    @Positive
    private Float betAmount;

    private boolean isWin = false;

    @PositiveOrZero
    private float wonAmount = 0;

    public boolean winning() {
        return this.getUserNumber() > this.getWinningNumber();
    }

    public void calculateWinnings() {
        if (isWin()) {
            float winnings = this.getBetAmount() * ((float) 99 / (100 - (100 - this.getUserNumber())));
            this.setWonAmount(winnings);
        }
    }

    public BetResponseDto toDto() {
        return new BetResponseDto(
                this.getId(),
                this.getWinningNumber(),
                this.getUserNumber(),
                this.getBetAmount(),
                this.isWin(),
                this.getWonAmount()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Bet that = (Bet) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
