/**
 * The EDF scheduler implementation
 * @author Phillip Igoe & Nick Van Beek
 *
 */
public class EDFScheduler extends Scheduler {

	/**
	 * NV constructs an EDF scheduler
	 * @param processList reference to the process list
	 */
	public EDFScheduler(ProcessList processList) {
		super(processList);
		this.readableName = "EDF"; // PI set the readable name
	}

	@Override
	public void schedule() {
		processList.incrementWaitTimeForProcessesInReadyQueue(); // NV increment the wait time for all processes in ready queue
	    processList.decrementCurrentProcessesWaiting(currentProcess); // NV decrement the current processes waiting by looping through all processes in IO and decrmenting their IO time
	    if (currentProcess != null) { // PI ensure current process isn't null
	      currentProcess.processInstruction(cpu.cycleCount);
	    }

	    if (processList.hasProcessInReadyQueue()) { // NV are there processes in the ready queue?
	      if (currentProcess == null) { // PI ensure current process isn't null
	        currentProcess = processWithSoonestDeadline(); // PI grab the process with the shortest CPU burst
	      } else {
	        if (currentProcess.isTerminated() || currentProcess.isWaiting()) { // NV is the current process terminated or waiting?
	          currentProcess = processWithSoonestDeadline(); // PI grab the process with the shortest CPU burst
	        } else if (processList.getProcessWithShortestPeriod().deadline < currentProcess.deadline) { // NV does the process we found have a lower deadline than the current processor's deadline?
	          processList.addtoReadyQueue(currentProcess, this.cpu.cycleCount); // NV add the current process to the ready queue
	          currentProcess = processWithSoonestDeadline(); // PI grab the process with the shortest CPU burst
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
	 * NV return the process with the soonest deadline, adding it to the final report in the process
	 * @return process with the soonest deadline
	 */
	  private Process processWithSoonestDeadline() {
	  	Process returnProcess = processList.takeProcessWithSoonestDeadline(); // PI search through the ready queue and return the process with the soonest deadline
	  	cpu.finalReport.addProcess(returnProcess); // PI add the current process to the final report
	  	return returnProcess;
	  }
}