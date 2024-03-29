/**
 * RR scheduler implementation
 * 
 * @author Phillip Igoe & Nick Van Beek
 * 
 */
public class RRScheduler extends Scheduler {

	public RRScheduler(ProcessList processList) {
		super(processList);
		preemptive = true;
		this.readableName = "RR";
	}

	@Override
	public void schedule() {
		processList.incrementWaitTimeForProcessesInReadyQueue(); // NV increment the wait time for all processes in ready queue
		processList.decrementCurrentProcessesWaiting(); // NV decrement the current processes waiting by looping through all processes in IO and decrmenting their IO time
		processList.moveWaitingToReady(); // NV moves all the processes that are waiting, and if the IO burst is less than or equal to 0, moves them to the ready queue

		if (currentProcess != null) { // PI ensure current process isn't null
			currentProcess.processInstruction(cpu.cycleCount);
		}

		if (processList.hasProcessInReadyQueue()) { // NV are there processes in the ready queue?
			if (currentProcess == null) { // PI ensure current process isn't null
				currentProcess = takeFirstProcessInReadyQueue(); // NV grab the first process in ready queue and remove it
			} else {
				if (currentProcess.isTerminated() || currentProcess.isWaiting()) { // NV is the current process terminated or waiting?
					currentProcess.resetCounter();
					currentProcess = this.takeFirstProcessInReadyQueue(); // NV grab the first process in ready queue and remove it
				} else if (currentProcess.getCounter() == processList.getQuantum()) {
					currentProcess.resetCounter();
					processList.addtoReadyQueue(currentProcess, this.cpu.cycleCount);
					currentProcess = this.takeFirstProcessInReadyQueue(); // NV grab the first process in ready queue and remove it
				}
			}
		}

		else {
			if (currentProcess != null) { // PI ensure current process isn't null
				if (currentProcess.isTerminated() || currentProcess.isWaiting()) { // NV is the current process terminated or waiting?
					// current process could still be executing, so return it
					currentProcess = null;
				}
			} else {
				currentProcess = null;
			}
		}

	}

	private Process takeFirstProcessInReadyQueue() {
		Process returnProcess = processList.takeFirstProcessInReadyQueue(); // NV grab the process with the shortest CPU burst
		cpu.finalReport.addProcess(returnProcess); // PI add the current process to the final report
		return returnProcess; // PI return the process
	}
}
