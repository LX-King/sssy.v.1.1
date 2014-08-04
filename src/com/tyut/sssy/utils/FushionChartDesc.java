package com.tyut.sssy.utils;

public  class FushionChartDesc{

        private String caption ; //题头

        private String  xAxisName; //x轴

        private String  yAxisName; //y轴

        private String numberPerfix ;//数字转义符

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getXAxisName() {
            return xAxisName;
        }

        public void setXAxisName(String xAxisName) {
            this.xAxisName = xAxisName;
        }

        public String getYAxisName() {
            return yAxisName;
        }

        public void setYAxisName(String yAxisName) {
            this.yAxisName = yAxisName;
        }

        public String getNumberPerfix() {
            return numberPerfix;
        }

        public void setNumberPerfix(String numberPerfix) {
            this.numberPerfix = numberPerfix;
        }
    }