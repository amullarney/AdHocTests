package sysconfig.client.hr.impl;


import io.ciera.runtime.instanceloading.AttributeChangedDelta;
import io.ciera.runtime.instanceloading.InstanceCreatedDelta;
import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.classes.IInstanceIdentifier;
import io.ciera.runtime.summit.classes.InstanceIdentifier;
import io.ciera.runtime.summit.classes.ModelInstance;
import io.ciera.runtime.summit.exceptions.EmptyInstanceException;
import io.ciera.runtime.summit.exceptions.InstancePopulationException;
import io.ciera.runtime.summit.statemachine.IEvent;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.IWhere;
import io.ciera.runtime.summit.types.IXtumlType;
import io.ciera.runtime.summit.types.StringUtil;
import io.ciera.runtime.summit.types.UniqueId;

import java.util.Iterator;

import sysconfig.Client;
import sysconfig.client.hr.Department;
import sysconfig.client.hr.Employee;
import sysconfig.client.hr.impl.DepartmentImpl;
import sysconfig.client.hr.EmployeeSet;

import org.json.*; 
//import org.json.JSONValue; 


public class EmployeeImpl extends ModelInstance<Employee,Client> implements Employee {

    public static final String KEY_LETTERS = "Employee";
    public static final Employee EMPTY_EMPLOYEE = new EmptyEmployee();

    private Client context;

    // constructors
    
    // @Added for 12002
 /*   public EmployeeImpl( sysconfig.server.hr.Employee serverEmp ) {
      System.out.printf( "Copy constructor\n" );
      try {
    	m_Name = serverEmp.getName();
    	m_Birthdate = serverEmp.getBirthdate();
    	m_Number = serverEmp.getNumber();
      } catch (Exception e) {
          System.out.printf( "Exception in copy constructor: %s\n", e );
      }
      
    }
*/
    
    private EmployeeImpl( Client context ) {
        this.context = context;
        m_Name = "";
        m_Birthdate = "";
        m_Number = 0;
        R100_works_in_Department_inst = DepartmentImpl.EMPTY_DEPARTMENT;
        statemachine = new EmployeeStateMachine(this, context());
    }

    private EmployeeImpl( Client context, UniqueId instanceId, String m_Name, String m_Birthdate, int m_Number, int initialState ) {
        super(instanceId);
        this.context = context;
        this.m_Name = m_Name;
        this.m_Birthdate = m_Birthdate;
        this.m_Number = m_Number;
        R100_works_in_Department_inst = DepartmentImpl.EMPTY_DEPARTMENT;
        statemachine = new EmployeeStateMachine(this, context());
     }

    public static Employee create( Client context ) throws XtumlException {
        Employee newEmployee = new EmployeeImpl( context );
        if ( context.addInstance( newEmployee ) ) {
            newEmployee.getRunContext().addChange(new InstanceCreatedDelta(newEmployee, KEY_LETTERS));
            return newEmployee;
        }
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }

    public static Employee create( Client context, UniqueId instanceId, String m_Name, String m_Birthdate, int m_Number, int initialState ) throws XtumlException {
        Employee newEmployee = new EmployeeImpl( context, instanceId, m_Name, m_Birthdate, m_Number, initialState );
        if ( context.addInstance( newEmployee ) ) {
            return newEmployee;
        }
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }



    private final EmployeeStateMachine statemachine;
    
    @Override
    public void accept(IEvent event) throws XtumlException {
        statemachine.transition(event);
    }

    @Override
    public int getCurrentState() {
        return statemachine.getCurrentState();
    }


    // attributes
    private String m_Name;
    @Override
    public String getName() throws XtumlException {
        checkLiving();
        return m_Name;
    }
    @Override
    public void setName(String m_Name) throws XtumlException {
        checkLiving();
        if (StringUtil.inequality(m_Name, this.m_Name)) {
            final String oldValue = this.m_Name;
            this.m_Name = m_Name;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "m_Name", oldValue, this.m_Name));
        }
    }
    private String m_Birthdate;
    @Override
    public String getBirthdate() throws XtumlException {
        checkLiving();
        return m_Birthdate;
    }
    @Override
    public void setBirthdate(String m_Birthdate) throws XtumlException {
        checkLiving();
        if (StringUtil.inequality(m_Birthdate, this.m_Birthdate)) {
            final String oldValue = this.m_Birthdate;
            this.m_Birthdate = m_Birthdate;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "m_Birthdate", oldValue, this.m_Birthdate));
        }
    }
    private int m_Number;
    @Override
    public int getNumber() throws XtumlException {
        checkLiving();
        return m_Number;
    }
    @Override
    public void setNumber(int m_Number) throws XtumlException {
        checkLiving();
        if (m_Number != this.m_Number) {
            final int oldValue = this.m_Number;
            this.m_Number = m_Number;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "m_Number", oldValue, this.m_Number));
        }
    }


    // instance identifiers

    // operations
    @Override
    public void Report() throws XtumlException {
        Department dept = context().Department_instances().any();
        EmployeeSet persons = dept.R100_employs_Employee();
        Employee person;
        for ( Iterator<Employee> _person_iter = persons.elements().iterator(); _person_iter.hasNext(); ) {
            person = _person_iter.next();
            context().LOG().LogInfo( "Reporting: " + person.getName() );
        }
    }

    
    // @Added for 12002
    public String toString() {
    	// @TODO
    	return "";
    }

    // static operations
    public static Employee deserialize( Object o, Client context ) {
        System.out.printf( "Client Employee deserialize\n" );
    	//JSONParser json = new JSONParser();
    	// fake this for now... create component-specific instance and populate attributes  from JSON, if it were here!
    	try {
        Employee e = EmployeeImpl.create( context );
        String s = "{ \"Name\": \"Jana\", \"Number\": \"1234\"}";
        //Object obj = JSONObject.Parse(s);
 //       JSONObject jobj = new JSONObject(s);
        //e.setBirthdate("07-Jan-1961");
 //       e.setName( (String) jobj.getString("Name") );
        e.setName("Jana");
        e.setNumber(123456);
        // e.setName( jstr.getString( "Name" ));
    	return (Employee) e;
    	}
    	catch(Exception ex ) { };
    	return (Employee) null;
    }
   

    // events


    // selections
    private Department R100_works_in_Department_inst;
    @Override
    public void setR100_works_in_Department( Department inst ) {
        R100_works_in_Department_inst = inst;
    }
    @Override
    public Department R100_works_in_Department() throws XtumlException {
        return R100_works_in_Department_inst;
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
    public Employee self() {
        return this;
    }

    @Override
    public Employee oneWhere(IWhere<IXtumlType> condition) throws XtumlException {
        if (null == condition) throw new XtumlException("Null condition passed to selection.");
        if (condition.evaluate(this)) return this;
        else return EMPTY_EMPLOYEE;
    }

}

class EmptyEmployee extends ModelInstance<Employee,Client> implements Employee {

    // attributes
    public void setName( String m_Name ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public String getName() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setBirthdate( String m_Birthdate ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public String getBirthdate() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setNumber( int m_Number ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public int getNumber() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }


    // operations
    public void Report() throws XtumlException {
        throw new EmptyInstanceException( "Cannot invoke operation on empty instance." );
    }


    // selections
    @Override
    public Department R100_works_in_Department() {
        return DepartmentImpl.EMPTY_DEPARTMENT;
    }


    @Override
    public String getKeyLetters() {
        return EmployeeImpl.KEY_LETTERS;
    }

    @Override
    public Employee self() {
        return this;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Employee oneWhere(IWhere<IXtumlType> condition) throws XtumlException {
        if (null == condition) throw new XtumlException("Null condition passed to selection.");
        return EmployeeImpl.EMPTY_EMPLOYEE;
    }

}
