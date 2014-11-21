/**
 * The EDF scheduler implementation
 * @author Phillip Igoe & Nick Van Beek
 *
 */
public class EDFScheduler extends Scheduler {

	public EDFScheduler(ProcessList processList) {
		super(processList);
		this.readableName = "EDF";
	}

	@Override
	public void schedule() {
		processList.incrementWaitTimeForProcessesInReadyQueue(); //fix this skip
	    processList.decrementCurrentProcessesWaiting();
	    if (currentProcess != null) {
	      currentProcess.processInstruction(cpu.cycleCount);
	    }

	    if (processList.hasProcessInReadyQueue()) {
	      if (currentProcess == null) {
	        currentProcess = processWithSoonestDeadline();
	      } else {
	        if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
	          currentProcess = processWithSoonestDeadline();
	        } else if (processList.getProcessWithShortestCPUBurst().getCPUBurst() < currentProcess.getCPUBurst()) {
	          processList.addtoReadyQueue(currentProcess, this.cpu.cycleCount);
	          currentProcess = processWithSoonestDeadline();
	        }
	      }
	    } else {
	      if (currentProcess != null) {
	        if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
	          //current process could still be executing, so return it
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