//Note: Make and interface for scheduler
public abstract class Scheduler {
  Process currentProcess;
  ProcessList processList;

  public Scheduler(ProcessList processList) {
    this.processList = processList;
    //Move all the processes into the ready queue to start;
    processList.reinitialize();
  }

  public void schedule() {
    if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
      currentProcess = getNextReadyProcess();
    }
    
    processList.decrementCurentProcessesWaiting();
  }

  public Process getCurrentProcess() {
    return currentProcess;
  }

  public abstract Process getNextReadyProcess();
}
