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
					String[] parsedLine = line.split("\t"); // PI split this line up by tabs
				}
				lineNumber++;
				
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
