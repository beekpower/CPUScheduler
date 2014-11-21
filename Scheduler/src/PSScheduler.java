/**
 * PS scheduler implementation
 * 
 * @author Phillip Igoe & Nick Van Beek
 * 
 */
public class PSScheduler extends Scheduler {
	/**
	 * NV Construct a PS scheduler
	 * 
	 * @param processList
	 *            reference to the process list
	 */
	public PSScheduler(ProcessList processList) {
		super(processList);
		this.readableName = "PS"; // NV set the readable name
	}

	public void schedule() {
		processList.incrementWaitTimeForProcessesInReadyQueue(); // NV increment the wait time for all processes in ready queue
		processList.decrementCurrentProcessesWaiting(currentProcess); // NV decrement the current processes waiting by looping through all processes in IO and decrmenting their IO time
		processList.moveWaitingToReady(); // NV moves all the processes that are waiting, and if the IO burst is less than or equal to 0, moves them to the ready queue
		if (currentProcess != null) { // PI ensure current process isn't null
			currentProcess.processInstruction(cpu.cycleCount);
		}

		if (processList.hasProcessInReadyQueue()) { // NV are there processes in the ready queue?
			if (currentProcess == null) { // PI ensure current process isn't null
				currentProcess = processWithHighestPriority();
			} else {
				if (currentProcess.isTerminated() || currentProcess.isWaiting()) { // NV is the current process terminated or waiting?
					currentProcess = processWithHighestPriority(); // NV get the process with the highest priority
				} else if (processList.getProcessWithHighestPriority().getPriority() < currentProcess.getPriority()) { // PI Does the priority of the highest process we found less than the priority of the current process?
					processList.addtoReadyQueue(currentProcess, this.cpu.cycleCount); // PI add the current process to the ready queue
					currentProcess = processWithHighestPriority(); // PI set the current process to the process we just got
				}
			}
		} else {
			if (currentProcess != null) { // PI ensure current process isn't null
				if (currentProcess.isTerminated() || currentProcess.isWaiting()) { // NV is the current process terminated or waiting?
					// NV current process could still be executing, so return it
					currentProcess = null;
				}
			} else {
				currentProcess = null;
			}
		}

	}

	/**
	 * NV find the process with the highest priority
	 * 
	 * @return process
	 */
	private Process processWithHighestPriority() {
		Process returnProcess = processList.takeProcessWithHighestPriority(); // NV take the process with the highest priority from the ready queue
		cpu.finalReport.addProcess(returnProcess); // NV add the current process to the final report
		return returnProcess;
	}
}
