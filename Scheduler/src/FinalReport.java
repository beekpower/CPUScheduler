import java.util.ArrayList;


public class FinalReport {
	public CPU cpu; // PI ref to the CPU
	// PI whole bunch of stats
	public ArrayList<Integer> executionOrder;
	public double throughput;
	public int turnAroundTime;
	public double CPUUtilization;
	
	private int lastAddedPID = -1;
	
	public int deadlineViolations; // PI deadline violations
	
	public FinalReport(CPU cpu) {
		this.executionOrder = new ArrayList<Integer>();
		this.cpu = cpu;
		throughput = 0;
		turnAroundTime = 0;
		CPUUtilization = 0;
		deadlineViolations = -1; // PI some schedulers do not report this
	}

	/**
	 * PI method that adds the process to the execution order - does not add sequential duplicates
	 * @param process process to add
	 */
	public void addProcess(Process process) {
		if(lastAddedPID > -1) {
			if(lastAddedPID != process.getPID()) {
				executionOrder.add(process.getPID());
			}
		} else {
			executionOrder.add(process.getPID());
		}
	}
}
