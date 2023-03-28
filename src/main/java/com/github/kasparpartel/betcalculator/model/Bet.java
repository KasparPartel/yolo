package com.github.kasparpartel.betcalculator.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "bets")
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private final int winningNumber = ThreadLocalRandom.current().nextInt(1, 101);

    private Integer userNumber;
    private Float betAmount;

    private boolean isWin = false;
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
