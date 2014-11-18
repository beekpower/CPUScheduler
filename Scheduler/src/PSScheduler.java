
public class PSScheduler extends Scheduler {
	public PSScheduler(ProcessList processList) {
	    super(processList);
	    this.readableName = "PS";
	  }

	  public Process getNextProcess() {
	    if (processList.hasProcessInReadyQueue()) {
	    	Process returnProcess = processList.takeProcessWithHighestPriority();
	    	cpu.finalReport.addProcess(returnProcess); // PI add the current process to the final report
	    	return returnProcess;
	    } else {
	      //Set the current process to null. The CPU will see this and enter an idle state
	      return null;
	    }
	  }
}
