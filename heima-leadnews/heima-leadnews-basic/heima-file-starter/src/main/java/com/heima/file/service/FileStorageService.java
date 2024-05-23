package com.heima.file.service;

import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @author itheima
 */

@Service
public interface FileStorageService {


    /**
     * upload image file
     *
     * @param prefix      文件前缀
     * @param filename    文件名
     * @param inputStream 文件流
     * @return 文件全路径
     */
    public String uploadImgFile(String prefix, String filename, InputStream inputStream);

    /**
     * upload html file
     *
     * @param prefix      文件前缀
     * @param filename    文件名
     * @param inputStream 文件流
     * @return 文件全路径
     */
    public String uploadHtmlFile(String prefix, String filename, InputStream inputStream);

    /**
     * delete files
     *
     * @param pathUrl 文件全路径
     */
    public void delete(String pathUrl);

    /**
     * download file
     *
     * @param pathUrl 文件全路径
     * @return
     */
    public byte[] downLoadFile(String pathUrl);

}
