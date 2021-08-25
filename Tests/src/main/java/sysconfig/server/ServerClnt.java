package sysconfig.server;


import interfaces.IFoo;

import io.ciera.runtime.summit.exceptions.BadArgumentException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.interfaces.IMessage;
import io.ciera.runtime.summit.interfaces.IPort;
import io.ciera.runtime.summit.interfaces.Port;

import sysconfig.Server;
import sysconfig.Client;
import sysconfig.Employee;


public class ServerClnt extends Port<Server> implements IFoo {

    public ServerClnt( Server context, IPort<?> peer ) {
        super( context, peer );
    }

    // inbound messages
    public void c( final Employee p_emp ) throws XtumlException {
    	sysconfig.server.hr.Employee emp = (sysconfig.server.hr.Employee) p_emp; // down-cast to component-specific
        context().LOG().LogInfo( "Server: Employee name, birthdate, number" );
        context().LOG().LogInfo( emp.getName() );
        context().LOG().LogInfo( emp.getBirthdate() );
        context().LOG().LogInteger( emp.getNumber() );
        Employee person = emp;
    }



    // outbound messages
    public void b( final int p_count ) throws XtumlException {
        if ( satisfied() ) send(new IFoo.B(p_count));
        else {
        }
    }
    public void a( final Employee p_emp ) throws XtumlException {
        if ( satisfied() ) send(new IFoo.A(p_emp));
        else {
        }
    }


    @Override
    public void deliver( IMessage message ) throws XtumlException {
        if ( null == message ) throw new BadArgumentException( "Cannot deliver null message." );
        switch ( message.getId() ) {
            case IFoo.SIGNAL_NO_C:
                System.out.printf( "Server heard response\n" );
                //a( sysconfig.server.hr.impl.EmployeeImpl.deserialize(message.get(0), context() ) );
                break;
        default:
            throw new BadArgumentException( "Message not implemented by this port." );
        }
    }



    @Override
    public String getName() {
        return "Clnt";
    }

}
