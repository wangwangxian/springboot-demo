package com.jw.common.mybatis.mapper;


import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * @author jony
 * @date 2018/09/25 15:07
 * @param <T>
 */
public interface BaseMapper<T> extends ExecuteMapper{


    /**
     * 插入实体
     *
     * @param t
     * @return
     */
    @InsertProvider(type = BaseSqlProvider.class, method = "insert")
    int insert(T t);

    /**
     * 更新实体
     *
     * @param t
     * @return
     */
    @UpdateProvider(type = BaseSqlProvider.class, method = "update")
    int update(T t);

    /**
     * 根据ID删除实体
     *
     * @param t
     * @return
     */
    @DeleteProvider(type = BaseSqlProvider.class, method = "deleteById")
    int deleteById(T t);

    /**
     * 根据ID获取实体
     *
     * @param t
     * @return
     */
    @SelectProvider(type = BaseSqlProvider.class, method = "getById")
    T getById(T t);

    /**
     * 准确查询实体
     *
     * @param t
     * @return
     */
    @SelectProvider(type = BaseSqlProvider.class, method = "seleteAccuracy")
    List<T> seleteAccuracy(T t);

}
