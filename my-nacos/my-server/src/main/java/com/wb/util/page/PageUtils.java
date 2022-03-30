package com.wb.util.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;

import java.util.Map;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/12 13:51
 */
public class PageUtils {

    public static <T> IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
        long curPage = 1L;
        long limit = 10L;
        if (params.get("page") != null) {
            curPage = Long.parseLong((String) params.get("page"));
        }

        if (params.get("limit") != null) {
            limit = Long.parseLong((String) params.get("limit"));
        }

        Page<T> page = new Page<T>(curPage, limit);
        params.put("page", page);
        String orderField = (String) params.get("orderField");
        String order = (String) params.get("order");
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

    public static <T> IPage<T> changePage(IPage<?> page,Class<T> target){
        Page<T> rPage = new Page<>();
        BeanUtils.copyProperties(page,rPage);
        return rPage;
    }

}
