package com.mael.payroll.transactions;

import com.mael.payroll.Employee;
import com.mael.payroll.PayrollDBFacade;

public class ChangeEmployeeName extends ChangeEmployee {

    private String name;

    public ChangeEmployeeName(PayrollDBFacade payrollDB, int employeeId, String name) {
        super(payrollDB, employeeId);
        this.name = name;
    }

    public void change(Employee employee) {
        employee.setName(name);
    }
}
