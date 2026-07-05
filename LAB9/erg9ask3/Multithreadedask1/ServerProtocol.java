package Multithreadedask1;

import java.net.*;
import java.io.*;

public class ServerProtocol {

	public String processRequest(String theInput) {
		System.out.println("Received message from client: " + theInput);

		String[] in = theInput.split("#"); //ΧΩΡΙΖΩ ΤΟ ΜΥΝΗΜΑ ΜΕ ΤΗΝ ΛΟΓΙΚΗ ΟΤΙ ΠΡΙΝ ΤΟ # ΕΧΩ ΤΗΝ ΕΠΙΛΟΓΗ ΚΑΙ ΜΕΤΑ ΤΟ ΚΕΙΜΕΝΟ
		String theOutput="";
		String option = in[0]; //ΕΠΙΛΟΓΗ ΧΡΗΣΤΗ
		String text=in[1]; 	//KEIMENO ΧΡΗΣΤΗ
		

		//ΚΑΘΕ ΖΗΤΟΥΜΕΝΟ ΕΙΝΑΙ ΜΙΑ ΛΕΙΤΟΥΡΓΙΑ
		switch (option) {
			case "A":
				theOutput=text.toLowerCase();
				break;

			case "B":
				theOutput=text.toUpperCase();
				break;

			case "C":{
			int offset = Integer.parseInt(in[2]);  //OFFSET ΑΝ ΔΩΣΕΙ ΕΠΙΛΟΓΗ C Η D 
			StringBuilder result = new StringBuilder();
					for (char character : text.toCharArray()) {
						if (character != ' ') {
							int originalAlphabetPosition = character - 'a';
							int newAlphabetPosition = (originalAlphabetPosition + offset) % 26;
							char newCharacter = (char) ('a' + newAlphabetPosition);
							result.append(newCharacter);
						} else {
						result.append(character);}
					}
					theOutput= result.toString();
				break;
				}
			case "D":{
			int offset = Integer.parseInt(in[2]);  //OFFSET ΑΝ ΔΩΣΕΙ ΕΠΙΛΟΓΗ C Η D 
			StringBuilder result = new StringBuilder();
					for (char character : text.toCharArray()) {
						if (character != ' ') {
							int originalAlphabetPosition = character - 'a';
							int newAlphabetPosition = (originalAlphabetPosition - offset) % 26;
							char newCharacter = (char) ('a' + newAlphabetPosition);
							result.append(newCharacter);
						} else {
						result.append(character);}
					}
					theOutput= result.toString();
				break;
		}
	}

		System.out.println("Send message to client: " + theOutput);
		return theOutput;
	}
}