public class Process {
	private int PID;
	private int initCPUBurst;
	private int cpuBurst;
	private int initIOBurst
	private int ioBurst;
	private int period;
	private int priority;
	private boolean isTerminated = false;
	private boolean isWaiting = false;

	public Process(int PID, int cpuBurst, int ioBurst, int priority, int period) {
		this.PID = PID;
		this.cpuBurst = cpuBurst;
		this.initCPUBurst = cpuBurst;
		this.ioBurst = ioBurst;
		this.initIOBurst = ioBurst;
		this.priority = priority;
		this.period = period;
	}

  //The CPU calls this to process the next inrcution/CPU burst
	public void processInstruction() {
		//Decrement the CPU burst
		cpuBurst--;
		//If the CPU burst reaches 0, then the process is terminated
		if (cpuBurst == 0) {
			isTerminated = true;
		}

		//If we have processed half of the CPU burst
		//then it is time to enter the I/O burst and enter the waiting state
		if (initBurst / 2 == cpuBurst) {
			isWaiting = true;
		}
	}

	//Reset the state of the process back to when it was first loaded in
	public void reset() {
		this.cpuBurst = initCPUBurst;
		this.ioBurst = initIOBurst;
		isTerminated = false;
		isWaiting = false;
	}

	//Decrement the IO Burst
	public void decrementIOBurst() {
		ioBurst--;
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
		return "PID: "+this.getPID()+" || CPU Burst: "+this.getCpuBurst()+" || IO Burst: "+this.getIOBurst()+" || Priority: "+this.getPriority()+" || Period: "+this.getPeriod();
	}
}
