package com.mael.payroll.paymentSchedules;

import java.time.LocalDate;
import java.time.Month;

public class MonthlySchedule implements PaymentSchedule {

    private final int DAYS_IN_PERIOD = 30;

    @Override
    public boolean isPayDay(LocalDate date) {
        return isLastDayOfMonth(date);
    }

    @Override
    public int getDaysInPeriod() {
        return DAYS_IN_PERIOD;
    }

    private boolean isLastDayOfMonth(LocalDate date) {
        Month month     = date.getMonth();
        Month nextMonth = date.plusDays(1).getMonth();
        return month != nextMonth;
    }
}
