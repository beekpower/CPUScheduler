
public class Simulator {
	/**
	 * PI the main method is what will be executed when the class is run
	 * @param args String[] of args passed along to the method
	 */
	public static void main (String[] args) {
        if(args.length > 0) { // PI check to see if we were actually passed the name of the data file + # of cycles to snapshot
        	if(args.length == 2) {
        		String dataFile = args[0];
            	int numberOfCyclesToSnapshot = Integer.parseInt(args[1]);
        	} else {
        		System.out.println("Wrong number of command line arguments");
        	}
        } else {
        	System.out.println("You must enter in the name of the datafile + number of cycles to snapshot as an argument!");
        }
    }
}
