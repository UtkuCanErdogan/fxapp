package com.utkucan.fxapp.instrastructure.postgres;

import com.utkucan.fxapp.domain.entity.ExchangeHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostgresExchangeHistoryRepository extends JpaRepository<ExchangeHistory, UUID> {

    Page<ExchangeHistory> findAll(Pageable pageable, Specification<ExchangeHistory> specification);
}
