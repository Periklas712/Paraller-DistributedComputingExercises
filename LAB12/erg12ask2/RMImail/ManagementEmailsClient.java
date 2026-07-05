import java.rmi.*;
import java.rmi.registry.*;

public class ManagementEmailsClient
{
	private static final String HOST = "localhost";
	private static final int PORT = Registry.REGISTRY_PORT; // 1099
	public static void main(String[] args)
	{
		try
		{
			// Locate rmi registry
			Registry registry = LocateRegistry.getRegistry(HOST, PORT);
			
			// Look up for a soecific name and get remote reference (stub)
			String rmiObjectName = "ManagementEmails";
			ManagementEmails ref = (ManagementEmails)registry.lookup(rmiObjectName);
			
			// Do remote method invocation
			String result = ref.getEmail("Panos");
			System.out.println("The email of the Panos is " + result);

			boolean epitixia_eisodou = ref.putEmail("George","george@yahoo.com");
			if (epitixia_eisodou){
				System.out.println("Successfully added George's email");
			}else {
				System.out.println("Failed to add George's email");
			}

			boolean epitixia_diagrafis = ref.removeEmail("John");
			if (epitixia_diagrafis){
				System.out.println("Successfully removed John's email");
			}else {
				System.out.println("Failed to remove John's email");
			}
			System.out.println("The email of the George is " + ref.getEmail("George"));
			System.out.println("The email of the John is " + ref.getEmail("John"));
		}
		catch (RemoteException re)
		{
			System.out.println("Remote Exception");
			re.printStackTrace();
		}
		catch (Exception e)
		{
			System.out.println("Other Exception");
			e.printStackTrace();
		}
	}
}

