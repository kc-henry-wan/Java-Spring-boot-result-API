package com.hope.apiapp.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hope.apiapp.dto.PharmacistDocProjection;
import com.hope.apiapp.exception.ResourceNotFoundException;
import com.hope.apiapp.model.PharmacistDoc;
import com.hope.apiapp.repository.PharmacistDocRepository;

@Service
public class PharmacistDocService {

	@Autowired
	private PharmacistDocRepository imageRepository;

	private final String UPLOAD_DIR = "C:\\lab\\image";

	@Transactional
	public PharmacistDoc saveImage(Long pharmacistId, String imageType, MultipartFile imageFile) {

		try {
			if (imageFile.isEmpty()) {
				throw new IllegalArgumentException("imageFile is empty");
			}

			// Check file type (MIME type)
			String contentType = imageFile.getContentType();

			if (contentType == null || !contentType.startsWith("image/")) {
				throw new IllegalArgumentException("Only image files are allowed");
			}

			// Save metadata to the database
			PharmacistDoc image = new PharmacistDoc();
			image.setPharmacistId(pharmacistId);
			image.setImageType(imageType);
			image.setMediaType(contentType);
			image.setOriginalFilename(imageFile.getOriginalFilename());

			// Save the image to the database first, so the image_id is generated
			PharmacistDoc savedImage = imageRepository.save(image);

			// Save the file with the image_id as the filename
			String storedFilename = savedImage.getImageId() + getExtension(imageFile.getOriginalFilename());
			Path filePath = Paths.get(UPLOAD_DIR, storedFilename);

			// Save the file to disk
			Files.write(filePath, imageFile.getBytes());

			// Update the image record with the file name on disk
			savedImage.setStoredFilename(storedFilename);
			imageRepository.save(savedImage);

			return savedImage;
		} catch (IOException e) {
			throw new IllegalArgumentException("Load image file failed");
		}
	}

	// View image
	public byte[] viewImage(Long imageId, Long userId) throws IOException {
		PharmacistDoc image = null;

		if (userId == null) {
			image = imageRepository.findByImageId(imageId).orElseThrow(() -> new IOException("Image not found"));
		} else {
			image = imageRepository.findByUserId(imageId, userId).orElseThrow(() -> new IOException("Image not found"));

		}
		// Retrieve the stored filename from the database
		String storedFilename = image.getStoredFilename();
		Path filePath = Paths.get(UPLOAD_DIR, storedFilename);

		if (!Files.exists(filePath)) {
			throw new IOException("File not found on disk");
		}

		return Files.readAllBytes(filePath);
	}

	public List<PharmacistDocProjection> getImageIdsByPharmacistId(Long pharmacistId) {
		return imageRepository.findByPharmacistId(pharmacistId);
	}

	public PharmacistDoc findByImageId(Long imageId) {
		return imageRepository.findByImageId(imageId)
				.orElseThrow(() -> new ResourceNotFoundException("Document record not found"));
	}

	private String getExtension(String filename) {
		return filename.substring(filename.lastIndexOf("."));
	}
}
