public class SJRScheduler extends Scheduler {

  public SJRScheduler(ProcessList processList) {
    super(processList);
    preemptive = true;
    this.readableName = "SJR";
  }

  public Process getNextProcess() {
    if (processList.hasProcessInReadyQueue()) {
      if (currentProcess == null) {
    	  return processWithShortestCPUBurst();
      } else {
        if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
          return processWithShortestCPUBurst();
        } else if (processList.getProcessWithShortestCPUBurst().getCPUBurst() < currentProcess.getCPUBurst()) {
          processList.addtoReadyQueue(currentProcess);
          return processWithShortestCPUBurst();
        } else {
          return currentProcess;
        }
      }
    } else {
      if (currentProcess != null) {
        if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
          //current process could still be executing, so return it
          return null;
        } else {
          return currentProcess;
        }
      } else {
        return null;
      }
    }
  }

  private Process processWithShortestCPUBurst() {
	Process returnProcess = processList.takeProcessWithShortestCPUBurst();
	cpu.finalReport.addProcess(returnProcess); // PI add the current process to the final report
	return returnProcess;
  }
}
