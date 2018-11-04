package com.lx.user.abs;

import java.io.Serializable;

/**
 * @author 赵志伟
 * @ClassName: AbstractResponse
 * @description [描述该类的功能]
 * @create 2018-07-06 下午2:20
 **/
public abstract class AbstractResponse implements Serializable{

    private static final long serialVersionUID = 5704430076796818950L;
    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}