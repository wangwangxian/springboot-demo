package com.jw.common.mybatis.service;

import com.jw.common.mybatis.exception.MapperUnsuitedException;
import com.jw.common.mybatis.mapper.BaseMapper;
import com.jw.common.mybatis.mapper.BaseSqlProvider;
import org.apache.ibatis.binding.MapperProxy;
import org.reflections.ReflectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author jony
 * @date 2018/09/25 15:07
 */
@SuppressWarnings("all")
public abstract class BaseService<M extends BaseMapper<T>, T> extends ExecuteService implements InitializingBean, CommandLineRunner {

    /**
     * 实体mapper
     */
    @Autowired
    protected M mapper;
    /**
     * 具体操作的实体类
     */
    private Class<T> clazz;

//    /**
//     * 一对多映射关系
//     */
//    private List<ManyContext> manyContexts;
//
//    /**
//     * 一对多映射关系
//     */
//    private List<OneContext> oneContexts;

    /**
     * 添加实体
     *
     * @param t
     * @return
     */
    @Transactional
    public boolean add(T t) {
        logger.debug("add {},value is {}", clazz.getSimpleName(), t);
        int count = mapper.insert(t);
        return count > 0;
    }
    /**
     * 更新实体
     *
     * @param t
     * @return
     */
    @Transactional
    public boolean update(T t) {
        logger.debug("update {},value is {}", clazz.getSimpleName(), t);
        return mapper.update(t) > 0;
    }

    /**
     * 保存或者修改实体
     *
     * @param t
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean saveOrUpdate(T t) throws Exception {
        logger.debug("saveOrUpdate {},value is {}", clazz.getSimpleName(), t);
        Field field = BaseSqlProvider.getIdField(t);
        Object id = field.get(t);
        boolean updateFlag = id != null && getById(id) != null;
        if (updateFlag) {
            return update(t);
        } else {
            return add(t);
        }
    }

    /**
     * 根据ID删除
     *
     * @param id
     * @return
     */
    @Transactional
    public boolean deleteById(Object id) {
        logger.debug("deleteById {},id value is {}", clazz.getSimpleName(), id);
        T t = assembly(id);
        return mapper.deleteById(t) > 0;
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */

    @Transactional(readOnly = true)
    public T getById(Object id) {
        logger.debug("getById {},id value is {}", clazz.getSimpleName(), id);
        T t = assembly(id);
        t = mapper.getById(t);
//        assemblyMany(t);
//        assemblyOne(t);
        return t;
    }

    /**
     * 精确匹配查询
     *
     * @param t
     * @return
     */
    @Transactional(readOnly = true)
    public List<T> seleteAccuracy(T t) {
        logger.debug("seleteAccuracy {},value is {}", clazz.getSimpleName(), t);
        return mapper.seleteAccuracy(t);
    }

    /**
     * 通过ID，反射创建实体
     *
     * @param id
     * @return
     */
    private T assembly(Object id) {
        try {
            T t = clazz.newInstance();
            Field field = BaseSqlProvider.getIdField(t);
            field.set(t,id);
            return t;
        } catch (Exception e) {
            logger.error("assembly entity with id error", e);
            return null;
        }
    }


    /*
     * 获取泛型的class
     * 获取一对一，一对多的关系
     * @see
     * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    @SuppressWarnings("all")
    public void afterPropertiesSet() throws Exception {
        Type type = this.getClass().getGenericSuperclass();
        ParameterizedType pt = ParameterizedType.class.cast(type);
        clazz = Class.class.cast(pt.getActualTypeArguments()[1]);
        Class<?> interfaces = Class.class.cast(pt.getActualTypeArguments()[0]);
        /*检测mapper是否合规*/
        if (mapper != null && !interfaces.isAssignableFrom(mapper.getClass())) {
            String msg = "[%s] required mapper is [%s],but spring autowired is [%s]";
            MapperProxy<?> mapperProxy = MapperProxy.class.cast(Proxy.getInvocationHandler(mapper));
            Field field = ReflectionUtils.getAllFields(mapperProxy.getClass(), ReflectionUtils.withName("mapperInterface")).iterator().next();
            field.setAccessible(true);
            String proxyMapper = Class.class.cast(field.get(mapperProxy)).getName();
            msg = String.format(msg, getClass().getName(), interfaces.getName(), proxyMapper);
            throw new MapperUnsuitedException(msg);
        }
        logger.info("the {} service's entity is {},mapper is {}", getClass().getName(), clazz.getName(), interfaces.getName());
//        manyContexts = ManyContext.analysisMetedata(clazz);
//        if (manyContexts.size() > 0) {
//            logger.info("assembly the one2many relation success");
//            manyContexts.forEach(context ->
//                    logger.info("\none2many field is {},one refrece field is {},many refrence filed is {} ,many class is {}",
//                            context.getRelationField().getName(), context.getOwnField().getName(), context.getOtherField().getName(), context.getOtherClass().getName())
//            );
//        }
//        oneContexts = OneContext.analysisMetedata(clazz);
//        if (oneContexts.size() > 0) {
//            logger.info("assembly the one2one relation success");
//            oneContexts.forEach(context ->
//                    logger.info("\none2one field is {},one refrece field is {},many refrence filed is {} ,many class is {}",
//                            context.getRelationField().getName(), context.getOwnField().getName(), context.getOtherField().getName(), context.getOtherClass().getName())
//            );
//        }
    }

    /**
     * 解析baseService
     *
     * @param args
     */
    @Override
    public void run(String... args) {
//        if (manyContexts.size() > 0) {
//            logger.info("assembly the one2many relation baseService");
//            manyContexts.forEach(ManyContext::analysisBaseService);
//        }
//        if (oneContexts.size() > 0) {
//            logger.info("assembly the one2one relation baseService");
//            oneContexts.forEach(OneContext::analysisBaseService);
//        }
    }

}
