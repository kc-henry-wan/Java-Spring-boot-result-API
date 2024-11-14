package com.hope.apiapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hope.apiapp.dto.PharmacistDto;

public interface CustomPharmacistRepository {
	Page<PharmacistDto> searchPharmacists(Pageable pageable, String term, String status);

}
