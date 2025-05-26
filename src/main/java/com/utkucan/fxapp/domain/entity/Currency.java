package com.utkucan.fxapp.domain.entity;

import com.utkucan.fxapp.domain.enums.CurrencyCode;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.math.BigDecimal;

@RedisHash("Currency")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Currency implements Serializable {
    @Id
    private String id;
    private BigDecimal rate;
    private CurrencyCode base;
    private Long timestamp;
}
