package com.hope.apiapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hope.apiapp.dto.NegotiationDto;

public interface CustomNegotiationRepository {
	Page<NegotiationDto> getNegotiations(Pageable pageable, String status, Long pharmacistId);

}
