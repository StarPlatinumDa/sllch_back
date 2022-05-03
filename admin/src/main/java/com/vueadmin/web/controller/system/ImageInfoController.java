package com.vueadmin.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vueadmin.common.annotation.Log;
import com.vueadmin.common.core.controller.BaseController;
import com.vueadmin.common.core.domain.AjaxResult;
import com.vueadmin.common.enums.BusinessType;
import com.vueadmin.system.domain.ImageInfo;
import com.vueadmin.system.service.IImageInfoService;
import com.vueadmin.common.utils.poi.ExcelUtil;
import com.vueadmin.common.core.page.TableDataInfo;

/**
 * 图像数据管理Controller
 * 
 * @author vueadmin
 * @date 2022-04-24
 */
@RestController
@RequestMapping("/imageInfo/imagemanage")
public class ImageInfoController extends BaseController
{
    @Autowired
    private IImageInfoService imageInfoService;

    /**
     * 查询图像数据管理列表
     */
    //@PreAuthorize("@ss.hasPermi('imageInfo:imagemanage:list')")
    @GetMapping("/list")
    public TableDataInfo list(ImageInfo imageInfo)
    {
        startPage();
        imageInfo.setUserId(getLoginUser().getUserId());
        List<ImageInfo> list = imageInfoService.selectImageInfoList(imageInfo);
        return getDataTable(list);
    }

    /**
     * 导出图像数据管理列表
     */
    @PreAuthorize("@ss.hasPermi('imageInfo:imagemanage:export')")
    @Log(title = "图像数据管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ImageInfo imageInfo)
    {
        List<ImageInfo> list = imageInfoService.selectImageInfoList(imageInfo);
        ExcelUtil<ImageInfo> util = new ExcelUtil<ImageInfo>(ImageInfo.class);
        util.exportExcel(response, list, "图像数据管理数据");
    }

    /**
     * 获取图像数据管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('imageInfo:imagemanage:query')")
    @GetMapping(value = "/{imageId}")
    public AjaxResult getInfo(@PathVariable("imageId") String imageId)
    {
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setUserId(getLoginUser().getUserId());
        List<ImageInfo> list = imageInfoService.selectImageInfoList(imageInfo);
        for (int i=0;i<list.size();i++){
            if (list.get(i).getImageId().equals(imageId)){
                return AjaxResult.success(list.get(i));
            }
        }
        return AjaxResult.success("没有找到该数据");
    }

    /**
     * 新增图像数据管理
     */
    //@PreAuthorize("@ss.hasPermi('imageInfo:imagemanage:add')")
    @Log(title = "图像数据管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ImageInfo imageInfo)
    {
        return toAjax(imageInfoService.insertImageInfo(imageInfo));
    }

    /**
     * 修改图像数据管理
     */
    //@PreAuthorize("@ss.hasPermi('imageInfo:imagemanage:edit')")
    @Log(title = "图像数据管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ImageInfo imageInfo)
    {
        return toAjax(imageInfoService.updateImageInfo(imageInfo));
    }

    /**
     * 删除图像数据管理
     */
    //@PreAuthorize("@ss.hasPermi('imageInfo:imagemanage:remove')")
    @Log(title = "图像数据管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{imageIds}")
    public AjaxResult remove(@PathVariable String[] imageIds)
    {
        return toAjax(imageInfoService.deleteImageInfoByImageIds(imageIds));
    }
}
