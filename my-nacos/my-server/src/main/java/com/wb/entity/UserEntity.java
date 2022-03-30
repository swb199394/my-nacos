package com.wb.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.wb.handler.ListTypeHandler;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/11 17:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "my_user",autoResultMap = true)
public class UserEntity {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "name")
    private String name;

    @ApiModelProperty(value = "age")
    private Integer age;

    @TableField(typeHandler = FastjsonTypeHandler.class)
    private List<String> coverUrl;

    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;
}
