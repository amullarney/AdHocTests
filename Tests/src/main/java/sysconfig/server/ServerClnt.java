package sysconfig.server;


import interfaces.IFoo;

import io.ciera.runtime.summit.exceptions.BadArgumentException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.interfaces.IMessage;
import io.ciera.runtime.summit.interfaces.Message;  // needed for serialize()
import io.ciera.runtime.summit.interfaces.IPort;
import io.ciera.runtime.summit.interfaces.Port;
import io.ciera.runtime.summit.types.IntegerUtil;
import io.ciera.runtime.summit.types.StringUtil;

import sysconfig.Server;
import sysconfig.Client;
import sysconfig.Employee;

import java.util.HashMap;

public class ServerClnt extends Port<Server> implements IFoo {

    public ServerClnt( Server context, IPort<?> peer ) {
        super( context, peer );
    }

    // inbound messages
    public void c( final String p_ename,  final int p_enumb ) throws XtumlException {
        context().LOG().LogInfo( "Server: Employee name, number" );
        context().LOG().LogInfo( p_ename );
        context().LOG().LogInteger( p_enumb );
    }


    // outbound messages
    public void b( final int p_count ) throws XtumlException {
        if ( satisfied() ) send(new IFoo.B(p_count));
        else {
        }
    }
    public void a( final Employee p_emp ) throws XtumlException {
        if ( satisfied() ) {
          IMessage msg = new IFoo.A(p_emp);
          String str = msg.serialize();
          //String str = "Employee data here...";
          System.out.printf( "Server send: %s\n", str );
          send(msg);
        }
        else {
        }
    }


    @Override
    public void deliver( IMessage message ) throws XtumlException {
        System.out.printf( "Server heard response c from Client\n" );
        String s = "{\"messageHandle\":\"89e6c56d-e487-474d-bbd4-ceaae28d919c\",\"name\":\"C\",\"parameterData\":[{\"ename\":\"Jana Burke\", \"enumb\":\"12345\"}],id:3}";
        System.out.printf( "Client invoked Server::deliver(message) : %s \n", s );
        message = Message.deserialize(s);
        if ( null == message ) throw new BadArgumentException( "Cannot deliver null message." );
        switch ( message.getId() ) {
            case IFoo.SIGNAL_NO_C:
            	Object o = message.get(0);
            	if (o instanceof HashMap) {
            		HashMap hm = (HashMap)o;
                    System.out.printf( "message param is HashMap %d  \n", hm.size() );
                    if ( hm.containsKey("ename"))
                        System.out.printf( "name is  %s  \n", hm.get("ename") );
        	        String eName = (String) hm.get("ename");
        	        int eNumber = Integer.parseInt( (String) hm.get("enumb") );
                    System.out.printf( "name & number %s  %d \n", eName, eNumber );
                    c((String) hm.get("ename"), Integer.parseInt( (String) hm.get("enumb") ));
            	}
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
