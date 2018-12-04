package com.jw.common.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * @author jony
 * @date 2018
 */
public interface ExecuteMapper{
    /**
     * 直接根据SQL语句查询单条数据
     *
     * @param sql
     * @return
     */
    @SelectProvider(type = BaseSqlProvider.class, method = "selectSQLOne")
    Map<String, Object> selectOne(@Param(value = "sql") String sql);
//
//    /**
//     * 带参数查询单条数据
//     *
//     * @param sql select * from table where id = #{param.id} limit 1
//     * @param obj param
//     * @return
//     */
//    @SelectProvider(type = BaseSqlProvider.class, method = "selectOneByParam")
//    Map<String, Object> selectOneByParam(@Param(value = "sql") String sql, @Param(value = "param") Object obj);
//
//    /**
//     * 多参数查询单条数据
//     *
//     * @param sql
//     * @param args
//     * @return
//     */
//    @SelectProvider(type = BaseSqlProvider.class, method = "selectOneByArgs")
//    Map<String, Object> selectOneByArgs(@Param(value = "sql") String sql, @Param(value = "args") Object... args);
//
//    /**
//     * 直接执行sql查询列表数据
//     *
//     * @param sql select * from table where status = 1
//     * @return
//     */
//    @SelectProvider(type = BaseSqlProvider.class, method = "selectList")
//    List<Map<String, Object>> selectList(@Param(value = "sql") String sql);
//
//    /**
//     * 带参数查询多条数据
//     *
//     * @param sql select * from table where status = #{param.status}
//     * @param obj param
//     * @return
//     */
//    @SelectProvider(type = BaseSqlProvider.class, method = "selectSQLListByParam")
//    List<Map<String, Object>> selectListByParam(@Param(value = "sql") String sql, @Param(value = "param") Object obj);
//
//    /**
//     * 直接执行SQL插入数据
//     *
//     * @param sql
//     * @return
//     */
//    @SelectProvider(type = BaseSqlProvider.class, method = "insert")
//    Object insert(@Param(value = "sql") String sql);
//
//    /**
//     * 直接执行SQL语句更新数据
//     *
//     * @param sql
//     * @return
//     */
//    @SelectProvider(type = BaseSqlProvider.class, method = "update")
//    Object update(@Param(value = "sql") String sql);
//
//    /**
//     * 直接执行SQL删除数据
//     * @param sql
//     * @return
//     */
//    @SelectProvider(type = BaseSqlProvider.class,method = "delete")
//    Object delete(@Param(value = "sql")String sql);


}
