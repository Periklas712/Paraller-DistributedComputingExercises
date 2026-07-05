package MultiThreadedask2;

import java.net.*;
import java.io.*;

public class ServerProtocol {

	public String processRequest(String theInput) {
		System.out.println("Received message from client: " + theInput);
		String theOutput ="";
		Double result;

		String[] in=theInput.split(";");

		Double numberA = Double.parseDouble(in[0]);
		Double numberB = Double.parseDouble(in[2]);
	
		String operation = in[1];

		switch (operation) {
			case "+":
				result = numberA+numberB;
				theOutput=String.valueOf(result);
				break;
			case "-":
				result = numberA-numberB;
				theOutput=String.valueOf(result);
				break;
			case "*":
				result=numberA*numberB;
				theOutput=String.valueOf(result);
			break;
			case "/":
			if(numberB==0 && numberA==0){
				theOutput="Undefined";
				break;
			}
			if (numberB==0){
				theOutput="Error: Cannot divide by zero";
				break;
			}
			theOutput=String.valueOf(numberA/numberB);
			break;
		}

		System.out.println(theOutput);
		return theOutput;
	}
}