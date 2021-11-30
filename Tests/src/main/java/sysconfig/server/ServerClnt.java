package sysconfig.server;


import interfaces.IFoo;

import io.ciera.runtime.summit.exceptions.BadArgumentException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.interfaces.IMessage;
import io.ciera.runtime.summit.interfaces.IPort;
import io.ciera.runtime.summit.interfaces.Port;

import java.util.Iterator;

import sysconfig.Server;
import sysconfig.server.hr.Employee;
import sysconfig.server.hr.impl.EmployeeImpl;
import sysconfig.server.hr.EmployeeSet;
import sysconfig.server.hr.impl.EmployeeSetImpl;

public class ServerClnt extends Port<Server> implements IFoo {

    public ServerClnt( Server context, IPort<?> peer ) {
        super( context, peer );
    }

    // inbound messages
    public void c( final sysconfig.hr.EmployeeSet q_p_emp ) throws XtumlException {
    	EmployeeSet p_empls = (EmployeeSet)q_p_emp;

    	EmployeeSet employees = p_empls;
        context().LOG().LogInfo( "Server: Employee name, birthdate, number" );
        Employee emp;
        for ( Iterator<Employee> _emp_iter = employees.elements().iterator(); _emp_iter.hasNext(); ) {
            emp = _emp_iter.next();
            context().LOG().LogInfo( emp.getName() );
            context().LOG().LogInfo( emp.getBirthdate() );
            context().LOG().LogInteger( emp.getNumber() );
            Employee person = emp;
            person.Report();
        }
    }



    // outbound messages
    public void a( final sysconfig.hr.Employee p_emp ) throws XtumlException {
        if ( satisfied() ) send(new IFoo.A(p_emp));
        else {
        }
    }
    public void b( final int p_count,  final String p_name ) throws XtumlException {
        if ( satisfied() ) send(new IFoo.B(p_count, p_name));
        else {
        }
    }


    @Override
    public void deliver( IMessage message ) throws XtumlException {
        if ( null == message ) throw new BadArgumentException( "Cannot deliver null message." );
        switch ( message.getId() ) {
            case IFoo.SIGNAL_NO_C:
//                c((sysconfig.hr.EmployeeSet) EmployeeSetImpl.deserialize(message.getParm("p_emp"), context()));
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
