
public class FCFSScheduler extends Scheduler{

  public FCFSScheduler(ProcessList processList) {
	  super(processList);
  }

  public Process getNextReadyProcess() {
    if (processList.hasProcessInReadyQueue()) {
      return processList.takeFirstProcessInReadyQueue();
    } else {
      //Set the current process to null. The CPU will see this and enter an idle state
      return null;
    }
  }

}
