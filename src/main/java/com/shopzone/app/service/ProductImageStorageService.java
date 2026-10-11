package com.shopzone.app.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductImageStorageService {

    @Value("${product.image.upload-dir:product-images}")
    private String uploadDir;


    private static final Logger log = LoggerFactory.getLogger(ProductImageStorageService.class);
    public String saveImage(
            Long productId,
            MultipartFile file)
            throws IOException {
    	
    	log.info("Saving image for product ID: {}", productId);

        if (file == null || file.isEmpty()) {
        	log.error("Image file is empty for product ID: {}", productId);
            throw new IllegalArgumentException(
                    "Image file is empty"
            );
        }

        // ============================================
        // PRODUCT FOLDER
        // ============================================

        Path productDirectory =
                Paths.get(uploadDir)
                     .resolve(String.valueOf(productId));

        Files.createDirectories(productDirectory);
        log.info("Created product directory: {}", productDirectory.toAbsolutePath()+" for product ID: {}", productId);


        // ============================================
        // ORIGINAL EXTENSION
        // ============================================

        String originalName =
                file.getOriginalFilename();

        String extension = "";

        if (originalName != null) {
        	log.info("Original filename: {}", originalName+" for product ID: {}", productId);

            int index =
                    originalName.lastIndexOf(".");

            if (index >= 0) {
                extension =
                        originalName.substring(index)
                                   .toLowerCase();
            }
        }
        log.info("File extension: {}", extension+" for product ID: {}", productId);
        


        // ============================================
        // UNIQUE FILE NAME
        // ============================================

        String fileName =
                UUID.randomUUID().toString()
                + extension;


        // ============================================
        // SAVE FILE
        // ============================================

        Path filePath =
                productDirectory.resolve(fileName);

        Files.copy(
                file.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );

log.info("Saved file: {} for product ID: {}", filePath.toAbsolutePath(), productId);
        // ============================================
        // URL SAVED IN DATABASE
        // ============================================

        return "/product-images/"
                + productId
                + "/"
                + fileName;
    }
}