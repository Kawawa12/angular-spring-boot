package com.Shop.serveices;

import com.Shop.dto.AdminDto;
import com.Shop.response.AdminRespDto;
import com.Shop.response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ManagerService {

    Response addAdmin(Long id, AdminDto adminDto, MultipartFile imgFile) throws IOException;
    Response updateAdmin(Long id, Long adminId, AdminDto adminDto, MultipartFile imgFile) throws IOException;
    List<AdminRespDto> allAdmins(Long managerId);
    AdminRespDto getAdmin(Long adminId);
    String getAdminImage(Long adminId);
    String updateAdminImg(Long adminId, MultipartFile imgFile) throws IOException;
}
