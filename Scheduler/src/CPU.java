
public class CPU {

  Scheduler scheduler;
  long busyCycles = 0;
  long idleCycles = 0;
  int cycleCount = 0; // PI add in a cycle counter
  int snapshotInterval = 0;

  public CPU(Scheduler scheduler, int snapshotInterval) {
    this.scheduler = scheduler;
    this.snapshotInterval = snapshotInterval; // PI set the snapshot interval
    cycleCount = 0; // PI set cycle counter to 0
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
      cycleCount++;
      // PI see if we need to take a snapshot
      if(cycleCount % snapshotInterval == 0) { // PI see if the total # cycles divides evenly w/ the snapshot interval
    	  this.scheduler.processList.takeSnapshot(this); // PI take a snapshot
      }
      scheduler.schedule();
    }
  }

}
