package com.hope.apiapp.controller;

import org.springframework.data.jpa.domain.Specification;

import com.hope.apiapp.dto.PharmacistProjection;

public class PharmacistSpecifications {

	public static Specification<PharmacistProjection> containsTerm(String term) {
		return (root, query, cb) -> {
			String likePattern = "%" + term.toLowerCase() + "%";
			return cb.or(cb.like(cb.lower(root.get("email")), likePattern),
					cb.like(cb.lower(root.get("firstname")), likePattern),
					cb.like(cb.lower(root.get("lastname")), likePattern));
		};
	}

	public static Specification<PharmacistProjection> hasStatus(String status) {
		return (root, query, cb) -> {
			if (status == null) {
				return cb.conjunction();
			}
			return cb.equal(root.get("status"), status);
		};
	}

}
