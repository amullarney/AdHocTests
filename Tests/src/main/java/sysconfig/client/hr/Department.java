package sysconfig.client.hr;


import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.exceptions.XtumlException;

import sysconfig.Client;
import sysconfig.client.hr.Employee;
import sysconfig.client.hr.EmployeeSet;


public interface Department extends IModelInstance<Department,Client>, sysconfig.Department {

    // attributes
    public String getTitle() throws XtumlException;
    public void setTitle( String m_Title ) throws XtumlException;


    // operations


    // selections
    default public void addR100_employs_Employee( Employee inst ) {}
    default public void removeR100_employs_Employee( Employee inst ) {}
    public EmployeeSet R100_employs_Employee() throws XtumlException;


}
