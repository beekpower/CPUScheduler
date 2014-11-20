
public class PSScheduler extends Scheduler {
	public PSScheduler(ProcessList processList) {
    super(processList);
    this.readableName = "PS";
	 }

  public void schedule() {
		processList.incrementWaitTimeForProcessesInReadyQueue(); //fix this skip
		processList.decrementCurrentProcessesWaiting();
		if (currentProcess != null) {
			currentProcess.processInstruction();
		}

		if (processList.hasProcessInReadyQueue()) {
			if (currentProcess == null) {
				currentProcess = processWithHighestPriority();
			} else {
				if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
					currentProcess = processWithHighestPriority();
				} else if (processList.getProcessWithHighestPriority().getPriority() < currentProcess.getPriority()) {
					processList.addtoReadyQueue(currentProcess);
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
		Process returnProcess = processList.takeProcessWithHighestPriority();
		cpu.finalReport.addProcess(returnProcess); // PI add the current process to the final report
		return returnProcess;
	}
}
