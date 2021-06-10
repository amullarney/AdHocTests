package sysconfig;


import io.ciera.runtime.summit.application.IApplication;
import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.components.Component;
import io.ciera.runtime.summit.exceptions.BadArgumentException;
import io.ciera.runtime.summit.exceptions.EmptyInstanceException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.util.LOG;
import io.ciera.runtime.summit.util.impl.LOGImpl;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import sysconfig.server.ServerClnt;
import sysconfig.server.hr.Employee;
import sysconfig.server.hr.EmployeeSet;
import sysconfig.server.hr.impl.EmployeeImpl;
import sysconfig.server.hr.impl.EmployeeSetImpl;


public class Server extends Component<Server> {

    private Map<String, Class<?>> classDirectory;

    public Server(IApplication app, IRunContext runContext, int populationId) {
        super(app, runContext, populationId);
        Employee_extent = new EmployeeSetImpl();

        LOG = null;
        classDirectory = new TreeMap<>();
        classDirectory.put("Employee", EmployeeImpl.class);
    }

    // domain functions
    public void T1() throws XtumlException {
        Employee e = EmployeeImpl.create( context() );
        e.setBirthdate("07-Jan-1961");
        e.setName("Jana Burke");
        e.setNumber(123456);
        context().Clnt().b( 5 );
        context().Clnt().a( e );
    }



    // relates and unrelates


    // instance selections
    private EmployeeSet Employee_extent;
    public EmployeeSet Employee_instances() {
        return Employee_extent;
    }


    // relationship selections


    // ports
    private ServerClnt ServerClnt;
    public ServerClnt Clnt() {
        if ( null == ServerClnt ) ServerClnt = new ServerClnt( this, null );
        return ServerClnt;
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
        T1();
    }

    @Override
    public String getVersion() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("ServerProperties.properties"));
        } catch (IOException e) { /* do nothing */ }
        return prop.getProperty("version", "Unknown");
    }
    @Override
    public String getVersionDate() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("ServerProperties.properties"));
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
    public Server context() {
        return this;
    }

    @Override
    public Class<?> getClassByKeyLetters(String keyLetters) {
        return classDirectory.get(keyLetters);
    }

}
