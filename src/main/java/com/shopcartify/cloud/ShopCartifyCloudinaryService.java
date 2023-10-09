package com.shopcartify.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class ShopCartifyCloudinaryService implements CloudService {

       private CloudConfig appConfig;

    @Override
    public String upload(File file) {

        Cloudinary cloudinary = new Cloudinary();
        Uploader uploader = cloudinary.uploader();

        try {
            Map<?, ?> response = uploader.upload(file, ObjectUtils.asMap(
                    "public_id", "ShopCartify/QrcodeImages/" + file.getName(),
                    "api_key", appConfig.getCloudKey(),
                    "api_secret", appConfig.getCloudSecret(),
                    "cloud_name", appConfig.getCloudName(),
                    "secure", true,
                    "resource_type", "auto"
            ));

            return response.get("url").toString();
        } catch (IOException exception) {
            throw new RuntimeException("File upload failed: " + exception.getMessage());
        }
    }


    }
