package mlfq;

import java.util.ArrayList;
/**
 * Class that simulates the Shortest Remaining Time First Scheduling Algorithm
 * @author Anter Aaron Custodio, Marquee Mae Enriquez, Cristine Grace Cañedo
 *
 */
public class ShortestRemainingTimeFirst {
	/**
	 * Executes SRTF
	 */
	public static void execute() {
		int current = 0;
		
		//get process with smallest burst time
		current = findShortestJob(MLFQ.queue4);
		
		while(MLFQ.queue4.size() > 0){
			
			current = findShortestJob(MLFQ.queue4);
			MLFQ.details += "\nAt time = " + MLFQ.time + "... \n";
			MLFQ.details += "   In Queue 5...\n";
			MLFQ.details += "     Using Shortest Job First: \n";
			MLFQ.queue4.get(current).setDecBurstTime(MLFQ.queue4.get(current).getDecBurstTime() - 1);
			MLFQ.details += "     Process " + MLFQ.queue4.get(current).getId() + ": -1 Burst Time (" + MLFQ.queue4.get(current).getDecBurstTime() +" remaining.)\n";
			//add waiting time
			MLFQ.addWaitingTime();
			if(MLFQ.queue4.size() > 0)
				MLFQ.queue4.get(current).setWaitingTime(MLFQ.queue4.get(current).getWaitingTime() - 1);
			
			
			if(MLFQ.queue4.get(current).getDecBurstTime() == 0){
				MLFQ.details += "     Process " + MLFQ.queue4.get(current).getId() + ": Burst Time = 0... Finished! \n";
				MLFQ.finishedProcesses.add(MLFQ.queue4.get(current));
				MLFQ.queue4.remove(current);
			}
				
			
			
			//if the arrival time in the process arraylist is equals to the current time, add it to queue0
			for(int i = 0; i < MLFQ.processes.size(); i++){
				if(MLFQ.processes.get(i).getArrivalTime() == MLFQ.time){
					return;
				}
			}
			
			MLFQ.time++;
		}
	}
	
	
	/**
	 * Finds the shortest job
	 * @param processes
	 * @return int index of the shortest job
	 */
	private static int findShortestJob(ArrayList<Process> processes){
		int current = processes.get(0).getDecBurstTime();
		int currentIndex = 0;
		
		for(int i = 0; i < processes.size(); i++){	
			if(current > processes.get(i).getDecBurstTime()){
				current = processes.get(i).getDecBurstTime();
				currentIndex = i;
				i = 0;
			}
		}	
		return currentIndex;
	}

}
