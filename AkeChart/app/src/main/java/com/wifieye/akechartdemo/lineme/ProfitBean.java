package com.wifieye.akechartdemo.lineme;

import java.util.List;

public class ProfitBean {


    /**
     * ErrorCode : 200
     * Data : [{"Month":"2019-07","Profit":"-5.53"},{"Month":"2019-08","Profit":"-8.44"},{"Month":"2019-09","Profit":"-8.26"},{"Month":"2019-10","Profit":"-15.52"},{"Month":"2019-11","Profit":"-6.89"}]
     * requestId : 897daa30-0f3c-11ea-8dea-6de3494e64fa
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
         * Month : 2019-07
         * Profit : -5.53
         */

        private String Month;
        private String Profit;

        public String getMonth() {
            return Month;
        }

        public void setMonth(String Month) {
            this.Month = Month;
        }

        public String getProfit() {
            return Profit;
        }

        public void setProfit(String Profit) {
            this.Profit = Profit;
        }
    }
}
