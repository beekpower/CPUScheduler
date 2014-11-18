
public class PSScheduler extends Scheduler {
	public PSScheduler(ProcessList processList) {
	    super(processList);
	    this.readableName = "PS";
			preemptive = true;
	  }

	  public Process getNextProcess() {
	    if (processList.hasProcessInReadyQueue()) {
	    	cpu.finalReport.addProcess(currentProcess); // PI add the current process to the final report
				processList.addProcess(currentProcess);
	      return processList.takeProcessWithHighestPriority();
	    } else {
	      //Set the current process to null. The CPU will see this and enter an idle state
	      return null;
	    }
	  }
}
