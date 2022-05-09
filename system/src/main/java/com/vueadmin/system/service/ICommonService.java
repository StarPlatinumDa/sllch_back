package com.vueadmin.system.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Description
 * @Author caiks
 * @Date 2022/5/9 15:09
 **/
public interface ICommonService {

    public Map<String, Object> uploadFile(MultipartFile file, String filePath, String netPath);

}
