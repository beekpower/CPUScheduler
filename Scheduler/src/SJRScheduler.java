public class SJRScheduler extends Scheduler {

  public SJRScheduler(ProcessList processList) {
    super(processList);
    preemptive = true;
  }

  public Process getNextReadyProcess() {
    if (processList.hasProcessInReadyQueue()) {
      if (currentProcess != null) {
        if (currentProcess.getCPUBurst() > processList.getProcessWithShortestCPUBurst().getCPUBurst()) {
          return processList.takeProcessWithShortestCPUBurst();
        } else {
          return currentProcess;
        }
      } else {
        return processList.takeProcessWithShortestCPUBurst();
      }
    } else {
      //Set the current process to null. The CPU will see this and enter an idle state
      return null;
    }
  }
}
