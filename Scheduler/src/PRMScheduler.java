
public class PRMScheduler extends Scheduler {

	public PRMScheduler(ProcessList processList) {
		super(processList);
	    this.readableName = "PRM";
	}

	@Override
	public void schedule() {
		processList.incrementWaitTimeForProcessesInReadyQueue(); //fix this skip
	    processList.decrementCurrentProcessesWaiting();
	    if (currentProcess != null) {
	      currentProcess.processInstruction();
	    }

	    if (processList.hasProcessInReadyQueue()) {
	      if (currentProcess == null) {
	        currentProcess = processWithShortestPeriod();
	      } else {
	        if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
	          currentProcess = processWithShortestPeriod();
	        } else if (processList.getProcessWithShortestCPUBurst().getCPUBurst() < currentProcess.getCPUBurst()) {
	          processList.addtoReadyQueue(currentProcess);
	          currentProcess = processWithShortestPeriod();
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

	  private Process processWithShortestPeriod() {
	  	Process returnProcess = processList.takeProcessWithShortestPeriod();
	  	cpu.finalReport.addProcess(returnProcess); // PI add the current process to the final report
	  	return returnProcess;
	  }

}
