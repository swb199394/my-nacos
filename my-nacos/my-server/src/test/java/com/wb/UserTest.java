package com.wb;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wb.controller.UserController;
import com.wb.entity.UserEntity;
import com.wb.feign.UserApiClient;
import com.wb.service.UserService;
import com.wb.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/14 8:46
 */
@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class UserTest {





    @Autowired
    WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Autowired
    private UserApiClient userApiClient;

    @Autowired
    private UserService userServiceImpl;

    @Autowired
    private UserController userController;

    @Test
    public void UserPageListTest() throws Exception {
        String url = "/user/pageList";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("num","20");
        ResultActions resultAction = mockMvc.perform(MockMvcRequestBuilders.post(url).characterEncoding(Constant.UTF_8).queryParams(params));
        resultAction.andExpect(MockMvcResultMatchers.status().isOk());
        resultAction.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultAction.andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void queryTest(){

        Long[] ids = {1L,2L};
        List<UserEntity> list = null;
//        LambdaQueryWrapper<UserEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.in(UserEntity::getId, (Object[]) ids);
//        list = userServiceImpl.list(lambdaQueryWrapper);
//        list.forEach(System.out::println);

        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);

        list = userServiceImpl.list(queryWrapper);
        list.forEach(System.out::println);
    }

}
