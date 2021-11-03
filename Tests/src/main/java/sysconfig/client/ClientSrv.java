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

import java.util.HashMap;

public class ClientSrv extends Port<Client> implements IFoo {

    public ClientSrv( Client context, IPort<?> peer ) {
        super( context, peer );
    }

    // inbound messages
    public void b( final int p_count ) throws XtumlException {
        context().LOG().LogInteger( p_count );
        EmployeeMenu menu = EmployeeMenuImpl.create( context() );
    }


    public void a( final sysconfig.Employee emp ) throws XtumlException {
    	Employee p_emp = (Employee) emp; // down-cast to component-specific
        context().LOG().LogInfo( "Client received from Server: Employee name, number" );
        context().LOG().LogInfo( p_emp.getName() );
        context().LOG().LogInteger( p_emp.getNumber() );
        sysconfig.client.hr.Employee person = p_emp;
        EmployeeMenu menu = context().EmployeeMenu_instances().any();
        if ( !menu.isEmpty() ) {
            context().relate_R1_Employee_is_shown_on_EmployeeMenu( person, menu );
        }
        context().RegisterEmployee( person );
        context().generate(new EmployeeImpl.leave(getRunContext(), context().getId()).to(person));
    }



    // outbound messages
    public void c( final String p_ename,  final int p_enumb ) throws XtumlException {
        if ( satisfied() ) send(new IFoo.C(p_ename, p_enumb));
        else {
        }
    }


    @Override
    public void deliver( IMessage message ) throws XtumlException {
    	// fake this received string data representation of the serialized message - note: this fake is 'always' msg 'A' with id=1
        String s = "{\"messageHandle\":\"89e6c56d-e487-474d-bbd4-ceaae28d919c\",\"name\":\"A\",\"parameterData\":[{\"name\":\"John Doe\", \"number\":\"12345\", \"curr_state\":\"0\"}],id:1}";
        System.out.printf( "Server invoked Client::deliver(message) : %s \n", s );
        message = Message.deserialize(s);
        if ( null == message ) throw new BadArgumentException( "Cannot deliver null message." );
        switch ( message.getId() ) {
            case IFoo.SIGNAL_NO_B:
                b(IntegerUtil.deserialize(message.get(0)));
                break;
            case IFoo.SIGNAL_NO_A:
            	Object o = message.get(0);
            	/*
            	if (o instanceof HashMap) {
            		HashMap h = (HashMap)o;
                    System.out.printf( "message param is HashMap %d  \n", h.size() );
                    if ( h.containsKey("name"))
                        System.out.printf( "name is  %s  \n", h.get("name") );
            	}
            	*/
                a( (sysconfig.Employee)EmployeeImpl.deserialize(message.get(0), context() ));
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
