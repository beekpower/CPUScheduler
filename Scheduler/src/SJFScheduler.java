public class SJFScheduler extends Scheduler {

  public SJFScheduler(ProcessList processList) {
    super(processList);
    this.readableName = "SJF";
  }

  public Process getNextProcess() {
    if (processList.hasProcessInReadyQueue()) {
    	Process returnProcess = processList.takeProcessWithShortestCPUBurst();
    	cpu.finalReport.addProcess(returnProcess); // PI add the current process to the final report
    	return returnProcess;
    } else {
      //Set the current process to null. The CPU will see this and enter an idle state
      return null;
    }
  }

}
