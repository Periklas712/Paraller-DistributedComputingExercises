import java.net.*;
import java.io.*;

public class MultithreadedChatServerTCP {
	private static final int PORT = 1234;
	
	public static void main(String args[]) throws IOException {

		ServerSocket connectionSocket = connectionSocket = new ServerSocket(PORT);
		SharedSockets sharedSockets = new SharedSockets(); //ΔΗΜΙΘΡΓΩ ΤΟ ΝΕΟ ΜΟΙΡΑΖΟΜΕΝΟ ΑΝΤΙΚΕΙΜΕΝΟ 

		System.out.println("Server is waiting first client in port: " + PORT);
		
		while (true) {	

			//ΓΙΑ ΑΘΕ ΣΥΝΔΕΣΗ ΠΟΥ ΜΟΥ ΕΡΧΕΤΑΙ ΤΗΝ ΠΡΟΣΘΕΤΩ ΣΤΟΝ ΠΙΝΑΚΑ 
			Socket dataSocket=connectionSocket.accept();
			sharedSockets.addSocket(dataSocket);

			//ΓΙΑ ΑΘΕ ΣΥΝΔΕΣΗ ΠΟΥ ΜΟΥ ΕΡΧΕΤΑΙ ΞΕΚΙΝΑΩ ΕΝΑ ΝΗΜΑ	
			ServerThread sThread = new ServerThread(dataSocket, sharedSockets);
			sThread.start();


		}
	}
}


