package com.utkucan.fxapp.common;

import com.utkucan.fxapp.application.dto.request.ExchangeHistoryFilter;
import com.utkucan.fxapp.application.dto.request.ExchangeHistorySaveRequest;
import com.utkucan.fxapp.application.dto.request.ExchangeRequest;
import com.utkucan.fxapp.domain.enums.CurrencyCode;

import java.math.BigDecimal;
import java.util.List;

public class TestExchangeHistoryFactory {

    public static List<ExchangeHistorySaveRequest> createMultipleHistoryRequests() {
        ExchangeRequest request1 = new ExchangeRequest(CurrencyCode.USD, CurrencyCode.TRY, BigDecimal.valueOf(100.000));
        ExchangeHistorySaveRequest historyRequest1 = new ExchangeHistorySaveRequest(request1, BigDecimal.valueOf(3100.00), BigDecimal.valueOf(31.00000000));

        ExchangeRequest request2 = new ExchangeRequest(CurrencyCode.EUR, CurrencyCode.GBP, BigDecimal.valueOf(50.0000));
        ExchangeHistorySaveRequest historyRequest2 = new ExchangeHistorySaveRequest(request2, BigDecimal.valueOf(43.00), BigDecimal.valueOf(0.86000000));

        ExchangeRequest request3 = new ExchangeRequest(CurrencyCode.JPY, CurrencyCode.EUR, BigDecimal.valueOf(10000.0000));
        ExchangeHistorySaveRequest historyRequest3 = new ExchangeHistorySaveRequest(request3, BigDecimal.valueOf(61.00), BigDecimal.valueOf(0.00610000));

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
