package sysconfig.client.hr.impl;


import io.ciera.runtime.instanceloading.AttributeChangedDelta;
import io.ciera.runtime.instanceloading.InstanceCreatedDelta;
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

import sysconfig.Client;
import sysconfig.client.hr.Employee;
import sysconfig.client.widgets.EmployeeMenu;
import sysconfig.client.widgets.impl.EmployeeMenuImpl;

import org.json.*; 
import org.json.JSONObject;


public class EmployeeImpl extends ModelInstance<Employee,Client> implements Employee {

    public static final String KEY_LETTERS = "Employee";
    public static final Employee EMPTY_EMPLOYEE = new EmptyEmployee();

    private Client context;

    // constructors
    
    // @Added for 12002
    public EmployeeImpl( sysconfig.server.hr.Employee serverEmp ) {
      System.out.printf( "Copy constructor\n" );
      try {
    	m_Name = serverEmp.getName();
    	m_Birthdate = serverEmp.getBirthdate();
    	m_Number = serverEmp.getNumber();
      } catch (Exception e) {
          System.out.printf( "Exception in copy constructor: %s\n", e );
      }
      
    }
    
    private EmployeeImpl( Client context ) {
        this.context = context;
        m_Name = "";
        m_Birthdate = "";
        m_Number = 0;
        R1_is_shown_on_EmployeeMenu_inst = EmployeeMenuImpl.EMPTY_EMPLOYEEMENU;
    }

    private EmployeeImpl( Client context, UniqueId instanceId, String m_Name, String m_Birthdate, int m_Number, int initialState ) {
        super(instanceId);
        this.context = context;
        this.m_Name = m_Name;
        this.m_Birthdate = m_Birthdate;
        this.m_Number = m_Number;
        R1_is_shown_on_EmployeeMenu_inst = EmployeeMenuImpl.EMPTY_EMPLOYEEMENU;
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
    }

    
    // @Added for 12002
    public String serialize() {
    	System.out.printf( "serializing employee on client side... %s\n", this.m_Name );
//    	return "\"\""  + this.m_Name + "\", "  +  "\""  + Integer.toString(this.m_Number) + "\"";
    	return "{  \"Name\": \"Jana\", \"Number\": \"1234\" }";
    }

    // static operations
    public static Employee deserialize( Object o, Client context ) {
    	// fake this for now... create component-specific instance and populate attributes  from JSON, if it were here!
        // String s = "{ \"Name\": \"Jana\", \"Number\": \"1234\"}";
        Employee e = null;
        System.out.printf( "Employee deserialize for client %s  \n", (String)o );
     	try {
	         e = EmployeeImpl.create( context );
	        System.out.printf( "Employee created for client \n" );

	        JSONObject jobj = new JSONObject(o);
	        System.out.printf( "JSON created  \n" );
	        int number = (int)jobj.opt("Number");
	        System.out.printf( "Number is %d  \n", number );
	        String name = (String)jobj.opt("Name");
	        System.out.printf( "Name is %s  \n", name );


	        e.setName( name );
	        e.setNumber( number );
 
	    	return  e;
    	}
    	catch(Exception ex ) { 
        System.out.printf( "Employee deserialize failed: %s \n", ex.toString() );
    	};
    	return e;
    }
   

    // events


    // selections
    private EmployeeMenu R1_is_shown_on_EmployeeMenu_inst;
    @Override
    public void setR1_is_shown_on_EmployeeMenu( EmployeeMenu inst ) {
        R1_is_shown_on_EmployeeMenu_inst = inst;
    }
    @Override
    public EmployeeMenu R1_is_shown_on_EmployeeMenu() throws XtumlException {
        return R1_is_shown_on_EmployeeMenu_inst;
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
    public EmployeeMenu R1_is_shown_on_EmployeeMenu() {
        return EmployeeMenuImpl.EMPTY_EMPLOYEEMENU;
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
