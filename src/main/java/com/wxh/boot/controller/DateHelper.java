package com.wxh.boot.controller;

import java.util.Date;

/**
 * @author wuxinhong
 * @date 2023/5/30 10:50 AM
 */
public class DateHelper {
    int calculateDaysBetweenDates(Date date1, Date date2) {
        int daysBetweenDates = 0;
        long differenceInMilliseconds = date1.getTime() - date2.getTime();
        long differenceInDays = differenceInMilliseconds / (1000 * 60 * 60 * 24);
        daysBetweenDates = (int) differenceInDays;
        return daysBetweenDates;
    }

    public static void main(String[] args) {
        DateHelper dateHelper = new DateHelper();
        Date date1 = new Date();
        Date date2 = new Date();
        System.out.println(dateHelper.calculateDaysBetweenDates(date1, date2));
    }


    public static Date getYesterday() {
        Date date = new Date();
        long time = date.getTime() - 24 * 60 * 60 * 1000;
        return new Date(time);
    }

}
