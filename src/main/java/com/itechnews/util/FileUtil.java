package com.itechnews.util;

import org.apache.commons.io.FilenameUtils;

public class FileUtil {
    public static String rename(String fileName) {
        return FilenameUtils.getBaseName(fileName) +"-" +System.nanoTime() +"-" +FilenameUtils.getExtension(fileName);
    }
}
