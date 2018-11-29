package com.jw.common.mybatis.service;

import com.jw.common.mybatis.mapper.ExecuteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class ExecuteService {

    /**
     * slf4j日志
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 执行SQL的executeMapper
     */
    @Autowired(required = false)
    private ExecuteMapper executeMapper;
}
