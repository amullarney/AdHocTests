package interfaces;


import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.interfaces.Message;

import sysconfig.Employee;


public interface IFoo {

    // to provider messages
    public static final int SIGNAL_NO_C = 3;
    public static class C extends Message {
        public C( final String p_ename,  final int p_enumb ) {
            super(new Object[]{p_ename,  p_enumb});
        }
        @Override
        public int getId() {
            return SIGNAL_NO_C;
        }
 
    }
    public void c( final String p_ename,  final int p_enumb ) throws XtumlException;


    // from provider messages
    public static final int SIGNAL_NO_A = 1;
    public static class A extends Message {
        public A( final Employee p_emp ) {
            super(new Object[]{p_emp});
        }
        @Override
        public int getId() {
            return SIGNAL_NO_A;
        }
 
    }
    public void a( final Employee p_emp ) throws XtumlException;
    public static final int SIGNAL_NO_B = 2;
    public static class B extends Message {
        public B( final int p_count ) {
            super(new Object[]{p_count});
        }
        @Override
        public int getId() {
            return SIGNAL_NO_B;
        }
 
    }
    public void b( final int p_count ) throws XtumlException;


}
