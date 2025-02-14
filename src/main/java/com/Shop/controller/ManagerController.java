package com.Shop.controller;

import com.Shop.dto.AdminDto;
import com.Shop.dto.AdminImageDto;
import com.Shop.dto.ManagerProfileDto;
import com.Shop.response.AdminRespDto;
import com.Shop.response.Response;
import com.Shop.serveices.ManagerService;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/auth/api")
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/admins-images")
    public ResponseEntity<List<AdminImageDto>> fetchAllAdmins() {
        return ResponseEntity.ok(managerService.getAdminsImageList());
    }

    @PutMapping("/activate-or-deactivate-admin/{id}")
    public ResponseEntity<String> changeAdminStatus(@PathVariable Long id){
        return ResponseEntity.ok(managerService.toggleAdminStatus(id));
    }

    @PutMapping("/update-prof-img/{id}")
    public ResponseEntity<String> updateManagerProfileImg(@PathVariable Long id,
                                                 @RequestParam("image")MultipartFile imgFile)throws IOException{
        return ResponseEntity.ok(managerService.updateProfManagerImg(id,imgFile));
    }

    @GetMapping("/manager-img/{id}")
    public ResponseEntity<String> getManagerProfImg(@PathVariable Long id) {
        return ResponseEntity.ok(managerService.getManagerImage(id));
    }

    @GetMapping("/manager-prof/{id}")
    public ResponseEntity<ManagerProfileDto> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(managerService.getProfile(id));
    }

    @PutMapping("update-manager-prof/{id}")
    public ResponseEntity<String> updateManagerProf(@PathVariable Long id, @RequestBody ManagerProfileDto request) {
        return ResponseEntity.ok(managerService.updateManagerProf(id,request));
    }

    @PostMapping("/add-admin")
    public ResponseEntity<Response> addAdmin(
            @RequestParam("id") Long id,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("phone") String phone,
            @RequestParam("yOfBirth") String yOfBirth,
            @RequestParam("address") String address,
            @RequestParam("gender") String gender,
            @RequestParam("image") MultipartFile image
    ) throws IOException {
        AdminDto adminDto = new AdminDto();
        adminDto.setFirstName(firstName);
        adminDto.setLastName(lastName);
        adminDto.setEmail(email);
        adminDto.setPassword(password);
        adminDto.setPhone(phone);
        adminDto.setAddress(address);
        adminDto.setGender(gender);
        adminDto.setyOfBirth(yOfBirth);

        return ResponseEntity.ok(managerService.addAdmin(id,adminDto,image));
    }

    @PutMapping("/update-admin")
    public ResponseEntity<Response> updateAdmin(
            @RequestParam("id") Long id,
            @RequestParam("adminId") Long adminId,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("yOfBirth") String yOfBirth,
            @RequestParam("address") String address,
            @RequestParam("gender") String gender,
            @RequestParam("image") MultipartFile image
    ) throws IOException {
        AdminDto adminDto = new AdminDto();
        adminDto.setFirstName(firstName);
        adminDto.setLastName(lastName);
        adminDto.setEmail(email);
        adminDto.setPhone(phone);
        adminDto.setAddress(address);
        adminDto.setGender(gender);
        adminDto.setyOfBirth(yOfBirth);

        return ResponseEntity.ok(managerService.updateAdmin(id,adminId,adminDto,image));
    }


    @GetMapping("/all-admins/{managerId}")
    public ResponseEntity<List<AdminRespDto>> getAllAdmins(@PathVariable Long managerId){
        return ResponseEntity.ok(managerService.allAdmins(managerId));
    }

    @GetMapping("/active-admins")
    public ResponseEntity<?> getTotalActiveAdminCount(){
        return ResponseEntity.ok(managerService.activeAdminCount());
    }

    @GetMapping("/total-stock-products")
    public ResponseEntity<?> getStockForActiveProduct(){
        return ResponseEntity.ok(managerService.totalActiveProductCount());
    }

    @GetMapping("/total-customers")
    public ResponseEntity<?> getTotalCustomer(){
        return ResponseEntity.ok(managerService.customerCount());
    }

    @GetMapping("/total-categories")
    public ResponseEntity<?> getTotalCategory(){
        return ResponseEntity.ok(managerService.totalCategory());
    }

    @GetMapping("/total-new-orders")
    public ResponseEntity<?> getNeOrders(){
        return ResponseEntity.ok(managerService.totalNewOrders());
    }

    @GetMapping("/total-canceled-orders")
    public ResponseEntity<?> getCanceledOrders(){
        return ResponseEntity.ok(managerService.totalCanceledOrders());
    }

    @GetMapping("/total-confirmed-orders")
    public ResponseEntity<?> getConfirmedOrders(){
        return ResponseEntity.ok(managerService.totalConfirmedOrders());
    }

    @GetMapping("/total-completed-orders")
    public ResponseEntity<?> getCompletedOrders(){
        return ResponseEntity.ok(managerService.totalCompletedOrders());
    }

    @PostMapping("/lock-account/{id}")
    public ResponseEntity<?> lockUserAccount(@PathVariable Long id) {
        return ResponseEntity.ok(managerService.lockAccount(id));
    }

    @PostMapping("/unlock-account/{id}")
    public ResponseEntity<?> unLockUserAccount(@PathVariable Long id) {
        return ResponseEntity.ok(managerService.unlockAccount(id));
    }
}
