package com.wb.service;

import com.wb.entity.CommodityItemEntity;
import com.wb.util.mybatis.BaseService;
import com.wb.util.page.PageData;

import java.util.Map;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/15 17:01
 */
public interface CommodityItemService extends BaseService<CommodityItemEntity> {

    void save(CommodityItemEntity entity);

    PageData<CommodityItemEntity> page(Map<String, Object> params);


}
