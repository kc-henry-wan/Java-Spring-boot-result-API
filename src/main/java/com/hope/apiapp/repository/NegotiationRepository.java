package com.hope.apiapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hope.apiapp.model.Negotiation;

@Repository
public interface NegotiationRepository extends JpaRepository<Negotiation, Long> {
	// Custom queries can be added here if necessary
//	List<NegotiationProjection> findAllNegotiationsWithLimitedFields();

//	Optional<Negotiation> findNegotiationByIdWithLimitedFields(Long id);

	List<Negotiation> findAll();

	Optional<Negotiation> findById(Long pharmacistId);
}
