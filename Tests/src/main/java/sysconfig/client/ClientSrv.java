package sysconfig.client;


import interfaces.IFoo;

import io.ciera.runtime.summit.exceptions.BadArgumentException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.interfaces.IMessage;
import io.ciera.runtime.summit.interfaces.IPort;
import io.ciera.runtime.summit.interfaces.Port;
import io.ciera.runtime.summit.types.IntegerUtil;

import java.util.Iterator;

import sysconfig.Client;
import sysconfig.client.clientapp.Menu;
import sysconfig.client.clientapp.impl.MenuImpl;
import sysconfig.client.hr.Employee;
import sysconfig.client.hr.EmployeeSet;


public class ClientSrv extends Port<Client> implements IFoo {

    public ClientSrv( Client context, IPort<?> peer ) {
        super( context, peer );
    }

    // inbound messages
    public void b( final int p_count ) throws XtumlException {
        context().LOG().LogInteger( p_count );
    }


    public void a( final sysconfig.Employee emp ) throws XtumlException {
    	sysconfig.client.hr.Employee p_emp = (Employee) emp; // down-cast to component-specific
        context().LOG().LogInfo( "Client: Employee name, birthdate, number" );
        context().LOG().LogInfo( p_emp.getName() );
        context().LOG().LogInfo( p_emp.getBirthdate() );
        context().LOG().LogInteger( p_emp.getNumber() );
        sysconfig.client.hr.Employee person = p_emp;
        context().RegisterEmployee( person );
        Menu menu = context().Menu_instances().any();
        if ( menu.isEmpty() ) {
            menu = MenuImpl.create( context() );
        }
        menu = context().Menu_instances().any();
        context().relate_R1_Employee_appears_in_Menu( person, menu );
        context().LOG().LogInfo( "Client display menu contents" );
        EmployeeSet entries = menu.R1_displays_Employee();
        Employee entry;
        for ( Iterator<Employee> _entry_iter = entries.elements().iterator(); _entry_iter.hasNext(); ) {
            entry = _entry_iter.next();
            context().LOG().LogInfo( entry.getName() );
        }
    }



    // outbound messages
    public void c( final sysconfig.Employee p_emp ) throws XtumlException {
        if ( satisfied() ) send(new IFoo.C(p_emp));
        else {
        }
    }


    @Override
    public void deliver( IMessage message ) throws XtumlException {
        System.out.printf( "Client-Server deliver message\n" );
        if ( null == message ) throw new BadArgumentException( "Cannot deliver null message." );
        switch ( message.getId() ) {
            case IFoo.SIGNAL_NO_B:
                b(IntegerUtil.deserialize(message.get(0)));
                break;
            case IFoo.SIGNAL_NO_A:
                a( sysconfig.client.hr.impl.EmployeeImpl.deserialize(message.get(0), context() ) );
                break;
        default:
            throw new BadArgumentException( "Message not implemented by this port." );
        }
    }



    @Override
    public String getName() {
        return "Srv";
    }

}
