//Note: Make and interface for scheduler
public abstract class Scheduler {
  Process currentProcess;
  ProcessList processList;
  Boolean preemptive = false;
  public String readableName;
  public CPU cpu;


  public Scheduler(ProcessList processList) {
    this.processList = processList;
    //Move all the processes into the ready queue to start;
    processList.reinitialize();
  }

  public void schedule() {
	  processList.incrementWaitTimeForProcessesInReadyQueue();
	  processList.decrementProcessesInIO();
	  processList.moveProcessesBackToReadyQueue();
	  processList.addAnyProcessesToWaitingQueue(currentProcess);
	  
	  currentProcess = getNextProcess();
    //processList.addAnyProcessesToWaitingQueue(currentProcess);
  }

  public Process getCurrentProcess() {
    return currentProcess;
  }

  /**
   * PI this is used to update the reference o the CPU
   */
  public void updateCPU(CPU cpu) {
	  this.cpu = cpu;
	  currentProcess = getNextProcess();
  }

  public abstract Process getNextProcess();
}
