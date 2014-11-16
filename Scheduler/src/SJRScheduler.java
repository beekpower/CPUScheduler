public class SJRScheduler extends Scheduler {

  public SJRScheduler(ProcessList processList) {
    super(processList);
    preemptive = true;
    this.readableName = "SJR";
  }

  public Process getNextProcess() {
    if (processList.hasProcessInReadyQueue()) {
      if (currentProcess == null) {
        return processList.takeProcessWithShortestCPUBurst();
      } else {
        if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
          return processList.takeProcessWithShortestCPUBurst();
        } else if (currentProcess.getCPUBurst() > processList.getProcessWithShortestCPUBurst().getCPUBurst()) {
          processList.addtoReadyQueue(currentProcess);
          return processList.takeProcessWithShortestCPUBurst();
        } else {
          return currentProcess;
        }
      }
    } else {
      //Set the current process to null. The CPU will see this and enter an idle state
      return null;
    }
  }
}
