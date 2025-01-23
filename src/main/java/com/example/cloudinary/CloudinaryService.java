package com.example.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;
    
    public CloudinaryService() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "your_cloud_name");
        config.put("api_key", "your_api_key");
        config.put("api_secret", "your_api_secret");
        this.cloudinary = new Cloudinary(config);
    }

    public Map<String, String> uploadImage(MultipartFile file) {
        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            Map<String, String> result = new HashMap<>();
            result.put("url", uploadResult.get("url").toString());
            result.put("public_id", uploadResult.get("public_id").toString());
            return result;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'upload de l'image", e);
        }
    }
}