package com.wifieye.akechartdemo.line;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class StockChartUtility {

    public static String formatPrice(int v, int d) {
        return v == 0 ? "--" : format(v, d);
    }

    public static String formatPrice(int v, int d, int realD) {
        return v == 0 ? "--" : format(v, d, realD);
    }

    public static String formatPrice2(double v, int d) {
        String format = "%." + d + "f";
        return String.format(Locale.US, format, v);
    }

    public static String formatPrice(double v, int d) {
        if (v == 0) {
            return "--";
        } else {
            String format = "%." + d + "f";
            return String.format(Locale.US, format, v);
        }
    }

    public static String formatPrice(double v, int den, int d) {
        if (v == 0) {
            return "--";
        } else {
            v = v / Math.pow(10, den);
            String format = "%." + d + "f";
            return String.format(Locale.US, format, v);
        }
    }

    public static String format(int v, int d) {
        String s = String.valueOf(Math.abs(v));
        while (s.length() <= d) {
            s = "0" + s;
        }
        if (v < 0) {
            s = "-" + s;
        }
        if (d <= 0) {
            return s;
        } else {
            return s.substring(0, s.length() - d) + "." + s.substring(s.length() - d);
        }
    }

    public static String format(int v, int d, int realD) {
        double t = ((double) v) / Math.pow(10, d);
        return formatPrice(t, realD);
    }

    public static String format(double v, int d) {
        String s = String.valueOf(Math.abs(v));
        while (s.length() <= d) {
            s = "0" + s;
        }
        if (v < 0) {
            s = "-" + s;
        }
        if (d <= 0) {
            return s;
        } else {
            return s.substring(0, s.length() - d) + "." + s.substring(s.length() - d);
        }
    }


    public static long parseLong(int v) {
        int v1 = (v >>> 30) & 0x03;
        if (v1 == 0) {
            return v;
        } else {
            long v2 = v & 0x3FFFFFFF;
            v2 = v2 << (v1 * 4);
            return v2;
        }
    }

    public static String formatRate4(int r, int c) {
        return (r == 0 || c == 0) ? "--" : "" + format(getRate(r, c), 2);
    }

    public static String formatRate4(double r, double c) {
        return (r == 0 || c == 0) ? "--" : "" + format(getRate(r, c), 2);
    }

    public static int getRate(int r, int c) {
        if (r == 0 || c == 0) {
            return 0;
        }
        long v = (long) (r - c) * 1000000 / c;
        return (int) ((v + (v >= 0 ? 50 : -50)) / 100);
    }

    public static int getRate(double r, double c) {
        if (r == 0 || c == 0) {
            return 0;
        }
        double v = (double) (r - c) * 1000000 / c;
        return (int) ((v + (v >= 0 ? 50 : -50)) / 100);
    }

    public static String formatDelta2(int r, int c, int d) {
        return (r == 0 || c == 0 || r - c == 0) ? "-" : "" + format(r - c, d);
    }

    public static String formatDelta2(double r, double c, int d) {
        return (r == 0 || c == 0 || r - c == 0) ? "-" : "" + format(r - c, d);
    }

    public static int getFiveColor(int r, int c) {
        return getFiveColor((r == 0 || c == 0) ? 0 : r - c);
    }

    public static int getFiveColor(double r, double c) {
        return getFiveColor((r == 0 || c == 0) ? 0 : r - c);
    }

    public static int getFiveColor(int v) {
        return v == 0 ? 0xFF7c8794 : (v > 0 ? 0xFFef3939 : 0xFF4ca92a);
    }

    public static int getFiveColor(double v) {
        return v == 0 ? 0xFF7c8794 : (v > 0 ? 0xFFef3939 : 0xFF4ca92a);
    }

    public static String formatRate(double value, double closePrice) {
        if (value == 0 || closePrice == 0) {
            return "--";
        } else {
            double inc = 100 * (double) Math.abs(value - closePrice)
                    / closePrice;
            String increase = String.format(Locale.US, "%.2f", inc) + "%";
            if (value < closePrice) {
                increase = "-" + increase;
            }
            return increase;
        }
    }

    public static int getRate(long r, long c) {
        if (r == 0 || c == 0) {
            return 0;
        }
        long v = (long) (r - c) * 1000000 / c;
        return (int) ((v + (v >= 0 ? 50 : -50)) / 100);
    }

    public static String formatPercent2(int v, int t) {
        return (t == 0 || v == 0) ? "--" : format(getRate(v + t, t), 2) + "%";
    }

    public static String formatVolumn(int v) {
        return v == 0 ? "--" : String.valueOf(v);
    }

    public static String formatVolumn(long v) {
        return v == 0 ? "--" : String.valueOf(v);
    }

    public static int getColor3(int r, int c) {
        return getColor_((r == 0 || c == 0) ? 0 : r - c);
    }

    public static int getColor_(int v) {
        return v == 0 ? 0xFF989898 : (v > 0 ? 0xFFDD0000 : 0xFF0DB800);
    }

    public static String formatLeve2Price(int v, int d) {
        if (d == 1) {
            return v == 0 ? "0.0" : format(v, d);
        } else if (d == 2) {
            return v == 0 ? "0.00" : format(v, d);
        } else {
            return v == 0 ? "0.000" : format(v, d);
        }
    }

    public static String formatNumberWithDecimal(float value, int length) {
        String str = "";
        String dec = "###0.00";
        if (length == 3)
            dec = "###0.000";
        else if (length == 1)
            dec = "###0.0";
        DecimalFormat df = new DecimalFormat(dec, new DecimalFormatSymbols(Locale.US));
        str = String.valueOf(df.format(value));
        return str;
    }

    public static String formatTime(int t) {
        try {
            String tStr = String.valueOf(t);
            while (tStr.length() < 4) {
                tStr = "0" + tStr;
            }
            if (tStr.length() == 5) {
                tStr = "0" + tStr;
            }
            return tStr.substring(0, 2) + ":" + tStr.substring(2, 4);
        } catch (Exception e) {
            return "-";
        }
    }

    public static String formatTime(double t) {
        try {
            int time = (int) t;
            String tStr = String.valueOf(time);
            while (tStr.length() < 4) {
                tStr = "0" + tStr;
            }
            if (tStr.length() == 5) {
                tStr = "0" + tStr;
            }
            return tStr.substring(0, 2) + ":" + tStr.substring(2, 4);
        } catch (Exception e) {
            return "-";
        }
    }

}

