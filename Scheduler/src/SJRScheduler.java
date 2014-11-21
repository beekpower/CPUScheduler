/**
 * SJR scheduler implementation
 * @author Phillip Igoe & Nick Van Beek
 *
 */
public class SJRScheduler extends Scheduler {

  public SJRScheduler(ProcessList processList) {
    super(processList);
    this.readableName = "SJR";
  }

  public void schedule() {
    processList.incrementWaitTimeForProcessesInReadyQueue(); // NV increment the wait time for all processes in ready queue
    processList.decrementCurrentProcessesWaiting(currentProcess); // NV decrement the current processes waiting by looping through all processes in IO and decrmenting their IO time
    if (currentProcess != null) {
      currentProcess.processInstruction(cpu.cycleCount);
    }

    if (processList.hasProcessInReadyQueue()) {
      if (currentProcess == null) {
        currentProcess = processWithShortestCPUBurst();
      } else {
        if (currentProcess.isTerminated() || currentProcess.isWaiting()) { // NV is the current process terminated or waiting?
          currentProcess = processWithShortestCPUBurst();
        } else if (processList.getProcessWithShortestCPUBurst().getCPUBurst() < currentProcess.getCPUBurst()) {
          processList.addtoReadyQueue(currentProcess, this.cpu.cycleCount);
          currentProcess = processWithShortestCPUBurst();
        }
      }
    } else {
      if (currentProcess != null) {
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

  private Process processWithShortestCPUBurst() {
  	Process returnProcess = processList.takeProcessWithShortestCPUBurst();
  	cpu.finalReport.addProcess(returnProcess); // PI add the current process to the final report
  	return returnProcess;
  }
}
