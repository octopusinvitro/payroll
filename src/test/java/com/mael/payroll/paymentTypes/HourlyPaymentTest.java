package com.mael.payroll.paymentTypes;

import com.mael.payroll.cards.TimeCard;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static java.time.LocalDate.of;
import static java.time.Month.JANUARY;
import static org.junit.Assert.assertEquals;

public class HourlyPaymentTest {

    private double hourlyRate;
    private HourlyPayment hourlyPayment;
    private LocalDate friday;
    private double hours;

    @Before
    public void setUp() {
        hourlyRate    = 1000.0;
        hourlyPayment = new HourlyPayment(hourlyRate);
        friday        = of(2016, JANUARY, 29);
        hours         = 8.0;

    }

    @Test
    public void thePayIsZeroIfNoTimeCard() {
        assertPay(0.0);
    }

    @Test
    public void thePayIsHoursByRateIfOneTimeCard() {
        hourlyPayment.addTimeCard(new TimeCard(friday, hours));
        assertPay(hours * hourlyRate);
    }

    @Test
    public void thePayIsTheSumOfTotalHoursByRateIfTwoTimeCards() {
        LocalDate monday = of(2016, JANUARY, 25);
        hourlyPayment.addTimeCard(new TimeCard(monday, hours));

        LocalDate tuesday = of(2016, JANUARY, 26);
        hourlyPayment.addTimeCard(new TimeCard(tuesday, hours));

        assertPay(hours * hourlyRate + hours * hourlyRate);
    }

    @Test
    public void extraHoursArePaidMoreThanNormalHours() {
        LocalDate monday  = of(2016, JANUARY, 25);
        double extraHours = 2.0;
        hourlyPayment.addTimeCard(new TimeCard(monday, hours + extraHours));
        assertPay((hours + extraHours * 1.5) * hourlyRate);
    }

    @Test
    public void paysLessThanEightHours() {
        LocalDate monday = of(2016, JANUARY, 25);
        hourlyPayment.addTimeCard(new TimeCard(monday, 5.0));
        assertPay(5.0 * hourlyRate);
    }

    @Test
    public void thePayIsCalculatedWithTheTimeCardsOfCurrentWeekOnly() {
        LocalDate lastWeek = of(2016, JANUARY, 22);
        hourlyPayment.addTimeCard(new TimeCard(lastWeek, hours));

        LocalDate monday = of(2016, JANUARY, 25);
        hourlyPayment.addTimeCard(new TimeCard(monday, hours));

        assertPay(hours * hourlyRate);
    }

    @Test (expected = HourlyPayment.TimeCardNotFoundException.class)
    public void throwsExceptionIfNoTimeCardIsFoundForADate() {
        hourlyPayment.getTimeCard(friday);
    }

    private void assertPay(double expectedPay) {
        assertEquals(expectedPay, hourlyPayment.calculatePay(friday), 0.001);
    }
}

