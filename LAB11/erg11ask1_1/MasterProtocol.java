import java.net.*;
import java.io.*;

public class MasterProtocol {

	private Pi myPi;
	private int myId;

	public MasterProtocol (Pi pi, int id) {
		
		myPi = pi;
		myId = id;
	}

	public void processReply(String theInput) {
		double  repl = Double.parseDouble(theInput);
		myPi.addTo(repl);
	}

     public String prepareRequest() {
        String theOutput = myPi.printInit() + " " + String.valueOf(myId);
        return theOutput;
    }
}
