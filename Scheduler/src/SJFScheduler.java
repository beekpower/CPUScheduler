public class SJFScheduler extends Scheduler {

  public SJFScheduler(ProcessList processList) {
    super(processList);
    this.readableName = "SJF";
  }

  public void schedule() {
    processList.incrementWaitTimeForProcessesInReadyQueue(); //fix this skip
    processList.decrementCurrentProcessesWaiting();
    if (currentProcess != null) {
      currentProcess.processInstruction();
      if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
        if (processList.hasProcessInReadyQueue()) {
          Process returnProcess = processList.takeProcessWithShortestCPUBurst();
          cpu.finalReport.addProcess(returnProcess); // PI add the current process to the final report
          currentProcess = returnProcess;
        } else {
          //Set the current process to null. The CPU will see this and enter an idle state
          currentProcess = null;
        }
      }
    } else  {
      if (processList.hasProcessInReadyQueue()) {
        Process returnProcess = processList.takeProcessWithShortestCPUBurst();
        cpu.finalReport.addProcess(returnProcess); // PI add the current process to the final report
        currentProcess = returnProcess;
      } else {
        //Set the current process to null. The CPU will see this and enter an idle state
        currentProcess = null;
      }
    }
  }

}
