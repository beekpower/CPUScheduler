import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author Phillip Igoe & Nick Van Beek
 * 
 */
public class FinalReport {
	public CPU cpu; // PI ref to the CPU
	// PI whole bunch of stats
	public ArrayList<Integer> executionOrder;
	public double throughput;
	public int turnAroundTime;
	public double CPUUtilization;
	public float averageWaitTime;

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
	 * 
	 * @param process
	 *            process to add
	 */
	public void addProcess(Process process) {
		if (process != null) {
			executionOrder.add(process.getPID());
		}
	}

	/**
	 * PI print the final report by appending a bunch of stats to the FinalReport.txt file
	 */
	public void printFinalReport() {
		try {
			FileWriter fileWriter = new FileWriter("FinalReport.txt", true); // PI make a new file writer
			// PI now output all the junk needed
			fileWriter.write("==================================================\n");
			fileWriter.write("Final report for " + this.cpu.scheduler.readableName + "\n");
			fileWriter.write("CPU Execution order for " + this.cpu.scheduler.readableName + "\n");
			int outputCount = 0;
			for (Integer pid : executionOrder) {
				if (outputCount == 0) { // PI is this the first line?
					fileWriter.write("\t"); // PI put in a tab for format
				}
				fileWriter.write("PID" + pid + " >> ");
				outputCount++;
				if (outputCount >= 6) {
					outputCount = 0;
					fileWriter.write("\n");
				}
			}
			fileWriter.write("Done\n");
			// PI let's do some data cals
			int numberOfProcesses = this.cpu.scheduler.processList.processes.size();
			if (this.cpu.idleCycles == 0) {
				this.CPUUtilization = 100;
			} else {
				this.CPUUtilization = ((double) this.cpu.busyCycles / (double) (this.cpu.idleCycles + this.cpu.busyCycles)) * 100;
			}
			this.turnAroundTime = (int) this.cpu.cycleCount;
			// PI calculate wait time for all processes
			int runningTotal = 0;
			for (Process process : this.cpu.scheduler.processList.processes) {
				runningTotal += process.waitTime;
			}
			// PI calculate deadline violations
			if (this.cpu.scheduler.readableName == "PRM" || this.cpu.scheduler.readableName == "EDF") {
				this.deadlineViolations = 0;
				for (Process process : this.cpu.scheduler.processList.processes) {
					if (process.getPeriod() > (process.cycleEnd - process.cycleStart)) {
						this.deadlineViolations++;
					}
				}
			}
			// PI let's do some calcs for the average wait time + throughput
			this.averageWaitTime = (float) runningTotal / (float) numberOfProcesses;
			this.throughput = (double) numberOfProcesses / (double) this.cpu.cycleCount;
			fileWriter.write("Throughput for " + this.cpu.scheduler.readableName + " = " + this.throughput + "\n");
			fileWriter.write("Total Turn-around Time for " + this.cpu.scheduler.readableName + " = " + this.turnAroundTime + "\n");
			fileWriter.write("Average Wait Time for " + this.cpu.scheduler.readableName + " = " + averageWaitTime + "\n");
			fileWriter.write("CPU Utilization for " + this.cpu.scheduler.readableName + " = " + this.CPUUtilization + "%\n");
			if (this.deadlineViolations != -1) {
				fileWriter.write("Deadline violations for " + this.cpu.scheduler.readableName + " = " + this.deadlineViolations + "\n");
			}
			fileWriter.write("\n");
			fileWriter.close(); // PI close the file writer

			// PI let's save our stats to the scheduler
			this.cpu.scheduler.throughput = throughput;
			this.cpu.scheduler.turnAroundTime = turnAroundTime;
			this.cpu.scheduler.waitTime = averageWaitTime;
			this.cpu.scheduler.cpuUtilization = CPUUtilization;
			this.cpu.scheduler.deadlineViolations = deadlineViolations;
		} catch (IOException e) {
			// uh oh
			e.printStackTrace();
		}
	}
}
