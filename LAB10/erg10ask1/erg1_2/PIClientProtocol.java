package erg10ask1.erg1_2;


import java.io.*;

public class PIClientProtocol {
	BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
	String number;
	Long num;

	public String prepareRequest() throws IOException {
		while (true) {
			System.out.print("Please enter a number of steps to calculate pi (or -1 to exit): ");
			number = user.readLine();

		if (number.equals("-1")) {
			return number;
		}
			try {
				num = Long.parseLong(number);
				if (num > 0) {
					break;
				} else {
					System.out.println("Number of steps must be a positive integer.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid positive integer.");
			}
		}

		return number;
	}
        
	public void processReply(String theInput) throws IOException {
	
		System.out.println("Reply from server : " + theInput);
	}
}
