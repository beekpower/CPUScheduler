public class SJRScheduler extends Scheduler {

  public SJRScheduler(ProcessList processList) {
    super(processList);
    this.readableName = "SJR";
  }

  public void schedule() {
    processList.incrementWaitTimeForProcessesInReadyQueue(); //fix this skip
    processList.decrementCurrentProcessesWaiting();
    if (currentProcess != null) {
      currentProcess.processInstruction(cpu.cycleCount);
    }

    if (processList.hasProcessInReadyQueue()) {
      if (currentProcess == null) {
        currentProcess = processWithShortestCPUBurst();
      } else {
        if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
          currentProcess = processWithShortestCPUBurst();
        } else if (processList.getProcessWithShortestCPUBurst().getCPUBurst() < currentProcess.getCPUBurst()) {
          processList.addtoReadyQueue(currentProcess, this.cpu.cycleCount);
          currentProcess = processWithShortestCPUBurst();
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
    processList.moveWaitingToReady();
  }

  private Process processWithShortestCPUBurst() {
  	Process returnProcess = processList.takeProcessWithShortestCPUBurst();
  	cpu.finalReport.addProcess(returnProcess); // PI add the current process to the final report
  	return returnProcess;
  }
}
