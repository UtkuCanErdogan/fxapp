package com.utkucan.fxapp.application.dto.converter;

import com.utkucan.fxapp.application.dto.response.ExchangeHistoryDto;
import com.utkucan.fxapp.domain.entity.ExchangeHistory;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor
public class ExchangeHistoryDtoConverter {

    public static ExchangeHistoryDto fromEntity(ExchangeHistory entity) {
        return new ExchangeHistoryDto(
                entity.getId(),
                entity.getFromCurrency(),
                entity.getTargetCurrency(),
                entity.getAmount(),
                entity.getConvertedAmount(),
                entity.getRate(),
                entity.getCreatedAt()
        );
    }

    public static Page<ExchangeHistoryDto> fromEntityPage(Page<ExchangeHistory> entityPage) {
        return entityPage.map(ExchangeHistoryDtoConverter::fromEntity);
    }
}
