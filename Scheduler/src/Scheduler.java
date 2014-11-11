//Note: Make and interface for scheduler
public abstract class Scheduler {
  Process currentProcess;
  ProcessList processList;

  public Scheduler(ProcessList processList) {
    this.processList = processList;
    //Move all the processes into the ready queue to start;
    processList.initQueues();
  }

  public void schedule() {
    if (currentProcess.isTerminated()) {
      currentProcess = getNextReadyProcess();
    } else if (currentProcess.isWaiting()) {
      processList.startWaitingTimer(currentProcess);
      currentProcess = getNextReadyProcess();
    }
  }

  public Process getCurrentProcess() {
    return currentProcess;
  }

  private abstract Process getNextReadyProcess();
}
