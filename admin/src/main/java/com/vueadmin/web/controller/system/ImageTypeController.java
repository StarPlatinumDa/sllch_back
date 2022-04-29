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
import com.vueadmin.system.domain.ImageType;
import com.vueadmin.system.service.IImageTypeService;
import com.vueadmin.common.utils.poi.ExcelUtil;
import com.vueadmin.common.core.page.TableDataInfo;

/**
 * 图像类型Controller
 * 
 * @author ouch
 * @date 2022-04-27
 */
@RestController
@RequestMapping("/imageType/imagetype")
public class ImageTypeController extends BaseController
{
    @Autowired
    private IImageTypeService imageTypeService;

    /**
     * 查询图像类型列表
     */
    @PreAuthorize("@ss.hasPermi('imageType:imagetype:list')")
    @GetMapping("/list")
    public TableDataInfo list(ImageType imageType)
    {
        startPage();
        List<ImageType> list = imageTypeService.selectImageTypeList(imageType);
        return getDataTable(list);
    }

    /**
     * 导出图像类型列表
     */
    @PreAuthorize("@ss.hasPermi('imageType:imagetype:export')")
    @Log(title = "图像类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ImageType imageType)
    {
        List<ImageType> list = imageTypeService.selectImageTypeList(imageType);
        ExcelUtil<ImageType> util = new ExcelUtil<ImageType>(ImageType.class);
        util.exportExcel(response, list, "图像类型数据");
    }

    /**
     * 获取图像类型详细信息
     */
    @PreAuthorize("@ss.hasPermi('imageType:imagetype:query')")
    @GetMapping(value = "/{imageTypeid}")
    public AjaxResult getInfo(@PathVariable("imageTypeid") Long imageTypeid)
    {
        return AjaxResult.success(imageTypeService.selectImageTypeByImageTypeid(imageTypeid));
    }

    /**
     * 新增图像类型
     */
    @PreAuthorize("@ss.hasPermi('imageType:imagetype:add')")
    @Log(title = "图像类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ImageType imageType)
    {
        return toAjax(imageTypeService.insertImageType(imageType));
    }

    /**
     * 修改图像类型
     */
    @PreAuthorize("@ss.hasPermi('imageType:imagetype:edit')")
    @Log(title = "图像类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ImageType imageType)
    {
        return toAjax(imageTypeService.updateImageType(imageType));
    }

    /**
     * 删除图像类型
     */
    @PreAuthorize("@ss.hasPermi('imageType:imagetype:remove')")
    @Log(title = "图像类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{imageTypeids}")
    public AjaxResult remove(@PathVariable Long[] imageTypeids)
    {
        return toAjax(imageTypeService.deleteImageTypeByImageTypeids(imageTypeids));
    }
}
