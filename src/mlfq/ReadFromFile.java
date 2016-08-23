package mlfq;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
/**
 *Class that reads a file that contains processes
 *@author Anter Aaron Custodio, Marquee Mae Enriquez, Cristine Grace Cañedo
 */
public class ReadFromFile {
	
	/**
	 * Method that executes the reading of file
	 * @param filePath the absolute file path of the file
	 * @return ArrayList<Process> of processes
	 */
	public static ArrayList<Process> execute(String filePath) throws Exception{
		ArrayList<Process> processes = new ArrayList<Process>();//arraylist that will contain the processes
		String line = null; //variable that will store the data in the file line by line
		int pId = 1; //process ID
		String[] properties; //variable that will hold the splitted line
		
		//try catch
		try {
			@SuppressWarnings("resource")
			//create a buffer object of the file
			BufferedReader inputStream = new BufferedReader(new FileReader(filePath));
			
			//read file line by line
			while(true){
				line = inputStream.readLine();
				
				//end of file
				if(line == null || line.equals("~~~")){
					break;
				}
				properties = line.split("\\s+");
				if(properties.length > 3)
					processes.add(new Process(pId,Integer.parseInt(properties[1]), Integer.parseInt(properties[2]), Integer.parseInt(properties[0]), Integer.parseInt(properties[3]), Integer.parseInt(properties[4]), Integer.parseInt(properties[5])));
				else
					processes.add(new Process(pId,Integer.parseInt(properties[1]), Integer.parseInt(properties[2]), Integer.parseInt(properties[0])));
				pId++;
			}
			
			
		} catch (Exception e){
			e.printStackTrace();
			throw e;
		}
		//returns all the processes which are read from the file
		return processes;
	}
	

}
