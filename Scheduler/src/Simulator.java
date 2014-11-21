
public class Simulator {
	private static ProcessList processList; // PI handle to the ProcessList object
	private static int snapshotInterval; // PI handle to the snapshot interval

	/**
	 * PI the main method is what will be executed when the class is run
	 * @param args String[] of args passed along to the method
	 */
	public static void main (String[] args) {
    if(args.length > 0) { // PI check to see if we were actually passed the name of the data file + # of cycles to snapshot
    	if(args.length == 2) { // PI check to see if we got the right # of args
    	  	// PI parse the args
    		String dataFile = args[0];
        	snapshotInterval = Integer.parseInt(args[1]); // PI parse the snapshot interval from the CL arg
        	processList = new ProcessList(dataFile, snapshotInterval); // PI create a new PList
    	} else {
    		System.out.println("Wrong number of command line arguments");
    	}
    } else {
    	System.out.println("You must enter in the name of the datafile + number of cycles to snapshot as an argument!");
    }
  	//Create an FCFSScheduler and execute it
  	Scheduler fcfsScheduler = new FCFSScheduler(processList);
  	CPU fcfsCPU = new CPU(fcfsScheduler, snapshotInterval);
  	fcfsScheduler.updateCPU(fcfsCPU);
  	fcfsCPU.execute();

		//Create an SJF Scheduler and execute it
		Scheduler sjfScheduler = new SJFScheduler(processList);
		CPU sjfCPU = new CPU(sjfScheduler, snapshotInterval);
		sjfScheduler.updateCPU(sjfCPU);
		sjfCPU.execute();

		// //Create a RR Scheduler and execute it
		Scheduler rrScheduler = new RRScheduler(processList);
		CPU rrCPU = new CPU(rrScheduler, snapshotInterval);
		rrScheduler.updateCPU(rrCPU);
		rrCPU.execute();

		//Create an SJF Scheduler and execute it
		Scheduler sjrScheduler = new SJRScheduler(processList);
		CPU sjrCPU = new CPU(sjrScheduler, snapshotInterval);
		sjrScheduler.updateCPU(sjrCPU);
		sjrCPU.execute();

		//Create a PS Scheduler and execute it
		Scheduler psScheduler = new PSScheduler(processList);
		CPU psCPU = new CPU(psScheduler, snapshotInterval);
		psScheduler.updateCPU(psCPU);
		psCPU.execute();
		
		//Create a PRM Scheduler and execute it
		Scheduler prmScheduler = new PRMScheduler(processList);
		CPU prmCPU = new CPU(prmScheduler, snapshotInterval);
		prmScheduler.updateCPU(prmCPU);
		prmCPU.execute();
		
		//Create a EDF Scheduler and execute it
		Scheduler edfScheduler = new EDFScheduler(processList);
		CPU edfCPU = new CPU(edfScheduler, snapshotInterval);
		edfScheduler.updateCPU(edfCPU);
		edfCPU.execute();
  }
}
