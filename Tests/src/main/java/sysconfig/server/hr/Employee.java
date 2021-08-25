package sysconfig.server.hr;


import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.exceptions.XtumlException;

import sysconfig.Server;
import sysconfig.server.hr.Department;

public interface Employee extends IModelInstance<Employee,Server>, sysconfig.Employee {


    // attributes
    public String getName() throws XtumlException;
    public void setName( String m_Name ) throws XtumlException;
    public String getBirthdate() throws XtumlException;
    public void setBirthdate( String m_Birthdate ) throws XtumlException;
    public int getNumber() throws XtumlException;
    public void setNumber( int m_Number ) throws XtumlException;


    // operations
    public void Report() throws XtumlException;

    // selections
    default public void setR100_works_in_Department( Department inst ) {}
    public Department R100_works_in_Department() throws XtumlException;

}
