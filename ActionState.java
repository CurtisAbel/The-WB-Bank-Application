import java.net.*;
import java.io.*;

public class ActionState {

	private ActionState mySharedObj;
	private String myThreadName; 
	private double mySharedVariable;
	private boolean accessing = false;
	private int threadsWaiting = 0;
	

	
	
	ActionState (double SharedVariable) {
		mySharedVariable = SharedVariable;
	}
	
	
	//waiting in line to gain lock
	public synchronized void acquireLock() throws InterruptedException{
		Thread me = Thread.currentThread();
		System.out.println(me.getName() + " is attempting to acquire a lock!");
		++threadsWaiting;
		while (accessing) {
			System.out.println(me.getName() + "waiting to get lock as someone else is accessing...");
			wait();
		}
		--threadsWaiting;
		accessing = true;
		System.out.println(me.getName() + "" + " got a lock!");
	}
	
	public synchronized void releaseLock() {
		accessing = false;
		notifyAll();
		Thread me = Thread.currentThread();
		System.out.println(me.getName()+ "" + "released a lock!");
	}
	
	public synchronized String processInput(String myThreadName, String fromWhatClient, String Action, int Amount) {
		System.out.println(myThreadName + " received " +  Action);
		System.out.println(myThreadName + " executing process");
		String theOutput = "UNSUCCESSFUL: COMMAND NOT RECOGNISE";

		if (Action.equalsIgnoreCase("Add")) {

			if (fromWhatClient.equals("UserA")) {
				BankServer.AccountBalance_C1 = BankServer.AccountBalance_C1 + Amount;
				theOutput = "TRANSACTION APPROVED: NEW ACCOUNT BALANCE: £" + BankServer.AccountBalance_C1;
			}
			if (fromWhatClient.equals("UserB")) {
				BankServer.AccountBalance_C2 = BankServer.AccountBalance_C2 + Amount;
				theOutput = "TRANSACTION APPROVED: NEW ACCOUNT BALANCE: £" + BankServer.AccountBalance_C2;
			}
			if (fromWhatClient.equals("UserC")) {
				BankServer.AccountBalance_C3 = BankServer.AccountBalance_C3 + Amount;
				theOutput = "TRANSACTION APPROVED: NEW ACCOUNT BALANCE: £" + BankServer.AccountBalance_C3;
			}
		}
		else if (Action.equalsIgnoreCase("Subtract")) {
			if (fromWhatClient.equals("UserA")) {
				BankServer.AccountBalance_C1 = BankServer.AccountBalance_C1 - Amount;
				theOutput = "TRANSACTION APPROVED: NEW ACCOUNT BALANCE: £" + BankServer.AccountBalance_C1;
			}
			if (fromWhatClient.equals("UserB")) {
				BankServer.AccountBalance_C2 = BankServer.AccountBalance_C2 - Amount;
				theOutput = "TRANSACTION APPROVED: NEW ACCOUNT BALANCE: £" + BankServer.AccountBalance_C2;
			}
			if (fromWhatClient.equals("UserC")) {
				BankServer.AccountBalance_C3 = BankServer.AccountBalance_C3 - Amount;
				theOutput = "TRANSACTION APPROVED: NEW ACCOUNT BALANCE: £" + BankServer.AccountBalance_C3;
			}
		}
		else if (Action.equalsIgnoreCase("TransfertoA")) {
			if (fromWhatClient.equals("UserA")) theOutput = "ERROR: CANNOT TRANSFER MONEY TO OWN ACCOUNT";
			if (fromWhatClient.equals("UserB")) {
				BankServer.AccountBalance_C2 = BankServer.AccountBalance_C2 - Amount;
				BankServer.AccountBalance_C1 = BankServer.AccountBalance_C1 + Amount;
				theOutput = "TRANSACTION APPROVED: YOUR NEW ACCOUNT BALANCE: £" + BankServer.AccountBalance_C2 + " | ACCOUNT A's NEW BALANCE: " + BankServer.AccountBalance_C1;
			}
			if (fromWhatClient.equals("UserC")) {
				BankServer.AccountBalance_C3 = BankServer.AccountBalance_C3 - Amount;
				BankServer.AccountBalance_C1 = BankServer.AccountBalance_C1 + Amount;
				theOutput = "TRANSACTION APPROVED: YOUR NEW ACCOUNT BALANCE: £" + BankServer.AccountBalance_C3 + " | ACCOUNT A's NEW BALANCE: " + BankServer.AccountBalance_C1;
			}
		}
		else if (Action.equalsIgnoreCase("TransfertoB")) {
			if (fromWhatClient.equals("UserA")) {
				BankServer.AccountBalance_C1 = BankServer.AccountBalance_C1 - Amount;
				BankServer.AccountBalance_C2 = BankServer.AccountBalance_C2 + Amount;
				theOutput = "TRANSACTION APPROVED: YOUR NEW ACCOUNT BALANCE: £" + BankServer.AccountBalance_C1 + " | ACCOUNT B's NEW BALANCE: " + BankServer.AccountBalance_C2;
			}
			if (fromWhatClient.equals("UserB")) theOutput = "ERROR: CANNOT TRANSFER MONEY TO OWN ACCOUNT";
			if (fromWhatClient.equals("UserC")) {
				BankServer.AccountBalance_C3 = BankServer.AccountBalance_C3 - Amount;
				BankServer.AccountBalance_C2 = BankServer.AccountBalance_C2 + Amount;
				theOutput = "TRANSACTION APPROVED: YOUR NEW ACCOUNT BALANCE: £" + BankServer.AccountBalance_C3 + " | ACCOUNT B's NEW BALANCE: " + BankServer.AccountBalance_C2;
			}
		}
		else if (Action.equalsIgnoreCase("TransfertoC")) {
			if (fromWhatClient.equals("UserA")) {
				BankServer.AccountBalance_C1 = BankServer.AccountBalance_C1 - Amount;
				BankServer.AccountBalance_C3 = BankServer.AccountBalance_C3 + Amount;
				theOutput = "TRANSACTION APPROVED: YOUR NEW ACCOUNT BALANCE: £" + BankServer.AccountBalance_C1 + " | ACCOUNT C's NEW BALANCE: " + BankServer.AccountBalance_C3;
			}
			if (fromWhatClient.equals("UserB")) {
				BankServer.AccountBalance_C2 = BankServer.AccountBalance_C2 - Amount;
				BankServer.AccountBalance_C3 = BankServer.AccountBalance_C3 + Amount;
				theOutput = "TRANSACTION APPROVED: YOUR NEW ACCOUNT BALANCE: £" + BankServer.AccountBalance_C2 + " | ACCOUNT C's NEW BALANCE: " + BankServer.AccountBalance_C3;
			}
			if (fromWhatClient.equals("UserC")) theOutput = "ERROR: CANNOT TRANSFER MONEY TO OWN ACCOUNT";
		}
		else {System.out.println("UNSUCCESSFUL: INPUT NOT RECOGNISE");}
		return theOutput;
	}
	
	
}

