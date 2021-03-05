package com.atguigu.common.exception;

/**
 * @author yanmz
 * @version 1.0
 * @date 2020/11/8 13:25
 */
public enum  BizCodeEnume {
    UNKONW_EXCEPTION(10000,"系统未知异常"),
    VAILD_EXCEPTION(10001,"参数格式检验失败");

    private Integer  code;
    private String  msg;

    BizCodeEnume(Integer  code,String  msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode(){
        return code;
    }

    public String getMsg(){
        return  msg;
    }
}
