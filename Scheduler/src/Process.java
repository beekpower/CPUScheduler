public class Process {
	private int PID;
	private int initCPUBurst;
	private int cpuBurst;
	private int initIOBurst;
	private int ioBurst;
	private int period;
	private int priority;
	private int counter;
	public int cycleStart;
	public int cycleEnd;
	public int waitTime;
	public int deadline;
	private boolean terminated = false;
	private boolean waiting = false;

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

	public void resetCounter() {
		counter = 0;
	}

	public int getCounter() {
		return counter;
	}

	//Reset the state of the process back to when it was first loaded in
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

	//Decrement the IO Burst
	public void decrementIOBurst() {
		ioBurst--;
		// if (ioBurst < 0) {
		// 	setWaiting(false);
		// }
	}

	//Return the CPU Burst of the process
	public int getCPUBurst() {
		return cpuBurst;
	}

  //Set the CPU Burst of the process
	public void setCPUBurst(int cpuBurst) {
		this.cpuBurst = cpuBurst;
	}

	//Set the period of the process
	public void setPeriod(int period) {
		this.period = period;
	}

	//Get the PID of the process
	public int getPID() {
		return PID;
	}

	//Get the initial CPU Burst of the process
	public int getInitCPUBurst() {
		return initCPUBurst;
	}

  //Get the remaining IO Burst of the process
	public int getIOBurst() {
		return ioBurst;
	}

  //Get the period of the process
	public int getPeriod() {
		return period;
	}

	//Get the priority of the process
	public int getPriority() {
		return priority;
	}

	//Set the priority of the process
	public void setPriority(int priority) {
		this.priority = priority;
	}

	//Convert the propreties to string format
	public String toString() {
		return "PID: "+this.getPID()+" || CPU Burst: "+this.getCPUBurst()+" || IO Burst: "+this.getIOBurst()+" || Priority: "+this.getPriority()+" || Period: "+this.getPeriod();
	}

	//Check the terminated state of the process
	public boolean isTerminated() {
		return terminated;
	}

  //Set the terminated state of the process
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
