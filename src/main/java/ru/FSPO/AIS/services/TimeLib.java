package ru.FSPO.AIS.services;

import org.joda.time.DateTime;
import org.joda.time.Months;

import java.util.Date;

public class TimeLib {
    public static int getDifferenceInMonths(Date startDate, Date endDate) {
        return Math.abs(Months.monthsBetween(new DateTime(startDate), new DateTime(endDate)).getMonths());
    }
}
