package MultiThreadedask2;

import java.net.*;
import java.io.*;

public class ClientProtocolask2 {

	BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
	
	public String prepareRequest() throws IOException {
		String numberA="";
		String numberB="";
		String operation="";
		
		System.out.println("Simple client-server calculator");
		System.out.println("-------------------------------");

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
		
		// το ΜΥΝΗΜΑ ΠΟΥ ΘΑ ΦΤΙΑΞΩ ΚΑΙ ΘΑ ΣΤΕΙΛΩ ΘΑ ΕΙΝΑΙ ΤΗΣ ΜΟΡΦΗΣ ΑΡΙΘΜΟΣ Α ; ΤΕΛΕΣΤΗΣ ; ΑΡΙΘΜΟΣ Β

		String theOutput = numberA+";"+operation+";"+numberB;
		return theOutput;
	}

	public void processReply(String theInput) throws IOException {
	
		System.out.println("Result received from server: " + theInput);
	}
}
