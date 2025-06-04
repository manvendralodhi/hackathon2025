package com.codeCrew.api.repository;

import com.codeCrew.api.entity.Customer;
import com.codeCrew.api.entity.CustomerFullServe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerFsRepository extends JpaRepository<CustomerFullServe, Long> {
    Optional<CustomerFullServe> findByCustId(String custId);
}
