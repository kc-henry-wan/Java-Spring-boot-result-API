package com.hope.apiapp.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hope.apiapp.model.Negotiation;

@Repository
public interface NegotiationRepository extends JpaRepository<Negotiation, Long> {

	Page<Negotiation> findAll(Pageable pageable);

	Page<Negotiation> findByStatus(String status, Pageable pageable);

	Optional<Negotiation> findById(Long pharmacistId);
}
