package sysconfig;


import io.ciera.runtime.summit.application.IApplication;
import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.classes.IRelationshipSet;
import io.ciera.runtime.summit.classes.Relationship;
import io.ciera.runtime.summit.classes.RelationshipSet;
import io.ciera.runtime.summit.components.Component;
import io.ciera.runtime.summit.exceptions.BadArgumentException;
import io.ciera.runtime.summit.exceptions.EmptyInstanceException;
import io.ciera.runtime.summit.exceptions.ModelIntegrityException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.util.LOG;
import io.ciera.runtime.summit.util.impl.LOGImpl;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import sysconfig.client.ClientSrv;
import sysconfig.client.hr.Department;
import sysconfig.client.hr.DepartmentSet;
import sysconfig.client.hr.Employee;
import sysconfig.client.hr.EmployeeSet;
import sysconfig.client.hr.impl.DepartmentImpl;
import sysconfig.client.hr.impl.DepartmentSetImpl;
import sysconfig.client.hr.impl.EmployeeImpl;
import sysconfig.client.hr.impl.EmployeeSetImpl;
// 3 bad server imports removed

public class Client extends Component<Client> {

    private Map<String, Class<?>> classDirectory;

    public Client(IApplication app, IRunContext runContext, int populationId) {
        super(app, runContext, populationId);
        Department_extent = new DepartmentSetImpl();
        Employee_extent = new EmployeeSetImpl();
        R100_Employee_works_in_Department_extent = new RelationshipSet();
        LOG = null;
        classDirectory = new TreeMap<>();
        classDirectory.put("Department", DepartmentImpl.class);
        classDirectory.put("Employee", EmployeeImpl.class);
    }

    // domain functions
    public void RegisterEmployee( final Employee p_employee ) throws XtumlException {
        Department dept = context().Department_instances().any();
        if ( dept.isEmpty() ) {
            dept = DepartmentImpl.create( context() );
        }
        dept = context().Department_instances().any();
        Employee person = p_employee;
        context().relate_R100_Employee_works_in_Department( person, dept );
        context().LOG().LogInfo( "Client requesting Employee.Report()" );
        person.Report();
        context().T2();
        context().LOG().LogInfo( "Client: Registered: " + person.getName() );
    }

    public void T2() throws XtumlException {
        context().LOG().LogInfo( "T2 invoked by client" );
        Employee e = context().Employee_instances().any();
        context().Srv().c( e );
    }



    // relates and unrelates
    public void relate_R100_Employee_works_in_Department( Employee form, Department part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot relate empty instances." );
        // TODO cardinality check
        if ( R100_Employee_works_in_Department_extent.add( new Relationship( form.getInstanceId(), part.getInstanceId() ) ) ) {
            part.addR100_employs_Employee(form);
            form.setR100_works_in_Department(part);
        }
        else throw new ModelIntegrityException( "Instances could not be related." );
    }

    public void unrelate_R100_Employee_works_in_Department( Employee form, Department part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot unrelate empty instances." );
        if ( R100_Employee_works_in_Department_extent.remove( R100_Employee_works_in_Department_extent.get( form.getInstanceId(), part.getInstanceId() ) ) ) {
            part.removeR100_employs_Employee(form);
            form.setR100_works_in_Department(DepartmentImpl.EMPTY_DEPARTMENT);
        }
        else throw new ModelIntegrityException( "Instances could not be unrelated." );
    }


    // instance selections
    private DepartmentSet Department_extent;
    public DepartmentSet Department_instances() {
        return Department_extent;
    }
    private EmployeeSet Employee_extent;
    public EmployeeSet Employee_instances() {
        return Employee_extent;
    }


    // relationship selections
    private IRelationshipSet R100_Employee_works_in_Department_extent;
    public IRelationshipSet R100_Employee_works_in_Departments() throws XtumlException {
        return R100_Employee_works_in_Department_extent;
    }


    // ports
    private ClientSrv ClientSrv;
    public ClientSrv Srv() {
        if ( null == ClientSrv ) ClientSrv = new ClientSrv( this, null );
        return ClientSrv;
    }


    // utilities
    private LOG LOG;
    public LOG LOG() {
        if ( null == LOG ) LOG = new LOGImpl<>( this );
        return LOG;
    }


    // component initialization function
    @Override
    public void initialize() throws XtumlException {

    }

    @Override
    public String getVersion() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("ClientProperties.properties"));
        } catch (IOException e) { /* do nothing */ }
        return prop.getProperty("version", "Unknown");
    }
    @Override
    public String getVersionDate() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("ClientProperties.properties"));
        } catch (IOException e) { /* do nothing */ }
        return prop.getProperty("version_date", "Unknown");
    }

    @Override
    public boolean addInstance( IModelInstance<?,?> instance ) throws XtumlException {
        if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
        if ( instance.isEmpty() ) throw new EmptyInstanceException( "Cannot add empty instance to population." );
        if ( instance instanceof Department ) return Department_extent.add( (Department)instance );
        else if ( instance instanceof Employee ) return Employee_extent.add( (Employee)instance );
        return false;
    }

    @Override
    public boolean removeInstance( IModelInstance<?,?> instance ) throws XtumlException {
        if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
        if ( instance.isEmpty() ) throw new EmptyInstanceException( "Cannot remove empty instance from population." );
        if ( instance instanceof Department ) return Department_extent.remove( (Department)instance );
        else if ( instance instanceof Employee ) return Employee_extent.remove( (Employee)instance );
        return false;
    }

    @Override
    public Client context() {
        return this;
    }

    @Override
    public Class<?> getClassByKeyLetters(String keyLetters) {
        return classDirectory.get(keyLetters);
    }

}
