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
    public void b( final int p_count ) throws XtumlException {
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
        System.out.printf( "Client-Server deliver message\n" );
        String s = "{\"messageHandle\":\"89e6c56d-e487-474d-bbd4-ceaae28d919c\",\"name\":\"A\",\"parameterData\":[\"Jana Burke\", \"123456\"],\"id\":1}";
        message = Message.deserialize(s);
        if ( null == message ) throw new BadArgumentException( "Cannot deliver null message." );
        switch ( message.getId() ) {
            case IFoo.SIGNAL_NO_B:
                b(IntegerUtil.deserialize(message.get(0)));
                break;
            case IFoo.SIGNAL_NO_A:
            	String name = StringUtil.deserialize(message.get(0));
            	int number = IntegerUtil.deserialize(message.get(1));
            	
                System.out.printf( "message params %s %d \n", name, number );
                a( (sysconfig.Employee)EmployeeImpl.deserialize( name, number, context() ));
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