package com.yf.personcheck.model;

public class IdentitySessionResp {


    /**
     * code : 200
     * msg : success
     * timestamp : 1589532517
     * result : {"msg":"张嘴;脸右摆;闭眼;睁眼","sessionId":"action_qjplZ5S7","type":1}
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
        @Override
        public String toString() {
            return "ResultBean{" +
                    "msg='" + msg + '\'' +
                    ", sessionId='" + sessionId + '\'' +
                    ", type=" + type +
                    '}';
        }

        /**
         * msg : 张嘴;脸右摆;闭眼;睁眼
         * sessionId : action_qjplZ5S7
         * type : 1
         */

        private String msg;
        private String sessionId;
        private int type;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
