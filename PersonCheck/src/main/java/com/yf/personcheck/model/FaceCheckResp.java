package com.yf.personcheck.model;

public class FaceCheckResp {


    /**
     * code : 200
     * msg : success
     * timestamp : 1589523160
     * result : {"feature":"","resultDetail":"0000001"}
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
         * feature :
         * resultDetail : 0000001
         */

        private String feature;
        private String resultDetail;

        public String getFeature() {
            return feature;
        }

        public void setFeature(String feature) {
            this.feature = feature;
        }

        public String getResultDetail() {
            return resultDetail;
        }

        public void setResultDetail(String resultDetail) {
            this.resultDetail = resultDetail;
        }
    }
}
