
import java.net.*;
import java.io.*;


public class BankServerThread extends Thread {

	
  private Socket bankSocket = null;
  private ActionState ActionStateObject;
  private String myActionServerThreadName;
  private double mySharedVariable;
   
  //Setup the thread
  	public BankServerThread(Socket bankSocket, String BankServerThreadName, ActionState SharedObject) {
	
//	  super(ActionServerThreadName);
	  this.bankSocket = bankSocket;
	  ActionStateObject = SharedObject;
	  myActionServerThreadName = BankServerThreadName;
	}

  public void run() {
    try {
      System.out.println(myActionServerThreadName + "initialising.");
      PrintWriter out = new PrintWriter(bankSocket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(bankSocket.getInputStream()));
      String inputLine, outputLine;

      while ((inputLine = in.readLine()) != null) {
    	  try { 
    		  //This allows the system to recognise the users input to see if it is valid or not
    		  inputLine = inputLine.replaceAll("\\s+", "");
    		  String [] part = inputLine.split("(?<=\\D)(?=\\d)");
    		  String fromWhatClient = part[0].substring(0, Math.min(part[0].length(), 5));
    		  part[0] = part[0].substring(5);
    		  String action = inputLine;
    		  int amount = 0;
    		  if (part.length == 2) { 
    			  action = part[0];
    			  try {
    				  amount = Integer.parseInt(part[1]);
    			  }
    			  catch(NumberFormatException e){
    				  	action = "Error";
    			  }  
    		  }
    		  // Getting a lock first
    		  ActionStateObject.acquireLock();  
    		  // inputting users input, in order to complete a process.
    		  //
    		  outputLine = ActionStateObject.processInput(myActionServerThreadName, fromWhatClient, action, amount);
    		  out.println(outputLine);
    		  ActionStateObject.releaseLock();  
    	  } 
    	  catch(InterruptedException e) {
    		  System.err.println("Failed to get lock when reading:"+e);
    	  }
      }

       out.close();
       in.close();
       bankSocket.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}