//Note: Make and interface for scheduler
public abstract class Scheduler {
  Process currentProcess;
  ProcessList processList;
  Boolean preemptive = false;
  public String readableName;
  public CPU cpu;

  // PI keep track of some stats
  public double throughput;
  public int turnAroundTime;
  public double waitTime;
  public double cpuUtilization;
  public int deadlineViolations;

  public Scheduler(ProcessList processList) {
    this.processList = processList;
    //Move all the processes into the ready queue to start;
    processList.reinitialize();
  }


  public Process getCurrentProcess() {
    return currentProcess;
  }

  /**
   * PI this is used to update the reference o the CPU
   */
  public void updateCPU(CPU cpu) {
	  this.cpu = cpu;
  }

  public abstract void schedule();
  
  public double getCalculatedRating() {
	  return (double) this.throughput + ((double)1 / (double)this.turnAroundTime) + ((double)1/this.waitTime) + this.cpuUtilization + this.deadlineViolations;
  }
}
