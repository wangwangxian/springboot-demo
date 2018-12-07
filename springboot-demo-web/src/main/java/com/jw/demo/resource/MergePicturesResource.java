package com.jw.demo.resource;

import com.jw.demo.utils.MergePicturesUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pic")
public class MergePicturesResource {


    @ApiOperation(value="测试图片合并接口", notes="测试图片合并接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "icon", value = "二维码图片地址", required = true ,dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "source", value = "背景图地址", required = true ,dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "degree", value = "是否旋转", required = false ,dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "x", value = "二维码放置位置x值", required = true ,dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "y", value = "二维码放置位置y值", required = true ,dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "scale", value = "二维码图片缩放比例", required = true ,dataType = "string",paramType = "query"),
    })
    @RequestMapping(value = "/mergePic",method = RequestMethod.GET)
    public String mergePic(@RequestParam("icon") String icon,
                           @RequestParam("source") String source,
                           @RequestParam(value = "degree",required = false) Integer degree,
                           @RequestParam(value = "x",defaultValue = "0") int x,
                           @RequestParam(value = "y",defaultValue = "0") int y,
                           @RequestParam(value = "scale",defaultValue = "1") double scale) {
        String base64Pic = null;
        try {
            base64Pic = MergePicturesUtil.markImageBySingleIcon(icon,source,degree,x,y,scale);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64Pic;
    }

}
