package com.utkucan.fxapp.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "exchange_history")
@Data
@NoArgsConstructor
public class ExchangeHistory {

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(name = "from_currency", nullable = false)
    private String fromCurrency;

    @Column(name = "target_currency", nullable = false)
    private String targetCurrency;

    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private Long amount;

    @Column(name = "converted_amount", nullable = false, precision = 19, scale = 4)
    private Long convertedAmount;

    @Column(name = "rate", nullable = false, precision = 19, scale = 8)
    private BigDecimal rate;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    public ExchangeHistory(String fromCurrency, String targetCurrency, Long originalAmount, Long convertedAmount, BigDecimal rateUsed) {
        this.fromCurrency = fromCurrency;
        this.targetCurrency = targetCurrency;
        this.amount = originalAmount;
        this.convertedAmount = convertedAmount;
        this.rate = rateUsed;
        this.createdAt = LocalDateTime.now();
    }
}

