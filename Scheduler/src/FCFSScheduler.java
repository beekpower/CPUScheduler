
public class FCFSScheduler extends Scheduler{

  public FCFSScheduler(ProcessList processList) {
	  super(processList);
	  this.readableName = "FCFS";
  }

  public Process getNextProcess() {
    if (processList.hasProcessInReadyQueue()) {
      return processList.takeFirstProcessInReadyQueue();
    } else {
      //Set the current process to null. The CPU will see this and enter an idle state
      return null;
    }
  }

}
