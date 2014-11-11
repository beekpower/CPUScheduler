public class Process {
	private int PID;
	private int initCPUBurst;
	private int cpuBurst;
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
		this.priority = priority;
		this.period = period;
	}

	public void processInstruction() {
		cpuBurst--;
		if (cpuBurst == 0) {
			isTerminated = true;
		}

		//If we have processed half of the CPU burst
		//then it is time to do the I/O burst and request
		//to be put in the waiting queue.
		if (initBurst / 2 == cpuBurst) {
			isWaiting = true;
			//start an I/O waiting timer here
		}
	}

	public int getCpuBurst() {
		return cpuBurst;
	}

	public void setCpuBurst(int cpuBurst) {
		this.cpuBurst = cpuBurst;
	}

	public void setPID(int pID) {
		PID = pID;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getPID() {
		return PID;
	}

	public int getInitCPUBurst() {
		return initCPUBurst;
	}

	public int getCPUBurst() {
		return cpuBurst;
	}

	public int getIOBurst() {
		return ioBurst;
	}

	public int getPeriod() {
		return period;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String toString() {
		return "PID: "+this.getPID()+" || CPU Burst: "+this.getCpuBurst()+" || IO Burst: "+this.getIOBurst()+" || Priority: "+this.getPriority()+" || Period: "+this.getPeriod();
	}
}
