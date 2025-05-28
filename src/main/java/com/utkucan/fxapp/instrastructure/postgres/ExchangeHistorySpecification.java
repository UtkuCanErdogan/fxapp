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

            if (filter.getTarget() != null)
                predicates.add(cb.equal(root.get("targetCurrency"), filter.getTarget()));

            if (filter.getMinAmount() != null)
                predicates.add(cb.greaterThanOrEqualTo(root.get("amount"), filter.getMinAmount()));

            if (filter.getMaxAmount() != null)
                predicates.add(cb.lessThanOrEqualTo(root.get("amount"), filter.getMaxAmount()));

            if (filter.getStartDate() != null)
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), filter.getStartDate().atStartOfDay()));

            if (filter.getEndDate() != null)
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), filter.getEndDate().atTime(LocalTime.MAX)));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
