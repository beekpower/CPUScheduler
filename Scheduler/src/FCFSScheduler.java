
public class FCFSScheduler extends Scheduler{

  public FCFSScheduler(ProcessList processList) {
    this.processList = processList;
    //As per FCFS, set the initial current process to the first process in the ready queue
    this.currentProcess = getNextReadyProcess;
  }

  private Process getNextReadyProcess() {
    if (processList.hasReady()) {
      Process temp = processList.getReadyProcessAtIndex(0);
      //This is FCFS, so get the first process in the list
      processList.removeFromReadyQueue(temp.getPID());
      return temp;
    } else {
      //Set the current process to null. The CPU will see this and enter an idle state
      return null;
    }
  }

}
