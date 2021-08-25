package sysconfig.server.hr;


import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.exceptions.XtumlException;

import sysconfig.Server;
import sysconfig.server.hr.Employee;
import sysconfig.server.hr.EmployeeSet;


public interface Department extends IModelInstance<Department,Server>, sysconfig.Department {

    // attributes
    public void setTitle( String m_Title ) throws XtumlException;
    public String getTitle() throws XtumlException;


    // operations


    // selections
    default public void addR100_employs_Employee( Employee inst ) {}
    default public void removeR100_employs_Employee( Employee inst ) {}
    public EmployeeSet R100_employs_Employee() throws XtumlException;


}
