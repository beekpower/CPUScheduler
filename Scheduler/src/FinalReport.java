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
		if(lastAddedPID > -1) {
			if(lastAddedPID != process.getPID()) {
				executionOrder.add(process.getPID());
			}
		} else {
			executionOrder.add(process.getPID());
		}
	}

	public void printFinalReport() {
		try {
			FileWriter fileWriter = new FileWriter("FinalReport.txt", true); // PI make a new file writer
			// PI now output all the junk needed
			fileWriter.write("==================================================\n");
			fileWriter.write("Final report for "+this.cpu.scheduler.readableName+"\n");
			fileWriter.write("CPU Execution order for "+this.cpu.scheduler.readableName+"\n");
			for(Integer pid: executionOrder) {
				fileWriter.write(">"+pid+"\n");
			}/*
			fileWriter.write("\n");
			fileWriter.write("Processes in IO\n");
			if(processesInIO.size() > 0) {
				for(Integer pid: processesInIO) {
					fileWriter.write(">"+pid+"\n");
				}
			} else {
				fileWriter.write("None\n");
			}*/
			fileWriter.close(); // PI close the file writer
		} catch (IOException e) {
			// uh oh
			e.printStackTrace();
		}
	}
}
