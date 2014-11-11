
public class FCFSScheduler extends Scheduler{

  public FCFSScheduler(ProcessList processList) {
	super(processList);
  }

  public Process getNextReadyProcess() {
    if (processList.hasProcessInReadyQueue()) {
      Process temp = processList.getFirstProcessInReadyQueue();
      //This is FCFS, so get the first process in the list
      processList.removeFromReadyQueue(temp.getPID());
      return temp;
    } else {
      //Set the current process to null. The CPU will see this and enter an idle state
      return null;
    }
  }

}
