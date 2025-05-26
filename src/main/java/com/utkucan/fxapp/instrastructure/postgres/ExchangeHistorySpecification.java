package com.utkucan.fxapp.instrastructure.postgres;

import com.utkucan.fxapp.application.dto.request.ExchangeHistoryFilter;
import com.utkucan.fxapp.domain.entity.ExchangeHistory;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ExchangeHistorySpecification {

    public static Specification<ExchangeHistory> filter(ExchangeHistoryFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getFrom() != null)
                predicates.add(cb.equal(root.get("fromCurrency"), filter.getFrom()));

            if (filter.getTo() != null)
                predicates.add(cb.equal(root.get("targetCurrency"), filter.getTo()));

            if (filter.getMinAmount() != null)
                predicates.add(cb.greaterThanOrEqualTo(root.get("originalAmount"), filter.getMinAmount()));

            if (filter.getMaxAmount() != null)
                predicates.add(cb.lessThanOrEqualTo(root.get("originalAmount"), filter.getMaxAmount()));

            if (filter.getStartDate() != null)
                predicates.add(cb.greaterThanOrEqualTo(root.get("timestamp"), filter.getStartDate().atStartOfDay()));

            if (filter.getEndDate() != null)
                predicates.add(cb.lessThanOrEqualTo(root.get("timestamp"), filter.getEndDate().atTime(LocalTime.MAX)));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
