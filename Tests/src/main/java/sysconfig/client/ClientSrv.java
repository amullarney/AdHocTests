package sysconfig.client;


import interfaces.IFoo;

import io.ciera.runtime.summit.exceptions.BadArgumentException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.interfaces.IMessage;
import io.ciera.runtime.summit.interfaces.Message;  // needed for serialize()
import io.ciera.runtime.summit.interfaces.IPort;
import io.ciera.runtime.summit.interfaces.Port;
import io.ciera.runtime.summit.types.IntegerUtil;
import io.ciera.runtime.summit.types.StringUtil;

import sysconfig.Client;
import sysconfig.client.hr.Employee;
import sysconfig.client.hr.impl.EmployeeImpl;
import sysconfig.client.widgets.EmployeeMenu;
import sysconfig.client.widgets.impl.EmployeeMenuImpl;

public class ClientSrv extends Port<Client> implements IFoo {

    public ClientSrv( Client context, IPort<?> peer ) {
        super( context, peer );
    }

    // inbound messages
    public void b( final int p_count,  final String p_name ) throws XtumlException {
        context().LOG().LogInteger( p_count );
        EmployeeMenu menu = EmployeeMenuImpl.create( context() );
    }

    public void a( final sysconfig.Employee emp ) throws XtumlException {
    	Employee p_emp = (Employee) emp; // down-cast to component-specific
        context().LOG().LogInfo( "Client: Employee name, birthdate, number" );
        context().LOG().LogInfo( p_emp.getName() );
        context().LOG().LogInfo( p_emp.getBirthdate() );
        context().LOG().LogInteger( p_emp.getNumber() );
        sysconfig.client.hr.Employee person = p_emp;
        EmployeeMenu menu = context().EmployeeMenu_instances().any();
        if ( !menu.isEmpty() ) {
            context().relate_R1_Employee_is_shown_on_EmployeeMenu( person, menu );
        }
        context().RegisterEmployee( person );
    }


    // outbound messages
    public void c( final sysconfig.Employee p_emp ) throws XtumlException {
        if ( satisfied() ) send(new IFoo.C(p_emp));
        else {
        }
    }


    @Override
    public void deliver( IMessage message ) throws XtumlException {
        if ( null == message ) throw new BadArgumentException( "Cannot deliver null message." );
        switch ( message.getId() ) {
            case IFoo.SIGNAL_NO_B:
                b(IntegerUtil.deserialize(message.get(0)), StringUtil.deserialize(message.get(1)));
                break;
            case IFoo.SIGNAL_NO_A:
                System.out.printf( "Client-Server received: %s \n", message.getParm("p_emp") );
                a( (sysconfig.Employee)EmployeeImpl.deserialize( message.getParm("p_emp"), context() ) );
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
