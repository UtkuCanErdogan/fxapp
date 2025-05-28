package com.utkucan.fxapp.common;

import com.utkucan.fxapp.application.dto.request.ExchangeHistoryFilter;
import com.utkucan.fxapp.application.dto.request.ExchangeHistorySaveRequest;
import com.utkucan.fxapp.application.dto.request.ExchangeRequest;
import com.utkucan.fxapp.domain.enums.CurrencyCode;

import java.math.BigDecimal;
import java.util.List;

public class TestExchangeHistoryFactory {

    public static List<ExchangeHistorySaveRequest> createMultipleHistoryRequests() {
        ExchangeRequest request1 = new ExchangeRequest(CurrencyCode.USD, CurrencyCode.TRY, 10000L);
        ExchangeHistorySaveRequest historyRequest1 = new ExchangeHistorySaveRequest(request1, 310000L, BigDecimal.valueOf(31.00000000));

        ExchangeRequest request2 = new ExchangeRequest(CurrencyCode.EUR, CurrencyCode.GBP, 5000L);
        ExchangeHistorySaveRequest historyRequest2 = new ExchangeHistorySaveRequest(request2, 4300L, BigDecimal.valueOf(0.86000000));

        ExchangeRequest request3 = new ExchangeRequest(CurrencyCode.JPY, CurrencyCode.EUR, 1000000L);
        ExchangeHistorySaveRequest historyRequest3 = new ExchangeHistorySaveRequest(request3, 6100L, BigDecimal.valueOf(0.00610000));

        return List.of(historyRequest1, historyRequest2, historyRequest3);
    }

    public static ExchangeHistoryFilter getDefaultFilter() {
        ExchangeHistoryFilter filter = new ExchangeHistoryFilter();
        filter.setOrderBy("createdAt");
        filter.setSortBy("ASC");
        filter.setLimit(10);
        filter.setSkip(0);
        return filter;
    }
}
