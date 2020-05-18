package com.yf.personcheck.model;

public class ActionCheckResp {


    /**
     * code : 200
     * msg : success
     * timestamp : 1589786117
     * result : {"resultDetail":"0399007","resultMsg":"动作校验失败","similarity":""}
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
         * resultDetail : 0399007
         * resultMsg : 动作校验失败
         * similarity :
         */

        private String resultDetail;
        private String resultMsg;
        private String similarity;

        public String getResultDetail() {
            return resultDetail;
        }

        public void setResultDetail(String resultDetail) {
            this.resultDetail = resultDetail;
        }

        public String getResultMsg() {
            return resultMsg;
        }

        public void setResultMsg(String resultMsg) {
            this.resultMsg = resultMsg;
        }

        public String getSimilarity() {
            return similarity;
        }

        public void setSimilarity(String similarity) {
            this.similarity = similarity;
        }
    }
}
