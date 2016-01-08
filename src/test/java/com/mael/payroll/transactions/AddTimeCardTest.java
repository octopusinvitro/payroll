package com.mael.payroll.transactions;

import com.mael.payroll.Employee;
import com.mael.payroll.PayrollDBFacade;
import com.mael.payroll.cards.TimeCard;
import com.mael.payroll.paymentTypes.HourlyPayment;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static junit.framework.TestCase.assertEquals;

public class AddTimeCardTest {

    private Employee employee;
    private PayrollDBFacade payrollDB;
    private AddTimeCard addTimeCard;

    @Before
    public void setUp() {
        payrollDB = new PayrollDBFacade();
    }

    @Test
    public void addsTimeCardToHourlyEmployee() {
        AddEmployee addHourlyEmployee = new AddHourlyEmployee(payrollDB, 1, "Squiddo", "FishBowl", 1000.0);
        addHourlyEmployee.execute();
        employee = payrollDB.getEmployee(1);

        LocalDate friday = LocalDate.of(2016, Month.JANUARY, 29);
        addTimeCard = new AddTimeCard(payrollDB, 1, friday, 8.0);
        addTimeCard.execute();

        TimeCard timeCard = ((HourlyPayment)employee.getPaymentType()).getTimeCard(friday);
        assertEquals(friday, timeCard.getDate());
        assertEquals(8.0, timeCard.getHours(), 0.001);
    }

    @Test (expected = PaymentTypeMismatchException.class)
    public void onlyHourlyEmployeesCanHaveTimeCards() {
        AddEmployee addMonthlyEmployee = new AddMonthlyEmployee(payrollDB, 1, "Squiddo", "FishBowl", 1000.0);
        addMonthlyEmployee.execute();
        employee = payrollDB.getEmployee(1);

        LocalDate friday = LocalDate.of(2016, Month.JANUARY, 29);
        addTimeCard = new AddTimeCard(payrollDB, 1, friday, 8.0);
        addTimeCard.execute();
    }
}
