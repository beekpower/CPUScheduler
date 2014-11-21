/**
 * The CPU class handles executing processes, calling the schedule method, and keeping track of the busy/idle/total cycle count
 * 
 * @author Phillip Igoe & Nick Van Beek
 * 
 */
public class CPU {

	Scheduler scheduler; // NV reference to the scheduler
	long busyCycles = 0; // NV number of busy cycles in this CPU
	long idleCycles = 0; // NV number of idle cycles in this CPU
	int cycleCount = 0; // PI add in a cycle counter
	int snapshotInterval = 0; // NV snapshot interval
	public FinalReport finalReport; // PI final report object

	/**
	 * NV constructs a CPU
	 * 
	 * @param scheduler
	 *            scheduler object
	 * @param snapshotInterval
	 *            interval to take snapshots at
	 */
	public CPU(Scheduler scheduler, int snapshotInterval) {
		this.scheduler = scheduler; // NV update scheduler reference
		this.snapshotInterval = snapshotInterval; // PI set the snapshot interval
		cycleCount = 0; // PI set cycle counter to 0
		finalReport = new FinalReport(this); // PI make a new final report for this CPU
	}

	/**
	 * NV execute, which is called every cycle, executes current process / takes care of keeping track of busy/idle cycles and printing final report
	 */
	public void execute() {
		while (scheduler.processList.anyJobsLeft()) { // NV only execute if we have jobs left
			scheduler.schedule(); // NV call the child's schedule method
			if (scheduler.getCurrentProcess() != null) { // NV if the current process is actually a process, this is a busy cycle
				busyCycles++; // NV increment the busy cycles
			} else { // NV else, this is an idle cycle
				idleCycles++; // NV increment the idle cycles
			}

			// PI see if we need to take a snapshot
			if (cycleCount % snapshotInterval == 0) { // PI see if the total # cycles divides evenly w/ the snapshot interval
				// scheduler.processList.printTable(scheduler.getCurrentProcess(), this, scheduler);
				scheduler.processList.takeSnapshot(this); // PI take a snapshot
			}
			cycleCount++; // NV increment the cycle count
		}

		// NV take care of decrementing our counts
		this.idleCycles--;
		this.cycleCount--;
		// PI now print out the report
		finalReport.printFinalReport();
	}

}
