package com.yf.personcheck.model;

public class IdentityPhotoCheckResp {


    /**
     * code : 200
     * msg : success
     * timestamp : 1589531426
     * result : {"Feature":"AID6RAAAoEEZEdi8J4cIPrtnIb5Jvnq82RqCuyx2B71yEII8koIMvX9CDr07lAK+GhWzPVKTtDwmkQO+4XWvvF+51rwwxmE9c/QAPF+kKTxqIQk7wO/jPeoDb739fgW+hxSXvYX4QLzo/Jo7zibIPGhHAb2Q4G491WvdvdM/hbu2gqq9AffCPWD0IbgpCss9+V86PP2RGz0l1j48GXLVvXV9LTxBdda9Si+fPfBV+DxVmJY9eTf6Oxp+qLyjiBI9mwjCOfctO70G9ZA9xRZMPXc6ljxzBNk97ZziPaD45b2wNtk9TIiEPIdpoDyFQf+8Jq3kvUxvhrwRyuY6+PwUPs/Tq724dF+8UR92PYcok7u/pFa9RhzhPVKysLlZUJ26aGjBvact1D2DrEg9suMcvtZGmb2lT+w8N6M0PElvULg/nc69p4K8PfgXWL0bOuW9Vi3MvBcu0z13gp48ggpOOvd7BL3ejmE9/SS8uy1rOz1pMog9XxlCPFK4sLxggWY9AYi0u0HZQr2rqUm9REGJO5OWBL2bwT69H/42veXndr26XZS9wTaovUBhu71pzsI78e9tvL36uT11ixC9P43bvQAh8ryXl689etFavWUELbj7dZA8TfHuPb4fvT1TKeM8MwtOPEkDPb17VLK86+TmvFWpbj2I2ci9ngcRvu6cVbwG64A77jqiPWH8Az5v5vU83ui9OwCMl717DoC9uMIBPVjlrL2dLn09I0M9PYZm1D3Xt6C8Y30FPUv9bLyn9YG9/82fO9GzZD0Oa5G8SYoFu37pRb2Obak9Jw+IvIlhDL3Pu1S870INPoj0+D15I6G9M6+3O3DYgT1RjmE7EAU+vaYlir0WNHG86MQ7vVN/qT3gjhi9MGsXPknKP7xbKEq9hgocPcVI371vbYA9Vm5wPIL9HD2bIc09ScSXPatdZLs+Wl28UOIvPX2Fqr0LEEI9PX0cvYYrZL32wjc8yFzCPTt6bD13Waq7NaKvPa2KxDvQ1MQ9IYSfvdj7dT0KC4C9QT8OvHsaMb0z/VI8rrWBvB/2Pr46DIa9B0lHPh6NiD3NUzq9J9jfvRqRCbpx3Ne9t1LSuzpt27xO2u29kUppPf8DGL0jgAM9GGiHPMdnHb1BYZo9/uUnvHERuj1NnBY9rl95PS4Q7jyHgIm95gyfvGqu1z3ir527YNi+PYbmuDt51D+9gXArvPLhgb0mKM69K/EIvbk2cL0uUYK8vXYZvDmrpj2C1wE9HEwDvsG/vLwIu3w9LUCfvQfQBr5pStm88o6SPbuIHr016mc7ahYKOzoBX7yhhHK9YD/jvUq+Rb0IsP27wLy6vU38N73y7wI8jyFbPPUQq72FntU9O6wovXUt2zyFIky9","resultDetail":"0000000","resultMsg":"系统判断为同一人","similarity":"89"}
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
         * Feature : AID6RAAAoEEZEdi8J4cIPrtnIb5Jvnq82RqCuyx2B71yEII8koIMvX9CDr07lAK+GhWzPVKTtDwmkQO+4XWvvF+51rwwxmE9c/QAPF+kKTxqIQk7wO/jPeoDb739fgW+hxSXvYX4QLzo/Jo7zibIPGhHAb2Q4G491WvdvdM/hbu2gqq9AffCPWD0IbgpCss9+V86PP2RGz0l1j48GXLVvXV9LTxBdda9Si+fPfBV+DxVmJY9eTf6Oxp+qLyjiBI9mwjCOfctO70G9ZA9xRZMPXc6ljxzBNk97ZziPaD45b2wNtk9TIiEPIdpoDyFQf+8Jq3kvUxvhrwRyuY6+PwUPs/Tq724dF+8UR92PYcok7u/pFa9RhzhPVKysLlZUJ26aGjBvact1D2DrEg9suMcvtZGmb2lT+w8N6M0PElvULg/nc69p4K8PfgXWL0bOuW9Vi3MvBcu0z13gp48ggpOOvd7BL3ejmE9/SS8uy1rOz1pMog9XxlCPFK4sLxggWY9AYi0u0HZQr2rqUm9REGJO5OWBL2bwT69H/42veXndr26XZS9wTaovUBhu71pzsI78e9tvL36uT11ixC9P43bvQAh8ryXl689etFavWUELbj7dZA8TfHuPb4fvT1TKeM8MwtOPEkDPb17VLK86+TmvFWpbj2I2ci9ngcRvu6cVbwG64A77jqiPWH8Az5v5vU83ui9OwCMl717DoC9uMIBPVjlrL2dLn09I0M9PYZm1D3Xt6C8Y30FPUv9bLyn9YG9/82fO9GzZD0Oa5G8SYoFu37pRb2Obak9Jw+IvIlhDL3Pu1S870INPoj0+D15I6G9M6+3O3DYgT1RjmE7EAU+vaYlir0WNHG86MQ7vVN/qT3gjhi9MGsXPknKP7xbKEq9hgocPcVI371vbYA9Vm5wPIL9HD2bIc09ScSXPatdZLs+Wl28UOIvPX2Fqr0LEEI9PX0cvYYrZL32wjc8yFzCPTt6bD13Waq7NaKvPa2KxDvQ1MQ9IYSfvdj7dT0KC4C9QT8OvHsaMb0z/VI8rrWBvB/2Pr46DIa9B0lHPh6NiD3NUzq9J9jfvRqRCbpx3Ne9t1LSuzpt27xO2u29kUppPf8DGL0jgAM9GGiHPMdnHb1BYZo9/uUnvHERuj1NnBY9rl95PS4Q7jyHgIm95gyfvGqu1z3ir527YNi+PYbmuDt51D+9gXArvPLhgb0mKM69K/EIvbk2cL0uUYK8vXYZvDmrpj2C1wE9HEwDvsG/vLwIu3w9LUCfvQfQBr5pStm88o6SPbuIHr016mc7ahYKOzoBX7yhhHK9YD/jvUq+Rb0IsP27wLy6vU38N73y7wI8jyFbPPUQq72FntU9O6wovXUt2zyFIky9
         * resultDetail : 0000000
         * resultMsg : 系统判断为同一人
         * similarity : 89
         */

        private String Feature;
        private String resultDetail;
        private String resultMsg;
        private String similarity;

        public String getFeature() {
            return Feature;
        }

        public void setFeature(String Feature) {
            this.Feature = Feature;
        }

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
