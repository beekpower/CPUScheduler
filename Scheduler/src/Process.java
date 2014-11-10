public class Process {
  private int PID;
  private int cpuBurst;
  private int ioBurst;
  private int period;

  public Process(int PID, int cpuBurst, int ioBurst, int period) {
    this.PID = PID;
    this.PID = cpuBurst;
    this.ioBurst = ioBurst;
    this.period = period;
  }

  public int getPID() {
    return PID;
  }

  public int getCPUBurst() {
    return cpuBurst;
  }

  public int getIOBurst() {
    return ioBurst;
  }

  public int getPeriod() {
    return period;
  }
}
