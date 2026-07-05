import java.rmi.*;
public interface ManagementEmails extends Remote
{
	// ypografes ton apomakrysmenon methodon
	String getEmail(String name) throws RemoteException;
	boolean putEmail(String name, String email) throws RemoteException;
	boolean removeEmail(String name) throws RemoteException;
}