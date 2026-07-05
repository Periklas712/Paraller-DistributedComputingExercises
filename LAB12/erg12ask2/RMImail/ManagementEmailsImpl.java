import java.rmi.*;
import java.rmi.server.*;
//import java.util.Hashtable;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

// H klash ayth ylopoiei thn apomakrysmenh diepafh ManagementEmails
public class ManagementEmailsImpl extends UnicastRemoteObject implements ManagementEmails
{
	//private Hashtable storeEmails;
	//tha balo concurent hash map gia na einai threadsafe
	private ConcurrentHashMap<String, String> storeEmails;
	
	// Kataskeyasths
	public ManagementEmailsImpl() throws RemoteException
	{
		// Dhmioyrgia bashs dedomenon e-mails
		//storeEmails = new Hashtable();
		storeEmails = new ConcurrentHashMap<String, String>();
		storeEmails.put("Panos","panosm@uom.gr");
		storeEmails.put("John","johnf@gmail.com");
	}
	
	// Kodikas ylopoihshs ths apomakrysmenhs methodoy getEmail
	public  String getEmail(String name) throws RemoteException
	{
		if (name==null || name.isEmpty()) return null;
		return storeEmails.get(name);		
	}
	
	// Kodikas ylopoihshs ths apomakrysmenhs methodoy putEmail
	public boolean putEmail(String name, String email) throws RemoteException
	{
		if (name==null || name.isEmpty()){
			if (email==null || email.isEmpty()){
				return false;
			}
		}
		storeEmails.put(name, email);
		return true;
	}
	
	// Kodikas ylopoihshs ths apomakrysmenhs methodoy removeEmail
	public  boolean removeEmail(String name) throws RemoteException
	{
		if (name==null || name.isEmpty()){
			return false;
		}
		return storeEmails.remove(name) !=null; //epistrefei true an to stoiceio uphrxe kai afairethike 
	}
}
