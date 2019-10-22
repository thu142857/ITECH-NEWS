package com.itechnews.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


public interface StorageService {

    int storeSingleFile(MultipartFile file);
    int storeMutipleFile(MultipartFile[] files);
    void storeMultipleFileWithThread(MultipartFile[] files);
    File getFileFromStorage(String filename);
    int deleteFileFromStorage(String filename);
}
