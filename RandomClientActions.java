import java.io.IOException;
import java.util.Random;

public class RandomClientActions {

	public static void main(String[] args) throws IOException {
		
		Client1.main(args);
		Client2.main(args);
		Client3.main(args);
	}
	
	public static String random_action() {
		String[] actions = {"add", "subtract", "transfer to a", "transfer to b", "transfer to c"};
		int random_action = UI(0,2); //0 = add, 1 = subtracts, 2 = transfer to a
		if (random_action == 2) //If number that is generates >2 then it re rolls it
			random_action  = UI(2,4); //3 = transfer to b, 4 = transfer to c
		int random_amount = UI(1,1000); //Amount within account of clients
		int random_client = UI(1,3); // number of clients making transactions
		
		String input = actions[random_action] + random_amount;
		
		return (input);
	}
	
	//generates random number
	static private Random rand; 
	static public int UI(int aa,int bb)
	{
		int a = Math.min(aa,bb);
		int b = Math.max(aa,bb);
		if (rand == null) 
		{
			rand = new Random();
			rand.setSeed(System.nanoTime());
		}
		int d = b - a + 1;
		int x = rand.nextInt(d) + a;
		return(x);
	}
}
