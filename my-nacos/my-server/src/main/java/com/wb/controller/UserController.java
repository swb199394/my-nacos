package com.wb.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.wb.dto.UserDTO;
import com.wb.entity.UserEntity;
import com.wb.service.UserService;
import com.wb.util.BaseController;
import com.wb.util.Constant;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;
import java.util.logging.Logger;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/12 14:06
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController extends BaseController {

    @Autowired
    private UserService userServiceImpl;

    @RequestMapping("/test")
    public R<?> test(){
        return R.ok("成功！");
    }


    @PostMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int")
    })
    public R<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        log.info("userServiceImpl is null ："+ String.valueOf(userServiceImpl==null));
        return R.ok(userServiceImpl.page(params));
    }

    @PostMapping("pageList")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int")
    })
    public R<?> pageList(@ApiIgnore @RequestParam Map<String, Object> params) {
        return R.ok(userServiceImpl.pageList(params));
    }

    @PostMapping("save")
    @ApiOperation("保存")
    public R<?> save(@RequestBody UserEntity entity) {
        userServiceImpl.updateById(entity);
        return R.ok("");
    }

    @PostMapping("get")
    @ApiOperation("获取")
    public R<?> get(@RequestParam("id") Long id) {
        UserEntity entity = userServiceImpl.getById(id);
        return R.ok(entity);
    }

    @DeleteMapping
    @ApiOperation("删除")
    public R<?> deleteByName(@RequestBody String[] names){
        userServiceImpl.deleteByName(names);
        return R.ok("");
    }
}
