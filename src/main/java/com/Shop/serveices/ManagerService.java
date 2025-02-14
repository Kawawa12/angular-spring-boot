package com.Shop.serveices;

import com.Shop.dto.AdminDto;
import com.Shop.dto.AdminImageDto;
import com.Shop.dto.ManagerProfileDto;
import com.Shop.response.AdminRespDto;
import com.Shop.response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface ManagerService {

    Response addAdmin(Long id, AdminDto adminDto, MultipartFile imgFile) throws IOException;

    Response updateAdmin(Long id, Long adminId, AdminDto adminDto, MultipartFile imgFile) throws IOException;

    String updateAdminProf(Long managerId, ManagerProfileDto request);

    List<AdminRespDto> allAdmins(Long managerId);

    AdminRespDto getAdmin(Long adminId);

    String getAdminImage(Long adminId);

    String updateAdminImg(Long adminId, MultipartFile imgFile) throws IOException;

    String updateProfManagerImg(Long managerId, MultipartFile imgFile) throws IOException;

    String getManagerImage(Long managerId);

    ManagerProfileDto getProfile(Long managerId);

    String updateManagerProf(Long managerId, ManagerProfileDto request);

    List<AdminImageDto> getAdminsImageList();

    String toggleAdminStatus(Long id);

    int customerCount();

    int activeAdminCount();

    int totalActiveProductCount();

    int totalCategory();

    int totalNewOrders();

    int totalCompletedOrders();

    int totalCanceledOrders();

    int totalConfirmedOrders();

    String lockAccount(Long id);

    String unlockAccount(Long id);

}
