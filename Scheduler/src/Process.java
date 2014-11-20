public class Process {
	private int PID;
	private int initCPUBurst;
	private int cpuBurst;
	private int initIOBurst;
	private int ioBurst;
	private int period;
	private int priority;
	private int counter;
	public int waitTime;
	private boolean terminated = false;
	public boolean addedToReadyQueue = false;

	public Process(int PID, int cpuBurst, int ioBurst, int priority, int period) {
		this.PID = PID;
		this.cpuBurst = cpuBurst;
		this.initCPUBurst = cpuBurst;
		this.ioBurst = ioBurst;
		this.initIOBurst = ioBurst;
		this.priority = priority;
		this.period = period;
		this.counter = 0;
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
	public void processInstruction() {
		//increment the counter for RR
		counter++;
		//Decrement the CPU burst
		cpuBurst--;
		//If the CPU burst reaches 0, then the process is terminated
		if (cpuBurst == 0) {
			setTerminated(true);
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
		counter = 0;
		waitTime = 0;
		this.addedToReadyQueue = false;
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

	public boolean readyForIO() {
		if (getInitCPUBurst() / 2 == getCPUBurst()) {
			if (getIOBurst() != 0) {
				return true;
			}
		}
		return false;
	}
}
