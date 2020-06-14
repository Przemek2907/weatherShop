package com.example.model.demo.service;

import com.example.model.demo.exceptions.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class MultipartFileService {

        public String generateFilename(MultipartFile file) {
            try {
                String filename = file.getOriginalFilename();
                String[] arr = filename.split("\\.");
                return UUID.randomUUID().toString() + "." + arr[arr.length - 1];
            } catch (Exception e) {
                throw new AppException("GENERATE FILENAME EXCEPTION - cannot load file to read goals");
            }
        }

        public String getRootPath() {
            try {
                String path = MultipartFileService.class.getResource("").toString().substring(6).replaceAll("%20", " ");
                String[] rootPath = path.split("target");
                return rootPath[0];
            } catch (Exception e) {
                throw new AppException("GENERATE FILENAME EXCEPTION - " + e.getMessage());
            }
        }

        public String addFile(MultipartFile multipartFile) {

            try {
                if (multipartFile == null || multipartFile.getBytes().length == 0) {
                    throw new AppException("MULTIPART FILE IS NULL");
                }
                Path path = Paths.get("/usr/");
                String filename = generateFilename(multipartFile);
                String fullPath = path.toString() +"//" + filename;
                log.info("My path inside the container is : {}", fullPath);
                FileCopyUtils.copy(multipartFile.getBytes(), new File(fullPath));
                return fullPath;
            } catch (Exception e) {
                e.printStackTrace();
                throw new AppException("GENERATE FILENAME EXCEPTION - cannot load file to read goals");
            }

        }
}
