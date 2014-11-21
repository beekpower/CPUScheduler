/**
 * PRM scheduler implementation
 * @author Phillip Igoe & Nick Van Beek
 *
 */
public class PRMScheduler extends Scheduler {

	/**
	 * Construct a PRM scheduler
	 * @param processList reference to the process list
	 */
	public PRMScheduler(ProcessList processList) {
		super(processList);
	    this.readableName = "PRM"; // PI set the readable name
	}

	@Override
	public void schedule() {
		processList.incrementWaitTimeForProcessesInReadyQueue(); // NV increment the wait time for all processes in ready queue
	    processList.decrementCurrentProcessesWaiting(); // NV decrement the current processes waiting by looping through all processes in IO and decrmenting their IO time
	    if (currentProcess != null) { // PI ensure current process isn't null
	      currentProcess.processInstruction(cpu.cycleCount); // NV process the instruction
	    }

	    if (processList.hasProcessInReadyQueue()) { // NV are there processes in the ready queue?
	      if (currentProcess == null) { // PI ensure current process isn't null
	        currentProcess = processWithShortestPeriod(); // NV grab the process with the shortest period
	      } else {
	        if (currentProcess.isTerminated() || currentProcess.isWaiting()) { // NV is the current process terminated or waiting?
	          currentProcess = processWithShortestPeriod();
	        } else if (processList.getProcessWithShortestCPUBurst().getCPUBurst() < currentProcess.getCPUBurst()) {
	          processList.addtoReadyQueue(currentProcess, this.cpu.cycleCount);
	          currentProcess = processWithShortestPeriod(); // NV grab the process with the shortest period
	        }
	      }
	    } else {
	      if (currentProcess != null) { // PI ensure current process isn't null
	        if (currentProcess.isTerminated() || currentProcess.isWaiting()) { // NV is the current process terminated or waiting?
	          //NV current process could still be executing, so return it
	          currentProcess = null;
	        }
	      } else {
	        currentProcess = null;
	      }
	    }
	  }

	/**
	 * PI take the process with the shortest period from the ready queue and return it
	 * @return process
	 */
	  private Process processWithShortestPeriod() {
	  	Process returnProcess = processList.takeProcessWithShortestPeriod(); // PI take the process with the shortest period
	  	cpu.finalReport.addProcess(returnProcess); // PI add the current process to the final report
	  	return returnProcess;
	  }

}
