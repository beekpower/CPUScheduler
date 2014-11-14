import java.util.ArrayList;


public class Snapshot {
	public ArrayList<Process> processesInReadyQueue; // PI array list containing the processes in ready queue
	public ArrayList<Process> processesInIO; // PI array list containing the processes in I/O
	public int numberProcessesCurrentlyProcessing; // PI # processes currently processing
	public int cycle;	// PI cycle # this was taken at
	
	public Snapshot(ArrayList<Process> readyQueue, ArrayList<Process> ioQueue, int numberProcessesCurrentlyProcessing, int cycle) {
		// PI init array lists
		processesInReadyQueue = new ArrayList<Process>();
		processesInIO = new ArrayList<Process>();
		// PI init other stats
		this.numberProcessesCurrentlyProcessing = numberProcessesCurrentlyProcessing;
		this.cycle = cycle;
		
		// PI copy all the processes in the ready queue to our array list
		for(Process process: readyQueue) {
			this.processesInReadyQueue.add(process.copyProcess());
		}
		
		// PI copy all the processes in the IO queue to our array list
		for(Process process: ioQueue) {
			this.processesInIO.add(process.copyProcess());
		}
	}
}
