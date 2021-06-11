package sysconfig;


import io.ciera.runtime.summit.types.IXtumlType;
import io.ciera.runtime.summit.exceptions.XtumlException;

import sysconfig.Server;

public interface Employee extends IXtumlType {

    // attributes
    public String getName() throws XtumlException;
    public void setName( String m_Name ) throws XtumlException;
    public String getBirthdate() throws XtumlException;
    public void setBirthdate( String m_Birthdate ) throws XtumlException;
    public int getNumber() throws XtumlException;
    public void setNumber( int m_Number ) throws XtumlException;
 
    // operations
    public void Report() throws XtumlException;

}
