package com.yf.personcheck.model;

public class RetrofitBaseResult {
    @Override
    public String toString() {
        return "RetrofitBaseResult{" +
                "returnValue=" + returnValue +
                ", msg='" + msg + '\'' +
                '}';
    }

    /**
     * returnValue : 2
     * msg : 服务器繁忙，请稍后重试
     */

    private int returnValue;
    private String msg;

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
