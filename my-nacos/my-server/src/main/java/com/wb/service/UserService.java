package com.wb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wb.dto.UserDTO;
import com.wb.entity.UserEntity;

import java.util.Map;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/11 16:27
 */
public interface UserService extends IService<UserEntity> {

    /**
     * 分页
     * @param params
     * @return
     */
    public IPage<UserDTO> page(Map<String, Object> params);

    public IPage<UserDTO> pageList(Map<String, Object> params);

    public void deleteByName(String[] names);

}
