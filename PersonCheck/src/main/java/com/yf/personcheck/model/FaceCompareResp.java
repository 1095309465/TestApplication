package com.yf.personcheck.model;

public class FaceCompareResp {


    /**
     * code : 200
     * msg : success
     * timestamp : 1589527979
     * result : {"resultDetail":"0000000","resultMsg":"同一个人","similarity":0.9912519}
     */

    private int code;
    private String msg;
    private long timestamp;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
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
         * resultDetail : 0000000
         * resultMsg : 同一个人
         * similarity : 0.9912519
         */

        private String resultDetail;
        private String resultMsg;
        private double similarity;

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

        public double getSimilarity() {
            return similarity;
        }

        public void setSimilarity(double similarity) {
            this.similarity = similarity;
        }
    }
}
