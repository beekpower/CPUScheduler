
public class FCFSScheduler extends Scheduler{

  public FCFSScheduler(ProcessList processList) {
	  super(processList);
	  this.readableName = "FCFS";
  }

  public void schedule() {
    processList.incrementWaitTimeForProcessesInReadyQueue(); //fix this skip
    processList.decrementCurrentProcessesWaiting();
    processList.moveWaitingToReady();
    if (currentProcess != null) {
      currentProcess.processInstruction(cpu.cycleCount);
      if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
        if (processList.hasProcessInReadyQueue()) {
          Process returnProcess = processList.takeFirstProcessInReadyQueue();
          cpu.finalReport.addProcess(returnProcess); // PI add the current process to the final report
          currentProcess = returnProcess;
        } else {
          //Set the current process to null. The CPU will see this and enter an idle state
          currentProcess = null;
        }
      }
    } else  {
      if (processList.hasProcessInReadyQueue()) {
        Process returnProcess = processList.takeFirstProcessInReadyQueue();
        cpu.finalReport.addProcess(returnProcess); // PI add the current process to the final report
        currentProcess = returnProcess;
      } else {
        //Set the current process to null. The CPU will see this and enter an idle state
        currentProcess = null;
      }
    }

  }

}
