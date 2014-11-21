import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Simulator class which drives all the simulations for the different schedulers
 * @author Phillip Igoe & Nick Van Beek
 *
 */
public class Simulator {
	private static ProcessList processList; // PI handle to the ProcessList object
	private static int snapshotInterval; // PI handle to the snapshot interval

	/**
	 * PI the main method is what will be executed when the class is run
	 * @param args String[] of args passed along to the method
	 */
	public static void main (String[] args) {
    if(args.length > 0) { // PI check to see if we were actually passed the name of the data file + # of cycles to snapshot
    	if(args.length >= 1) { // PI check to see if we got the right # of args
    	  	// PI parse the args
    		String dataFile = args[0];
    		if(args.length == 2) {
    			snapshotInterval = Integer.parseInt(args[1]); // PI parse the snapshot interval from the CL arg
    		} else {
    			snapshotInterval = 10; // PI else default to 10
    		}
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
		//psCPU.execute();

		//Create a PRM Scheduler and execute it
		Scheduler prmScheduler = new PRMScheduler(processList);
		CPU prmCPU = new CPU(prmScheduler, snapshotInterval);
		prmScheduler.updateCPU(prmCPU);
		prmCPU.execute();

		//Create a EDF Scheduler and execute it
		Scheduler edfScheduler = new EDFScheduler(processList);
		CPU edfCPU = new CPU(edfScheduler, snapshotInterval);
		edfScheduler.updateCPU(edfCPU);
		//edfCPU.execute();

		// PI let's do some ranking for the different schedulers
		ArrayList<Scheduler> firstStats = new ArrayList<Scheduler>();
		firstStats.add(fcfsScheduler);
		firstStats.add(sjfScheduler);
		firstStats.add(rrScheduler);
		firstStats.add(sjrScheduler);
		firstStats.add(psScheduler);

		// PI let's do ranking for the real time schedulers
		ArrayList<Scheduler> secondStats = new ArrayList<Scheduler>();
		secondStats.add(prmScheduler);
		secondStats.add(edfScheduler);

		// PI now let's print out some rankings
		try {
			FileWriter fileWriter = new FileWriter("FinalReport.txt", true); // PI make a new file writer
			// PI now output all the junk needed
			fileWriter.write("==================================================\n");
			fileWriter.write("\t\tScheduling Algorithm Placement\n");
			fileWriter.write("==================================================\n");
			fileWriter.write("Standard Schedulers:\n");
			fileWriter.write("1. "+findLargestCalculatedRating(firstStats).readableName+"\n");
			fileWriter.write("2. "+findLargestCalculatedRating(firstStats).readableName+"\n");
			fileWriter.write("3. "+findLargestCalculatedRating(firstStats).readableName+"\n");
			fileWriter.write("4. "+findLargestCalculatedRating(firstStats).readableName+"\n");
			fileWriter.write("5. "+findLargestCalculatedRating(firstStats).readableName+"\n");
			fileWriter.write("\n");
			fileWriter.write("Real Time Schedulers:\n");
			fileWriter.write("1. "+findLargestCalculatedRating(secondStats).readableName+"\n");
			fileWriter.write("2. "+findLargestCalculatedRating(secondStats).readableName+"\n");
			fileWriter.write("\n");
			fileWriter.close(); // PI close the file writer
		} catch (IOException e) {

		}
  }

	/**
	 * PI finds the largest calculated rating in the arraylist, removes it, and returns it
	 * @param schedulers list of schedulers to choose from
	 * @return scheduler with the largest calculated rating in the list
	 */
	private static Scheduler findLargestCalculatedRating(ArrayList<Scheduler> schedulers) {
		Scheduler largest = schedulers.get(0); // PI set the first element as the largest
		int index = 0;
		// PI loop through all schedulers and test em
		for(int i = 0; i < schedulers.size(); i++) {
			Scheduler scheduler = schedulers.get(i);
			if(scheduler.getCalculatedRating() > largest.getCalculatedRating()) { // PI we found a larger one
				largest = scheduler;
				index = i;
			}
		}
		schedulers.remove(index); // PI be sure to remove it from the array list
		return largest; // PI now return the largest
	}
}
