package com.vueadmin.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.vueadmin.common.utils.StringUtils;
import com.vueadmin.common.utils.algorithm.CosineSimilarity;
import com.vueadmin.common.utils.algorithm.TokenizerUtils;
import com.vueadmin.system.domain.ImageInfo;
import com.vueadmin.system.mapper.ImageInfoMapper;
import com.vueadmin.system.service.IImageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 图像数据管理Service业务层处理
 * 
 * @author ouch
 * @date 2022-04-24
 */
@Service
public class ImageInfoServiceImpl implements IImageInfoService
{
    @Resource
    private ImageInfoMapper imageInfoMapper;

    @Override
    public List<ImageInfo> selectImageInfoListById(ImageInfo imageInfo) {
        return imageInfoMapper.selectImageInfoListById(imageInfo);
    }

    /**
     * 查询图像数据管理
     * 
     * @param imageId 图像数据管理主键
     * @return 图像数据管理
     */
    @Override
    public ImageInfo selectImageInfoByImageId(String imageId)
    {
        return imageInfoMapper.selectImageInfoByImageId(imageId);
    }

    /**
     * 查询图像数据管理列表
     * 
     * @param imageInfo 图像数据管理
     * @return 图像数据管理
     */
    @Override
    public List<ImageInfo> selectImageInfoList(ImageInfo imageInfo)
    {
        return imageInfoMapper.selectImageInfoList(imageInfo);
    }

    @Override
    public List<ImageInfo> selectlistswithoutlimitation(ImageInfo imageInfo) {
        return imageInfoMapper.selectlistswithoutlimitation(imageInfo);
    }

    /**
     * 新增图像数据管理
     * 
     * @param imageInfo 图像数据管理
     * @return 结果
     */
    @Override
    public int insertImageInfo(ImageInfo imageInfo)
    {
        return imageInfoMapper.insertImageInfo(imageInfo);
    }

    /**
     * 修改图像数据管理
     * 
     * @param imageInfo 图像数据管理
     * @return 结果
     */
    @Override
    public int updateImageInfo(ImageInfo imageInfo)
    {
        return imageInfoMapper.updateImageInfo(imageInfo);
    }

    /**
     * 批量删除图像数据管理
     * 
     * @param imageIds 需要删除的图像数据管理主键
     * @return 结果
     */
    @Override
    public int deleteImageInfoByImageIds(String[] imageIds)
    {
        return imageInfoMapper.deleteImageInfoByImageIds(imageIds);
    }

    /**
     * 删除图像数据管理信息
     * 
     * @param imageId 图像数据管理主键
     * @return 结果
     */
    @Override
    public int deleteImageInfoByImageId(String imageId)
    {
        return imageInfoMapper.deleteImageInfoByImageId(imageId);
    }

    /**
     * 根据输入文本检索图像
     *
     * @param query 查询文本
     * @param level 图像等级
     * @param beginTime 和 endTime 控制图片创建时间范围
     * @param endTime 和 startTime 控制图片创建时间范围
     * @param userId 用户id
     * @return 结果
     */
    @Override
    public List<ImageInfo> getImageByText(String userId, String query, String level, String beginTime, String endTime) {
        HashMap<String, Object> inMap = new HashMap<>();
        inMap.put("userId", userId);
        if (!StringUtils.isEmpty(level) && "undefined".equals(level)) {
            inMap.put("imagePerlevel", level);
        }
        if (!StringUtils.isEmpty(beginTime) && "undefined".equals(beginTime)) {
            inMap.put("beginTime", beginTime);
        }
        if (!StringUtils.isEmpty(endTime) && "undefined".equals(endTime)) {
            inMap.put("endTime", endTime);
        }
        List<ImageInfo> imageInfos = imageInfoMapper.selectImageInfoByLevelAndTime(inMap);
        ArrayList<ImageInfo> retImageInfos = new ArrayList<>();

        ArrayList<List<String>> corpus = new ArrayList<>();
        for (int i=0; i<imageInfos.size(); i++) {
            ArrayList<String> labels = new ArrayList<>();
            ImageInfo imageInfo = imageInfos.get(i);
            String imageRemarks = imageInfo.getImageRemarks();
            if (imageRemarks == null) imageRemarks = "";
            String[] split = imageRemarks.split("\\|");
            for (String s : split) {
                List<String> temp = TokenizerUtils.lucene3Tokenizer(s);
                labels.addAll(temp);
            }
            labels.add("混凝土");
            labels.add("桥梁");
            corpus.add(labels);
        }
        // 余弦相似度
        CosineSimilarity cosineSimilarity = new CosineSimilarity(corpus);
        for (int i = 0; i < corpus.size(); i++) {
            List<String> words = TokenizerUtils.lucene3Tokenizer(query);
            double score = cosineSimilarity.getScore(words, i);
            if (score >= 0.65) {
                retImageInfos.add(imageInfos.get(i));
            }
        }

        return retImageInfos;
    }
}
