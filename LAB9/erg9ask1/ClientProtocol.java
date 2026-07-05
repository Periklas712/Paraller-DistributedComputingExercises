import java.net.*;
import java.io.*;

public class ClientProtocol {

	BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
	
	public String prepareRequest() throws IOException {
		String message="";
		String option = "";

		//ΜΕΝΟΥ ΕΠΙΛΟΓΩΝ ΧΡΗΣΤΗ ΚΑΘΕ ΕΡΩΤΗΜΑ ΕΙΝΑΙ ΕΜΙΑ ΕΠΙΛΟΓΗ 
		while (true) {
			System.out.println("Choose your option to server:");
			System.out.println("A - > Text to upper case from lower case");
			System.out.println("B - > Text to lower case from upper case");
			System.out.println("C - > Text to Caesar Cipher encrypt");
			System.out.println("D - > Text to Caesar Cipher decrypt");
			option = user.readLine().toUpperCase(); //ΔΙΑΒΑΖΩ ΤΗΝ ΕΠΙΛΟΓΗ ΤΟΥ ΧΡΗΣΤΗ 

			if (option.equals("A") || option.equals("B") || option.equals("C") || option.equals("D")) {
				break;
			} else {
				System.out.println("Invalid option. Please choose A, B, C, or D.");
			}
		}

		System.out.print("Enter text to send to server:");
		String text = user.readLine(); //ΔΙΑΒΑΖΩ ΤΗ ΛΕΞΗ/ΚΕΙΜΕΝΟ ΠΟΥ ΘΑ ΣΤΕΙΛΟΥΜΕ

		//ΑΝ Η ΕΠΙΛΟΓΗ ΕΙΝΑΙ C Η D ΤΟΤΕ ΤΟ ΚΕΙΜΕΝΟ ΠΟΥ ΘΑ ΣΤΑΛΕΙ ΣΤΟ SERVER ΕΙΝΑΙ ΕΠΙΛΟΓΗ # OFFSET # KEIMENO 
		if (option.equals("C") || option.equals("D")) {
				String offsetStr = "";
				int offset = 0;
				while (true) {
					System.out.print("Enter offset to encrypt/decrypt: ");
					offsetStr = user.readLine();
					try {
						offset = Integer.parseInt(offsetStr);
						break;
					} catch (NumberFormatException e) {
						System.out.println("Offset must be an integer.");
					}
				}
				message = option + "#" + text + "#" + offset;
			} else {
				//ΕΔΩ ΘΑ ΦΤΙΑΞΩ ΣΕ ΕΝΑ STRING ΤΟ ΜΥΝΗΜΑ ΠΟΥ ΘΑ ΣΤΕΙΛΩ. Η ΛΟΓΙΚΉ ΕΙΝΑΙ ΟΤΙ ΤΟ ΜΥΝΗΜΑ ΘΑ ΕΙΝΑΙ ΤΥΠΟΥ:
				// "ΕΠΙΛΟΓΗ # ΜΥΝΗΜΑ" ΤΟ # ΧΩΡΙΖΕΙ ΤΟ ΜΥΝΗΜΑ ΩΣΤΕ ΜΕΤΑ Ο SERVER ΝΑ ΚΑΝΕΙ SPLIT ΒΑΣΗ ΤΟ #.
				message = option + "#" + text;
			}
		return message;
	}


	public void processReply(String theInput) throws IOException {
	
		System.out.println("Message received from server: " + theInput);
	}
}
