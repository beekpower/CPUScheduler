public class RRScheduler extends Scheduler {

  public RRScheduler(ProcessList processList) {
    super(processList);
    preemptive = true;
    this.readableName = "RR";
  }

  public Process getNextProcess() {
    if (processList.hasProcessInReadyQueue()) {
      if (currentProcess == null) {
        return processList.takeFirstProcessInReadyQueue();
      } else {
        if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
          currentProcess.resetCounter();
          return processList.takeFirstProcessInReadyQueue();
        } else if (currentProcess.getCounter() == processList.getQuantum()) {
          currentProcess.resetCounter();
          processList.addtoReadyQueue(currentProcess);
          return processList.takeFirstProcessInReadyQueue();
        } else {
          return currentProcess;
        }
      }
    } else {
      //Set the current process to null. The CPU will see this and enter an idle state
      return null;
    }
  }
}
