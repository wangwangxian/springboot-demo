package com.jw.demo.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;

@RestController
public class HomeResource {

    @ApiIgnore
    @RequestMapping(value = "/swagger",method = RequestMethod.GET)
    public void index(HttpServletResponse response) throws Exception{
        response.sendRedirect("swagger-ui.html");
    }
}
