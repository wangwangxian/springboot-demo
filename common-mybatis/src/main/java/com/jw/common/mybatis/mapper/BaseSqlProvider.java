package com.jw.common.mybatis.mapper;


import com.google.common.collect.Maps;
import com.jw.common.mybatis.annotation.CustTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @author jony
 * @date 2018/09/25
 */
public class BaseSqlProvider {

    private static Logger logger = LoggerFactory.getLogger(BaseSqlProvider.class);

    private Map<Class<?>,List<Field>> classFieldsCache = Maps.newHashMap();

    private Map<Class<?>,String> fieldStringCache = Maps.newHashMap();

    private Map<Class<?>,String> selectStringCache = Maps.newHashMap();

    /**
     * 获取表名称
     * @param obj
     * @return
     */
    public static CustTable getMyTable(Object obj) {
        Class<?> clazz = obj instanceof Class<?> ? (Class<?>) obj : obj.getClass();
        return clazz.getAnnotation(CustTable.class);
    }



}
