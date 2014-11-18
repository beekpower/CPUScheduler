
public class PSScheduler extends Scheduler {
	public PSScheduler(ProcessList processList) {
	    super(processList);
	    this.readableName = "PS";
	  }

	  public Process getNextProcess() {
	    if (processList.hasProcessInReadyQueue()) {
	    	cpu.finalReport.addProcess(currentProcess); // PI add the current process to the final report
	      return processList.takeProcessWithHighestPriority();
	    } else {
	      //Set the current process to null. The CPU will see this and enter an idle state
	      return null;
	    }
	  }
}
