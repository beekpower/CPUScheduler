
public class PSScheduler extends Scheduler {
	public PSScheduler(ProcessList processList) {
    super(processList);
    this.readableName = "PS";
	 }

  public void schedule() {
		processList.incrementWaitTimeForProcessesInReadyQueue(); //fix this skip
		processList.decrementCurrentProcessesWaiting(currentProcess);
		processList.moveWaitingToReady();
		if (currentProcess != null) {
			currentProcess.processInstruction(cpu.cycleCount);
		}

		if (processList.hasProcessInReadyQueue()) {
			if (currentProcess == null) {
				currentProcess = processWithHighestPriority();
			} else {
				if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
					currentProcess = processWithHighestPriority();
				} else if (processList.getProcessWithHighestPriority(this.currentProcess).getPriority() < currentProcess.getPriority()) {
					processList.addtoReadyQueue(currentProcess, this.cpu.cycleCount);
					currentProcess = processWithHighestPriority();
				}
			}
		} else {
			if (currentProcess != null) {
				if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
					//current process could still be executing, so return it
					currentProcess = null;
				}
			} else {
				currentProcess = null;
			}
		}

  }

	private Process processWithHighestPriority() {
		Process returnProcess = processList.takeProcessWithHighestPriority(this.currentProcess);
		cpu.finalReport.addProcess(returnProcess); // PI add the current process to the final report
		return returnProcess;
	}
}
