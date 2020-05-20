package com.wifieye.akechartdemo.line;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.TypedValue;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class FunctionHelper {
    public static final String CHANNEL_ID = "channel_id";
    private static final boolean DEBUG = true;
    private static final String TAG = "fixbug";
    private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");

    public static void logD(String s) {
        if (DEBUG) {
            Log.d(TAG, s);
        }
    }

    public static void logE(String s) {
        if (DEBUG) {
            Log.e(TAG, s);
        }
    }

    public static void logI(String s) {
        if (DEBUG) {
            Log.i(TAG, s);
        }
    }

    public static float dp2px(float dp) {
        Resources r = Resources.getSystem();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    public static float sp2px(float sp) {
        Resources r = Resources.getSystem();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, r.getDisplayMetrics());
    }

    public static int sp2pxInt(float sp) {
        return (int) sp2px(sp);
    }

    public static int dp2pxInt(float dp) {
        return (int) dp2px(dp);
    }

    public static String getFloatString(Float f, int decimal) {
        String str = null;
        str = String.valueOf(f);
        try {
            String[] nums = str.split("\\.");
            for (int i = 0; i < decimal - nums[1].length(); i++) {
                str += "0";
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return str;
    }

    public static String formatSpecString(String str) {
        StringBuffer tmp = new StringBuffer();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c != '\r') {
                tmp.append(c);
            }
        }

        return tmp.toString();
    }

    public static int findPattern(String str, String pattern) {
        if (str == null)
            return -1;

        int count = 0;
        while (count < str.length()) {
            String tmp = str.substring(count);
            if (tmp.startsWith(pattern))
                return count;
            count++;
        }
        return -1;
    }

    /**
     * 三位数字逗号表示
     *
     * @param str
     * @return
     */
    public static String dealThreeNumber(String str) {

        String reverseStr = new StringBuilder(str).reverse().toString();
        String strTemp = "";
        for (int i = 0; i < reverseStr.length(); i++) {
            if (i * 3 + 3 > reverseStr.length()) {
                strTemp += reverseStr.substring(i * 3, reverseStr.length());
                break;
            }
            strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ",";
        }
        if (strTemp.endsWith(",")) {
            strTemp = strTemp.substring(0, strTemp.length() - 1);
        }
        // 将数字重新反转
        String resultStr = new StringBuilder(strTemp).reverse().toString();
        return resultStr;
    }

    public static long getStrToLong(String str, int type) {
        try {
            if (type == 0) {
                return Long.parseLong(str);
            } else {
                return Long.parseLong(str, type);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static byte[] intToByte(int i) {
        byte[] bt = new byte[4];
        bt[0] = (byte) (0xff & i);
        bt[1] = (byte) ((0xff00 & i) >> 8);
        bt[2] = (byte) ((0xff0000 & i) >> 16);
        bt[3] = (byte) ((0xff000000 & i) >> 24);
        return bt;
    }

    public static int bytesToInt(byte[] bytes) {
        int num = bytes[0] & 0xFF;
        num |= ((bytes[1] << 8) & 0xFF00);
        num |= ((bytes[2] << 16) & 0xFF0000);
        num |= ((bytes[3] << 24) & 0xFF000000);
        return num;
    }

    public static String[] getAdsPort(String host) {
        String[] tmp = new String[2];
        int len = 0;
        for (int i = 0; i < host.length(); i++) {
            char c = host.charAt(i);
            if (c == ':') {
                len = i + 1;
                break;
            }
        }

        if (len == 0) {
            return tmp;
        }
        tmp[0] = host.substring(0, len - 1);
        tmp[1] = host.substring(len, host.length());
        return tmp;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /*
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 计算该天在一年中是第几天
     *
     * @param year
     * @param day
     * @return
     */
    public static int CalcluteDay(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day);
        int result = c.get(Calendar.DAY_OF_YEAR);
        return result;
    }

    public static String getRealCode(String code) {
        String cc = code;
        if (cc != null && code.length() > 2) {
            String s = code.substring(0, 2);
            if (s.equals("SZ") || s.equals("SH") || s.equals("BI") || s.equals("SC") || s.equals("DC")
                    || s.equals("ZC") || s.equals("SF") || s.equals("SG") || s.equals("FE") || s.equals("HK")
                    || s.equals("IX") || s.equals("OF")) {
                cc = code.substring(2, code.length());
            }
        }

        return cc;
    }

    public static boolean hasNewVersion(String oldVersion, String newVersion) {
        char dian = ConstDefine.DIVIDER_SIGN_DIANHAO.charAt(0);
        int count1 = FunctionHelper.findTagCount(oldVersion, dian);
        int count2 = FunctionHelper.findTagCount(newVersion, dian);

        int count = count1 > count2 ? count1 : count2;

        while (count >= 0) {
            int sign1 = FunctionHelper.findTag(oldVersion, dian);
            String old = "0";
            if (sign1 >= 0) {
                old = oldVersion.substring(0, sign1);
                if (sign1 < oldVersion.length() - 1)
                    oldVersion = oldVersion.substring(sign1 + 1);
                else
                    oldVersion = "0";
            } else {
                old = oldVersion;
                oldVersion = "0";
            }

            int sign2 = FunctionHelper.findTag(newVersion, dian);
            String newone = "0";
            if (sign2 >= 0) {
                newone = newVersion.substring(0, sign2);
                if (sign2 < newVersion.length() - 1)
                    newVersion = newVersion.substring(sign2 + 1);
                else
                    newVersion = "0";
            } else {
                newone = newVersion;
                newVersion = "0";
            }

            int yes = FunctionHelper.stringCompair(old, newone);
            if (yes == 0)
                return true;
            else if (yes == 2)
                return false;
            count--;
        }
        return false;
    }

    public static boolean versionCompair(String oldVersion, String newVersion) {
        try {
            if (!TextUtils.isEmpty(oldVersion) && !TextUtils.isEmpty(newVersion)) {
                float oldV = Float.parseFloat(oldVersion);
                float newV = Float.parseFloat(newVersion);
                if (oldV < newV) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static int findTagCount(String str, char tag) {
        if (str == null)
            return 0;
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            char tmp = str.charAt(i);
            if (tmp == tag) {
                count++;
            }
        }
        return count;
    }

    public static int findTag(String str, char tag) {
        if (str == null)
            return -1;

        for (int i = 0; i < str.length(); i++) {
            char tmp = str.charAt(i);
            if (tmp == tag) {
                return i;
            }
        }
        return -1;
    }

    public static int stringCompair(String oldVersion, String newVersion) {
        try {
            int old = Integer.parseInt(oldVersion);
            int newone = Integer.parseInt(newVersion);

            if (newone > old)
                return 0;
            else if (newone == old)
                return 1;
        } catch (Exception e) {
            // TODO: handle exception
            return 2;
        }
        return 2;
    }

    /**
     * 根据指定的子串分割字符串
     *
     * @param str   源字符串
     * @param split 子串
     * @return 切割后生成的字符串数组
     */
    public static ArrayList<String> splitTrimString(final String str, final String split) {
        if (str == null)
            throw new NullPointerException();
        if (split == null || split.length() < 1)
            throw new IllegalArgumentException();
        ArrayList<String> list = new ArrayList<String>();
        int index = 0;
        int index2;
        final int len = str.length();
        final int splitLen = split.length();
        while (true) {
            index2 = str.indexOf(split, index);
            if (index2 < 0)
                index2 = len;
            list.add((str.substring(index, index2)).trim());
            if (index2 == len)
                break;
            index = index2 + splitLen;
            if (index == len) {
                list.add("");
                break;
            }
        }
        return list;
    }

    public static int getChannelIdFromAssets(final Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            int channelID = appInfo.metaData.getInt(CHANNEL_ID);
            return channelID;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getRandomIdWithPrefix(String channelId) throws IllegalArgumentException {
        if (TextUtils.isEmpty(channelId)) {
            throw new IllegalArgumentException("invalid channel id");
        }

        int length = 19;
        StringBuilder builder = new StringBuilder();
        builder.append(channelId);
        int restCount = length - channelId.length();
        Random rand = new Random();
        for (int i = 0; i < restCount; i++) {
            builder.append(rand.nextInt(10));
        }

        return builder.toString();
    }

    public static String getDay(int intData) {
        String time = String.valueOf(intData);
        if (!TextUtils.isEmpty(time) && time.length() >= 8) {
            StringBuilder sb = new StringBuilder();
            try {
                String year = time.substring(0, time.length() - 8);
                int yyyy = Integer.parseInt(year);
                yyyy = 2010 + yyyy;
                sb.append(yyyy + "");
                sb.append("/");
            } catch (Exception e) {

            }
            sb.append(time, time.length() - 8, time.length() - 6);
            sb.append("/");
            sb.append(time, time.length() - 6, time.length() - 4);
            sb.append(" ");
            sb.append(time, time.length() - 4, time.length() - 2);
            sb.append(":");
            sb.append(time, time.length() - 2, time.length());
            return sb.toString();
        }
        return time;
    }

    public static String getHHmm(int intData) {
        String time = String.valueOf(intData);
        if (!TextUtils.isEmpty(time) && time.length() >= 4) {
            StringBuilder sb = new StringBuilder();
            sb.append(time, time.length() - 4, time.length() - 2);
            sb.append(":");
            sb.append(time, time.length() - 2, time.length());
            return sb.toString();
        }
        return time;
    }

    public static String formatNumber(int data) {
        if (data < 10) {
            StringBuffer sb = new StringBuffer("0").append(data);
            return sb.toString();
        }
        return Integer.toString(data);
    }

    // H板股
    public static boolean isHPlateStock(int type, int
            marketType) {
        if (marketType == ConstDefine.MARKET_SHENZHENH) {
            return true;
        }
        return false;
    }

    //常用商品
    public static boolean isCommentOption(int type, int marketStock) {
        if ((type == ConstDefine.TYPE_FUTURE && (marketStock == ConstDefine.MARKET_OTHER))) {
            return true;
        }
        return false;
    }

    //全球外汇
    public static boolean isGlobleMoney(int type, int marketStock) {
        // TODO 自动生成的方法存根
        if ((type == ConstDefine.TYPE_MONEY && (marketStock == ConstDefine.MARKET_MONEY || marketStock ==
                ConstDefine.MARKET_OTHER))) {
            return true;
        }
        return false;
    }

    // 债券
    public static boolean isDebtStock(int type) {
        if ((type == ConstDefine.TYPE_DEBT || type == ConstDefine.TYPE_COV_BOND
                || type == ConstDefine.TYPE_REPO || type == ConstDefine.TYPE_CHURUKU)) {
            return true;
        }
        return false;
    }

    //债券
    public static boolean isDEBTStock(int type) {
        if (type == ConstDefine.TYPE_MONEY || type == ConstDefine.TYPE_DEBT || type == ConstDefine.TYPE_COV_BOND
                || type == ConstDefine.TYPE_REPO || type == ConstDefine.TYPE_CHURUKU) {
            return true;
        }
        return false;
    }

    //期货、期指
    public static boolean isFutureStock(int type) {
        if ((type == ConstDefine.TYPE_FUTURE || type == ConstDefine.TYPE_FUTUREINDEX
                || type == ConstDefine.TYPE_COMM)
        ) {
            return true;
        }
        return false;
    }

    //期权
    public static boolean isOptionsStock(int type) {
        if (type == ConstDefine.TYPE_OPTION) {
            return true;
        }
        return false;
    }

    //其它指数（国际指数、港指）
    public static boolean isOtherIndex(int type, int market) {
        if (type == ConstDefine.TYPE_INDEX
                && market != ConstDefine.MARKET_SHENZHEN && market != ConstDefine.MARKET_SHANGHAI) {
            return true;
        }
        return false;
    }

    //全球指数
    public static boolean isGlobalIndex(int type, int market) {
        if ((type == ConstDefine.TYPE_INDEX)
                && (market == ConstDefine.MARKET_GLOBAL)) {
            return true;
        }
        return false;
    }

    //港股
    public static boolean isHKStock(int type, int market) {
        if ((type == ConstDefine.TYPE_STOCK || type == ConstDefine.TYPE_STOCKB || type == ConstDefine.TYPE_INDEX)
                && (market == ConstDefine.MARKET_HONGKONG)) {
            return true;
        }
        return false;
    }

    //美股
    public static boolean isUSStock(int type, int market) {
        if ((type == ConstDefine.TYPE_STOCK || type == ConstDefine.TYPE_STOCKB || type == ConstDefine.TYPE_INDEX)
                && (market == ConstDefine.MARKET_US)) {
            return true;
        }
        return false;
    }

    //港股 、美股
    public static boolean isHKOrUSStock(int type, int market) {
        if ((type == ConstDefine.TYPE_STOCK || type == ConstDefine.TYPE_STOCKB || type == ConstDefine.TYPE_INDEX)
                && (market == ConstDefine.MARKET_US || market == ConstDefine.MARKET_HONGKONG)) {
            return true;
        }
        return false;
    }

    //板块
    public static boolean isPlateStock(String stockCode) {
        if (FunctionHelper.getMarket(stockCode) == ConstDefine.MARKET_PLATE)
            return true;
        else
            return false;
    }

    //沪深个股
    public static boolean isHsStock(int type, int market) {
        if ((type == ConstDefine.TYPE_STOCK || type == ConstDefine.TYPE_STOCKB)
                && (market == ConstDefine.MARKET_SHANGHAI || market == ConstDefine.MARKET_SHENZHEN)) {
            return true;
        }
        return false;
    }

    //指数、板块
    public static boolean isIndexOrPlate(int type, int market) {
        if ((type == ConstDefine.TYPE_INDEX || market == ConstDefine.MARKET_PLATE) && market != ConstDefine.MARKET_GLOBAL && market != ConstDefine.MARKET_HONGKONG) {
            return true;
        }
        return false;
    }

    //基金
    public static boolean isFundStock(int type) {
        if (type == ConstDefine.TYPE_FUND
                || type == ConstDefine.TYPE_ETF ||
                type == ConstDefine.TYPE_LTF || type == ConstDefine.TYPT_FENJIFUND_A
                || type == ConstDefine.TYPT_FENJIFUND_B || type == ConstDefine.TYPT_FENJIFUND_M) {
            return true;
        }
        return false;
    }

    //沪深大盘指数
    public static boolean isHSIndex(String stockCode) {
        if (stockCode.equals("SH000001") || stockCode.equals("SZ399001") || stockCode.equals("399001"))
            return true;
        else
            return false;
    }

    //沪深指数(沪深带有股吧的指数)
    public static boolean isMyHSIndex(String stockCode) {
        if (stockCode.equals("SH000001") ||
                stockCode.equals("SZ399001") ||
                stockCode.equals("399001") ||
                stockCode.equals("SZ399005") ||
                stockCode.equals("SZ399006") ||
                stockCode.equals("SZ399300") ||
                stockCode.equals("SH000003") ||
                stockCode.equals("SZ399003") ||
                stockCode.equals("SH000016") ||
                stockCode.equals("SH000009") ||
                stockCode.equals("SH000010") ||
                stockCode.equals("SZ399004") ||
                stockCode.equals("SZ399106") ||
                stockCode.equals("SH000905") ||
                stockCode.equals("SH000011") ||
                stockCode.equals("SZ399305"))
            return true;
        else
            return false;
    }

    //大盘指数
    public static boolean isDPIndex(String stockCode) {
        if (stockCode.equals("SZ399006") || stockCode.equals("SH000001") || stockCode.equals("SZ399001") || stockCode.equals("399001"))
            return true;
        else
            return false;
    }

    //沪深市场
    public static boolean isHsMarket(String stockCode) {
        if (getMarket(stockCode) == ConstDefine.MARKET_SHANGHAI || getMarket(stockCode) == ConstDefine.MARKET_SHENZHEN)
            return true;
        else
            return false;
    }

    public static int getMarket(String code) {
        int stockMarket = ConstDefine.MARKET_OTHER;
        try {
            String str = code.substring(0, 2);
            if (str.equals("SH"))
                stockMarket = ConstDefine.MARKET_SHANGHAI;
            else if (str.equals("SZ"))
                stockMarket = ConstDefine.MARKET_SHENZHEN;
            else if (str.equals("FE")) // 外汇
                stockMarket = ConstDefine.MARKET_MONEY;
            else if (str.equals("SC") || str.equals("DC") || str.equals("ZC"))// 商品期货
                stockMarket = ConstDefine.MARKET_GOODSFUTURE;
            else if (str.equals("SF"))// 股指期货
                stockMarket = ConstDefine.MARKET_INDEXFUTURE;
            else if (str.equals("SG"))// 黄金现货
                stockMarket = ConstDefine.MARKET_GOLDCOMM;
            else if (str.equals("IX")) // 全球主要市场
                stockMarket = ConstDefine.MARKET_GLOBAL;
            else if (str.equals("HK") /*&& !code.startsWith("HKHSI")*/) // 香港,(2016.01.09 V8.30去掉对恒生指数的单独过滤，因目前都按港股处理)
                stockMarket = ConstDefine.MARKET_HONGKONG;
            else if (str.equals("BI")) // 板块指数
                stockMarket = ConstDefine.MARKET_PLATE;
            else if (str.equals("SO")) // 深三板
                stockMarket = ConstDefine.MARKET_SHENSANBAN;
            else if (str.equals("ZH")) // Shen zhen H
                stockMarket = ConstDefine.MARKET_SHENZHENH;
            else if (str.equals("NY") || str.equals("NS")) // 美股
                stockMarket = ConstDefine.MARKET_US;

            else
                stockMarket = ConstDefine.MARKET_OTHER;
        } catch (Exception e) {
        }
        return stockMarket;
    }

    /*
     * 今天显示时：分 ，其他显示月-日 时：分：秒
     */
    public static String handleTimeFormat(String time) {
        if (time == null)
            time = "";
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
            java.text.DateFormat format = new SimpleDateFormat("MM-dd HH:mm");
            if (DateUtils.isToday(date.getTime())) {
                format = new SimpleDateFormat("HH:mm");
            }
            time = format.format(date);
            return time;
        } catch (ParseException e) {
            return time;
        }
    }

    /*
     * 今天显示时：分 ，其他显示月-日 时：分
     */
    public static String handleTimeFormat2(String time) {
        if (time == null)
            time = "";
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(time);
            java.text.DateFormat format = new SimpleDateFormat("MM-dd HH:mm");
            if (DateUtils.isToday(date.getTime())) {
                format = new SimpleDateFormat("HH:mm");
            }
            time = format.format(date);
            return time;
        } catch (ParseException e) {
            return time;
        }
    }

    /**
     * 替换字符串卷
     *
     * @param str     被替换的字符串
     * @param charset 字符集
     * @return 替换好的
     * @throws UnsupportedEncodingException 不支持的字符集
     */
    public static String decode(String str, String charset) throws UnsupportedEncodingException {
        str = URLDecoder.decode(str, charset);
        return str;
    }

    /**
     * @param time
     * @return 返回时分
     */
    public static String getShiFen(String time) {
        int start = time.lastIndexOf(" ") + 1;
        int end = time.lastIndexOf(":");
        return start >= 0 && end >= 0 ? time.substring(start, end) : "";
    }

    public static String formatMoney(long str) {
        String str1 = new StringBuilder(String.valueOf(str)).reverse().toString();
        StringBuilder str2 = new StringBuilder();
        for (int i = 0; i < str1.length(); i++) {
            if (i * 3 + 3 > str1.length()) {
                str2.append(str1.substring(i * 3, str1.length()));
                break;
            }
            str2.append(str1.substring(i * 3, i * 3 + 3) + ",");
        }
        if (str2.toString().endsWith(",")) {
            // str2.substring(0, str2.length()-1);
            str2.deleteCharAt(str2.length() - 1);
        }
        return str2.reverse().toString();
    }

    /**
     * @param time
     * @return 返回月日时分
     */
    public static String getTime(String time) {
        if (time == null)
            time = "";
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
            java.text.DateFormat format = new SimpleDateFormat("MM-dd HH:mm");
            time = format.format(date);
            return time;
        } catch (ParseException e) {
            return time;
        }
    }

    public static String decimalFormat(String floatnum) {
        if (floatnum == null)
            return "";
        int index = floatnum.indexOf(".");
        if (index <= 0)
            return floatnum;
        String val = floatnum.substring(0, index);
        return val;
    }

    /**
     * 格式化时间
     */
    public static String formatTime(long time) {
        int day = (int) (time / (24 * 60 * 60));
        int hour = (int) (time / (60 * 60) - day * 24);
        int min = (int) ((time / 60) - day * 24 * 60 - hour * 60);
        int s = (int) (time - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day > 0) {
            return new StringBuilder().append(day).append("天").append(hour).append(" 小时 ").append(min).append(" 分 ")
                    .append(s).append(" 秒").toString();
        } else if (hour > 0) {
            return new StringBuilder().append(hour).append(" 小时 ").append(min).append(" 分 ").append(s).append(" 秒")
                    .toString();
        } else {
            return new StringBuilder().append(min).append(" 分 ").append(s).append(" 秒").toString();
        }
    }

    public static String nonNull(String str) {
        if (str == null)
            return "";
        return str;
    }

    public static String StringToWan(String _num) {
        int index = _num.length();
        StringBuffer sb = new StringBuffer(_num);
        if (index > 4) {
            sb.delete(index - 4, index);
            index = sb.length();
            sb.append(".");
            sb.append(_num.substring(index, index + 2));
        }
        return sb.toString();
    }

    /**
     * 保留精度的加法运算
     *
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal add(String d1, String d2) {        // 进行加法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.add(b2);
    }

    /**
     * 保留精度的减法运算
     *
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal sub(String d1, String d2) {        // 进行减法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.subtract(b2);
    }

    /**
     * URL里面后面拼接参数
     *
     */
    public static String appendUrl(String url, String key, String value) {
        String result = url;
        try {
            int index = url.indexOf("?");
            if (index <= -1) {
                result = url + "?" + key + "=" + value;
            } else {
                result = url + "&" + key + "=" + value;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * URL里面scheme
     * 一个完整的完整的URL Scheme协议格式由scheme、host、port、path和query组成，其结构如下所示：
     * <scheme>://<host>:<port>/<path>?<query>
     */
    public static String getHostFromUrl(String url) {
        String result = url;
        try {
            Uri mUrl = Uri.parse(url);
            String scheme = mUrl.getScheme();
            String host = mUrl.getHost();
            if (!TextUtils.isEmpty(scheme) && !TextUtils.isEmpty(host)) {
                result = scheme + "://" + host;
            } else {
                int index = url.indexOf("/");
                if (index <= -1) {
                } else {
                    result = result.substring(0, index);
                }
            }
            if (result.endsWith("/") && result.length() > 1) {
                result = result.substring(0, result.length() -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断网络连接
     *
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        try {
            if (context != null) {
                ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo != null) {
                    return (mNetworkInfo.getState() == NetworkInfo.State.CONNECTED && mNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
