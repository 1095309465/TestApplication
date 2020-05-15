package com.yf.personcheck.model;

public class TokenResp {

    @Override
    public String toString() {
        return "TokenResp{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", timestamp=" + timestamp +
                ", result=" + result +
                '}';
    }

    /**
     * code : 200
     * msg : success
     * timestamp : 1589506777
     * result : {"accessToken":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBpZCI6IjY4MDEyMzE1IiwiZWF0IjoxNTkyMDk4Nzc3LCJpYXQiOjE1ODk1MDY3NzcsIm5iZiI6MTU4OTUwNjc3N30._PyYQSY3c4O0URGAZSUaU6JwhpPe0jqHXeKcqrKL4LQ","expiressIn":1592098777}
     */

    private int code;
    private String msg;
    private int timestamp;
    private ResultBean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * accessToken : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBpZCI6IjY4MDEyMzE1IiwiZWF0IjoxNTkyMDk4Nzc3LCJpYXQiOjE1ODk1MDY3NzcsIm5iZiI6MTU4OTUwNjc3N30._PyYQSY3c4O0URGAZSUaU6JwhpPe0jqHXeKcqrKL4LQ
         * expiressIn : 1592098777
         */

        private String accessToken;
        private int expiressIn;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public int getExpiressIn() {
            return expiressIn;
        }

        public void setExpiressIn(int expiressIn) {
            this.expiressIn = expiressIn;
        }
    }
}
