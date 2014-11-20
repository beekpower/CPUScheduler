
public class PSScheduler extends Scheduler {
	public PSScheduler(ProcessList processList) {
    super(processList);
    this.readableName = "PS";
		preemptive = true;
	 }

  public Process getNextProcess() {
		if (processList.hasProcessInReadyQueue()) {
			if (currentProcess == null) {
				return processWithHighestPriority();
			} else {
				if (currentProcess.isTerminated() || processList.ioQueue.contains(currentProcess)) {
					return processWithHighestPriority();
				} else if (processList.getProcessWithHighestPriority().getPriority() < currentProcess.getPriority()) {
					processList.addtoReadyQueue(currentProcess);
					return processWithHighestPriority();
				} else {
					return currentProcess;
				}
			}
		} else {
			if (currentProcess != null) {
				if (currentProcess.isTerminated() || processList.ioQueue.contains(currentProcess)) {
					//current process could still be executing, so return it
					return null;
				} else {
					return currentProcess;
				}
			} else {
				return null;
			}
		}
  }

	private Process processWithHighestPriority() {
		Process returnProcess = processList.takeProcessWithHighestPriority();
		cpu.finalReport.addProcess(returnProcess); // PI add the current process to the final report
		return returnProcess;
	}
}
