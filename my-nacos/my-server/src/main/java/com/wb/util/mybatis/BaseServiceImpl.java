package com.wb.util.mybatis;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.wb.util.ConvertUtils;
import com.wb.util.page.PageData;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/15 17:05
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> implements BaseService<T> {
    @Autowired
    protected M baseDao;

    public BaseServiceImpl() {
    }

    protected IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
        long curPage = 1L;
        long limit = 10L;
        if (params.get("page") != null) {
            curPage = Long.parseLong((String)params.get("page"));
        }

        if (params.get("limit") != null) {
            limit = Long.parseLong((String)params.get("limit"));
        }

        Page<T> page = new Page(curPage, limit);
        params.put("page", page);
        String orderField = (String)params.get("orderField");
        String order = (String)params.get("order");
        if (StringUtils.isNotBlank(orderField) && StringUtils.isNotBlank(order)) {
            return "asc".equalsIgnoreCase(order) ? page.addOrder(new OrderItem[]{OrderItem.asc(orderField)}) : page.addOrder(new OrderItem[]{OrderItem.desc(orderField)});
        } else if (StringUtils.isBlank(defaultOrderField)) {
            return page;
        } else {
            if (isAsc) {
                page.addOrder(new OrderItem[]{OrderItem.asc(defaultOrderField)});
            } else {
                page.addOrder(new OrderItem[]{OrderItem.desc(defaultOrderField)});
            }

            return page;
        }
    }

    protected <T> PageData<T> getPageData(List<?> list, long total, Class<T> target) {
        List<T> targetList = ConvertUtils.sourceToTarget(list, target);
        return new PageData(targetList, total);
    }

    protected <T> PageData<T> getPageData(IPage page, Class<T> target) {
        return this.getPageData(page.getRecords(), page.getTotal(), target);
    }

    protected Map<String, Object> paramsToLike(Map<String, Object> params, String... likes) {
        String[] var3 = likes;
        int var4 = likes.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String like = var3[var5];
            String val = (String)params.get(like);
            if (StringUtils.isNotBlank(val)) {
                params.put(like, "%" + val + "%");
            } else {
                params.put(like, (Object)null);
            }
        }

        return params;
    }

    protected static boolean retBool(Integer result) {
        return SqlHelper.retBool(result);
    }

    protected Class<T> currentModelClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(this.getClass(), 1);
    }

    protected SqlSession sqlSessionBatch() {
        return SqlHelper.sqlSessionBatch(this.currentModelClass());
    }

    protected void closeSqlSession(SqlSession sqlSession) {
        SqlSessionUtils.closeSqlSession(sqlSession, GlobalConfigUtils.currentSessionFactory(this.currentModelClass()));
    }

    protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(this.currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }

    @Override
    public boolean insert(T entity) {
        return retBool(this.baseDao.insert(entity));
    }

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean insertBatch(Collection<T> entityList) {
        return this.insertBatch(entityList, 100);
    }

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean insertBatch(Collection<T> entityList, int batchSize) {
        SqlSession batchSqlSession = this.sqlSessionBatch();
        int i = 0;
        String sqlStatement = this.sqlStatement(SqlMethod.INSERT_ONE);

        try {
            for(Iterator var6 = entityList.iterator(); var6.hasNext(); ++i) {
                T anEntityList = (T) var6.next();
                batchSqlSession.insert(sqlStatement, anEntityList);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
            }

            batchSqlSession.flushStatements();
        } finally {
            this.closeSqlSession(batchSqlSession);
        }

        return true;
    }

    @Override
    public boolean updateById(T entity) {
        return retBool(this.baseDao.updateById(entity));
    }

    @Override
    public boolean update(T entity, Wrapper<T> updateWrapper) {
        return retBool(this.baseDao.update(entity, updateWrapper));
    }

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean updateBatchById(Collection<T> entityList) {
        return this.updateBatchById(entityList, 30);
    }

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        } else {
            SqlSession batchSqlSession = this.sqlSessionBatch();
            int i = 0;
            String sqlStatement = this.sqlStatement(SqlMethod.UPDATE_BY_ID);

            try {
                for(Iterator var6 = entityList.iterator(); var6.hasNext(); ++i) {
                    T anEntityList = (T) var6.next();
                    MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap();
                    param.put("et", anEntityList);
                    batchSqlSession.update(sqlStatement, param);
                    if (i >= 1 && i % batchSize == 0) {
                        batchSqlSession.flushStatements();
                    }
                }

                batchSqlSession.flushStatements();
                return true;
            } finally {
                this.closeSqlSession(batchSqlSession);
            }
        }
    }

    @Override
    public T selectById(Serializable id) {
        return this.baseDao.selectById(id);
    }

    @Override
    public boolean deleteById(Serializable id) {
        return SqlHelper.retBool(this.baseDao.deleteById(id));
    }

    @Override
    public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
        return SqlHelper.retBool(this.baseDao.deleteBatchIds(idList));
    }
}
