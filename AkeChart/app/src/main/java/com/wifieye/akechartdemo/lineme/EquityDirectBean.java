package com.wifieye.akechartdemo.lineme;

import java.util.List;

public class EquityDirectBean {


    /**
     * ErrorCode : 200
     * Data : [{"CloseTime":"2019-08-13 06:39:41","Equity":"86034.91"},{"CloseTime":"2019-07-31 09:57:25","Equity":"84772.49"},{"CloseTime":"2019-07-31 09:49:05","Equity":"84772.62"},{"CloseTime":"2019-07-31 09:14:50","Equity":"84772.08"},{"CloseTime":"2019-07-18 05:57:31","Equity":"9977.56"},{"CloseTime":"2019-07-17 03:04:55","Equity":"10000.00"}]
     * requestId : 4051ee70-df80-11e9-8c53-f9436fe2458d
     */

    private int ErrorCode;
    private String requestId;
    private List<DataBean> Data;

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * CloseTime : 2019-08-13 06:39:41
         * Equity : 86034.91
         */

        private String CloseTime;
        private String Equity;

        public String getCloseTime() {
            return CloseTime;
        }

        public void setCloseTime(String CloseTime) {
            this.CloseTime = CloseTime;
        }

        public String getEquity() {
            return Equity;
        }

        public void setEquity(String Equity) {
            this.Equity = Equity;
        }
    }
}
