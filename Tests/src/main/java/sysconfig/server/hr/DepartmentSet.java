package sysconfig.server.hr;


import io.ciera.runtime.summit.classes.IInstanceSet;
import io.ciera.runtime.summit.exceptions.XtumlException;

import sysconfig.server.hr.EmployeeSet;


public interface DepartmentSet extends IInstanceSet<DepartmentSet,Department> {

    // attributes
    public void setTitle( String m_Title ) throws XtumlException;


    // selections
    public EmployeeSet R100_employs_Employee() throws XtumlException;


}
