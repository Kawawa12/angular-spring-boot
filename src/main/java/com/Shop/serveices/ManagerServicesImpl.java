package com.Shop.serveices;

import com.Shop.dto.AdminDto;
import com.Shop.dto.AdminImageDto;
import com.Shop.dto.ManagerProfileDto;
import com.Shop.entity.*;
import com.Shop.repo.AdminImageRepository;
import com.Shop.repo.AdminRepository;
import com.Shop.repo.ManagerRepository;
import com.Shop.repo.UserRepository;
import com.Shop.response.AdminRespDto;
import com.Shop.response.Response;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ManagerServicesImpl implements ManagerService {

    private final ManagerRepository managerRepository;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminImageRepository adminImageRepository;

    @Autowired
    public ManagerServicesImpl (ManagerRepository managerRepository, UserRepository userRepository,
                                AdminRepository adminRepository, PasswordEncoder passwordEncoder,
                                AdminImageRepository adminImageRepository) {
        this.managerRepository = managerRepository;
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminImageRepository = adminImageRepository;
    }

    public Response addAdmin(Long id, AdminDto adminDto, MultipartFile imgFile) throws IOException {
        Response response = new Response();
        Optional<Manager> managerOptional = managerRepository.findById(id);
        if(managerOptional.isEmpty()) {
            response.setMessage("Manager does not exist");
            return response;
        } else {
            // Create the Admin entity
            Admin admin = new Admin();
            admin.setFullName(adminDto.getFirstName() + " " + adminDto.getLastName());
            admin.setEmail(adminDto.getEmail());
            admin.setPassword(passwordEncoder.encode(adminDto.getPassword()));
            admin.setPhone(adminDto.getPhone());
            admin.setAddress(adminDto.getAddress());
            admin.setyOfBirth(adminDto.getyOfBirth());
            admin.setGender(adminDto.getGender());
            admin.setActive(true);
            admin.setRole(Role.ADMIN);
            admin.setDateCreated(LocalDateTime.now());
            admin.setUpdatedDate(LocalDateTime.now());
            admin.setManager(managerOptional.get());

            // Save Admin
            Admin savedAdmin = userRepository.save(admin);

            // Handle the image upload
            if (imgFile != null && !imgFile.isEmpty()) {
                AdminImage adminImage = new AdminImage();
                adminImage.setImg(imgFile.getBytes()); // Convert the MultipartFile to byte[]
                adminImage.setAdmin(savedAdmin); // Link the image with the admin
                adminImageRepository.save(adminImage); // Save the image to the database
                savedAdmin.setAdminImage(adminImage); // Set the image in the Admin object
                userRepository.save(savedAdmin); // Save the admin with the image relation
            }

            if (savedAdmin.getId() > 0) {
                response.setStatus(200);
                response.setMessage("Admin added successfully.");
            } else {
                response.setMessage("Something went wrong! Admin not added. Please try again.");
            }
        }
        return response;
    }



    public Response updateAdmin(Long id, Long adminId, AdminDto adminDto, MultipartFile imgFile) throws IOException {
        Response response = new Response();
        Optional<Manager> managerOptional = managerRepository.findById(id);
        Optional<Admin> optionalAdmin = adminRepository.findById(adminId);

        if (managerOptional.isEmpty() || optionalAdmin.isEmpty()) {
            response.setStatus(403);
            response.setMessage("Manager or Admin does not exist.");
            return response;
        } else {
            Admin existingAdmin = optionalAdmin.get();
            existingAdmin.setFullName(adminDto.getFirstName() + " " + adminDto.getLastName());
            existingAdmin.setEmail(adminDto.getEmail());
            existingAdmin.setPhone(adminDto.getPhone());
            existingAdmin.setAddress(adminDto.getAddress());
            existingAdmin.setyOfBirth(adminDto.getyOfBirth());
            existingAdmin.setGender(adminDto.getGender());
            existingAdmin.setActive(true);
            existingAdmin.setRole(Role.ADMIN);
            existingAdmin.setUpdatedDate(LocalDateTime.now());
            existingAdmin.setManager(managerOptional.get());

            // Handle the image upload
            if (imgFile != null && !imgFile.isEmpty()) {
                // Check if the Admin already has an image
                if (existingAdmin.getAdminImage() != null) {
                    // Update the existing image
                    AdminImage existingAdminImage = existingAdmin.getAdminImage();
                    existingAdminImage.setImg(imgFile.getBytes()); // Update the image bytes
                    adminImageRepository.save(existingAdminImage); // Save the updated image
                } else {
                    // Create a new AdminImage if not already associated
                    AdminImage adminImage = new AdminImage();
                    adminImage.setImg(imgFile.getBytes()); // Convert MultipartFile to byte[]
                    adminImage.setAdmin(existingAdmin); // Link the image with the admin
                    adminImageRepository.save(adminImage); // Save the image to the database
                    existingAdmin.setAdminImage(adminImage); // Set the image in the Admin object
                }
            }

            // Save the updated Admin
            Admin savedAdmin = adminRepository.save(existingAdmin);

            if (savedAdmin.getId() > 0) {
                response.setStatus(200);
                response.setMessage("Admin updated successfully.");
            } else {
                response.setMessage("Something went wrong! Admin not updated. Please try again.");
            }
        }
        return response;
    }


    public List<AdminRespDto> allAdmins(Long managerId){
        List<Admin> admins = adminRepository.findAll();
        return admins.stream().map(Admin::getAdminDto).toList();
    }

    public AdminRespDto getAdmin(Long adminId){
        AdminRespDto response = new AdminRespDto();
        Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
        if (optionalAdmin.isPresent()){
            Admin admin = optionalAdmin.get();
            return admin.getAdminDto();
        }else{
            response.setMessage("Admin is not found!");
        }
        return response;
    }


//    //METHOD TO UPDATE ADMIN
//    public Response updateAdminImage(Long adminId, MultipartFile imgFile) throws IOException {
//        Response response = new Response();
//
//        // Step 1: Find the Admin by ID
//        Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
//        if (optionalAdmin.isEmpty()) {
//            response.setStatus(404);
//            response.setMessage("Admin is not found");
//            return response;
//        }
//
//        Admin admin = optionalAdmin.get();
//
//        // Step 2: Check if the Admin has an existing AdminImage
//        AdminImage adminImage = admin.getAdminImage();
//        if (adminImage == null) {
//            // If there's no image, create a new one
//            adminImage = new AdminImage();
//            adminImage.setAdmin(admin);  // Associate the image with the admin
//        }
//        // Step 3: Set the new image data
//        adminImage.setImg(imgFile.getBytes());
//
//        // Step 4: Save the AdminImage
//        adminImageRepository.save(adminImage);
//
//        // Step 5: Respond with success
//        response.setStatus(200);
//        response.setMessage("Image updated successfully.");
//        return response;
//    }


    @Transactional
    public String updateProfManagerImg(Long managerId, MultipartFile imgFile) throws IOException {
        Optional<Manager> optionalManager = managerRepository.findById(managerId);

        if (optionalManager.isPresent()) {
            Manager manager = optionalManager.get();
            AdminImage image = manager.getProfImage();

            if (imgFile != null && !imgFile.isEmpty()) {
                if (image == null) {
                    // Create a new AdminImage if it doesn't exist
                    image = new AdminImage();
                    image.setManager(manager); // Set the manager reference
                    manager.setProfImage(image); // Set the image reference in manager
                }
                image.setImg(imgFile.getBytes()); // Update image data
                adminImageRepository.save(image); // Save the image

                // Ensure the updated manager is saved
                managerRepository.save(manager);
            }
            return "Image Profile updated successfully.";
        }
        return "Manager not found.";
    }


    @Transactional
    public String getAdminImage(Long adminId) {
        // Step 1: Find the Admin by ID
        Admin admin = adminRepository.findById(adminId).orElseThrow();

        // Step 2: Fetch the AdminImage (Lazy loading will happen here)
        AdminImage adminImage = admin.getAdminImage();

        // Step 3: Convert the image byte array to Base64 encoded string
        byte[] imageBytes = adminImage.getImg();
        if (imageBytes != null) {
            return Base64.getEncoder().encodeToString(imageBytes); // Return as base64 encoded string
        } else {
            return null; // Or handle case where image is not found
        }
    }

    @Transactional
    public String getManagerImage(Long managerId) {
        // Step 1: Find the Admin by ID
        Manager manager = managerRepository.findById(managerId).orElseThrow();

        // Step 2: Fetch the AdminImage (Lazy loading will happen here)
        AdminImage managerImage = manager.getProfImage();

        // Step 3: Convert the image byte array to Base64 encoded string
        byte[] imageBytes = managerImage.getImg();
        if (imageBytes != null) {
            return Base64.getEncoder().encodeToString(imageBytes); // Return as base64 encoded string
        } else {
            return null; // Or handle case where image is not found
        }
    }

    @Transactional
    public String updateAdminImg(Long adminId, MultipartFile imgFile) throws IOException {
        Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
        if (optionalAdmin.isPresent()){
            Admin admin = optionalAdmin.get();
            AdminImage image = admin.getAdminImage();

            if(imgFile != null && !imgFile.isEmpty() && image != null) {
                image.setAdmin(admin);
                image.setImg(imgFile.getBytes());
                adminImageRepository.save(image);
            }
        }else {
            return "Admin is not found";
        }

        return "Image Profile updated successful.";
    }

    public ManagerProfileDto getProfile(Long managerId) {

        //Find manager if exist
        Optional<Manager> optionalManager = managerRepository.findById(managerId);
        if(optionalManager.isPresent()){
            Manager manager = optionalManager.get();
            return manager.getProfileDto();
        }else {
            throw new UsernameNotFoundException("Manager is not found");
        }
    }

    public String updateManagerProf(Long managerId, ManagerProfileDto request){
        //Find if manager exist
        Optional<Manager> optionalManager = managerRepository.findById(managerId);
        if (optionalManager.isPresent()) {
             Manager existingManager = optionalManager.get();
             existingManager.setFullName(request.getFullName());
             existingManager.setEmail(request.getEmail());
             existingManager.setPhone(request.getPhone());
             existingManager.setGender(request.getGender());
             existingManager.setAddress(request.getAddress());
             managerRepository.save(existingManager);
        }else {
            return "Failed to update profile.";
        }
        return "Profile updated successful.";
    }

    public List<AdminImageDto> getAdminsImageList() {
        List<AdminImage> adminImages = adminImageRepository.findAll();

        return adminImages.stream()
                .filter(image -> image.getAdmin() != null) // ✅ Exclude null Admins
                .map(AdminImage::getImageDto)
                .toList();
    }

    public String toggleAdminStatus(Long id) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isEmpty()) {
            return "Admin not found.";
        }

        Admin admin = optionalAdmin.get();
        boolean newStatus = !admin.isActive(); // Toggle the status
        admin.setActive(newStatus);
        adminRepository.save(admin);

        return newStatus ? "Admin activated successfully." : "Admin deactivated successfully.";
    }



}
