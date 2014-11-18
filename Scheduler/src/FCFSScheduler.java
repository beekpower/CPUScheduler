
public class FCFSScheduler extends Scheduler{

  public FCFSScheduler(ProcessList processList) {
	  super(processList);
	  this.readableName = "FCFS";
  }

  public Process getNextProcess() {
    if (processList.hasProcessInReadyQueue()) {
    	Process returnProcess = processList.takeFirstProcessInReadyQueue();
    	cpu.finalReport.addProcess(returnProcess); // PI add the current process to the final report
    	return returnProcess;
    } else {
      //Set the current process to null. The CPU will see this and enter an idle state
      return null;
    }
  }

}
