
public class PSScheduler extends Scheduler {
	public PSScheduler(ProcessList processList) {
	    super(processList);
	    this.readableName = "PS";
			preemptive = true;
	  }

	  public Process getNextProcess() {
	    if (processList.hasProcessInReadyQueue()) {
<<<<<<< HEAD
	    	cpu.finalReport.addProcess(currentProcess); // PI add the current process to the final report
				processList.addProcess(currentProcess);
	      return processList.takeProcessWithHighestPriority();
=======
	    	Process returnProcess = processList.takeProcessWithHighestPriority();
	    	cpu.finalReport.addProcess(returnProcess); // PI add the current process to the final report
	    	return returnProcess;
>>>>>>> e9729e66e57ec25f85ec46be647e373c9fd203c9
	    } else {
	      //Set the current process to null. The CPU will see this and enter an idle state
	      return null;
	    }
	  }
}
