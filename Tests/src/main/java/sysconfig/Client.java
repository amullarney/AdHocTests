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
import sysconfig.client.hr.Employee;
import sysconfig.client.hr.EmployeeSet;
import sysconfig.client.hr.impl.EmployeeImpl;
import sysconfig.client.hr.impl.EmployeeSetImpl;
import sysconfig.client.widgets.EmployeeMenu;
import sysconfig.client.widgets.EmployeeMenuSet;
import sysconfig.client.widgets.impl.EmployeeMenuImpl;
import sysconfig.client.widgets.impl.EmployeeMenuSetImpl;
/* @Removed for 12002
import sysconfig.server.hr.Employee;
import sysconfig.server.hr.impl.EmployeeImpl;
*/


public class Client extends Component<Client> {

    private Map<String, Class<?>> classDirectory;

    public Client(IApplication app, IRunContext runContext, int populationId) {
        super(app, runContext, populationId);
        Employee_extent = new EmployeeSetImpl();
        EmployeeMenu_extent = new EmployeeMenuSetImpl();
        R1_Employee_is_shown_on_EmployeeMenu_extent = new RelationshipSet();
        LOG = null;
        classDirectory = new TreeMap<>();
        classDirectory.put("Employee", EmployeeImpl.class);
    }

    // domain functions
    public void T2() throws XtumlException {
        Employee e = EmployeeImpl.create( context() );
        e.setBirthdate("07-Jan-1961");
        e.setName("Jana Burke");
        e.setNumber(123456);
        context().Srv().c( e );
    }




    // relates and unrelates
    public void relate_R1_Employee_is_shown_on_EmployeeMenu( Employee form, EmployeeMenu part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot relate empty instances." );
        // TODO cardinality check
        if ( R1_Employee_is_shown_on_EmployeeMenu_extent.add( new Relationship( form.getInstanceId(), part.getInstanceId() ) ) ) {
            part.addR1_displays_Employee(form);
            form.setR1_is_shown_on_EmployeeMenu(part);
        }
        else throw new ModelIntegrityException( "Instances could not be related." );
    }

    public void unrelate_R1_Employee_is_shown_on_EmployeeMenu( Employee form, EmployeeMenu part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot unrelate empty instances." );
        if ( R1_Employee_is_shown_on_EmployeeMenu_extent.remove( R1_Employee_is_shown_on_EmployeeMenu_extent.get( form.getInstanceId(), part.getInstanceId() ) ) ) {
            part.removeR1_displays_Employee(form);
            form.setR1_is_shown_on_EmployeeMenu(EmployeeMenuImpl.EMPTY_EMPLOYEEMENU);
        }
        else throw new ModelIntegrityException( "Instances could not be unrelated." );
    }


    // instance selections
    private EmployeeMenuSet EmployeeMenu_extent;
    public EmployeeMenuSet EmployeeMenu_instances() {
        return EmployeeMenu_extent;
    }
    private EmployeeSet Employee_extent;
    public EmployeeSet Employee_instances() {
        return Employee_extent;
    }


    // relationship selections
    // relationship selections
    private IRelationshipSet R1_Employee_is_shown_on_EmployeeMenu_extent;
    public IRelationshipSet R1_Employee_is_shown_on_EmployeeMenus() throws XtumlException {
        return R1_Employee_is_shown_on_EmployeeMenu_extent;
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
        if ( instance instanceof Employee ) return Employee_extent.add( (Employee)instance );
        return false;
    }

    @Override
    public boolean removeInstance( IModelInstance<?,?> instance ) throws XtumlException {
        if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
        if ( instance.isEmpty() ) throw new EmptyInstanceException( "Cannot remove empty instance from population." );
        if ( instance instanceof Employee ) return Employee_extent.remove( (Employee)instance );
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
