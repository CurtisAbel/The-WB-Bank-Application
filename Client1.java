import java.io.*;
import java.net.*;
public class Client1 {

	 public static void main(String[] args) throws IOException {
        // Set up the socket, in and out variables

        Socket ActionClientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int ActionSocketNumber = 4545;
        String ActionServerName = "localhost";
        String ActionClientID = "User A";
        

        try {
            ActionClientSocket = new Socket(ActionServerName, ActionSocketNumber);
            out = new PrintWriter(ActionClientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(ActionClientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: "+ ActionSocketNumber);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;
        
        // This is modified as it's the client that speaks first
        System.out.println("Client A has Started Up, press Enter to start");
        String go = stdIn.readLine();
        while (true) {
        	
            fromUser = RandomClientActions.random_action();
            fromUser = ActionClientID + fromUser;
            if (fromUser != null) {
                System.out.println(" Sending the following command \"" + fromUser + "\" to the Bank");
                out.println(fromUser);
            }
            fromServer = in.readLine();
            System.out.println(" " + fromServer);
            
            //out.close();
            //in.close();
            //stdIn.close();
            //ActionClientSocket.close();

            
        }
        
            
    }
}

