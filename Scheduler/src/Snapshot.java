import java.util.ArrayList;


public class Snapshot {
	public Scheduler scheduler; // PI ref to the scheduler
	public ArrayList<Integer> processesInReadyQueue; // PI array list containing the processes in ready queue
	public ArrayList<Integer> processesInIO; // PI array list containing the processes in I/O
	public int processCurrentlyProcessing; // PI PID of process currently processing
	public int cycle;	// PI cycle # this was taken at
	
	public Snapshot(Scheduler scheduler, ArrayList<Process> readyQueue, ArrayList<Process> ioQueue, int processCurrentlyProcessing, int cycle) {
		// PI init array lists
		this.scheduler = scheduler;
		processesInReadyQueue = new ArrayList<Integer>();
		processesInIO = new ArrayList<Integer>();
		// PI init other stats
		this.processCurrentlyProcessing = processCurrentlyProcessing;
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
