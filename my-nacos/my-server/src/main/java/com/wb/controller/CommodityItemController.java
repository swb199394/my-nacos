package com.wb.controller;

import com.wb.entity.CommodityItemEntity;
import com.wb.service.CommodityItemService;
import com.wb.util.BaseController;
import com.wb.util.Constant;
import com.wb.util.Result;
import com.wb.util.page.PageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/15 17:16
 */
@RestController
@RequestMapping("/commodity/item")
@Api(tags = "commodityItem - 商品类型")
public class CommodityItemController extends BaseController {


    @Autowired
    private CommodityItemService commodityItemService;


    @PostMapping("/save")
    @ApiOperation("保存")
//    @NeedLogin(LoginBody.USER_TYPE.MERCHANT)
    public Result<?> save(@RequestBody CommodityItemEntity entity) {
        //效验数据
        commodityItemService.save(entity);
        return new Result<>();
    }

    @GetMapping("/page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
    })
    //@NeedLogin(LoginBody.USER_TYPE.MERCHANT)
    public Result<?> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<CommodityItemEntity> page = commodityItemService.page(params);
        return new Result<>().ok(page);
    }

    @GetMapping("/get")
    @ApiOperation(value = "信息", notes = "author:hlk")
    public Result<?> get(@Param("id") Long id) throws Exception {
        if(id == null){
            throw new RuntimeException("id不能为空");
        }
        CommodityItemEntity data = commodityItemService.selectById(id);
        return new Result<>().ok(data);
    }


}
