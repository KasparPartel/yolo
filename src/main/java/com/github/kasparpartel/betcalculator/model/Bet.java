package com.github.kasparpartel.betcalculator.model;

import lombok.Getter;
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

    private int winningNumber = ThreadLocalRandom.current().nextInt(1, 101);

    private float betAmount;
    private int number;

    private boolean won = false;
    private float wonAmount = 0;

    public boolean isWinning() {
        if (this.getNumber() < this.getWinningNumber()) {
            return false;
        }
        this.setWon(true);
        return true;
    }

    public void calculateWinnings() {
        float winnings = this.getBetAmount() * (99 / (100 - this.getNumber()));
        this.setWonAmount(winnings);
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
