package com.jw.common.mybatis.mapper;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.jw.common.mybatis.annotation.CustColumn;
import com.jw.common.mybatis.annotation.CustId;
import com.jw.common.mybatis.annotation.CustOrderBy;
import com.jw.common.mybatis.annotation.CustTable;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jony
 * @date 2018/09/25
 */
public class BaseSqlProvider {

    private static Logger logger = LoggerFactory.getLogger(BaseSqlProvider.class);

    private static Map<Class<?>,List<Field>> classFieldsCache = Maps.newHashMap();

    private static Map<Class<?>,String> fieldStringCache = Maps.newHashMap();

    private static Map<Class<?>,String> selectStringCache = Maps.newHashMap();

    /**
     * 获取表名称
     * @param obj
     * @return
     */
    public static CustTable getMyTable(Object obj) {
        Class<?> clazz = obj instanceof Class<?> ? (Class<?>) obj : obj.getClass();
        return clazz.getAnnotation(CustTable.class);
    }

    /**
     * 插入实体的SQL
     *
     * @param obj insert的实体对象
     * @return 插入的SQL
     * @throws Exception
     */
    public static String insert(Object obj) throws Exception {
        SQL sql = new SQL();
        CustTable table = getMyTable(obj);
        sql.INSERT_INTO(table.value());
        for (Field field : getAllField(obj)) {
            field.setAccessible(true);
            Object colValue = field.get(obj);
            CustColumn column = field.getAnnotation(CustColumn.class);
            CustId id = field.getAnnotation(CustId.class);
            if (column != null && colValue == null) {
                continue;
            }
            if (id != null && colValue == null) {
                colValue = UUID.randomUUID().toString().replace("-", "");
                field.set(obj, colValue);
            }
            String colName;
            if (column != null) {
                colName = StringUtils.isBlank(column.value()) ? field.getName() : column.value();
            } else {
                colName = StringUtils.isBlank(id.value()) ? field.getName() : id.value();
            }
            sql.VALUES(String.format("`%s`", colName), String.format("#{%s}", field.getName()));
        }
        logger.debug("insert sql is {}", sql.toString());
        return sql.toString();
    }

    /**
     * 更新实体的SQL
     *
     * @param obj update的实体对象
     * @return 修改的SQL
     * @throws Exception
     */
    public static String update(Object obj) throws Exception {
        SQL sql = new SQL();
        CustTable table = getMyTable(obj);
        sql.UPDATE(table.value());
        Field idField = null;
        for (Field field : getAllField(obj)) {
            field.setAccessible(true);
            Object colValue = field.get(obj);
            CustColumn column = field.getAnnotation(CustColumn.class);
            CustId id = field.getAnnotation(CustId.class);
            if (column != null && colValue == null) {
                continue;
            }
            if (id != null) {
                idField = field;
                continue;
            }
            String colName = StringUtils.isBlank(column.value()) ? field.getName() : column.value();
            sql.SET(String.format("`%s` = #{%s}", colName, field.getName()));
        }
        if (idField != null) {
            CustId id = idField.getAnnotation(CustId.class);
            String idColName = StringUtils.isBlank(id.value()) ? idField.getName() : id.value();
            sql.WHERE(String.format("`%s` = #{%s}", idColName, idField.getName()));
        }
        logger.debug("update sql is {}", sql.toString());
        return sql.toString();
    }

    /**
     * 根据ID删除的sql
     *
     * @param obj 设置@MyId属性的对象
     * @return 删除的SQL
     * @throws Exception
     */
    public static String deleteById(Object obj) throws Exception {
        SQL sql = new SQL();
        CustTable table = getMyTable(obj);
        sql.DELETE_FROM(table.value());
        Field idField = getIdField(obj);
        CustId id = idField.getAnnotation(CustId.class);
        String idColName = StringUtils.isBlank(id.value()) ? idField.getName() : id.value();
        sql.WHERE(String.format("`%s` = #{%s}", idColName, idField.getName()));
        logger.debug("deleteById sql is {}", sql.toString());
        return sql.toString();
    }

    /**
     * 通过ID获取实体的sql
     *
     * @param obj 设置@MyId属性的对象
     * @return 查询的SQL
     * @throws Exception
     */
    public static String getById(Object obj) throws Exception {
        SQL sql = new SQL();
        CustTable table = getMyTable(obj);
        sql.SELECT(getAllFieldString(obj));
        sql.FROM(table.value());
        Field idField = getIdField(obj);
        CustId id = idField.getAnnotation(CustId.class);
        String idColName = StringUtils.isBlank(id.value()) ? idField.getName() : id.value();
        sql.WHERE(String.format("`%s` = #{%s}", idColName, idField.getName()));
        logger.debug("getById sql is {}", sql.toString());
        return sql.toString();
    }

    /**
     * 精确查询数据的sql生成
     *
     * @param obj 精确查询数据 实体
     * @return 精确查询数据的sql
     * @throws Exception
     */
    public static String seleteAccuracy(Object obj) throws Exception {
        return seleteAccuracyParamAlias("", obj);
    }

    /**
     * 精确查询一条的sql生成
     *
     * @param paramAlias 参数的前缀，组装SQL的是时候  paramAlias.pro
     * @param obj        精确查询一条数据 实体
     * @return 精确查询一条的sql
     * @throws Exception
     */
    public static String seleteAccuracyParamAlias(String paramAlias, Object obj) throws Exception {
        SQL sql = new SQL();
        CustTable table = getMyTable(obj);
        sql.SELECT(getAllFieldString(obj));
        sql.FROM(table.value());
        assemblyAccuracy(paramAlias, obj, sql);
        assemblyOrderBy(obj, sql);
        logger.debug("seleteAccuracy sql is {}", sql.toString());
        return sql.toString();
    }

    /**
     * 组装精确匹配的查询
     *
     * @param obj
     * @param sql
     * @throws Exception
     */
    public static void assemblyAccuracy(String paramAlias, Object obj, SQL sql) throws Exception {
        for (Field field : getAllField(obj)) {
            field.setAccessible(true);
            Object colValue = field.get(obj);
            CustColumn column = field.getAnnotation(CustColumn.class);
            if (column != null && colValue != null) {
                if ((colValue instanceof String) && StringUtils.isBlank(String.valueOf(colValue))) {
                    continue;
                }
                String colName = StringUtils.isBlank(column.value()) ? field.getName() : column.value();
                sql.WHERE(String.format("`%s` = #{%s%s}", colName, StringUtils.isNotEmpty(paramAlias) ? paramAlias + "." : "", field.getName()));
            }
        }
    }

    /**
     * 组装 order by
     *
     * @param obj
     * @param sql
     * @throws Exception
     */
    public static void assemblyOrderBy(Object obj, SQL sql) throws Exception {
        Field orderField = getOrderByField(obj);
        if (orderField != null) {
            Object order = orderField.get(obj);
            if (order != null) {
                String orderby[] = String.valueOf(order).split("\\s+");
                if (orderby.length == 2) {
                    String field = orderby[0];
                    try {
                        Field temp = ReflectionUtils.getAllFields(obj.getClass(), ReflectionUtils.withName(field)).iterator().next();
                        temp.setAccessible(true);
                        CustColumn column = temp.getAnnotation(CustColumn.class);
                        if (column != null) {
                            if (StringUtils.isEmpty(column.value())) {
                                sql.ORDER_BY(String.valueOf(order));
                            } else {
                                sql.ORDER_BY(column.value() + " " + orderby[1]);
                            }
                        } else {
                            sql.ORDER_BY(String.valueOf(order));
                        }
                    } catch (Exception e) {
                        sql.ORDER_BY(String.valueOf(order));
                    }
                } else {
                    sql.ORDER_BY(String.valueOf(order));
                }
            }
        }
    }

    /**
     * 获取排序字段@MyOrderBy
     *
     * @param obj 可以是Dict.class 也可以是new Dict()
     * @return
     */
    public static Field getOrderByField(Object obj) {
        Field orderbyField = null;
        Class<?> clazz = obj instanceof Class<?> ? (Class) obj : obj.getClass();
        while (!clazz.equals(Object.class)) {
            Optional<Field> optional =
                    ReflectionUtils.getFields(clazz, ReflectionUtils.withAnnotation(CustOrderBy.class)).stream().findFirst();
            if (optional.isPresent()) {
                orderbyField = optional.get();
                orderbyField.setAccessible(true);
                break;
            }
            clazz = clazz.getSuperclass();
        }
        return orderbyField;
    }


    /**
     * 获取所有的带有@MyId和@MyColumn的列的，insert，select字符串
     *
     * @param obj 实体，可以是Dict.class 也可以是new Dict()
     * @return 所有字段的sql映射，不带表名
     */
    public static String getAllFieldString(Object obj) {
        Class<?> clazz = obj instanceof Class<?> ? (Class) obj : obj.getClass();
        if (fieldStringCache.containsKey(clazz)) {
            return fieldStringCache.get(clazz);
        }
        String sql = getAllFieldString(obj, "");
        fieldStringCache.put(clazz, sql);
        return sql;
    }

    /**
     * 获取带有@MyId的field
     *
     * @param obj 可以是Dict.class 也可以是new Dict()
     * @return field对象
     */
    public static Field getIdField(Object obj) {
        Field idField = null;
        Class<?> clazz = obj instanceof Class<?> ? (Class) obj : obj.getClass();
        while (!clazz.equals(Object.class)) {
            Optional<Field> optional =
                    ReflectionUtils.getFields(clazz, ReflectionUtils.withAnnotation(CustId.class)).stream().findFirst();
            if (optional.isPresent()) {
                idField = optional.get();
                idField.setAccessible(true);
                break;
            }
            clazz = clazz.getSuperclass();
        }
        return idField;
    }

    /**
     * 获取所有的带有@MyId和@MyColumn的列的，inser，select字符串
     *
     * @param obj
     * @param alias 表的别名
     * @return
     */
    public static String getAllFieldString(Object obj, String alias) {
        StringBuilder sb = new StringBuilder(500);
        getAllField(obj).forEach(field -> {
            CustColumn column = field.getAnnotation(CustColumn.class);
            CustId id = field.getAnnotation(CustId.class);
            if (column != null) {
                sb.append(StringUtils.isEmpty(alias) ? "`" : alias + ".`").append(StringUtils.isBlank(column.value()) ? field.getName() : column.value()).append("`");
            } else if (id != null) {
                sb.append(StringUtils.isEmpty(alias) ? "`" : alias + ".`").append(StringUtils.isBlank(id.value()) ? field.getName() : id.value()).append("`");
            }
            if (column != null || id != null) {
                sb.append(" as ").append("`").append(field.getName()).append("`").append(",");
            }
        });
        sb.setCharAt(sb.length() - 1, ' ');
        return sb.toString();
    }

    /**
     * 获取所有的带有@MyId和@MyColumn的列
     *
     * @param obj 实体，可以是Dict.class 也可以是new Dict()
     * @return Field的集合
     */
    public static List<Field> getAllField(Object obj) {
        Class<?> clazz = obj instanceof Class<?> ? (Class) obj : obj.getClass();
        Class<?> objectClass = clazz;
        if (classFieldsCache.containsKey(clazz)) {
            return classFieldsCache.get(clazz);
        }
        List<Field> fields = Lists.newArrayList();
        Set<String> nameSet = Sets.newHashSet();
        while (!clazz.equals(Object.class)) {
            List<Field> current = ReflectionUtils.getAllFields(clazz).stream()
                    .filter(field -> ReflectionUtils.getAnnotations(field, input -> input.annotationType().equals(CustId.class) || input.annotationType().equals(CustColumn.class)).size() > 0)
                    .collect(Collectors.toList());
            current.stream().filter(f -> !nameSet.contains(f.getName())).forEach(f -> {
                fields.add(f);
                nameSet.add(f.getName());
            });
            clazz = clazz.getSuperclass();
        }
        classFieldsCache.put(objectClass, fields);
        return fields;
    }
}
