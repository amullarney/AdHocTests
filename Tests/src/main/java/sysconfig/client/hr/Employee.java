package sysconfig.client.hr;


import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.exceptions.XtumlException;

import sysconfig.Client;
import sysconfig.client.widgets.EmployeeMenu;

public interface Employee extends IModelInstance<Employee,Client>, sysconfig.Employee {


    // selections
    default public void setR1_is_shown_on_EmployeeMenu( EmployeeMenu inst ) {}
    public EmployeeMenu R1_is_shown_on_EmployeeMenu() throws XtumlException;

}