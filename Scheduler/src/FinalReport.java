import java.io.FileWriter;
import java.io.IOException;
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
		if(process != null) {
			int pid = process.getPID();
			if(lastAddedPID > -1) {
				if(lastAddedPID != pid) {
					lastAddedPID = pid;
					executionOrder.add(pid);
				}
			} else {
				lastAddedPID = pid;
				executionOrder.add(pid);
			}
		}
	}

	public void printFinalReport() {
		try {
			FileWriter fileWriter = new FileWriter("FinalReport.txt", true); // PI make a new file writer
			// PI now output all the junk needed
			fileWriter.write("==================================================\n");
			fileWriter.write("Final report for "+this.cpu.scheduler.readableName+"\n");
			fileWriter.write("CPU Execution order for "+this.cpu.scheduler.readableName+"\n");
			int outputCount = 0;
			for(Integer pid: executionOrder) {
				if(outputCount == 0) { // PI is this the first line?
					fileWriter.write("\t"); // PI put in a tab for format
				}
				fileWriter.write("PID"+pid+" >> ");
				outputCount++;
				if(outputCount >= 6) {
					outputCount = 0;
					fileWriter.write("\n");
				}
			}
			fileWriter.write("Done\n");
			fileWriter.write("\n");
			fileWriter.close(); // PI close the file writer
		} catch (IOException e) {
			// uh oh
			e.printStackTrace();
		}
	}
}
