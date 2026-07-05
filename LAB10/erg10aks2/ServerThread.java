import java.io.*;
import java.net.*;

class ServerThread extends Thread
{
	private Socket myDataSocket;
	private InputStream is;
   	private BufferedReader in;
	private OutputStream os;
   	private PrintWriter out;
	private static final String EXIT = "CLOSE";
	SharedSockets listOfSockets ;

   	public ServerThread(Socket socket, SharedSockets sockets)
   	{
      		myDataSocket = socket;
			listOfSockets=sockets;
      		try {
			is = myDataSocket.getInputStream();
			in = new BufferedReader(new InputStreamReader(is));
			os=myDataSocket.getOutputStream();
			out = new PrintWriter(os,true);
		}
		catch (IOException e)	{		
	 		System.out.println("I/O Error " + e);
		}
	}

	public void run()
	{
   		String inmsg, outmsg;
		
		try {
			
			ServerProtocol app = new ServerProtocol();
			//ΠΑΙΡΝΩ ΤΗΝ IP ΤΟΥ ΑΠΟΣΤΟΛΕΑ 
			String senderId = myDataSocket.getInetAddress().toString();
			while ((inmsg = in.readLine()) != null) {
            	outmsg = app.processRequest(inmsg);
            
            if (outmsg.equals(EXIT)) {
                break;  
            }
				//ΣΤΕΛΝΩ ΤΟ ΜΥΝΗΜΑ ΣΕ ΟΛΟΥΣ 
				String message = senderId+": "+outmsg;
				listOfSockets.sendMessage(message,myDataSocket);
			}
			
			listOfSockets.removeSocket(myDataSocket); // αποσυνδεση χρηστη
			listOfSockets.sendMessage(senderId+" left the chat", myDataSocket); //ΜΘΝΗΜΑ ΑΠΟΣΥΝΔΕΣΗΣ 

			myDataSocket.close();
		} catch (IOException e)	{		
	 		System.out.println("I/O Error " + e);
		}
	}	
}	
			
		
