package sysconfig.server.hr;


import io.ciera.runtime.summit.classes.IInstanceSet;
import io.ciera.runtime.summit.exceptions.XtumlException;

import sysconfig.Server;
import sysconfig.server.hr.Employee;

public interface EmployeeSet extends IInstanceSet<EmployeeSet,Employee>, sysconfig.hr.EmployeeSet {

    // attributes
    public void setBirthdate( String m_Birthdate ) throws XtumlException;
    public void setNumber( int m_Number ) throws XtumlException;
    public void setName( String m_Name ) throws XtumlException;


    // selections


}
