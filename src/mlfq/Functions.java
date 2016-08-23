package mlfq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
/**
 * Class that contains different utilities for debugging purposes
 * @author Anter Aaron Custodio, Marquee Mae Enriquez, Cristine Grace Cañedo
 *
 */
public class Functions {

	/**
	 * Sorts the processes by processID
	 * @param process The process to be sorted
	 * @return sorted process
	 */
	public static ArrayList<Process> sortProcessId(ArrayList<Process> process){
		Collections.sort(process, new MyComparator());
		return process;
	}
	
	/**
	 * Sorts the processes by arrival time
	 * @param process The process to be sorted
	 * @return sorted process
	 */
	public static ArrayList<Process> sortArrivalTime(ArrayList<Process> process){
		Collections.sort(process, new MyComparator2());
		return process;
	}
	
	/**
	 * Prints the table of processes.
	 * @param processes processes to be printed.
	 */
	public static void print(ArrayList<Process> processes, int firstProcessTime, int lastProcessTime){
		processes = sortProcessId(processes);
		double avgWaitTime = 0;
		double avgTurnAroundTime = 0;
		
		
		System.out.println("+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+");
		System.out.println("|    Process ID   |  Arrival Time   |    Burst Time   |     Priority    |   Waiting Time  | Turn-Around Time|     End Time    |");
		System.out.println("+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+");		
		//loop that prints cells and align them to center
			
	    for(Process process : processes){
	    	System.out.print(String.format("%s%17s%s","|",center(process.getId()),"|"));
	    	System.out.print(String.format("%17s%s",center(process.getArrivalTime()),"|"));
	    	System.out.print(String.format("%17s%s",center(process.getDecBurstTime()),"|"));
	    	System.out.print(String.format("%17s%s",center(process.getPriorityNum()),"|"));
	    	System.out.print(String.format("%17s%s",center(process.getWaitingTime()),"|"));
	    	System.out.print(String.format("%17s%s",center(process.getTurnaroundTime()),"|"));
	    	System.out.print(String.format("%17s%s",center(process.getEndTime()),"|\n"));
	    	//get the total waiting time and turn around time
	    	avgWaitTime += process.getWaitingTime();
	    	avgTurnAroundTime += process.getTurnaroundTime();
	    }
	    //divide by # of processes
	    avgWaitTime = (double)Math.round((avgWaitTime/processes.size())*1000)/1000;
	    avgTurnAroundTime =  (double)Math.round((avgTurnAroundTime/processes.size()) * 1000)/1000;
	    
	    System.out.println("+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+");
	    System.out.println("|     Average     |       --        |        --       |        --       |" + String.format("%17s", center(avgWaitTime)) + "|" + String.format("%17s", center(avgTurnAroundTime)) + "|        --       |");
	    System.out.println("+-----------------+-----------------+-----------------------------------+-----------------+-----------------+-----------------+");
	    System.out.println(String.format("%-108s%s","|   Throughput:   | " + processes.size() + "/" + (lastProcessTime - firstProcessTime) + 
				" (" + (double)processes.size()/(lastProcessTime - firstProcessTime) + ")", "                  |"));
	    System.out.println("+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+");
	}
	
	/**
	 * Centers the object passed
	 * @param obj the object to be centered
	 * @return centered version of the object
	 */
	private static String center(Object obj){
		String str = obj.toString();
		int width = 17;	
		int padSize = width - str.length();
		int padStart = str.length() + padSize / 2; 
		str = String.format("%" + padStart + "s", str);
		str = String.format("%-" + width  + "s", str);
		
		return str;
	}
	

}

/**
 *Comparator class that sorts arraylist by processId
 */
class MyComparator implements Comparator<Process> {
    @Override
    public int compare(Process first, Process second) {
    	if (first.getId() > second.getId()){
            return 1;
        }else if (first.getId() < second.getId()){
            return -1;
        }
        return 0;    
    } 
}

/**
 *Comparator class that sorts arraylist by arrival time
 */
class MyComparator2 implements Comparator<Process> {
    @Override
    public int compare(Process first, Process second) {
    	if (first.getArrivalTime() > second.getArrivalTime()){
            return 1;
        }else if (first.getArrivalTime() < second.getArrivalTime()){
            return -1;
        }
        return 0;    
    } 

}


