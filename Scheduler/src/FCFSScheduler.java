/**
 * FCFSS Scheduler scheduler implementation
 * @author Phillip Igoe & Nick Van Beek
 *
 */
public class FCFSScheduler extends Scheduler{

	/**
	 * NV construct a FCFS scheduler
	 * @param processList reference to the process list
	 */
  public FCFSScheduler(ProcessList processList) {
	  super(processList);
	  this.readableName = "FCFS"; // NV set the readable name
  }

  public void schedule() {
    processList.incrementWaitTimeForProcessesInReadyQueue(); // NV increment the wait time for all processes in ready queue
    processList.decrementCurrentProcessesWaiting(); // NV decrement the current processes waiting by looping through all processes in IO and decrmenting their IO time
    processList.moveWaitingToReady(); // NV moves all the processes that are waiting, and if the IO burst is less than or equal to 0, moves them to the ready queue
    if (currentProcess != null) {
      currentProcess.processInstruction(cpu.cycleCount);
      if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
        if (processList.hasProcessInReadyQueue()) {
          Process returnProcess = processList.takeFirstProcessInReadyQueue();
          cpu.finalReport.addProcess(returnProcess); // PI add the current process to the final report
          currentProcess = returnProcess;
        } else {
          //Set the current process to null. The CPU will see this and enter an idle state
          currentProcess = null;
        }
      }
    } else  {
      if (processList.hasProcessInReadyQueue()) {
        Process returnProcess = processList.takeFirstProcessInReadyQueue(); // NV grab the first process in ready queue and remove it
        cpu.finalReport.addProcess(returnProcess); // PI add the current process to the final report
        currentProcess = returnProcess;
      } else {
        //Set the current process to null. The CPU will see this and enter an idle state
        currentProcess = null;
      }
    }

  }

}
