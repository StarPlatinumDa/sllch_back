package com.vueadmin.system.service.impl;

import java.util.List;

import com.vueadmin.system.domain.ImageInfo;
import com.vueadmin.system.mapper.ImageInfoMapper;
import com.vueadmin.system.service.IImageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 图像数据管理Service业务层处理
 * 
 * @author ouch
 * @date 2022-04-24
 */
@Service
public class ImageInfoServiceImpl implements IImageInfoService
{
    @Autowired
    private ImageInfoMapper imageInfoMapper;

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
}
