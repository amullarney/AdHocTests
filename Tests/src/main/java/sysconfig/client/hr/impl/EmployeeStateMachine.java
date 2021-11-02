package sysconfig.client.hr.impl;


import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.statemachine.ITransition;
import io.ciera.runtime.summit.statemachine.StateMachine;

import sysconfig.Client;
import sysconfig.client.hr.Employee;


public class EmployeeStateMachine extends StateMachine<Employee,Client> {

    public static final int Active = 0;
    public static final int OnLeave = 1;
    public static final int Retired = 2;


    private Employee self;

    public EmployeeStateMachine(Employee self, Client context) {
        this(self, context, Active);
    }

    public EmployeeStateMachine(Employee self, Client context, int initialState) {
        super(context, initialState);
        this.self = self;
    }

    private void Active_entry_action() throws XtumlException {
        int a = 5;
    }

    private void OnLeave_entry_action() throws XtumlException {
        context().LOG().LogInfo( self().getName() + " is away from the office" );
        int b = 6;
    }

    private void Retired_entry_action() throws XtumlException {
        int c = 7;
    }



    private void Active_leave_txn_to_OnLeave_action() throws XtumlException {
    }

    private void Active_retire_txn_to_Retired_action() throws XtumlException {
    }

    private void OnLeave_return_txn_to_Active_action() throws XtumlException {
    }



    @Override
    public ITransition[][] getStateEventMatrix() {
        return new ITransition[][] {
            { CANT_HAPPEN,
              (event) -> {Active_retire_txn_to_Retired_action();Retired_entry_action();return Retired;},
              (event) -> {Active_leave_txn_to_OnLeave_action();OnLeave_entry_action();return OnLeave;}
            },
            { (event) -> {OnLeave_return_txn_to_Active_action();Active_entry_action();return Active;},
              CANT_HAPPEN,
              CANT_HAPPEN
            },
            { CANT_HAPPEN,
              CANT_HAPPEN,
              CANT_HAPPEN
            }
        };
    }

    @Override
    public Employee self() {
        return self;
    }

    @Override
    public String getClassName() {
        return "Employee";
    }

}
