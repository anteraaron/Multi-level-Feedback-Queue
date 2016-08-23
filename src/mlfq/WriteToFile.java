package mlfq;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Class that writes to file
 * @author Anter Aaron Custodio, Marquee Mae Enriquez, Cristine Grace Cañedo
 */
public class WriteToFile {
	/**
	 * Method that executes writing to a file
	 * @param content the content to be written
	 * @param file the file object to be created
	 */
	public static void execute(String content, File file) throws Exception{
		//remove extensions
		String filePath = file.getPath();
		if (filePath.indexOf(".") > 0)
		    filePath = filePath.substring(0, filePath.lastIndexOf("."));
		//create the text file
		FileWriter writer = new FileWriter(filePath + ".txt");
		try {
	        writer.write(content);
	    } catch (Exception e) {
	        throw e;
	    } finally {
	       if (writer != null) try { writer.close(); } catch (IOException ignore) {}
	    }
	}
}