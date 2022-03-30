package com.wb.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/15 16:36
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("commodity_item")
public class CommodityItemEntity {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "name")
    private String name;

    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

}
