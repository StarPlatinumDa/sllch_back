package com.vueadmin.system.service;

import java.util.List;
import com.vueadmin.system.domain.ImageType;

/**
 * 图像类型Service接口
 * 
 * @author ouch
 * @date 2022-04-27
 */
public interface IImageTypeService 
{
    /**
     * 查询图像类型
     * 
     * @param imageTypeid 图像类型主键
     * @return 图像类型
     */
    public ImageType selectImageTypeByImageTypeid(Long imageTypeid);

    /**
     * 查询图像类型列表
     * 
     * @param imageType 图像类型
     * @return 图像类型集合
     */
    public List<ImageType> selectImageTypeList(ImageType imageType);

    /**
     * 新增图像类型
     * 
     * @param imageType 图像类型
     * @return 结果
     */
    public int insertImageType(ImageType imageType);

    /**
     * 修改图像类型
     * 
     * @param imageType 图像类型
     * @return 结果
     */
    public int updateImageType(ImageType imageType);

    /**
     * 批量删除图像类型
     * 
     * @param imageTypeids 需要删除的图像类型主键集合
     * @return 结果
     */
    public int deleteImageTypeByImageTypeids(Long[] imageTypeids);

    /**
     * 删除图像类型信息
     * 
     * @param imageTypeid 图像类型主键
     * @return 结果
     */
    public int deleteImageTypeByImageTypeid(Long imageTypeid);
}
