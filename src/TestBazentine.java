/**
 * This class is main class which starts the process.
 * 
 * @author      Prawaal Sharma
 * @author      2015HT12650
 */
 
import java.util.Scanner;
import java.util.StringTokenizer;

public class TestBazentine
{
	public static void main(String[] args) 
	{
		try
		{
			GlobalValues glb = new GlobalValues();
			Scanner in = new Scanner(System.in);
			String inputChoiceString = "";
			int inputChoice = -1;
			
			System.out.println("Enter the number of processes (less than 9 please)");
			glb.setProcessCount(in.nextInt());
			System.out.println("Enter the number of bazentine nodes (less than number of nodes of course)");
			inputChoice = in.nextInt();
			
			if((glb.getProcessCount()>9)||(inputChoice>9)||(glb.getProcessCount()<inputChoice))
			{
				System.out.println("!! Please input values by following instruction.....");
				System.exit(1);
			}
			
			glb.setBazentine(inputChoice);
			System.out.println();
			
			ProcessInstructions p = new ProcessInstructions();
			p.setValues(glb,inputChoice,"1",1);
			p.start();
			Thread.sleep(3000);
			
			for(int i = 1;i<=glb.getProcessCount();i++)
			{
				System.out.println("Messages on node --> " + i + " -> "+ glb.getIstructionMatrix(i).substring(1));
			}
			glb.getMajorDecision(glb.getGlobalMaster());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
