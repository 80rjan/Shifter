package com.shifterwebapp.shifter.upload;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.upload.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadController {

    private final S3Service s3Service;
    private final Validate validate;

    @PostMapping(
            value = "/course-image-and-content",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Void> uploadFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("types") List<String> types,
            @RequestParam("meta") List<String> meta,
            Authentication authentication
    ) throws IOException {
        validate.validateUserIsAdmin(authentication);

//        s3Service.uploadFilesWithMeta(files, types, meta);
        return ResponseEntity.ok().build();
    }

}
