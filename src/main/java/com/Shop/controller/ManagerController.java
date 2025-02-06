package com.Shop.controller;

import com.Shop.dto.AdminDto;
import com.Shop.response.AdminRespDto;
import com.Shop.response.Response;
import com.Shop.serveices.ManagerService;
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
}
