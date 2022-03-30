package com.wb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wb.dao.UserDao;
import com.wb.dto.UserDTO;
import com.wb.entity.UserEntity;
import com.wb.service.UserService;
import com.wb.util.Constant;
import com.wb.util.page.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/11 16:58
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public IPage<UserDTO> page(Map<String, Object> params){
        IPage<UserEntity> page = baseMapper.selectPage(PageUtils.getPage(params, Constant.CREATE_DATE, false),getWrapper(params));
        return PageUtils.changePage(page,UserDTO.class);
    }

    @Override
    public IPage<UserDTO> pageList(Map<String, Object> params) {
        return baseMapper.selectPageList(PageUtils.getPage(params,Constant.CREATE_DATE, false),params);
    }

    @Override
    public void deleteByName(String[] names) {
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(UserEntity::getName, Arrays.toString(names));
        userDao.delete(wrapper);
    }

    private QueryWrapper<UserEntity> getWrapper(Map<String, Object> params) {
        String time = (String) params.get("time");
        String num = (String) params.get("num");
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
//        wrapper.eq(StringUtils.isNotBlank(id), "id", id);
        wrapper.apply(StringUtils.isNotBlank(time)," (to_days(create_date) <= TO_DAYS({0})) ",time)
                .and(StringUtils.isNotBlank(num),q->q.eq("id",num).or().eq("age",num));
        return wrapper;
    }

}
