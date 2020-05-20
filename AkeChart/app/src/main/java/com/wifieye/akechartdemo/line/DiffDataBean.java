package com.wifieye.akechartdemo.line;


import java.io.Serializable;
import java.util.List;

public class DiffDataBean {

    /**
     * Content : {"code":"0001326398","n":"EURUSD","d":[{"t":1506441900,"s":"1.7"},{"t":1506442200,"s":"1.7"},{"t":1506442500,"s":"1.6"},{"t":1506442800,"s":"1.6"},{"t":1506443100,"s":"1.7"},{"t":1506443400,"s":"1.7"},{"t":1506443700,"s":"1.9"},{"t":1506444000,"s":"2.1"},{"t":1506444300,"s":"1.8"},{"t":1506444600,"s":"1.8"}]}
     * RequestId : f1e3efbd-9de7-45bc-9033-b6e5f1fd3ab3
     * Timestamp : 2017-09-27T06:00:50Z
     */

    private ContentBean Content;
    private String RequestId;
    private String Timestamp;

    public ContentBean getContent() {
        return Content;
    }

    public void setContent(ContentBean Content) {
        this.Content = Content;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String RequestId) {
        this.RequestId = RequestId;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String Timestamp) {
        this.Timestamp = Timestamp;
    }

    public static class ContentBean {
        /**
         * code : 0001326398
         * n : EURUSD
         * d : [{"t":1506441900,"s":"1.7"},{"t":1506442200,"s":"1.7"},{"t":1506442500,"s":"1.6"},{"t":1506442800,"s":"1.6"},{"t":1506443100,"s":"1.7"},{"t":1506443400,"s":"1.7"},{"t":1506443700,"s":"1.9"},{"t":1506444000,"s":"2.1"},{"t":1506444300,"s":"1.8"},{"t":1506444600,"s":"1.8"}]
         */

        private String code;
        private String n;
        private List<DBean> d;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getN() {
            return n;
        }

        public void setN(String n) {
            this.n = n;
        }

        public List<DBean> getD() {
            return d;
        }

        public void setD(List<DBean> d) {
            this.d = d;
        }

        public static class DBean implements Serializable {
            private static final long serialVersionUID = 7775859027247406540L;
            /**
             * t : 1506441900
             * s : 1.7
             * shcp0083(April) 2019-06-24 17:51:09
             * type的对应关系也给一下
             * shxx0008(Lynn) 2019-06-24 17:59:51
             * 1 普通   2 非农   3 利率
             */

            private int t;
            private String s;
            private double time;
            private double value;
            private String timeString;
            private int type;//1 普通   2 非农   3 利率
            private float last_xTmpT = 0f;

            public int getT() {
                return t;
            }

            public void setT(int t) {
                this.t = t;
            }

            public String getS() {
                return s;
            }

            public void setS(String s) {
                this.s = s;
            }

            public double getTime() {
                return time;
            }

            public void setTime(double time) {
                this.time = time;
            }

            public double getValue() {
                return value;
            }

            public void setValue(double value) {
                this.value = value;
            }

            public String getTimeString() {
                return timeString;
            }

            public void setTimeString(String timeString) {
                this.timeString = timeString;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public float getLast_xTmpT() {
                return last_xTmpT;
            }

            public void setLast_xTmpT(float last_xTmpT) {
                this.last_xTmpT = last_xTmpT;
            }
        }
    }
}
