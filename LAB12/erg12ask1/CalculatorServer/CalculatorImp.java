package RMIadd.CalculatorServer;
import java.rmi.*;
import java.rmi.server.*;

import RMIadd.CalculatorClient.Calculator;

//idia logiki me ton kodika pou mas dwsate 
public class CalculatorImp extends UnicastRemoteObject implements Calculator {

    public CalculatorImp() throws RemoteException{}

    @Override
    public int add(int a, int b) throws RemoteException {
        return a+b;
    }

    @Override
    public int substract(int a, int b) throws RemoteException {
      return a-b;
    }

    @Override
    public int multiplication(int a, int b) throws RemoteException {
        return a*b;
    }

    @Override
    public int division(int a, int b) throws RemoteException {
        return a/b;
    }
    
}
