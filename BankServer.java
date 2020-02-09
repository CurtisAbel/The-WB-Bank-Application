import java.net.*;
import java.io.*;


public class BankServer {
	//All accounts starts with 1000 unit which represents how much money is within the clients account
	static int AccountBalance_C1 = 1000;
	static int AccountBalance_C2 = 1000;
	static int AccountBalance_C3 = 1000;
	public static void main (String[] args) throws IOException {
	
		ServerSocket BankServerSocket = null;
		boolean listening = true;
		String BankServerName = "Bank's Server";
		int BankServerNumber = 4545;
		
		double SharedVariable = 100;
		
		
		ActionState ourSharedActionStateObject = new ActionState(SharedVariable); //creating and linking shared object 
		//making server socket
		try {
			BankServerSocket = new ServerSocket(BankServerNumber);
		} 
		catch (IOException e) {
			System.err.println("Could not start" + BankServerName + "specified port.");
			System.exit(-1);
		}
		System.out.println(BankServerName + " has commenced... ");
		
		//Clients need to be placed in the correct order for three clients
		while (listening) {
			new BankServerThread(BankServerSocket.accept(), "BankServerThread1", ourSharedActionStateObject).start();
			new BankServerThread(BankServerSocket.accept(), "BankServerThread2", ourSharedActionStateObject).start();
			new BankServerThread(BankServerSocket.accept(), "BankServerThread3", ourSharedActionStateObject).start();
			System.out.println("New " + BankServerName + " thread started.");
		}
		BankServerSocket.close();
	}
}

