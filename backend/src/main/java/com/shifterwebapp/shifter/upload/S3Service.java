package com.shifterwebapp.shifter.upload;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import java.time.Duration;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${aws.access-key}")
    private String accessKey;

    @Value("${aws.secret-key}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public URL generatePresignedGetUrl(String key, int expirySeconds) {
        S3Presigner presigner = S3Presigner.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)
                ))
                .build();

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofSeconds(expirySeconds))
                .getObjectRequest(getObjectRequest)
                .build();

        URL url = presigner.presignGetObject(presignRequest).url();
        presigner.close();
        return url;
    }

    public List<S3UploadResponse> uploadCourseImageAndFiles(
            Long courseId,
            MultipartFile courseImage,
            List<MultipartFile> files,
            List<String> types,
            List<String> metaList
    ) throws IOException {
        S3Client s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKey, secretKey)
                        )
                )
                .build();

        List<S3UploadResponse> responses = new ArrayList<>();

        // Upload public course image
        if (courseImage != null && !courseImage.isEmpty()) {
            String imageKey = "public/courseImages/course" + courseId + "_" + courseImage.getOriginalFilename();

            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(imageKey)
                            .build(),
                    RequestBody.fromBytes(courseImage.getBytes())
            );

            String imageUrl = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + imageKey;

            responses.add(new S3UploadResponse("COURSE_IMAGE", imageUrl, new MetaInfo(null, null, courseId)));
        }

        // Upload private course content files
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String type = types.get(i);
            String metaJson = metaList.get(i);

            String key = "private/courseContent/" + type.toLowerCase() + "/course_" + courseId + "/" + file.getOriginalFilename();

            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .build(),
                    RequestBody.fromBytes(file.getBytes())
            );


            MetaInfo meta = new ObjectMapper().readValue(metaJson, MetaInfo.class);
            responses.add(new S3UploadResponse("COURSE_LECTURE", file.getOriginalFilename(), meta));
        }

        return responses;
    }
}
