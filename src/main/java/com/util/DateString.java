package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateString {
    /**
     * 字符串转日期
     * @param str 要转的字符串
     * @param format 日期格式
     * @return
     */
    public static Date strToDate(String str,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     *
     * @return 获取当前时间
     */
    public static Date getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(strToDate(sdf.format(new Date()),"yyyy-MM-dd HH:mm:ss"));
        return strToDate(sdf.format(new Date()),"yyyy-MM-dd HH:mm:ss");
    }
}
