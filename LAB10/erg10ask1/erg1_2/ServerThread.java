package erg10ask1.erg1_2;

import java.io.*;
import java.net.*;

class ServerThread extends Thread
{
	private Socket dataSocket;
	private InputStream is;
   	private BufferedReader in;
	private OutputStream os;
   	private PrintWriter out;
	private SharedPiCalculations sharedPiCalculations; //pernao to moirazomeno antikeimeno se kathe nhma
	

   	public ServerThread(Socket socket,SharedPiCalculations shared)
   	{
		dataSocket = socket;
		try {
			is = dataSocket.getInputStream();
			in = new BufferedReader(new InputStreamReader(is));
			os = dataSocket.getOutputStream();
			out = new PrintWriter(os,true);
			this.sharedPiCalculations=shared;
		}
		catch (IOException e)	{		
			System.out.println("I/O Error " + e);
		}
	}
	
	public void run()
	{
   		String inmsg, outmsg;
		
		try {
			inmsg = in.readLine();
			PiServerProtocol app = new PiServerProtocol();
			outmsg = app.processRequest(inmsg,sharedPiCalculations);
			while (!outmsg.equals("EXIT")) {
				out.println(outmsg);
				inmsg = in.readLine();
				outmsg = app.processRequest(inmsg,sharedPiCalculations);		
			}	
			dataSocket.close();	

		} catch (IOException e)	{		
	 		System.out.println("I/O Error " + e);
		}
	}	
}	
			
		
