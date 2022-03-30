package com.wb.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.io.Serializable;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/16 8:33
 */
@ApiModel("响应")
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("编码：0表示成功，其他值表示失败")
    private int code = 0;
    @ApiModelProperty("消息内容")
    private String msg = "success";
    @ApiModelProperty("响应数据")
    private T data;

    public Result() {
    }

    public Result<T> ok(T data) {
        this.setData(data);
        return this;
    }

    public boolean success() {
        return this.code == 0;
    }

    public Result<T> error() {
        this.code = 500;
        this.msg = MessageUtils.getMessage(this.code);
        return this;
    }

    public Result<T> error(int code) {
        this.code = code;
        this.msg = MessageUtils.getMessage(this.code);
        return this;
    }

    public Result<T> error(int code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public Result<T> error(String msg) {
        this.code = 500;
        this.msg = msg;
        return this;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
