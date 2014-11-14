import java.util.ArrayList;


public class Snapshot {
	public Simulator simulator; // PI ref to the simulator
	public ArrayList<Integer> processesInReadyQueue; // PI array list containing the processes in ready queue
	public ArrayList<Integer> processesInIO; // PI array list containing the processes in I/O
	public int numberProcessesCurrentlyProcessing; // PI # processes currently processing
	public int cycle;	// PI cycle # this was taken at
	
	public Snapshot(Simulator simulator, ArrayList<Process> readyQueue, ArrayList<Process> ioQueue, int numberProcessesCurrentlyProcessing, int cycle) {
		// PI init array lists
		this.simulator = simulator;
		processesInReadyQueue = new ArrayList<Integer>();
		processesInIO = new ArrayList<Integer>();
		// PI init other stats
		this.numberProcessesCurrentlyProcessing = numberProcessesCurrentlyProcessing;
		this.cycle = cycle;
		
		// PI copy all the processes in the ready queue to our array list
		for(Process process: readyQueue) {
			this.processesInReadyQueue.add(process.getPID());
		}
		
		// PI copy all the processes in the IO queue to our array list
		for(Process process: ioQueue) {
			this.processesInIO.add(process.getPID());
		}
	}
}
