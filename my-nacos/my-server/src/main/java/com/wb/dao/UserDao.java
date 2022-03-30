package com.wb.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wb.dto.UserDTO;
import com.wb.entity.UserEntity;
import com.wb.util.mybatis.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/11 17:00
 */
@Mapper
public interface UserDao extends BaseDao<UserEntity> {


    IPage<UserDTO> selectPageList(@Param("page") IPage<UserEntity> page,@Param("params") Map<String,Object> params);

}
