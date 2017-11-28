package com.bwie.yang1128;

public class LoginBean {
    private String msg;
    private String code;


    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}