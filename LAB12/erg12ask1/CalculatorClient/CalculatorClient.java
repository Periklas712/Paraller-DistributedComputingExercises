package RMIadd.CalculatorClient;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.*;
import java.rmi.registry.*;

public class CalculatorClient {

    private static final String HOST = "localhost";
	private static final int PORT = Registry.REGISTRY_PORT; // 1099
	
	public static void main(String[] args) {
				
		try {
			// Locate rmi registry
			
			Registry registry = LocateRegistry.getRegistry(HOST, PORT);
			
			// Look up for a soecific name and get remote reference (stub)
			String rmiObjectName = "MyCalculator";
            Calculator refCalculator = (Calculator) registry.lookup(rmiObjectName);

            //idios kodikas me ergasthrio 9 
            BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
            String numberA="";
		    String numberB="";
		    String operation="";

            while (true) {
			System.out.print("Enter first number: ");
			numberA = user.readLine();
			try {
				Double.parseDouble(numberA); 
				break;
			} catch (NumberFormatException e) {
				System.out.println("Invalid input.");
			    }
		    }
		    while(true){
		    System.out.print("Enter operation ( + , - , * , / ) : ");
			operation = user.readLine();
			if(operation.equals("+") || operation.equals("-") || operation.equals("*") || operation.equals("/"))
				break;
			else System.out.println("Invalid input operation should be one of +,-,*,/");}

		    while (true) {
			System.out.print("Enter second number: ");
			numberB = user.readLine();
			try {
				Double.parseDouble(numberB); 
				break;
			} catch (NumberFormatException e) {
				System.out.println("Invalid input.");
			    }
		    }

            //klhsh ths apomakrusmenhs methodou 

            int result=0;
            switch (operation) {
                case "+":
                     result = refCalculator.add(Integer.parseInt(numberA),Integer.parseInt(numberB));
                break;
                case "-":
                     result = refCalculator.substract(Integer.parseInt(numberA),Integer.parseInt(numberB));
                break;
                case "*":
                    result = refCalculator.multiplication(Integer.parseInt(numberA),Integer.parseInt(numberB));
                break;
                case "/":
                    result= refCalculator.division(Integer.parseInt(numberA),Integer.parseInt(numberB));
                break;
            }
            System.out.println("Result for "+ numberA + operation +numberB+" ="+ String.valueOf(result));
		} catch (RemoteException re) {
			System.out.println("Remote Exception");
			re.printStackTrace();
		} catch (Exception e) {
			System.out.println("Other Exception");
			e.printStackTrace();
		}
	}
    
}
