package com.wb.feign.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.wb.feign.UserApiClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/14 8:33
 */
@Slf4j
@Component
public class UserApiClientFallbackFactory implements FallbackFactory<UserApiClient> {

    @Override
    public UserApiClient create(Throwable throwable) {
        return params -> R.failed("分页查询失败");
    }
}
