import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class for the process list that retains all the processes loaded in from the data file
 * @author phil
 *
 */
public class ProcessList {
	private ArrayList<Process> processes;
	private int numberProcesses;
	private int quantum;
	
	public ProcessList(String dataFile, int numberOfCyclesToSnapshot) {
		processes = new ArrayList<Process>(); // PI instantiate the array list of Processes
		try {
			File file = new File(dataFile);		// PI Let's create a file object with a path to the data file
			FileReader fileReader = new FileReader(file);	// PI create a new file reader
			BufferedReader bufferedReader = new BufferedReader(fileReader); // PI use a buffered reader to read the file
			String line; // PI keep a reference to the line
			int lineNumber = 1; // PI keep track of the line numbers
			while ((line = bufferedReader.readLine()) != null) {
				if(lineNumber == 1) { // PI This line has the total # processes
					String[] parsedLine = line.split("\\s+"); // PI split this line up by spaces
					numberProcesses = Integer.parseInt(parsedLine[1]); // PI assign the number processes instance field to the value in this line
				} else if(lineNumber == 2) {
					String[] parsedLine = line.split("\\s+"); // PI split this line up by spaces
					quantum = Integer.parseInt(parsedLine[1]); // PI assign the quantum instance field to the value in this line
				} else if(lineNumber > 3) { // PI otherwise this is just a normal line containing a process
					String[] parsedLine = line.split("\\s+"); // PI split this line up by tabs
					Integer[] lineParameters = new Integer[5]; // PI create an array to keep track of all the integer line parameters for this particular process
					int currentIndex = 0;
					for(String element: parsedLine) { // PI loop through each element in the array
						lineParameters[currentIndex] = Integer.parseInt(element); // PI stuff the integer (of this param) into the line params array at our current index
						currentIndex++; // PI increment the current index
					}
					Process process = new Process(lineParameters[0], lineParameters[1], lineParameters[2], lineParameters[3], lineParameters[4]); // PI Create a new process based on the lineParams array
					System.out.println("Adding process: "+process.toString());
					processes.add(process); // PI add this process to our master list of processes
				}
				lineNumber++; // PI increment our line number so we can parse the next line (if not empty)
				
			}
			fileReader.close(); // PI close out the file reader
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
