
public class CPU {

  Scheduler scheduler;
  long busyCycles = 0;
  long idleCycles = 0;
  int cycleCount = 0; // PI add in a cycle counter
  int snapshotInterval = 0;
  Process currentProcessProcessing;
  public FinalReport finalReport; // PI final report object

  public CPU(Scheduler scheduler, int snapshotInterval) {
    this.scheduler = scheduler;
    this.snapshotInterval = snapshotInterval; // PI set the snapshot interval
    cycleCount = 0; // PI set cycle counter to 0
    finalReport = new FinalReport(this); // PI make a new final report for this CPU
  }

  public void execute() {
    while (scheduler.processList.anyJobsLeft()) {
    	if (currentProcessProcessing != null) {
      	  currentProcessProcessing.processInstruction();
          busyCycles++;
        } else {
          idleCycles++;
        }
      scheduler.schedule();
      currentProcessProcessing = scheduler.getCurrentProcess();
      // PI see if we need to take a snapshot
      if(cycleCount % snapshotInterval == 0) { // PI see if the total # cycles divides evenly w/ the snapshot interval
        scheduler.processList.printTable(currentProcessProcessing, this, scheduler);
    	  this.scheduler.processList.takeSnapshot(this); // PI take a snapshot
      }
      cycleCount++;
    }
    this.idleCycles--;
    this.cycleCount--;
    // PI now print out the report
    finalReport.printFinalReport();
  }

}
