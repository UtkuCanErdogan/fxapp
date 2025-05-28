package com.utkucan.fxapp.instrastructure.postgres;

import com.utkucan.fxapp.application.dto.request.ExchangeHistoryFilter;
import com.utkucan.fxapp.domain.entity.ExchangeHistory;
import com.utkucan.fxapp.domain.repository.ExchangeHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;


@Repository
public class ExchangeHistoryRepositoryImpl implements ExchangeHistoryRepository {
    private final PostgresExchangeHistoryRepository repository;

    public ExchangeHistoryRepositoryImpl(PostgresExchangeHistoryRepository repository) {
        this.repository = repository;
    }


    @Override
    public void save(ExchangeHistory history) {
        repository.save(history);
    }

    @Override
    public Page<ExchangeHistory> findAll(ExchangeHistoryFilter filter) {
        Sort.Direction direction = Sort.Direction.fromString(filter.getSortBy());
        Sort sort = Sort.by(direction, filter.getOrderBy());
        Pageable pageable = PageRequest.of(filter.getSkip(), filter.getLimit(), sort);

        Specification<ExchangeHistory> spec = ExchangeHistorySpecification.filter(filter);
        return repository.findAll(spec, pageable);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
