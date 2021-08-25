package sysconfig.client.hr.impl;


import io.ciera.runtime.summit.classes.InstanceSet;
import io.ciera.runtime.summit.exceptions.XtumlException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import sysconfig.client.hr.Employee;
import sysconfig.client.hr.EmployeeSet;


public class EmployeeSetImpl extends InstanceSet<EmployeeSet,Employee> implements EmployeeSet {

    public EmployeeSetImpl() {
    }

    public EmployeeSetImpl(Comparator<? super Employee> comp) {
        super(comp);
    }

    // attributes
    @Override
    public void setName( String m_Name ) throws XtumlException {
        for ( Employee employee : this ) employee.setName( m_Name );
    }
    @Override
    public void setNumber( int m_Number ) throws XtumlException {
        for ( Employee employee : this ) employee.setNumber( m_Number );
    }
    @Override
    public void setBirthdate( String m_Birthdate ) throws XtumlException {
        for ( Employee employee : this ) employee.setBirthdate( m_Birthdate );
    }


    // selections


    @Override
    public Employee nullElement() {
        return EmployeeImpl.EMPTY_EMPLOYEE;
    }

    @Override
    public EmployeeSet emptySet() {
      return new EmployeeSetImpl();
    }

    @Override
    public EmployeeSet emptySet(Comparator<? super Employee> comp) {
      return new EmployeeSetImpl(comp);
    }

    @Override
    public List<Employee> elements() {
        return Arrays.asList(toArray(new Employee[0]));
    }

}
