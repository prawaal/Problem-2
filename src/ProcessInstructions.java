/**
 * This is a thread class which is spawned for each node and works independently.
 * 
 * @author      Prawaal Sharma
 * @author      2015HT12650
 */

public class ProcessInstructions extends Thread 
{
	//Declare Variables
	public GlobalValues glb = null;
	int processId = -1;
	public int bazentine = -1;
	public String instruction = "";
	int trace[] = {0,0,0,0,0,0,0,0,0,0};

 	/**
	 * This method would initialise the global values for across thread communication
	 *
	 * @param  g GlobalValue object
	 * @param  m Number of Bazentine
	 * @param  ins instruction code
	 * @param  pid Process Id
	 *
	 * @return      void	 
	 */		
	public void setValues(GlobalValues g,int m,String ins, int pid )
	{
		glb = g;
		processId = pid;
		bazentine = m;
		instruction = ins;
		for(int count=0; count<instruction.length();count++)
		{
			int charIns = Integer.parseInt(instruction.substring(count,count+1));
			trace[count] = charIns;
		}
	}

 	/**
	 * Run method of thread
	 *
	 * @param  void
	 *
	 * @return      void	 
	 */			
	public void run() 
	{
		String mgsIns ="";
		//To check if message is passed to self
		if(selfGoal())
		{
			this.stop();
		}
		glb.addToIstructionMatrix(processId,instruction);
		
		//Spawn threads to pass values to other nodes
		for(int count =1;count<=glb.getProcessCount();count++)
			{
				if((bazentine-1)<-1) this.stop();
				ProcessInstructions p = new ProcessInstructions();
				mgsIns = applyBazentine(instruction)+processId;
				p.setValues(glb,bazentine-1,mgsIns,count);
				p.start();				
			}
	}

 	/**
	 * This method would change the instructions in case the node is bazentine. Here i have used process id/node number to pass to other thread
	 * instead of 0/1 since the corrupt message is needed to pass
	 *
	 * @param  instruction
	 * 
	 * @return  String corrupted instruction set to be passed	 
	 */		
	public String applyBazentine(String instruction)
	{
		if(glb.getProcessList(processId))
		{
			int inst = Integer.parseInt(instruction.substring(0,1));	
			inst=processId;
			return inst + instruction.substring(1,instruction.length());
		}	
		return instruction;
	}

 	/**
	 * This method would check if from node and to node is same and if it is same it would not allow to send message
	 *
	 * @param  void
	 *
	 * @return      void	 
	 */		
	public boolean selfGoal()
	{
		String nodeNumber = instruction.substring(1);
		if(nodeNumber.indexOf(String.valueOf(processId)) == -1) return false;
		return true;
	}
}
