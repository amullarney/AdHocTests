package sysconfig.client.hr;


import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.exceptions.XtumlException;

import sysconfig.Client;

public interface Employee extends IModelInstance<Employee,Client>, sysconfig.Employee {

    // attributes
    public String getName() throws XtumlException;
    public void setName( String m_Name ) throws XtumlException;
    public String getBirthdate() throws XtumlException;
    public void setBirthdate( String m_Birthdate ) throws XtumlException;
    public void setNumber( int m_Number ) throws XtumlException;
    public int getNumber() throws XtumlException;


    // operations
    public void Report() throws XtumlException;


    // selections
    default public void setR100_works_in_Department( Department inst ) {}
    public Department R100_works_in_Department() throws XtumlException;


}
