package com.shifterwebapp.shifter.external.upload;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class S3UploadResponse {
    public String type;
    public String fileName;
    public MetaInfo meta;
}