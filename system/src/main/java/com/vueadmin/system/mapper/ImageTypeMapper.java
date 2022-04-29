package com.vueadmin.system.mapper;

import java.util.List;
import com.vueadmin.system.domain.ImageType;

/**
 * 图像类型Mapper接口
 * 
 * @author ouch
 * @date 2022-04-27
 */
public interface ImageTypeMapper 
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
     * 删除图像类型
     * 
     * @param imageTypeid 图像类型主键
     * @return 结果
     */
    public int deleteImageTypeByImageTypeid(Long imageTypeid);

    /**
     * 批量删除图像类型
     * 
     * @param imageTypeids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImageTypeByImageTypeids(Long[] imageTypeids);
}
