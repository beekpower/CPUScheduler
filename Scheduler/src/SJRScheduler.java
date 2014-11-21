/**
 * SJR scheduler implementation
 * @author Phillip Igoe & Nick Van Beek
 *
 */
public class SJRScheduler extends Scheduler {

	/**
	 * NV construct a SJR scheduler
	 * @param processList reference to the process list
	 */
  public SJRScheduler(ProcessList processList) {
    super(processList);
    this.readableName = "SJR"; // NV set the readable name
  }

  public void schedule() {
    processList.incrementWaitTimeForProcessesInReadyQueue(); // NV increment the wait time for all processes in ready queue
    processList.decrementCurrentProcessesWaiting(currentProcess); // NV decrement the current processes waiting by looping through all processes in IO and decrmenting their IO time
    if (currentProcess != null) { // PI ensure current process isn't null
      currentProcess.processInstruction(cpu.cycleCount);
    }

    if (processList.hasProcessInReadyQueue()) { // NV are there processes in the ready queue?
      if (currentProcess == null) { // PI ensure current process isn't null
        currentProcess = processWithShortestCPUBurst(); // PI grab the process with the shortest CPU burst
      } else {
        if (currentProcess.isTerminated() || currentProcess.isWaiting()) { // NV is the current process terminated or waiting?
          currentProcess = processWithShortestCPUBurst(); // PI grab the process with the shortest CPU burst
        } else if (processList.getProcessWithShortestCPUBurst().getCPUBurst() < currentProcess.getCPUBurst()) { // NV does the process we found have a lower CPU burst than the current processor's CPU burst?
          processList.addtoReadyQueue(currentProcess, this.cpu.cycleCount); // NV add the current process to the ready queue
          currentProcess = processWithShortestCPUBurst(); // PI grab the process with the shortest CPU burst
        }
      }
    } else {
      if (currentProcess != null) { // PI ensure current process isn't null
        if (currentProcess.isTerminated() || currentProcess.isWaiting()) { // NV is the current process terminated or waiting?
          //current process could still be executing, so return it
          currentProcess = null;
        }
      } else {
        currentProcess = null;
      }
    }
    processList.moveWaitingToReady(); // NV moves all the processes that are waiting, and if the IO burst is less than or equal to 0, moves them to the ready queue
  }

  /**
   * NV Grab and return the process with the shortest CPU burst
   * @return
   */
  private Process processWithShortestCPUBurst() {
  	Process returnProcess = processList.takeProcessWithShortestCPUBurst(); // NV grab the process with the shortest CPU burst
  	cpu.finalReport.addProcess(returnProcess); // PI add the current process to the final report
  	return returnProcess;
  }
}
