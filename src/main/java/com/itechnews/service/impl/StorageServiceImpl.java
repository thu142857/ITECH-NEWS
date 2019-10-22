package com.itechnews.service.impl;

import com.itechnews.service.StorageProperties;
import com.itechnews.service.StorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private ServletContext servletContext;

    private String uploadDirectory;

    @Autowired
    public StorageServiceImpl(StorageProperties properties) {
        uploadDirectory = properties.getLocation();
    }

    @Override
    public int storeSingleFile(MultipartFile file) {
        File dir = createStorageFolder();
        return uploadSingleFile(file, dir);
    }

    @Override
    public int storeMutipleFile(MultipartFile[] files) {
        File dir = createStorageFolder();
        int totalOfUploadedFiles = 0;
        for (MultipartFile cmf : files) {
            totalOfUploadedFiles += uploadSingleFile(cmf, dir);
        }
        return totalOfUploadedFiles;
    }



    @Override
    public void storeMultipleFileWithThread(MultipartFile[] files) {
        File dir = createStorageFolder();
        Thread thread = new Thread(() -> {
            for (MultipartFile cmf : files) {
                uploadSingleFile(cmf, dir);
            }
        });
        thread.start();
    }

    private int uploadSingleFile(MultipartFile file, File dir) {
        int totalOfUploadedFiles = 0;
        String originalFilename = file.getOriginalFilename();
        if (!"".equals(originalFilename)) {
            try {
                String tmp = dir.getAbsolutePath() + File.separator +
                        FilenameUtils.getBaseName(originalFilename) +
                        "-" + System.nanoTime() + "." + FilenameUtils.getExtension(originalFilename);
                file.transferTo(new File(tmp));
                totalOfUploadedFiles++;
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        return totalOfUploadedFiles;
    }

    private File createStorageFolder() {
        final String DIR_PATH = servletContext.getRealPath("") + uploadDirectory;
        File dir = new File(DIR_PATH);
        //File dir = new File("images");
        //File dir = new File("src/main/resources/static/images");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    // TODO
    @Override
    public int deleteFileFromStorage(String filename) {
        return 0;
    }

    // TODO
    @Override
    public File getFileFromStorage(String filename) {
        return null;
    }
}
