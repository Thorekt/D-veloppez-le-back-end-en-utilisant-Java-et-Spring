package com.thorekt.chatop.service;

import java.math.BigDecimal;

import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.thorekt.chatop.model.DBRental;
import com.thorekt.chatop.repository.DBRentalRepository;

/**
 * Service for rental management
 * 
 * @author thorekt
 */
@Service
public class RentalService {

    @Autowired
    private DBRentalRepository rentalRepository;

    @Value("${app.base-url}")
    private String appBaseUrl;

    @Value("${app.uploads.base-dir}")
    private String baseDir;

    @Value("${app.uploads.base-url}")
    private String uploadBaseUrl;

    @Value("${app.uploads.allowed-types}")
    private String allowedTypes;

    /**
     * Create a new rental
     * 
     * @param name
     * @param description
     * @param price
     * @param surface
     * @param picture
     * @param ownerId
     * @throws Exception
     */
    @Transactional
    public void createRental(String name,
            String description,
            BigDecimal price,
            BigDecimal surface,
            MultipartFile picture,
            int ownerId) throws Exception {

        String picturePath = savePicture(picture);

        DBRental rental = new DBRental(
                name,
                description,
                picturePath,
                price,
                surface,
                ownerId);

        rentalRepository.save(rental);
    }

    /**
     * Update an existing rental
     * 
     * @param id
     * @param name
     * @param description
     * @param price
     * @param surface
     * @param picture
     * @throws Exception
     */
    @Transactional
    public void updateRental(int id,
            String name,
            String description,
            BigDecimal price,
            BigDecimal surface,
            MultipartFile picture) throws Exception {

        if (!rentalRepository.existsById(id)) {
            throw new IllegalArgumentException("Rental with id " + id + " does not exist");
        }

        String picturePath = savePicture(picture);

        DBRental rental = rentalRepository.findById(id);
        rental.setName(name);
        rental.setDescription(description);
        rental.setPrice(price);
        rental.setSurface(surface);
        rental.setPicture(picturePath);

        rentalRepository.save(rental);
    }

    /**
     * Save picture and return its path
     * 
     * @param picture
     * @return picture path
     * @throws Exception
     */
    private String savePicture(MultipartFile picture) throws Exception {
        if (picture == null || picture.isEmpty()) {
            throw new IllegalArgumentException("Picture cannot be empty");
        }

        if (!allowedTypes.contains(picture.getContentType())) {
            throw new IllegalArgumentException("Invalid picture type");
        }

        String original = StringUtils.cleanPath(
                Objects.requireNonNullElse(picture.getOriginalFilename(), "image"));
        String baseName = StringUtils.stripFilenameExtension(original);
        String extension = StringUtils.getFilenameExtension(original);

        String safeName = StringUtils.hasText(baseName)
                ? String.format("%s-%d", baseName, System.currentTimeMillis())
                : String.format("image-%d", System.currentTimeMillis());
        if (StringUtils.hasText(extension)) {
            safeName += "." + extension.toLowerCase(Locale.ROOT);
        }

        LocalDate now = LocalDate.now();
        String year = DateTimeFormatter.ofPattern("yyyy").format(now);
        String month = DateTimeFormatter.ofPattern("MM").format(now);

        Path uploadDir = Path.of(baseDir, year, month)
                .toAbsolutePath()
                .normalize();
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        Path targetPath = uploadDir.resolve(safeName).normalize();

        picture.transferTo(targetPath.toFile());

        String publicUrl = String.join("/",
                uploadBaseUrl,
                year,
                month,
                safeName);

        return appBaseUrl + publicUrl;
    }

    /**
     * Get rental by id
     * 
     * @param id
     * @return DBRental
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public DBRental getRentalById(int id) throws Exception {
        if (!rentalRepository.existsById(id)) {
            throw new IllegalArgumentException("Rental with id " + id + " does not exist");
        }
        return rentalRepository.findById(id);
    }

    /**
     * Get all rentals
     * 
     * @return Iterable of DBRental
     */
    @Transactional(readOnly = true)
    public Iterable<DBRental> getAllRentals() {
        return rentalRepository.findAll();
    }

}
