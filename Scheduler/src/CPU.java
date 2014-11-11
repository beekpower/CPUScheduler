
public class CPU {

  Scheduler scheduler;
  long busyCycles = 0;
  long idleCycles = 0;

  public CPU(Scheduler scheduler, int snapshotInterval) {
    this.scheduler = scheduler;
  }

  public void execute() {
    while (scheduler.processList.anyJobsLeft()) {
      Process process = scheduler.getCurrentProcess();
      if (process != null) {
        process.processInstruction();
        busyCycles++;
      } else {
        idleCycles++;
      }
      scheduler.schedule();
    }
  }

}
