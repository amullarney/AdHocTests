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

import org.json.JSONObject;

public class ServerClnt extends Port<Server> implements IFoo {

    public ServerClnt( Server context, IPort<?> peer ) {
        super( context, peer );
    }

    // inbound messages
    public void c( final String p_ename,  final int p_enumb ) throws XtumlException {
        context().LOG().LogInfo( "Server: Employee name, number" );
        context().LOG().LogInfo( p_ename );
        context().LOG().LogInteger( p_enumb );
        Employee emp = context().Employee_instances().anyWhere(selected -> StringUtil.equality(((Employee)selected).getName(), p_ename));
        emp.Report();
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
//   {"messageHandle":"89e6c56d-e487-474d-bbd4-ceaae28d919c","name":"C","parameterData":"{"ename":"John Doe", "enumb":"12345"}",id:3} 

//    {"messageHandle":"89e6c56d-e487-474d-bbd4-ceaae28d919c","name":"C","parameterData":"{'ename':'John Doe', 'enumb':'12345'}",id:3} 

    @Override
    public void deliver( IMessage message ) throws XtumlException {
        if ( null == message ) throw new BadArgumentException( "Cannot deliver null message." );
        System.out.printf( "Server heard response c from Client\n" );
        //String s = "{\"messageHandle\":\"89e6c56d-e487-474d-bbd4-ceaae28d919c\",\"name\":\"C\",\"parameterData\":[{\"ename\":\"John Doe\", \"enumb\":\"12345\"}],id:3}";
        String msgstr = "{\"messageHandle\":\"89e6c56d-e487-474d-bbd4-ceaae28d919c\",\"name\":\"C\",\"parameterData\":\"{\\\"ename\\\":\\\"John Doe\\\", \\\"enumb\\\":\\\"12345\\\"}\",id:3}";
        System.out.printf( "Client invoked Server::deliver(message) : %s \n", msgstr );
        JSONObject msgobj = new JSONObject(msgstr);
        int msgid = msgobj.getInt("id");
        switch ( msgid ) {
            case IFoo.SIGNAL_NO_C:
                String params = msgobj.getString("parameterData");
                JSONObject parmobj = new JSONObject(params);
                c( parmobj.getString("ename"), parmobj.getInt("enumb") );
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
