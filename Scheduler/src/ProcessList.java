import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class for the process list that retains all the processes loaded in from the data file
 * @author phil
 *
 */
public class ProcessList {
	public ArrayList<Process> processes;
	public ArrayList<Process> readyQueue;
	private int numberProcesses;
	private int quantum;

	public ProcessList(String dataFile, int numberOfCyclesToSnapshot) {
		processes = new ArrayList<Process>(); // PI instantiate the array list of Processes
		readyQueue = new ArrayList<Process>(); // PI instantiate the array list of the ready queue
		File snapshotFile = new File("snapshot.dat");// P IDelete the existing snapshot.dat file
		snapshotFile.delete();
		File finalReportFile = new File("FinalReport.txt");// P IDelete the existing snapshot.dat file
		finalReportFile.delete();

		try {
			File file = new File(dataFile);		// PI Let's create a file object with a path to the data file
			FileReader fileReader = new FileReader(file);	// PI create a new file reader
			BufferedReader bufferedReader = new BufferedReader(fileReader); // PI use a buffered reader to read the file
			String line; // PI keep a reference to the line
			int lineNumber = 1; // PI keep track of the line numbers
			while ((line = bufferedReader.readLine()) != null) {
				if(lineNumber == 1) { // PI This line has the total # processes
					String[] parsedLine = line.split("\\s+"); // PI split this line up by spaces
					numberProcesses = Integer.parseInt(parsedLine[1]); // PI assign the number processes instance field to the value in this line
				} else if(lineNumber == 2) {
					String[] parsedLine = line.split("\\s+"); // PI split this line up by spaces
					quantum = Integer.parseInt(parsedLine[1]); // PI assign the quantum instance field to the value in this line
				} else if(lineNumber > 3) { // PI otherwise this is just a normal line containing a process
					String[] parsedLine = line.split("\\s+"); // PI split this line up by tabs
					Integer[] lineParameters = new Integer[5]; // PI create an array to keep track of all the integer line parameters for this particular process
					int currentIndex = 0;
					for(String element: parsedLine) { // PI loop through each element in the array
						lineParameters[currentIndex] = Integer.parseInt(element); // PI stuff the integer (of this param) into the line params array at our current index
						currentIndex++; // PI increment the current index
					}
					Process process = new Process(lineParameters[0], lineParameters[1], lineParameters[2], lineParameters[3], lineParameters[4]); // PI Create a new process based on the lineParams array
					System.out.println("Adding process: "+process.toString());
					processes.add(process); // PI add this process to our master list of processes
				}
				lineNumber++; // PI increment our line number so we can parse the next line (if not empty)
			}
			fileReader.close(); // PI close out the file reader
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * PI Searches for the process in the ready queue, and removes it if found
	 * @param pid PID of the program to search for
	 * @return true if successfully found / removed
	 */
	public boolean removeFromReadyQueue(int pid) {
		boolean removed = false; //  PI return bool
		Process process = this.findProcessInProcessList(pid); // PI grab process based on PID
		if(process != null) { // PI make sure the process isnt null
			this.readyQueue.remove(process); // PI remove process from ready queue
			removed = true;
		}
		return removed;
	}

	/**
	 * PI Adds the given process to the ready queue
	 * @param pid
	 */
	public boolean addtoReadyQueue(int pid) {
		boolean added = false; //  PI return bool
		Process process = this.findProcessInProcessList(pid); // PI grab process based on PID
		if(process != null) { // PI make sure the process isnt null
			this.readyQueue.add(process); // PI add process to ready queue
			added = true;
		}
		return added;
	}

	/**
	 * PI Adds the given process to the ready queue
	 * @param process process to add
	 */
	public boolean addtoReadyQueue(Process process) {
		boolean added = false; //  PI return bool
		if(process != null) { // PI make sure the process isnt null
			this.readyQueue.add(process); // PI add process to ready queue
			added = true;
		}
		return added;
	}

	/**
	 * PI finds a process in the process list based on the supplied PID
	 * @param pid process id
	 * @return return a process; if not found return null
	 */
	private Process findProcessInProcessList(int pid) {
		Process returnProcess = null;
		// PI alright, loop through all processes and see if we can find it
		for(Process process: this.processes) {
			if(process.getPID() == pid) { // PI we found it!
				returnProcess = process;
				break;
			}
		}
		return returnProcess;
	}

	/**
	 * PI this method loops through all the processes, and for all processes
	 * that have the isWaiting flag set to true, it decrements that processes IO burst time
	 */
	public void decrementCurrentProcessesWaiting() {
		for(Process process: this.processes) { // PI loop through all processes
			if(process.isWaiting()) { // PI this program is currently waiting for I/O, let's decrement the I/O burst
				process.decrementIOBurst(); // PI decrement process' I/O burst
				if(process.getIOBurst() <= 0) { // PI if IO burst is leq 0
					process.setWaiting(false); // PI set the waiting to false
					this.readyQueue.add(process); // PI put it back in ready queue
				}
			}
		}
	}

	public void decrementCurentProcessesWaiting(Process p) {
		for(Process process: this.processes) { // PI loop through all processes
			if(process.isWaiting()) { // PI this program is currently waiting for I/O, let's decrement the I/O burst
				boolean decrement = true;
				if(p != null) {
					if(process.getPID() == p.getPID()) {
						decrement = false;
					}
				}
				if(decrement) {
					process.decrementIOBurst(); // PI decrement process' I/O burst
					if(process.getIOBurst() <= 0) { // PI if IO burst is leq 0
						process.setWaiting(false); // PI set the waiting to false
						process.addedToReadyQueue = true;
						this.readyQueue.add(process); // PI put it back in ready queue
					}
				}
		  }
	  }
  }

	/**
	 * PI called whenever the process list is to reinitalize itself by clearing the
	 *  ready queue, reset  all it's processes, and then move all the processes into
	 *  the ready queue
	 */
	public void reinitialize() {
		this.readyQueue.clear(); // PI clear ready queue
		// PI now reset all processes
		for(Process process: this.processes) {
			process.reset(); // PI reset the current process back to it's initial state
			this.readyQueue.add(process); // PI add this process into the ready queue
		}
	}

	/**
	 * PI check to see if we have any processes left in the ready queue
	 * @return
	 */
	public boolean hasProcessInReadyQueue() {
		if(this.readyQueue.size() > 0) { // PI check to see the size of the proceses in ready queue
			return true;
		} else {
			return false;
		}
	}

	/**
	 * PI check to see if we have any jobs left (any processes that are isTerminated
	 * @return true / false to see if we have any processes left to process
	 */
	public boolean anyJobsLeft() {
		boolean anyJobsLeft = false;
		for(Process process: this.processes) { // PI loop through all processes
			if(!process.isTerminated()) { // PI check to see if this process HASN'T been terminated
				anyJobsLeft = true; // PI process is still active, we have jobs left!
				break;
			}
		}
		return anyJobsLeft;
	}

	/**
	 * PI get the next process to be executed in the ready queue, AND remove it from the ready queue
	 * @return the process located at index 0 in our ready queue array list
	 */
	public Process takeFirstProcessInReadyQueue() {
		return this.readyQueue.remove(0);
	}

	/**
	 * PI returns the process with the shortest CPU burst, and removes it from the ready queue
	 * @return
	 */
	public Process takeProcessWithShortestCPUBurst() {
		Process processWithLeastCPUBurst = this.readyQueue.get(0); // PI set the least process
		for(Process process: this.readyQueue) { // PI loop through all processes in ready queue
			if(process.getCPUBurst() < processWithLeastCPUBurst.getCPUBurst()) { // PI if the cur process' CPU test is less...
				processWithLeastCPUBurst = process; // PI current process' CPU burst is less - let's use this process as the least
			}
		}
		this.readyQueue.remove(processWithLeastCPUBurst); // PI remove the process with least CPU burst from the ready queue
		return processWithLeastCPUBurst; // PI return the process with least CPU burst
	}

	public Process getProcessWithShortestCPUBurst() {
		Process processWithLeastCPUBurst = this.readyQueue.get(0); // PI set the least process
		for(Process process: this.readyQueue) { // PI loop through all processes in ready queue
			if(process.getCPUBurst() < processWithLeastCPUBurst.getCPUBurst()) { // PI if the cur process' CPU test is less...
				processWithLeastCPUBurst = process; // PI current process' CPU burst is less - let's use this process as the least
			}
		}
		return processWithLeastCPUBurst; // PI return the process with least CPU burst
	}

	public ArrayList<Process> getProcessesInIO() {
		ArrayList<Process> returnProcesses = new ArrayList<Process>();
		for(Process process: this.processes) {
			if(process.isWaiting() && !process.isTerminated()) {
				returnProcesses.add(process);
			}
		}
		return returnProcesses;
	}

	/**
	 * PI get process with highest priority
	 * @return
	 */
	public Process takeProcessWithHighestPriority() {
		if(this.readyQueue.size() == 0) {
			return null;
		}
		Process processWithHighestPriority = this.readyQueue.get(0); // PI set the least process
		for(Process process: this.readyQueue) { // PI loop through all processes in ready queue
			if(process.getPriority() < processWithHighestPriority.getPriority()) { // PI if the cur process' priority test is less...
				processWithHighestPriority = process; // PI current process' priority is higher - let's use this process as the highest priority
			}
		}
		this.readyQueue.remove(processWithHighestPriority);
		return processWithHighestPriority; // PI return the process with highest priority
	}

	public Process getProcessWithHighestPriority() {
		Process processWithHighestPriority = this.readyQueue.get(0); // PI set the least process
		for(Process process: this.readyQueue) { // PI loop through all processes in ready queue
			if(process.getPriority() < processWithHighestPriority.getPriority()) { // PI if the cur process' priority test is less...
				processWithHighestPriority = process; // PI current process' priority is higher - let's use this process as the highest priority
			}
		}
		return processWithHighestPriority; // PI return the process with highest priority
	}

	public int getQuantum() {
		return quantum;
	}

	/**
	 * PI take a snapshot of the current CPU
	 * @param cpu CPU to take a snapshot of
	 */
	public void takeSnapshot(CPU cpu) {
		int pid = -1;
		if(cpu.scheduler.getCurrentProcess() != null) {
			pid = cpu.scheduler.getCurrentProcess().getPID();
		}
		Snapshot snapshot = new Snapshot(cpu.scheduler, this.readyQueue, this.getProcessesInIO(), pid, cpu.cycleCount);
		// PI now print the snapshot
		try {
			FileWriter fileWriter = new FileWriter("snapshot.dat", true); // PI make a new file writer
			snapshot.printSnapshot(fileWriter);
			fileWriter.close(); // PI close the file writer
		} catch (IOException e) {
			// uh oh
		}
	}

	public void incrementWaitTimeForProcessesInReadyQueue() {
		for(Process process: this.readyQueue) {
			process.waitTime++;
		}

	}

	public Process takeFirstProcessInReadyQueueNotWaiting() {
		Process p = null;
		for(Process process: this.readyQueue) {
			if(!process.isWaiting() && process.getCPUBurst() > 0) {
				p = process;
				break;
			}
		}
		return p;
	}

	public void printTable(Process currentProcess, CPU cpu, Scheduler scheduler) {
		System.out.println("==================================================");
		System.out.println(scheduler.readableName + " Table at Cycle " + cpu.cycleCount);

		System.out.println("Current Process:");
		System.out.format("%s%15s%15s\n", "PID", "CPU Burst", "IO Burst");
		if (currentProcess != null) {
			System.out.format("%s%15s%15s\n", currentProcess.getPID(), currentProcess.getCPUBurst(), currentProcess.getIOBurst());
		} else {
			System.out.format("%s%15s%15s\n", "none", "none", "none");
		}


		System.out.println("");

		System.out.println("Ready Queue:");
		System.out.format("%s%15s%15s\n", "PID", "CPU Burst", "IO Burst");
		for(Process process: this.readyQueue) {
			System.out.format("%s%15s%15s\n", process.getPID(), process.getCPUBurst(), process.getIOBurst());
		}

		System.out.println("");

		System.out.println("Waiting for IO:");
		System.out.format("%s%15s%15s\n", "PID", "CPU Burst", "IO Burst");
		for(Process process: this.processes) {
			if (process.isWaiting()) {
				System.out.format("%s%15s%15s\n", process.getPID(), process.getCPUBurst(), process.getIOBurst());
			}
		}




	}
}
