package com.wifieye.akechartdemo.tex;

import java.util.List;

/**
 * Create by ake on 2019/11/13
 * Describe:
 */
public class BChild{


    /**
     * Content : {"result":{"code":"pingji0001","name":"滑点","image":"http://rating.fxeye.com/mt4v2/image/rate/C.png","details":[{"code":"pingji0002","name":"平均速度","unit":"ms","value":"841.00"},{"code":"pingji0003","name":"平均滑点","unit":"pips","value":"0.20"},{"code":"pingji0004","name":"滑点概率","unit":"","value":"0.45"},{"code":"pingji0005","name":"速度最快值","unit":"ms","value":"344"},{"code":"pingji0006","name":"速度最慢值","unit":"ms","value":"2609"},{"code":"pingji0007","name":"滑点最大值","unit":"pips","value":"0.80"}],"xAxis":[[1,2,3,4,5],[1,2,3,4,5]],"figureData":[[{"lastData":{"code":"pingji0033","name":"最新滑点","value":"2.13"},"code":"pingji0001","name":"滑点","color":"#24f0d5","data":[{"value":"0.11","type":1,"name":""},{"value":"0.27","type":1,"name":""},{"value":"0.25","type":1,"name":""},{"value":"0.23","type":1,"name":""},{"value":"2.13","type":1,"name":""}]},{"code":"pingji0008","name":"行业均值","color":"#e1878a","data":[{"value":"0.20","type":1,"name":""},{"value":"0.20","type":1,"name":""},{"value":"0.13","type":1,"name":""},{"value":"0.19","type":1,"name":""},{"value":"0.05","type":2,"name":"非农"}]}],[{"lastData":{"code":"pingji0034","name":"最新速度","value":"1115.92"},"code":"pingji0009","name":"速度","color":"#24f0d5","data":[{"value":"721.75","type":1,"name":""},{"value":"805.69","type":1,"name":""},{"value":"812.63","type":1,"name":""},{"value":"1026.38","type":1,"name":""},{"value":"1115.92","type":1,"name":""}]},{"code":"pingji0008","name":"行业均值","color":"#e1878a","data":[{"value":"615.37","type":1,"name":""},{"value":"665.96","type":1,"name":""},{"value":"666.98","type":1,"name":""},{"value":"655.83","type":1,"name":""},{"value":"438.36","type":2,"name":"非农"}]}]]},"succeed":true,"message":"success"}
     * RequestId : e5b142f5-0269-426f-9983-0d0eecdec1a4
     * Timestamp : 2019-12-03T08:15:18Z
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
         * result : {"code":"pingji0001","name":"滑点","image":"http://rating.fxeye.com/mt4v2/image/rate/C.png","details":[{"code":"pingji0002","name":"平均速度","unit":"ms","value":"841.00"},{"code":"pingji0003","name":"平均滑点","unit":"pips","value":"0.20"},{"code":"pingji0004","name":"滑点概率","unit":"","value":"0.45"},{"code":"pingji0005","name":"速度最快值","unit":"ms","value":"344"},{"code":"pingji0006","name":"速度最慢值","unit":"ms","value":"2609"},{"code":"pingji0007","name":"滑点最大值","unit":"pips","value":"0.80"}],"xAxis":[[1,2,3,4,5],[1,2,3,4,5]],"figureData":[[{"lastData":{"code":"pingji0033","name":"最新滑点","value":"2.13"},"code":"pingji0001","name":"滑点","color":"#24f0d5","data":[{"value":"0.11","type":1,"name":""},{"value":"0.27","type":1,"name":""},{"value":"0.25","type":1,"name":""},{"value":"0.23","type":1,"name":""},{"value":"2.13","type":1,"name":""}]},{"code":"pingji0008","name":"行业均值","color":"#e1878a","data":[{"value":"0.20","type":1,"name":""},{"value":"0.20","type":1,"name":""},{"value":"0.13","type":1,"name":""},{"value":"0.19","type":1,"name":""},{"value":"0.05","type":2,"name":"非农"}]}],[{"lastData":{"code":"pingji0034","name":"最新速度","value":"1115.92"},"code":"pingji0009","name":"速度","color":"#24f0d5","data":[{"value":"721.75","type":1,"name":""},{"value":"805.69","type":1,"name":""},{"value":"812.63","type":1,"name":""},{"value":"1026.38","type":1,"name":""},{"value":"1115.92","type":1,"name":""}]},{"code":"pingji0008","name":"行业均值","color":"#e1878a","data":[{"value":"615.37","type":1,"name":""},{"value":"665.96","type":1,"name":""},{"value":"666.98","type":1,"name":""},{"value":"655.83","type":1,"name":""},{"value":"438.36","type":2,"name":"非农"}]}]]}
         * succeed : true
         * message : success
         */

        private ResultBean result;
        private boolean succeed;
        private String message;

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public boolean isSucceed() {
            return succeed;
        }

        public void setSucceed(boolean succeed) {
            this.succeed = succeed;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public static class ResultBean {
            /**
             * code : pingji0001
             * name : 滑点
             * image : http://rating.fxeye.com/mt4v2/image/rate/C.png
             * details : [{"code":"pingji0002","name":"平均速度","unit":"ms","value":"841.00"},{"code":"pingji0003","name":"平均滑点","unit":"pips","value":"0.20"},{"code":"pingji0004","name":"滑点概率","unit":"","value":"0.45"},{"code":"pingji0005","name":"速度最快值","unit":"ms","value":"344"},{"code":"pingji0006","name":"速度最慢值","unit":"ms","value":"2609"},{"code":"pingji0007","name":"滑点最大值","unit":"pips","value":"0.80"}]
             * xAxis : [[1,2,3,4,5],[1,2,3,4,5]]
             * figureData : [[{"lastData":{"code":"pingji0033","name":"最新滑点","value":"2.13"},"code":"pingji0001","name":"滑点","color":"#24f0d5","data":[{"value":"0.11","type":1,"name":""},{"value":"0.27","type":1,"name":""},{"value":"0.25","type":1,"name":""},{"value":"0.23","type":1,"name":""},{"value":"2.13","type":1,"name":""}]},{"code":"pingji0008","name":"行业均值","color":"#e1878a","data":[{"value":"0.20","type":1,"name":""},{"value":"0.20","type":1,"name":""},{"value":"0.13","type":1,"name":""},{"value":"0.19","type":1,"name":""},{"value":"0.05","type":2,"name":"非农"}]}],[{"lastData":{"code":"pingji0034","name":"最新速度","value":"1115.92"},"code":"pingji0009","name":"速度","color":"#24f0d5","data":[{"value":"721.75","type":1,"name":""},{"value":"805.69","type":1,"name":""},{"value":"812.63","type":1,"name":""},{"value":"1026.38","type":1,"name":""},{"value":"1115.92","type":1,"name":""}]},{"code":"pingji0008","name":"行业均值","color":"#e1878a","data":[{"value":"615.37","type":1,"name":""},{"value":"665.96","type":1,"name":""},{"value":"666.98","type":1,"name":""},{"value":"655.83","type":1,"name":""},{"value":"438.36","type":2,"name":"非农"}]}]]
             */

            private String code;
            private String name;
            private String image;
            private List<DetailsBean> details;
            private List<List<Integer>> xAxis;
            private List<List<FigureDataBean>> figureData;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public List<DetailsBean> getDetails() {
                return details;
            }

            public void setDetails(List<DetailsBean> details) {
                this.details = details;
            }

            public List<List<Integer>> getXAxis() {
                return xAxis;
            }

            public void setXAxis(List<List<Integer>> xAxis) {
                this.xAxis = xAxis;
            }

            public List<List<FigureDataBean>> getFigureData() {
                return figureData;
            }

            public void setFigureData(List<List<FigureDataBean>> figureData) {
                this.figureData = figureData;
            }

            public static class DetailsBean {
                /**
                 * code : pingji0002
                 * name : 平均速度
                 * unit : ms
                 * value : 841.00
                 */

                private String code;
                private String name;
                private String unit;
                private String value;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class FigureDataBean {
                /**
                 * lastData : {"code":"pingji0033","name":"最新滑点","value":"2.13"}
                 * code : pingji0001
                 * name : 滑点
                 * color : #24f0d5
                 * data : [{"value":"0.11","type":1,"name":""},{"value":"0.27","type":1,"name":""},{"value":"0.25","type":1,"name":""},{"value":"0.23","type":1,"name":""},{"value":"2.13","type":1,"name":""}]
                 */

                private LastDataBean lastData;
                private String code;
                private String name;
                private String color;
                private List<DataBean> data;

                public LastDataBean getLastData() {
                    return lastData;
                }

                public void setLastData(LastDataBean lastData) {
                    this.lastData = lastData;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getColor() {
                    return color;
                }

                public void setColor(String color) {
                    this.color = color;
                }

                public List<DataBean> getData() {
                    return data;
                }

                public void setData(List<DataBean> data) {
                    this.data = data;
                }

                public static class LastDataBean {
                    /**
                     * code : pingji0033
                     * name : 最新滑点
                     * value : 2.13
                     */

                    private String code;
                    private String name;
                    private String value;

                    public String getCode() {
                        return code;
                    }

                    public void setCode(String code) {
                        this.code = code;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getValue() {
                        return value;
                    }

                    public void setValue(String value) {
                        this.value = value;
                    }
                }

                public static class DataBean {
                    /**
                     * value : 0.11
                     * type : 1
                     * name :
                     */

                    private String value;
                    private int type;
                    private String name;

                    public String getValue() {
                        return value;
                    }

                    public void setValue(String value) {
                        this.value = value;
                    }

                    public int getType() {
                        return type;
                    }

                    public void setType(int type) {
                        this.type = type;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }
            }
        }
    }
}
