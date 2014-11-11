//Note: Make and interface for scheduler
public abstract class Scheduler {
  Process currentProcess;
  ProcessList processList;
  Boolean preemptive = false;


  public Scheduler(ProcessList processList) {
    this.processList = processList;
    //Move all the processes into the ready queue to start;
    processList.reinitialize();

    this.currentProcess = this.getNextReadyProcess();
  }

  public void schedule() {
    if (preemptive) {
      getNextReadyProcess();
    } else {
      if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
        currentProcess = getNextReadyProcess();
      }
    }
    processList.decrementCurrentProcessesWaiting();
  }

  public Process getCurrentProcess() {
    return currentProcess;
  }

  public abstract Process getNextReadyProcess();
}
