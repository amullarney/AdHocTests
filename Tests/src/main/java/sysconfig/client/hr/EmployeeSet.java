package sysconfig.client.hr;


import io.ciera.runtime.summit.classes.IInstanceSet;
import io.ciera.runtime.summit.exceptions.XtumlException;

import sysconfig.Client;
import sysconfig.client.hr.Employee;

import sysconfig.client.widgets.EmployeeMenuSet;


public interface EmployeeSet extends IInstanceSet<EmployeeSet,Employee>, sysconfig.hr.EmployeeSet {

    // attributes
    public void setName( String m_Name ) throws XtumlException;
    public void setNumber( int m_Number ) throws XtumlException;
    public void setBirthdate( String m_Birthdate ) throws XtumlException;


    // selections
    public EmployeeMenuSet R1_is_shown_on_EmployeeMenu() throws XtumlException;


}
