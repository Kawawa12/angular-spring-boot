package com.Shop.serveices;

import com.Shop.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {


    Attachment getAttachment(String fileId) throws Exception;


    Attachment saveAttachment(MultipartFile file) throws Exception;
}
