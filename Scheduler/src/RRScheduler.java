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
	      currentProcess.processInstruction();
	    }

	    if (processList.hasProcessInReadyQueue()) {
	        if (currentProcess == null) {
	      	  currentProcess = takeFirstProcessInReadyQueue();
	        } else {
	          if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
	            currentProcess.resetCounter();
	            currentProcess =  this.takeFirstProcessInReadyQueue();
	          } else if (currentProcess.getCounter() == processList.getQuantum()) {
	            currentProcess.resetCounter();
	            processList.addtoReadyQueue(currentProcess);
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

  public Process getNextProcess() {
    if (processList.hasProcessInReadyQueue()) {
      if (currentProcess == null) {
    	  return this.takeFirstProcessInReadyQueue();
      } else {
        if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
          currentProcess.resetCounter();
          return this.takeFirstProcessInReadyQueue();
        } else if (currentProcess.getCounter() == processList.getQuantum()) {
          currentProcess.resetCounter();
          processList.addtoReadyQueue(currentProcess);
          return this.takeFirstProcessInReadyQueue();
        } else {
          return currentProcess;
        }
      }
    } else {
      //Set the current process to null. The CPU will see this and enter an idle state
      if (currentProcess != null) {
        if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
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

	private Process takeFirstProcessInReadyQueue() {
		cpu.finalReport.addProcess(currentProcess); // PI add the current process to the final report
		return processList.takeFirstProcessInReadyQueue();
	}
}
