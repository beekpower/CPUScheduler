public class SJFScheduler extends Scheduler {

  public SJFScheduler(ProcessList processList) {
    super(processList);
  }

  public Process getNextReadyProcess() {
    if (processList.hasProcessInReadyQueue()) {
      return processList.takeProcessWithShortestCPUBurst();
    } else {
      //Set the current process to null. The CPU will see this and enter an idle state
      return null;
    }
  }

}
