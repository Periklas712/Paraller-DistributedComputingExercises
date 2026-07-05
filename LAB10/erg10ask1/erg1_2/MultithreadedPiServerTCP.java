package erg10ask1.erg1_2;

import java.net.*;
import java.io.*;

public class MultithreadedPiServerTCP {
	private static final int PORT = 1234;


	public static void main(String args[]) throws IOException {


		ServerSocket connectionSocket = connectionSocket = new ServerSocket(PORT);
		//ftiaxno to moirazomeno antikeimeno me thn domi
		SharedPiCalculations sharedPiCalculations = new SharedPiCalculations();
		
		while (true) {	

			System.out.println("Server is listening to port: " + PORT);
			Socket dataSocket = connectionSocket.accept();
			System.out.println("Received request from " + dataSocket.getInetAddress());

			ServerThread sthread = new ServerThread(dataSocket,sharedPiCalculations);
			sthread.start();
		}
	}
}


