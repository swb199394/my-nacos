package com.wb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wb.dao.CommodityItemMapper;
import com.wb.entity.CommodityItemEntity;
import com.wb.service.CommodityItemService;
import com.wb.util.Constant;
import com.wb.util.Result;
import com.wb.util.mybatis.BaseServiceImpl;
import com.wb.util.page.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/15 17:04
 */
@Service
public class CommodityItemServiceImpl extends BaseServiceImpl<CommodityItemMapper, CommodityItemEntity> implements CommodityItemService {

    @Autowired
    private CommodityItemMapper commodityItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(CommodityItemEntity entity) {
        Long id = entity.getId();
        if(id == null){
            insert(entity);
        } else {
            updateById(entity);
        }
    }

    @Override
    public PageData<CommodityItemEntity> page(Map<String, Object> params) {
        IPage<CommodityItemEntity> page = baseDao.selectPage(
            getPage(params, Constant.CREATE_DATE, false),
            getWrapper(params)
        );

        return getPageData(page, CommodityItemEntity.class);
    }

    private QueryWrapper<CommodityItemEntity> getWrapper(Map<String, Object> params) {
        QueryWrapper<CommodityItemEntity> wrapper = new QueryWrapper<>();
        return wrapper;
    }
}
