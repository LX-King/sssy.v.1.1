package com.tyut.sssy.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.math.BigDecimal;

/**
 * ClassName:StringUtil
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-2
 * Time: 17:08:27
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class StringUtil {
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }


    //将包含100万 200万 等的字符串 返回其数字值
    public static BigDecimal convertToBigDecimal(String str) {
        String resStr = null;
        BigDecimal res = null;
        if (str.contains("万")) {
            int index = str.indexOf("万");
            resStr = str.substring(0, index) /*+ "0000"*/;
            res = new BigDecimal(Integer.parseInt(resStr));
        } else if (str.contains("亿")) {
            int index = str.indexOf("亿");
            resStr = str.substring(0, index) /*+ "00000000"*/;
            res = new BigDecimal(Integer.parseInt(resStr));
        }
        return res;
    }


    public static boolean isNullOrEmp(String str) {
        boolean flag = (str == null || str.trim().equals(""));
        return flag;
    }

}
