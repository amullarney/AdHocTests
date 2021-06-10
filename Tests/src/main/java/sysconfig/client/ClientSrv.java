package sysconfig.client;


import interfaces.IFoo;

import io.ciera.runtime.summit.exceptions.BadArgumentException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.interfaces.IMessage;
import io.ciera.runtime.summit.interfaces.IPort;
import io.ciera.runtime.summit.interfaces.Port;
import io.ciera.runtime.summit.types.IntegerUtil;

import sysconfig.Client;
import sysconfig.Employee;


public class ClientSrv extends Port<Client> implements IFoo {

    public ClientSrv( Client context, IPort<?> peer ) {
        super( context, peer );
    }

    // inbound messages
    public void b( final int p_count ) throws XtumlException {
        context().LOG().LogInteger( p_count );
    }

    public void a( final Employee p_emp ) throws XtumlException {
    	sysconfig.client.hr.Employee emp = (sysconfig.client.hr.Employee) p_emp; // down-cast to component-specific
        context().LOG().LogInfo( "Client: Employee name, birthdate, number" );
        context().LOG().LogInfo( emp.getName() );
        context().LOG().LogInfo( emp.getBirthdate() );
        context().LOG().LogInteger( emp.getNumber() );
    }



    // outbound messages
    public void c( final Employee p_emp ) throws XtumlException {
        if ( satisfied() ) send(new IFoo.C(p_emp));
        else {
        }
    }


    @Override
    public void deliver( IMessage message ) throws XtumlException {
        if ( null == message ) throw new BadArgumentException( "Cannot deliver null message." );
        switch ( message.getId() ) {
            case IFoo.SIGNAL_NO_B:
                b(IntegerUtil.deserialize(message.get(0)));
                break;
            case IFoo.SIGNAL_NO_A:
                a( sysconfig.client.hr.impl.EmployeeImpl.deserialize(message.get(0)) );
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
