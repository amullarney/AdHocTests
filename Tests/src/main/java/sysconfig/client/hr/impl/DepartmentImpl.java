package sysconfig.client.hr.impl;


import io.ciera.runtime.instanceloading.AttributeChangedDelta;
import io.ciera.runtime.instanceloading.InstanceCreatedDelta;
import io.ciera.runtime.summit.application.ActionHome;
import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.classes.IInstanceIdentifier;
import io.ciera.runtime.summit.classes.InstanceIdentifier;
import io.ciera.runtime.summit.classes.ModelInstance;
import io.ciera.runtime.summit.exceptions.EmptyInstanceException;
import io.ciera.runtime.summit.exceptions.InstancePopulationException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.IWhere;
import io.ciera.runtime.summit.types.IXtumlType;
import io.ciera.runtime.summit.types.StringUtil;
import io.ciera.runtime.summit.types.UniqueId;

import java.util.Iterator;

import sysconfig.Client;
import sysconfig.client.hr.Department;
import sysconfig.client.hr.Employee;
import sysconfig.client.hr.EmployeeSet;
import sysconfig.client.hr.impl.EmployeeSetImpl;
// 3 bad server imports removed

public class DepartmentImpl extends ModelInstance<Department,Client> implements Department {

    public static final String KEY_LETTERS = "Department";
    public static final Department EMPTY_DEPARTMENT = new EmptyDepartment();

    private Client context;

    // constructors
    private DepartmentImpl( Client context ) {
        this.context = context;
        m_Title = "";
        R100_employs_Employee_set = new EmployeeSetImpl();
    }

    private DepartmentImpl( Client context, String m_Title ) {
        this.context = context;
        this.m_Title = m_Title;
        R100_employs_Employee_set = new EmployeeSetImpl();
    }

    private DepartmentImpl( Client context, UniqueId instanceId, String m_Title ) {
        super(instanceId);
        this.context = context;
        this.m_Title = m_Title;
        R100_employs_Employee_set = new EmployeeSetImpl();
    }

    public static Department create( Client context ) throws XtumlException {
        Department newDepartment = new DepartmentImpl( context );
        if ( context.addInstance( newDepartment ) ) {
            newDepartment.getRunContext().addChange(new InstanceCreatedDelta(newDepartment, KEY_LETTERS));
            return newDepartment;
        }
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }

    public static Department create( Client context, String m_Title ) throws XtumlException {
        Department newDepartment = new DepartmentImpl( context, m_Title );
        if ( context.addInstance( newDepartment ) ) {
            return newDepartment;
        }
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }

    public static Department create( Client context, UniqueId instanceId, String m_Title ) throws XtumlException {
        Department newDepartment = new DepartmentImpl( context, instanceId, m_Title );
        if ( context.addInstance( newDepartment ) ) {
            return newDepartment;
        }
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }



    // attributes
    private String m_Title;
    @Override
    public String getTitle() throws XtumlException {
        checkLiving();
        return m_Title;
    }
    @Override
    public void setTitle(String m_Title) throws XtumlException {
        checkLiving();
        if (StringUtil.inequality(m_Title, this.m_Title)) {
            final String oldValue = this.m_Title;
            this.m_Title = m_Title;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "m_Title", oldValue, this.m_Title));
        }
    }


    // instance identifiers

    // operations


    // static operations
    public static class CLASS extends ActionHome<Client> {

        public CLASS( Client context ) {
            super( context );
        }

        public void Report() throws XtumlException {
            Department dept = context().Department_instances().any();
            EmployeeSet persons = dept.R100_employs_Employee();
            Employee person;
            for ( Iterator<Employee> _person_iter = persons.elements().iterator(); _person_iter.hasNext(); ) {
                person = _person_iter.next();
                context().LOG().LogInfo( "Reporting: " + person.getName() );
            }
        }



    }


    // events


    // selections
    private EmployeeSet R100_employs_Employee_set;
    @Override
    public void addR100_employs_Employee( Employee inst ) {
        R100_employs_Employee_set.add(inst);
    }
    @Override
    public void removeR100_employs_Employee( Employee inst ) {
        R100_employs_Employee_set.remove(inst);
    }
    @Override
    public EmployeeSet R100_employs_Employee() throws XtumlException {
        return R100_employs_Employee_set;
    }


    @Override
    public IRunContext getRunContext() {
        return context().getRunContext();
    }

    @Override
    public Client context() {
        return context;
    }

    @Override
    public String getKeyLetters() {
        return KEY_LETTERS;
    }

    @Override
    public Department self() {
        return this;
    }

    @Override
    public Department oneWhere(IWhere<IXtumlType> condition) throws XtumlException {
        if (null == condition) throw new XtumlException("Null condition passed to selection.");
        if (condition.evaluate(this)) return this;
        else return EMPTY_DEPARTMENT;
    }

}

class EmptyDepartment extends ModelInstance<Department,Client> implements Department {

    // attributes
    public String getTitle() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setTitle( String m_Title ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }


    // operations


    // selections
    @Override
    public EmployeeSet R100_employs_Employee() {
        return (new EmployeeSetImpl());
    }


    @Override
    public String getKeyLetters() {
        return DepartmentImpl.KEY_LETTERS;
    }

    @Override
    public Department self() {
        return this;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Department oneWhere(IWhere<IXtumlType> condition) throws XtumlException {
        if (null == condition) throw new XtumlException("Null condition passed to selection.");
        return DepartmentImpl.EMPTY_DEPARTMENT;
    }

}
