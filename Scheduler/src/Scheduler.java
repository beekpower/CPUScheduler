/**
 * Scheduler class acts as the abstract class, and allows for easy implementations of child classes
 * 
 * @author Phillip Igoe & Nick Van Beek
 * 
 */
public abstract class Scheduler {
	Process currentProcess; // NV current process currently processing
	ProcessList processList; // NV reference to the process list
	Boolean preemptive = false; // NV is this scheduler preemptive or not?
	public String readableName; // PI readable name for this scheduler (used in the final reports)
	public CPU cpu; // NV reference to the CPU

	// PI keep track of some stats
	public double throughput;
	public int turnAroundTime;
	public double waitTime;
	public double cpuUtilization;
	public int deadlineViolations;

	public Scheduler(ProcessList processList) {
		this.processList = processList; // NV assign a reference the process list
		// Move all the processes into the ready queue to start;
		processList.reinitialize();
	}

	/**
	 * NV return the current process
	 * 
	 * @return
	 */
	public Process getCurrentProcess() {
		return currentProcess;
	}

	/**
	 * PI this is used to update the reference to the CPU
	 */
	public void updateCPU(CPU cpu) {
		this.cpu = cpu;
	}

	/**
	 * NV abstract schedule method - to be overriden by child instances of this class
	 */
	public abstract void schedule();

	/**
	 * PI return the calcualted rating of this scheduler - for use in the statistics at the end
	 * 
	 * @return a double val of the rating for this scheduler
	 */
	public double getCalculatedRating() {
		return (double) this.throughput + ((double) 1 / (double) this.turnAroundTime) + ((double) 1 / this.waitTime) + this.cpuUtilization + this.deadlineViolations;
	}
}
