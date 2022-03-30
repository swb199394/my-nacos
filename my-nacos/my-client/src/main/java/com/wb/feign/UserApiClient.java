package com.wb.feign;

import com.baomidou.mybatisplus.extension.api.R;
import com.wb.feign.impl.UserApiClientFallbackFactory;
import com.wb.utils.ServiceConstant;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/14 8:24
 */
@FeignClient(name = ServiceConstant.SERVER_NAME, contextId = "UserApiFeignClient", fallbackFactory = UserApiClientFallbackFactory.class)
public interface UserApiClient {

    @RequestMapping(value = "/mytest/user/pageList", method = RequestMethod.POST)
    R<?> pageLis(@RequestParam Map<String, Object> params);
}
