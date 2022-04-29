package com.vueadmin.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vueadmin.system.mapper.ImageTypeMapper;
import com.vueadmin.system.domain.ImageType;
import com.vueadmin.system.service.IImageTypeService;

/**
 * 图像类型Service业务层处理
 * 
 * @author ouch
 * @date 2022-04-27
 */
@Service
public class ImageTypeServiceImpl implements IImageTypeService 
{
    @Autowired
    private ImageTypeMapper imageTypeMapper;

    /**
     * 查询图像类型
     * 
     * @param imageTypeid 图像类型主键
     * @return 图像类型
     */
    @Override
    public ImageType selectImageTypeByImageTypeid(Long imageTypeid)
    {
        return imageTypeMapper.selectImageTypeByImageTypeid(imageTypeid);
    }

    /**
     * 查询图像类型列表
     * 
     * @param imageType 图像类型
     * @return 图像类型
     */
    @Override
    public List<ImageType> selectImageTypeList(ImageType imageType)
    {
        return imageTypeMapper.selectImageTypeList(imageType);
    }

    /**
     * 新增图像类型
     * 
     * @param imageType 图像类型
     * @return 结果
     */
    @Override
    public int insertImageType(ImageType imageType)
    {
        return imageTypeMapper.insertImageType(imageType);
    }

    /**
     * 修改图像类型
     * 
     * @param imageType 图像类型
     * @return 结果
     */
    @Override
    public int updateImageType(ImageType imageType)
    {
        return imageTypeMapper.updateImageType(imageType);
    }

    /**
     * 批量删除图像类型
     * 
     * @param imageTypeids 需要删除的图像类型主键
     * @return 结果
     */
    @Override
    public int deleteImageTypeByImageTypeids(Long[] imageTypeids)
    {
        return imageTypeMapper.deleteImageTypeByImageTypeids(imageTypeids);
    }

    /**
     * 删除图像类型信息
     * 
     * @param imageTypeid 图像类型主键
     * @return 结果
     */
    @Override
    public int deleteImageTypeByImageTypeid(Long imageTypeid)
    {
        return imageTypeMapper.deleteImageTypeByImageTypeid(imageTypeid);
    }
}
