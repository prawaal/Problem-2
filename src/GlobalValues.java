/**
 * This class is main class which shares variables and states across various threads.
 * 
 * @author      Prawaal Sharma
 * @author      2015HT12650
 */
 
import java.util.StringTokenizer;
 
public class GlobalValues 
{
	//Declare variables
	private boolean processList[] = {Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}; 
	private String instructionMatrix[] = {"","","","","","","","","",""}; 
	private String globalMaster = "";
	private int processCount = 0;
	private int bazentineCount = 0;
	
	//Trivial getters and setters and add methods for various variables so that there is no direct access and all changes are synchronized
	public synchronized void setProcessList(int processId, boolean bazentineFlag){
       processList[processId] = bazentineFlag;
     }

	 public synchronized void setProcessCount(int count){
       processCount = count;
     }
	 
	 public synchronized void setBazentineCount(int tom){
       bazentineCount = tom;
     }

	 public synchronized int getBazentineCount(){
       return  bazentineCount;
     }
	 
	 public synchronized int getProcessCount(){
       return processCount;
     }
	 
	 public synchronized String getGlobalMaster(){
       return globalMaster;
     }
	 
	 public synchronized void addGlobalMaster(String pop){
       globalMaster = globalMaster + pop + ",";
     }
	 
	 public synchronized void addToIstructionMatrix(int processId, String messageId){
 	   instructionMatrix[processId] = instructionMatrix[processId] + "," + messageId;
	   if(messageId.length()==bazentineCount+2)
			addGlobalMaster(messageId + ",");
     }
	 
	 public synchronized String getIstructionMatrix(int processId){
       return instructionMatrix[processId];
     }

	 public synchronized boolean getProcessList(int processId){
       return processList[processId];
     }
	 
 	/**
	 * This method would initialise the nodes to bazentine nodes based on the inputs received from end user. 
	 *
	 * @param  countBazentine  number of nodes which are to be set as bazentine
	 * @return      void	 
	 */		
	 public synchronized void setBazentine(int countBazentine){
	 System.out.print("Bazentine nodes set - ");
	 setBazentineCount(countBazentine);
	 for (int count=1;count<=countBazentine;count++)
		{
			processList[count+1] =  Boolean.TRUE;
			System.out.print(" " + (count+1));
		}
     }

 	/**
	 * This is the most important method of this class. It computes the global agreement based on the vlaues at each node. Computes aggregation 
	 * at each step and takes it to next level. Finally displays agreement achieved or not.
	 *
	 * @param  code  the values of instructions passed on each level based on the protocol set.
	 * @return      void	 
	 */		
		public synchronized void getMajorDecision(String code){
		String strTok[] = code.split(",");
		int sizeOut = strTok.length;
		int sizeIn = strTok.length;
		String nextGen = "";
		int countOne =0;
		int countJunk=0;
		
		//Loop the list one indie other to compare all vaues to each other
		for(int count=0;count<sizeOut;count++){
			if(strTok[count].equals("")) continue;
			String compare1 = strTok[count].substring(1);
			String outputOuter = strTok[count].substring(0,1);
			if (outputOuter.equals("1"))
					countOne++;
			else 
					countJunk++;
					
			for(int countIn=0;countIn<sizeIn;countIn++){
				if(strTok[countIn].equals("")) continue;
				if(count==countIn) continue;
				String compare2 = strTok[countIn].substring(1);
				if (compare1.equals(compare2))
				{
					String output = strTok[countIn].substring(0,1);
					if (output.equals("1"))
						countOne++;
					else 
						countJunk++;
				}
			}
		    if (countOne>countJunk) 
				nextGen += ",1" + compare1.substring(0,compare1.length()-1) ;
			else
				nextGen += ",2" + compare1.substring(0,compare1.length()-1) ;
			countOne =0;
			countJunk=0;
        }
		if(nextGen.equals(""))
			return;
		String strTokTemp[] = nextGen.split(",");
		if(strTokTemp[1].length()>1)
		{
			getMajorDecision(nextGen);
		}
		else
		{
			if(strTokTemp[1].equals("1"))
				System.out.println("Agreement Arrived");
			else
				System.out.println("Agreement not arrived");
		}
	}
		  
}
