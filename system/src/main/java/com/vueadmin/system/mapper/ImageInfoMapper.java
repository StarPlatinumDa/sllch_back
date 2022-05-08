package com.vueadmin.system.mapper;

import java.util.List;
import java.util.Map;

import com.vueadmin.system.domain.ImageInfo;

/**
 * 图像数据管理Mapper接口
 * 
 * @author ouch
 * @date 2022-04-24
 */
public interface ImageInfoMapper 
{
    /**
     * 查询图像数据管理
     * 
     * @param imageId 图像数据管理主键
     * @return 图像数据管理
     */
    public ImageInfo selectImageInfoByImageId(String imageId);

    /**
     * 查询图像数据管理列表
     * 
     * @param imageInfo 图像数据管理
     * @return 图像数据管理集合
     */
    public List<ImageInfo> selectImageInfoList(ImageInfo imageInfo);

    /**
     * 新增图像数据管理
     * 
     * @param imageInfo 图像数据管理
     * @return 结果
     */
    public int insertImageInfo(ImageInfo imageInfo);

    /**
     * 修改图像数据管理
     * 
     * @param imageInfo 图像数据管理
     * @return 结果
     */
    public int updateImageInfo(ImageInfo imageInfo);

    /**
     * 删除图像数据管理
     * 
     * @param imageId 图像数据管理主键
     * @return 结果
     */
    public int deleteImageInfoByImageId(String imageId);


    /**
     * 批量删除图像数据管理
     * 
     * @param imageIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImageInfoByImageIds(String[] imageIds);

    /**
     * 根据等级和时间查询图像
     *
     * @param map {userId、imagePerlevel、startTime、endTime}
     * @return 结果
     */
    public List<ImageInfo> selectImageInfoByLevelAndTime(Map map);

}
