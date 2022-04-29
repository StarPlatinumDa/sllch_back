package com.vueadmin.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.vueadmin.common.annotation.Excel;
import com.vueadmin.common.core.domain.BaseEntity;

/**
 * 图像数据管理对象 image_info
 * 
 * @author ouch
 * @date 2022-04-24
 */
public class ImageInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 图像编号 */
    private String imageId;

    /** 图像类型 */
    @Excel(name = "图像类型")
    private Long imageTypeid;

    /** 图像链接 */
    @Excel(name = "图像链接")
    private String imageSrc;

    /** 图像录入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "图像录入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date imageCreatetime;

    /** 图像拍摄地点 */
    @Excel(name = "图像拍摄地点")
    private String imageShotplace;

    /** 图像注删 */
    @Excel(name = "图像注删")
    private String imageIsdelete;

    /** 图像备注 */
    @Excel(name = "图像备注")
    private String imageRemarks;

    /** 用户编号 */
    @Excel(name = "用户编号")
    private Long userId;

    public void setImageId(String imageId) 
    {
        this.imageId = imageId;
    }

    public String getImageId() 
    {
        return imageId;
    }
    public void setImageTypeid(Long imageTypeid) 
    {
        this.imageTypeid = imageTypeid;
    }

    public Long getImageTypeid() 
    {
        return imageTypeid;
    }
    public void setImageSrc(String imageSrc) 
    {
        this.imageSrc = imageSrc;
    }

    public String getImageSrc() 
    {
        return imageSrc;
    }
    public void setImageCreatetime(Date imageCreatetime) 
    {
        this.imageCreatetime = imageCreatetime;
    }

    public Date getImageCreatetime() 
    {
        return imageCreatetime;
    }
    public void setImageShotplace(String imageShotplace) 
    {
        this.imageShotplace = imageShotplace;
    }

    public String getImageShotplace() 
    {
        return imageShotplace;
    }
    public void setImageIsdelete(String imageIsdelete) 
    {
        this.imageIsdelete = imageIsdelete;
    }

    public String getImageIsdelete() 
    {
        return imageIsdelete;
    }
    public void setImageRemarks(String imageRemarks) 
    {
        this.imageRemarks = imageRemarks;
    }

    public String getImageRemarks() 
    {
        return imageRemarks;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("imageId", getImageId())
            .append("imageTypeid", getImageTypeid())
            .append("imageSrc", getImageSrc())
            .append("imageCreatetime", getImageCreatetime())
            .append("imageShotplace", getImageShotplace())
            .append("imageIsdelete", getImageIsdelete())
            .append("imageRemarks", getImageRemarks())
            .append("userId", getUserId())
            .toString();
    }
}
