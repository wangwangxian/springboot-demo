package com.jw.demo.resource;

import com.jw.demo.domain.ResultMap;
import com.jw.demo.domain.WxQrcodeEntity;
import com.jw.demo.service.WxQrcodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/qrcode")
@Api(value = "WxQrcodeResource",description = "二维码相关接口")
public class WxQrcodeResource {

    @Autowired
    private WxQrcodeService qrcodeService;

    @ApiOperation(value="获取二维码列表", notes="获取二维码列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wxAppId", value = "微信appid", required = true ,dataType = "string",paramType = "query")
    })
    @RequestMapping(value = "/getQrcodes",method = RequestMethod.GET)
    public ResultMap<List<WxQrcodeEntity>> getQrcodes(@RequestParam(value = "wxAppId")String wxAppId) {
        WxQrcodeEntity wxQrcodeEntity = new WxQrcodeEntity();
        wxQrcodeEntity.setWxAppId(wxAppId);
        ResultMap resultMap = new ResultMap(200,"ok");
        List<WxQrcodeEntity> list = qrcodeService.seleteAccuracy(wxQrcodeEntity);
        resultMap.setData(list);
        return resultMap;
    }

    @ApiOperation(value="获取二维码", notes="获取二维码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wxAppId", value = "微信appid", required = true ,dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "id",value = "id",required = true,dataType = "integer",paramType = "query")
    })
    @RequestMapping(value = "/getQrcodeOne",method = RequestMethod.GET)
    public ResultMap<WxQrcodeEntity> getQrcodeOne(@RequestParam(value = "wxAppId")String wxAppId,
                                                  @RequestParam(value = "id")int id) {
        ResultMap resultMap = new ResultMap(200,"ok");
        WxQrcodeEntity wxQrcodeEntity = qrcodeService.getById(id);
        resultMap.setData(wxQrcodeEntity);
        return resultMap;
    }

    @ApiOperation(value="保存二维码", notes="保存二维码")
    @RequestMapping(value = "/saveQrcode",method = RequestMethod.POST)
    public ResultMap<WxQrcodeEntity> saveQrcode(@RequestBody WxQrcodeEntity wxQrcodeEntity) {
        ResultMap resultMap = new ResultMap(200,"ok");
        boolean flag = qrcodeService.add(wxQrcodeEntity);
        resultMap.setData(flag);
        return resultMap;
    }



}
