public class RRScheduler extends Scheduler {

  public RRScheduler(ProcessList processList) {
    super(processList);
    preemptive = true;
    this.readableName = "RR";
  }

  @Override
	public void schedule() {
	  	processList.incrementWaitTimeForProcessesInReadyQueue(); //fix this skip
	    processList.decrementCurrentProcessesWaiting();


	    if (currentProcess != null) {
	    	currentProcess.processInstruction(cpu.cycleCount);
	    }

      processList.moveWaitingToReady();

	    if (processList.hasProcessInReadyQueue()) {
	        if (currentProcess == null) {
	      	  currentProcess = takeFirstProcessInReadyQueue();
	        } else {
	          if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
	        	  currentProcess.resetCounter();
	        	  currentProcess =  this.takeFirstProcessInReadyQueue();
	          } else if (currentProcess.getCounter() == processList.getQuantum()) {
	        	  currentProcess.resetCounter();
	        	  processList.addtoReadyQueue(currentProcess, this.cpu.cycleCount);
	        	  currentProcess = this.takeFirstProcessInReadyQueue();
	          }
	        }
	      }

	    else {
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

	private Process takeFirstProcessInReadyQueue() {
		cpu.finalReport.addProcess(currentProcess); // PI add the current process to the final report
		return processList.takeFirstProcessInReadyQueue();
	}
}
