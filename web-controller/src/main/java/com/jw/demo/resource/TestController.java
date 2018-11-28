package com.jw.demo.resource;

import com.jw.demo.domain.ResultMap;
import org.apache.catalina.core.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/getServiceInfo",method = RequestMethod.GET)
    @ResponseBody
    public ResultMap getServiceInfo() {
        ResultMap resultMap = new ResultMap(200,"OK","这是一个测试接口");
        logger.info("getServiceInfo");
        return resultMap;
    }

}
