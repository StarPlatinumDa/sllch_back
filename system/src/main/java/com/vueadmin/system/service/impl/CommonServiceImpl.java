package com.vueadmin.system.service.impl;

import com.vueadmin.system.service.ICommonService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author caiks
 * @Date 2022/5/9 15:11
 **/
@Service
public class CommonServiceImpl implements ICommonService {

    @Override
    public Map<String, Object> uploadFile(MultipartFile file, String filePath, String netPath) {
        HashMap<String, Object> map = new HashMap<>();

        if (file == null || file.isEmpty()) {
            map.put("msg", "未选择需上传的文件！");
            return map;
        }

        File fileUpload = new File(filePath);
        if (!fileUpload.exists()) {
            fileUpload.mkdirs();
        }

        fileUpload = new File(filePath, file.getOriginalFilename());
        try {
            file.transferTo(fileUpload);
            map.put("msg", "上传文件到服务器成功！");
            map.put("status", 1);
            map.put("filePath", netPath + file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            map.put("msg", "上传文件到服务器失败！");
            map.put("status", -1);
        }

        return map;
    }
}
