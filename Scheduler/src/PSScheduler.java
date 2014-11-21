/**
 * PS scheduler implementation
 * @author Phillip Igoe & Nick Van Beek
 *
 */
public class PSScheduler extends Scheduler {
	public PSScheduler(ProcessList processList) {
    super(processList);
    this.readableName = "PS";
	 }

  public void schedule() {
		processList.incrementWaitTimeForProcessesInReadyQueue(); // NV increment the wait time for all processes in ready queue
		processList.decrementCurrentProcessesWaiting(currentProcess); // NV decrement the current processes waiting by looping through all processes in IO and decrmenting their IO time
		processList.moveWaitingToReady(); // NV moves all the processes that are waiting, and if the IO burst is less than or equal to 0, moves them to the ready queue
		if (currentProcess != null) {
			currentProcess.processInstruction(cpu.cycleCount);
		}

		if (processList.hasProcessInReadyQueue()) {
			if (currentProcess == null) {
				currentProcess = processWithHighestPriority();
			} else {
				if (currentProcess.isTerminated() || currentProcess.isWaiting()) { // NV is the current process terminated or waiting?
					currentProcess = processWithHighestPriority();
				} else if (processList.getProcessWithHighestPriority().getPriority() < currentProcess.getPriority()) {
					processList.addtoReadyQueue(currentProcess, this.cpu.cycleCount);
					currentProcess = processWithHighestPriority();
				}
			}
		} else {
			if (currentProcess != null) {
				if (currentProcess.isTerminated() || currentProcess.isWaiting()) { // NV is the current process terminated or waiting?
					//NV current process could still be executing, so return it
					currentProcess = null;
				}
			} else {
				currentProcess = null;
			}
		}

  }

	private Process processWithHighestPriority() {
		Process returnProcess = processList.takeProcessWithHighestPriority();
		cpu.finalReport.addProcess(returnProcess); // PI add the current process to the final report
		return returnProcess;
	}
}
