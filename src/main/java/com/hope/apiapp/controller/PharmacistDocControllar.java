package com.hope.apiapp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hope.apiapp.dto.PharmacistDocProjection;
import com.hope.apiapp.helper.ApiResponseSuccess;
import com.hope.apiapp.model.PharmacistDoc;
import com.hope.apiapp.service.PharmacistDocService;
import com.hope.apiapp.util.CommonUtil;

@RestController
@RequestMapping("/api")
@Validated

public class PharmacistDocControllar {

	@Autowired
	private PharmacistDocService pharmacistDocService;

	@PostMapping("/v1/mydoc/upload")
	public ResponseEntity<ApiResponseSuccess<Long>> uploadImage(@RequestParam("imageType") String imageType,
			@RequestParam("imageFile") MultipartFile imageFile) {

		Long userId = CommonUtil.getCurrentUserId();
		// Save the image and get the metadata with image_id
		PharmacistDoc savedImage = pharmacistDocService.saveImage(userId, imageType, imageFile);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", savedImage.getImageId()), HttpStatus.CREATED);

	}

	@GetMapping("/v1/mydoc/download/{id}")
	public ResponseEntity<byte[]> viewImageByUserID(@PathVariable Long id) {

		try {
			// Get the image bytes
			Long userId = CommonUtil.getCurrentUserId();
			byte[] imageBytes = pharmacistDocService.viewImage(id, userId);
			PharmacistDoc image = pharmacistDocService.findByImageId(id);

			// Return the image content as a byte array, with the correct content type
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getMediaType())).body(imageBytes);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@GetMapping("/staff/v1/image/download/{id}")
	public ResponseEntity<byte[]> viewImage(@PathVariable Long id) {

		try {
			// Get the image bytes
			byte[] imageBytes = pharmacistDocService.viewImage(id, null);
			PharmacistDoc image = pharmacistDocService.findByImageId(id);

			// Return the image content as a byte array, with the correct content type
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getMediaType())).body(imageBytes);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@GetMapping("/v1/mydoc/")
	public ResponseEntity<ApiResponseSuccess<List<PharmacistDocProjection>>> getImagesByPharmacistId() {

		Long userId = CommonUtil.getCurrentUserId();
		List<PharmacistDocProjection> imageIds = pharmacistDocService.getImageIdsByPharmacistId(userId);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", imageIds), HttpStatus.OK);
	}

	@GetMapping("/staff/v1/image/pharmacist/{id}")
	public ResponseEntity<ApiResponseSuccess<List<PharmacistDocProjection>>> getImagesByPharmacistId(
			@PathVariable Long id) {

		List<PharmacistDocProjection> imageIds = pharmacistDocService.getImageIdsByPharmacistId(id);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", imageIds), HttpStatus.OK);
	}

}
