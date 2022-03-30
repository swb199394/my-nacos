package com.wb.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/15 16:39
 */
@Data
@ApiModel(value = "数字社区商品类型")
public class CommodityItemDTO {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "类型名称")
    private String name;


}
