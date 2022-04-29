package com.vueadmin.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.vueadmin.common.annotation.Excel;
import com.vueadmin.common.core.domain.BaseEntity;

/**
 * 图像类型对象 image_type
 * 
 * @author ouch
 * @date 2022-04-27
 */
public class ImageType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 图像类型编号 */
    private Long imageTypeid;

    /** 图像类型 */
    @Excel(name = "图像类型")
    private String imageType;

    public void setImageTypeid(Long imageTypeid) 
    {
        this.imageTypeid = imageTypeid;
    }

    public Long getImageTypeid() 
    {
        return imageTypeid;
    }
    public void setImageType(String imageType) 
    {
        this.imageType = imageType;
    }

    public String getImageType() 
    {
        return imageType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("imageTypeid", getImageTypeid())
            .append("imageType", getImageType())
            .toString();
    }
}
