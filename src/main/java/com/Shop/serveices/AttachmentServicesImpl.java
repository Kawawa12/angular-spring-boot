package com.Shop.serveices;


import com.Shop.entity.Attachment;
import com.Shop.repo.AttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AttachmentServicesImpl implements AttachmentService{

    private final AttachmentRepository attachmentRepository;

    public AttachmentServicesImpl(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public Attachment getAttachment(String fileId) throws Exception {
        return attachmentRepository.findById(Long.valueOf(fileId)).orElseThrow(
                ()-> new Exception("file not found.")
        );
    }

    @Override
    public Attachment saveAttachment(MultipartFile file) throws Exception {
         String fileName = StringUtils.cleanPath(file.getOriginalFilename());

         try{
             if(fileName.contains("..")) {
                 throw  new Exception("File contain invalid path sequence." + fileName);
             }

             Attachment attachment = new Attachment(fileName,
                     file.getContentType(),
                     file.getBytes());
             return attachmentRepository.save(attachment);
         }catch (Exception e){
             throw new Exception("Could not save file: " + fileName);
         }
    }
}
