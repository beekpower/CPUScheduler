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

    if (preemptive) {
    	updateCurrentProcess();
    } else {
      if (currentProcess == null) {
    	  updateCurrentProcess();
      } else {
        if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
        	updateCurrentProcess();
        }
      }
    }
    processList.decrementCurentProcessesWaiting();
  }

  public Process getCurrentProcess() {
    return currentProcess;
  }
  
  public void updateCPU(CPU cpu) {
	  this.cpu = cpu;
	  this.updateCurrentProcess();
  }

  private void updateCurrentProcess() {
	  currentProcess = getNextProcess();
	  cpu.finalReport.addProcess(currentProcess); // PI add the current process to the final report
  }
  
  public abstract Process getNextProcess();
}
