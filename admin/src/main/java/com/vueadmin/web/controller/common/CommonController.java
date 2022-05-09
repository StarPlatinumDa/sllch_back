package com.vueadmin.web.controller.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vueadmin.common.utils.ossUploader;
import com.vueadmin.system.service.ICommonService;
import com.vueadmin.system.service.impl.CommonServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.vueadmin.common.config.Config;
import com.vueadmin.common.constant.Constants;
import com.vueadmin.common.core.domain.AjaxResult;
import com.vueadmin.common.utils.StringUtils;
import com.vueadmin.common.utils.file.FileUploadUtils;
import com.vueadmin.common.utils.file.FileUtils;
import com.vueadmin.framework.config.ServerConfig;
import static com.vueadmin.common.utils.file.FileUploadUtils.extractFilename;
import static com.vueadmin.common.utils.file.FileUploadUtils.getAbsoluteFile;

/**
 * 通用请求处理
 * 
 * @author vueadmin
 */
@RestController
@RequestMapping("/common")
public class CommonController
{
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    private CommonServiceImpl commonService;

    @Value("${vueadmin.netSourcePath}")
    private String netSourcePath;
    private static final String FILE_DELIMETER = ",";

    /**
     * 通用下载请求
     * 
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @GetMapping("/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            if (!FileUtils.checkAllowDownload(fileName))
            {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = Config.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete)
            {
                FileUtils.deleteFile(filePath);
            }
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/upload")
    public AjaxResult uploadFile(@RequestParam("file") MultipartFile file) throws Exception
    {
        try
        {
            String fileName1 = extractFilename(file);
            String[] splitedFilename = fileName1.split("/");
            // 上传文件路径
            String filePath = Config.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String[] splitedNewFileName = fileName.split("/");
//            String url = serverConfig.getUrl() + fileName;
            String url = filePath+"/"+splitedFilename[0]+"/"+splitedFilename[1]+"/"+splitedFilename[2]+"/"+splitedNewFileName[6];
            ossUploader ossUploader = new ossUploader();
            String callBackUrl = ossUploader.ossImageLoader(url);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", callBackUrl);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }
    /*移动端上传*/
    @PostMapping("/mobileupload")
    public AjaxResult mobileuploadFile(MultipartFile file) throws Exception
    {
        try
        {
            String fileName1 = extractFilename(file);
            String[] splitedFilename = fileName1.split("/");
            // 上传文件路径
            String filePath = Config.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String[] splitedNewFileName = fileName.split("/");
//            String url = serverConfig.getUrl() + fileName;
            String url = filePath+"/"+splitedFilename[0]+"/"+splitedFilename[1]+"/"+splitedFilename[2]+"/"+splitedNewFileName[6];
            ossUploader ossUploader = new ossUploader();
            String callBackUrl = ossUploader.ossImageLoader(url);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", callBackUrl);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 通用上传请求（多个）
     */
    @PostMapping("/uploads")
    public AjaxResult uploadFiles(List<MultipartFile> files) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = Config.getUploadPath();
            List<String> urls = new ArrayList<String>();
            List<String> fileNames = new ArrayList<String>();
            List<String> newFileNames = new ArrayList<String>();
            List<String> originalFilenames = new ArrayList<String>();
            for (MultipartFile file : files)
            {
                // 上传并返回新文件名称
                String fileName = FileUploadUtils.upload(filePath, file);
                String url = serverConfig.getUrl() + fileName;
                urls.add(url);
                fileNames.add(fileName);
                newFileNames.add(FileUtils.getName(fileName));
                originalFilenames.add(file.getOriginalFilename());
            }
            AjaxResult ajax = AjaxResult.success();
            ajax.put("urls", StringUtils.join(urls, FILE_DELIMETER));
            ajax.put("fileNames", StringUtils.join(fileNames, FILE_DELIMETER));
            ajax.put("newFileNames", StringUtils.join(newFileNames, FILE_DELIMETER));
            ajax.put("originalFilenames", StringUtils.join(originalFilenames, FILE_DELIMETER));
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/download/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        try
        {
            if (!FileUtils.checkAllowDownload(resource))
            {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = Config.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 上传聊天文件（单个）
     */
    @PostMapping("/uploadNewsFile")
    public AjaxResult uploadNewsFile(MultipartFile file, int fromId, int toId, int type) throws Exception {
        String filePath = Config.getUploadPath();
        Map<String, Object> result = null;
        // type: 0-voice 1-image
        if (type == 0) {
            String voicePath = filePath + "/news/voice/" + fromId + "-" + toId + "/";
            String netPath = netSourcePath + "news/voice/" + fromId + "-" + toId + "/";
            result = commonService.uploadFile(file, voicePath, netPath);
        } else if (type == 1) {
            String imagePath = filePath + "/news/image/" + fromId + "-" + toId + "/";
            String netPath = netSourcePath + "news/image/" + fromId + "-" + toId + "/";
            result = commonService.uploadFile(file, imagePath, netPath);
        }
        return AjaxResult.success(result);
    }

}
