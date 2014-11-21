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
	    processList.decrementCurrentProcessesWaiting();	// NV decrement the current processes waiting by looping through all processes in IO and decrmenting their IO time
	    if (currentProcess != null) {
	      currentProcess.processInstruction(cpu.cycleCount); // NV process the next instruction/CPU burst
	    }

	    if (processList.hasProcessInReadyQueue()) {
	      if (currentProcess == null) {
	        currentProcess = processWithSoonestDeadline(); // NV set current process to process with the soonest deadline
	      } else {
	        if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
	          currentProcess = processWithSoonestDeadline(); // NV set current process to process with the soonest deadline
	        } else if (processList.getProcessWithShortestCPUBurst().getCPUBurst() < currentProcess.getCPUBurst()) {
	          processList.addtoReadyQueue(currentProcess, this.cpu.cycleCount);
	          currentProcess = processWithSoonestDeadline(); // NV set current process to process with the soonest deadline
	        }
	      }
	    } else {
	      if (currentProcess != null) {
	        if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
	          // NV current process could still be executing, so return it
	          currentProcess = null;
	        }
	      } else {
	        currentProcess = null;
	      }
	    }
	  }

	/**
	 * NV return the process with the soonest deadline, adding it to the final report in the process
	 * @return process with the soonest deadline
	 */
	  private Process processWithSoonestDeadline() {
	  	Process returnProcess = processList.takeProcessWithSoonestDeadline();
	  	cpu.finalReport.addProcess(returnProcess); // PI add the current process to the final report
	  	return returnProcess;
	  }
}