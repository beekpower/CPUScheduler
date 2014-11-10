public class Process {
	private int PID;
	private int cpuBurst;
	private int ioBurst;
	private int period;
	private int priority;
	
	public Process(int PID, int cpuBurst, int ioBurst, int priority, int period) {
		this.PID = PID;
		this.cpuBurst = cpuBurst;
		this.ioBurst = ioBurst;
		this.priority = priority;
		this.period = period;
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
