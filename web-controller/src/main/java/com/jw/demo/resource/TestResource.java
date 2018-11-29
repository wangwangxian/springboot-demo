package com.jw.demo.resource;

import com.alibaba.fastjson.JSONObject;
import com.jw.demo.domain.ResultMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/test")
@Api(value = "TestController",description = "测试controller")
public class TestResource {

    private Logger logger = LoggerFactory.getLogger(TestResource.class);

    @ApiOperation(value="测试接口", notes="测试接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param1", value = "参数1", required = true ,dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "param2", value = "参数2", required = false ,dataType = "string",paramType = "query")
    })
    @RequestMapping(value = "/getServiceInfo",method = RequestMethod.GET)
    public ResultMap getServiceInfo(@RequestParam("param1") String param1,
                                    @RequestParam(value = "param2",required = false)String param2) {
        ResultMap resultMap = new ResultMap(200,"OK");
        logger.info("getServiceInfo");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("param1",param1);
        jsonObject.put("param2",param2);
        resultMap.setData(jsonObject);
        return resultMap;
    }

}
