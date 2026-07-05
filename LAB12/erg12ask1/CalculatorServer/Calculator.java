package RMIadd.CalculatorClient;
import java.rmi.*;

public interface Calculator extends Remote{

    public int add(int a ,int b ) throws RemoteException;
    public int substract(int a , int b) throws RemoteException;
    public int multiplication(int a, int b) throws RemoteException;
    public int division(int a,int b) throws RemoteException;
    
}
