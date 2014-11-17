import java.io.FileWriter;
import java.io.IOException;
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
	
	/**
	 * Prints this snapshot to the file writer
	 * @param fileWriter
	 */
	public void printSnapshot(FileWriter fileWriter) {
		try {
			// PI now output all the junk needed
			fileWriter.write("==================================================\n");
			fileWriter.write(scheduler.readableName+" Snapshot at Cycle "+cycle+"\n");
			fileWriter.write("\n");
			if(processCurrentlyProcessing == -1) {
				fileWriter.write("Process Currently Processing:\n");
			} else {
				fileWriter.write("Process Currently Processing: "+processCurrentlyProcessing+"\n");
			}
			fileWriter.write("\n");
			fileWriter.write("Processes in Ready Queue\n");
			if(processesInReadyQueue.size() > 0) {
				for(Integer pid: processesInReadyQueue) {
					fileWriter.write(">"+pid+"\n");
				}
			} else {
				fileWriter.write("None\n");
			}
			fileWriter.write("\n");
			fileWriter.write("Processes in IO\n");
			if(processesInIO.size() > 0) {
				for(Integer pid: processesInIO) {
					fileWriter.write(">"+pid+"\n");
				}
			} else {
				fileWriter.write("None\n");
			}
		} catch (IOException e) {
			// uh oh
			e.printStackTrace();
		}
	}
}
