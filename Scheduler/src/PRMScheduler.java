/**
 * PRM scheduler implementation
 * @author Phillip Igoe & Nick Van Beek
 *
 */
public class PRMScheduler extends Scheduler {

	public PRMScheduler(ProcessList processList) {
		super(processList);
	    this.readableName = "PRM";
	}

	@Override
	public void schedule() {
		processList.incrementWaitTimeForProcessesInReadyQueue(); // NV increment the wait time for all processes in ready queue
	    processList.decrementCurrentProcessesWaiting(); // NV decrement the current processes waiting by looping through all processes in IO and decrmenting their IO time
	    if (currentProcess != null) { // PI ensure current process isn't null
	      currentProcess.processInstruction(cpu.cycleCount);
	    }

	    if (processList.hasProcessInReadyQueue()) { // NV are there processes in the ready queue?
	      if (currentProcess == null) { // PI ensure current process isn't null
	        currentProcess = processWithShortestPeriod();
	      } else {
	        if (currentProcess.isTerminated() || currentProcess.isWaiting()) { // NV is the current process terminated or waiting?
	          currentProcess = processWithShortestPeriod();
	        } else if (processList.getProcessWithShortestCPUBurst().getCPUBurst() < currentProcess.getCPUBurst()) {
	          processList.addtoReadyQueue(currentProcess, this.cpu.cycleCount);
	          currentProcess = processWithShortestPeriod();
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

	  private Process processWithShortestPeriod() {
	  	Process returnProcess = processList.takeProcessWithShortestPeriod();
	  	cpu.finalReport.addProcess(returnProcess); // PI add the current process to the final report
	  	return returnProcess;
	  }

}
