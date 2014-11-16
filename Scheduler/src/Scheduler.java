//Note: Make and interface for scheduler
public abstract class Scheduler {
  Process currentProcess;
  ProcessList processList;
  Boolean preemptive = false;
  public String readableName;


  public Scheduler(ProcessList processList) {
    this.processList = processList;
    //Move all the processes into the ready queue to start;
    processList.reinitialize();

    this.currentProcess = this.getNextProcess();
  }

  public void schedule() {

    if (preemptive) {
      currentProcess = getNextProcess();
    } else {
      if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
        currentProcess = getNextProcess();
      }
    }
    processList.decrementCurentProcessesWaiting();
  }

  public Process getCurrentProcess() {
    return currentProcess;
  }

  public abstract Process getNextProcess();
}
