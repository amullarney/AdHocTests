package interfaces;


import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.interfaces.Message;

import sysconfig.Employee;


public interface IFoo {

    // to provider messages
    public void c( final Employee p_emp ) throws XtumlException;
    public static final int SIGNAL_NO_C = 3;
    public static class C extends Message {
        public C( final Employee p_emp ) throws XtumlException {
            super.addParm("p_emp", p_emp);
        }
        @Override
        public int getId() {
            return SIGNAL_NO_C;
        }
    }
    


    // from provider messages
    public void a( final Employee p_emp ) throws XtumlException;
    public static final int SIGNAL_NO_A = 1;
    public static class A extends Message {
        public A( final Employee p_emp ) throws XtumlException {
            super.addParm("p_emp", p_emp);
        }
        @Override
        public int getId() {
            return SIGNAL_NO_A;
        }
    }
    
    public void b( final int p_count,  final String p_name ) throws XtumlException;
    public static final int SIGNAL_NO_B = 2;
    public static class B extends Message {
        public B( final int p_count,  final String p_name ) throws XtumlException {
            super(new Object[]{p_count,  p_name});
        }
        @Override
        public int getId() {
            return SIGNAL_NO_B;
        }
    }
    


}
