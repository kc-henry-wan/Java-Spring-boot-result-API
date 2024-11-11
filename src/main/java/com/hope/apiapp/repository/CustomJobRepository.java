package com.hope.apiapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hope.apiapp.dto.JobDTO;

public interface CustomJobRepository {
	Page<JobDTO> findFilteredJobsWithLimitedFields(Pageable pageable, Double fromLat, Double fromLng, String fromDate,
			String toDate, String statusCode, String jobIds, String groupCode, Long pharmacistId);

}
