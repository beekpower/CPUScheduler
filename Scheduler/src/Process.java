/**
 * 
 * @author Phillip Igoe & Nick Van Beek
 *
 */
public class Process {
	private int PID;			// NV pid of this process
	private int initCPUBurst;	// NV the initial CPU burst of this process
	private int cpuBurst;		// NV current CPU burst
	private int initIOBurst;	// NV initial io burst
	private int ioBurst;		// NV current io burst
	private int period;			// NV initial period
	private int priority;		// NV intial priority
	private int counter;		// NV counter used for RR
	public int cycleStart;		// PI cycle start keeping track of when this process started executing
	public int cycleEnd;		// PI cycle end keeping track when this process finished executing
	public int waitTime;		// PI time this process has waited to execute
	public int deadline;		// PI deadline calculation for this process
	private boolean terminated = false;	// NV has this process terminated?
	private boolean waiting = false;	// NV is this process currently waiting in IO

	public Process(int PID, int cpuBurst, int ioBurst, int priority, int period) {
		this.PID = PID;
		this.cpuBurst = cpuBurst;
		this.initCPUBurst = cpuBurst;
		this.ioBurst = ioBurst;
		this.initIOBurst = ioBurst;
		this.priority = priority;
		this.period = period;
		this.counter = 0;
		this.cycleStart = -1;
		this.cycleEnd = -1;
		this.deadline = period - cpuBurst;
		waitTime = 0;
	}

	/**
	 * PI copy's this process and returns a new one
	 * @return
	 */
	public Process copyProcess() {
		return new Process(this.getPID(), this.getCPUBurst(), this.getIOBurst(), this.getPriority(), this.getPriority());
	}

  //The CPU calls this to process the next inrcution/CPU burst
	public void processInstruction(int cycle) {
		//increment the counter for RR
		counter++;
		//Decrement the CPU burst
		cpuBurst--;
		//If the CPU burst reaches 0, then the process is terminated
		if (cpuBurst == 0) {
			setTerminated(true);
		}

		//If we have processed half of the CPU burst
		//then it is time to enter the I/O burst and enter the waiting state
		if (initCPUBurst / 2 == cpuBurst) {
			if (ioBurst != 0) {
				setWaiting(true);
				this.cycleEnd = cycle;
			}
		}
	}

	/**
	 * NV reset the counter for RR
	 */
	public void resetCounter() {
		counter = 0;
	}

	/**
	 * NV return the current count
	 * @return int
	 */
	public int getCounter() {
		return counter;
	}

	/** 
	 * NV Reset the state of the process back to when it was first loaded in
	 */
	public void reset() {
		this.cpuBurst = initCPUBurst;
		this.ioBurst = initIOBurst;
		setTerminated(false);
		setWaiting(false);
		counter = 0;
		waitTime = 0;
		cycleStart = -1;
		cycleEnd = -1;
	}

	/**
	 * NV Decrement the IO Burst
	 */
	public void decrementIOBurst() {
		ioBurst--;
		// if (ioBurst < 0) {
		// 	setWaiting(false);
		// }
	}

	/**
	 * Return the CPU Burst of the process
	 * @return
	 */
	public int getCPUBurst() {
		return cpuBurst;
	}

	/**
	 * Set the CPU Burst of the process
	 * @param cpuBurst
	 */
	public void setCPUBurst(int cpuBurst) {
		this.cpuBurst = cpuBurst;
	}

	/**
	 * NV Set the period of the process
	 * @param period
	 */
	public void setPeriod(int period) {
		this.period = period;
	}

	/**
	 * NV Get the PID of the process
	 * @return
	 */
	public int getPID() {
		return PID;
	}

	/**
	 * NV Get the initial CPU Burst of the process
	 * @return
	 */
	public int getInitCPUBurst() {
		return initCPUBurst;
	}

	/**
	 * NV Get the remaining IO Burst of the process
	 * @return
	 */
	public int getIOBurst() {
		return ioBurst;
	}

	/**
	 * NV Get the period of the process
	 * @return
	 */
	public int getPeriod() {
		return period;
	}

	/**
	 * NV Get the priority of the process
	 * @return
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * NV Set the priority of the process
	 * @param priority
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * NV Convert the propreties to string format
	 */
	public String toString() {
		return "PID: "+this.getPID()+" || CPU Burst: "+this.getCPUBurst()+" || IO Burst: "+this.getIOBurst()+" || Priority: "+this.getPriority()+" || Period: "+this.getPeriod();
	}

	/**
	 * NV Check the terminated state of the process
	 * @return
	 */
	public boolean isTerminated() {
		return terminated;
	}

    /**
     * NV Set the terminated state of the process
     * @param terminated
     */
	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}

	//Check the waiting state of teh process
	public boolean isWaiting() {
		return waiting;
	}

	//Set the waiting state of the process
	public void setWaiting(boolean waiting) {
		this.waiting = waiting;
	}
}
