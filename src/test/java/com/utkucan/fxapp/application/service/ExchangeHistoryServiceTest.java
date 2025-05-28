package com.utkucan.fxapp.application.service;

import com.utkucan.fxapp.application.dto.request.ExchangeHistoryFilter;
import com.utkucan.fxapp.application.dto.request.ExchangeHistorySaveRequest;
import com.utkucan.fxapp.application.dto.response.ExchangeHistoryDto;
import com.utkucan.fxapp.common.TestExchangeHistoryFactory;
import com.utkucan.fxapp.domain.enums.CurrencyCode;
import com.utkucan.fxapp.domain.repository.ExchangeHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
class ExchangeHistoryServiceTest {

    @Autowired
    private ExchangeHistoryService exchangeHistoryService;

    @Autowired
    private ExchangeHistoryRepository exchangeHistoryRepository;

    @BeforeEach
    void clearDatabase() {
        exchangeHistoryRepository.deleteAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoHistoryExists() {
        ExchangeHistoryFilter filter = TestExchangeHistoryFactory.getDefaultFilter();
        Page<ExchangeHistoryDto> page = exchangeHistoryService.findAll(filter);

        assertThat(page).isNotNull();
        assertThat(page.getContent()).isEmpty();
        assertThat(page.getTotalElements()).isZero();
    }

    @Test
    void shouldSaveThreeHistories() {
        saveTestHistories();

        long count = exchangeHistoryRepository.count();
        assertThat(count).isEqualTo(3);
    }

    @Test
    void shouldReturnAllResults() {
        List<ExchangeHistorySaveRequest> requestList = saveTestHistories();

        Page<ExchangeHistoryDto> page = exchangeHistoryService.findAll(TestExchangeHistoryFactory.getDefaultFilter());

        assertThat(page.getContent()).hasSize(3);
        for (int i = 0; i < page.getContent().size(); i++) {
            ExchangeHistoryDto dto = page.getContent().get(i);
            ExchangeHistorySaveRequest request = requestList.get(i);

            assertThat(dto.getFromCurrency()).isEqualTo(request.getFrom().getCode());
            assertThat(dto.getTargetCurrency()).isEqualTo(request.getTo().getCode());
            assertThat(dto.getOriginalAmount()).isEqualByComparingTo(request.getAmount());
            assertThat(dto.getConvertedAmount()).isEqualByComparingTo(request.getConvertedAmount());
            assertThat(dto.getRateUsed()).isEqualByComparingTo(request.getRate());
            assertThat(dto.getCreatedAt()).isNotNull();
        }
    }

    @Test
    void shouldReturnResultsWhenFilteredByFromAndToCurrency() {
        List<ExchangeHistorySaveRequest> requestList = saveTestHistories();

        ExchangeHistoryFilter filter = TestExchangeHistoryFactory.getDefaultFilter();
        filter.setFrom(requestList.get(0).getFrom().getCode());
        filter.setTo(requestList.get(0).getTo().getCode());

        Page<ExchangeHistoryDto> page = exchangeHistoryService.findAll(filter);

        assertThat(page.getContent()).hasSize(1);
        ExchangeHistoryDto dto = page.getContent().get(0);
        ExchangeHistorySaveRequest request = requestList.get(0);

        assertThat(dto.getFromCurrency()).isEqualTo(request.getFrom().getCode());
        assertThat(dto.getTargetCurrency()).isEqualTo(request.getTo().getCode());
        assertThat(dto.getOriginalAmount()).isEqualByComparingTo(request.getAmount());
        assertThat(dto.getConvertedAmount()).isEqualByComparingTo(request.getConvertedAmount());
        assertThat(dto.getRateUsed()).isEqualByComparingTo(request.getRate());
        assertThat(dto.getCreatedAt()).isNotNull();
    }

    @Test
    void shouldReturnResultsWhenFilteredByFromAndToCurrencyDoNotMatchAny() {
        List<ExchangeHistorySaveRequest> requestList = saveTestHistories();

        ExchangeHistoryFilter filter = TestExchangeHistoryFactory.getDefaultFilter();
        filter.setFrom(CurrencyCode.COP.getCode());
        filter.setTo(CurrencyCode.THB.getCode());

        Page<ExchangeHistoryDto> page = exchangeHistoryService.findAll(filter);

        assertThat(page).isNotNull();
        assertThat(page.getContent()).isEmpty();
        assertThat(page.getTotalElements()).isZero();
    }

    @Test
    void shouldReturnHistoriesWithOriginalAmountBetweenMinAndMax() {
        List<ExchangeHistorySaveRequest> requestList = saveTestHistories();

        ExchangeHistoryFilter filter = TestExchangeHistoryFactory.getDefaultFilter();
        filter.setMinAmount(BigDecimal.valueOf(70.0000));
        filter.setMaxAmount(BigDecimal.valueOf(100.0000));

        Page<ExchangeHistoryDto> page = exchangeHistoryService.findAll(filter);

        assertThat(page.getContent()).hasSize(1);
        for (int i = 0; i < page.getContent().size(); i++) {
            ExchangeHistoryDto dto = page.getContent().get(i);
            ExchangeHistorySaveRequest request = requestList.get(i);

            assertThat(dto.getFromCurrency()).isEqualTo(request.getFrom().getCode());
            assertThat(dto.getTargetCurrency()).isEqualTo(request.getTo().getCode());
            assertThat(dto.getOriginalAmount()).isEqualByComparingTo(request.getAmount());
            assertThat(dto.getConvertedAmount()).isEqualByComparingTo(request.getConvertedAmount());
            assertThat(dto.getRateUsed()).isEqualByComparingTo(request.getRate());
            assertThat(dto.getCreatedAt()).isNotNull();
        }
    }

    @Test
    void shouldReturnEmptyWhenNoOriginalAmountInRange() {
        List<ExchangeHistorySaveRequest> requestList = saveTestHistories();

        ExchangeHistoryFilter filter = TestExchangeHistoryFactory.getDefaultFilter();
        filter.setMinAmount(BigDecimal.valueOf(150));
        filter.setMaxAmount(BigDecimal.valueOf(200));

        Page<ExchangeHistoryDto> page = exchangeHistoryService.findAll(filter);

        assertThat(page).isNotNull();
        assertThat(page.getContent()).isEmpty();
        assertThat(page.getTotalElements()).isZero();
    }

    private List<ExchangeHistorySaveRequest> saveTestHistories() {
        List<ExchangeHistorySaveRequest> requestList = TestExchangeHistoryFactory.createMultipleHistoryRequests();
        for (ExchangeHistorySaveRequest request : requestList) {
            exchangeHistoryService.save(request);
        }

        return requestList;
    }
}