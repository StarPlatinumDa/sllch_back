package com.vueadmin.system.service;

import java.util.List;
import com.vueadmin.system.domain.ImageInfo;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 图像数据管理Service接口
 * 
 * @author ouch
 * @date 2022-04-24
 */
public interface IImageInfoService 
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

    public List<ImageInfo> selectlistswithoutlimitation(ImageInfo imageInfo);

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
     * 批量删除图像数据管理
     * 
     * @param imageIds 需要删除的图像数据管理主键集合
     * @return 结果
     */
    public int deleteImageInfoByImageIds(String[] imageIds);

    /**
     * 删除图像数据管理信息
     * 
     * @param imageId 图像数据管理主键
     * @return 结果
     */
    public int deleteImageInfoByImageId(String imageId);

    /**
     * 根据输入文本检索图像
     *
     * @param query 查询文本
     * @param level 图像等级
     * @param beginTime 和 endTime 控制图片创建时间范围
     * @param endTime 和 startTime 控制图片创建时间范围
     * @return 结果
     */
    public List<ImageInfo> getImageByText(String userId, String query, String level, String beginTime, String endTime);

}
