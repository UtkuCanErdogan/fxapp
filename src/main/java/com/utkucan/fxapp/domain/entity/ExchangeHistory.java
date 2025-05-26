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

    @Column(name = "original_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal originalAmount;

    @Column(name = "converted_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal convertedAmount;

    @Column(name = "rate_used", nullable = false, precision = 19, scale = 8)
    private BigDecimal rateUsed;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    public ExchangeHistory(String fromCurrency, String targetCurrency, BigDecimal originalAmount, BigDecimal convertedAmount, BigDecimal rateUsed) {
        this.fromCurrency = fromCurrency;
        this.targetCurrency = targetCurrency;
        this.originalAmount = originalAmount;
        this.convertedAmount = convertedAmount;
        this.rateUsed = rateUsed;
        this.createdAt = LocalDateTime.now();
    }
}

