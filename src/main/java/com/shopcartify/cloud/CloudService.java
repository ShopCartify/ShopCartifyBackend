package com.shopcartify.cloud;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface CloudService {
    String upload(File file);
}
