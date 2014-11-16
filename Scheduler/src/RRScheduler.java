public class RRScheduler extends Scheduler {
  int quantum;

  public RRScheduler(ProcessList processList, int quantum) {
    super(processList);
    preemptive = true;
    this.readableName = "RR";
    this.quantum = quantum;
  }

  public Process getNextProcess() {
    if (processList.hasProcessInReadyQueue()) {
      if (currentProcess == null) {
        return processList.takeFirstProcessInReadyQueue();
      } else {
        if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
          currentProcess.resetCounter();
          return processList.takeFirstProcessInReadyQueue();
        } else if (currentProcess.getCounter() == quantum) {
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
