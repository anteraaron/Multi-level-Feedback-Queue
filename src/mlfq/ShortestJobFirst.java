package mlfq;

import java.util.ArrayList;
import java.util.Collections;
/**
 * Class that simulates Shortest Job First Scheduling Algorithm
 * @author Anter Aaron Custodio, Marquee Mae Enriquez, Cristine Grace Cañedo
 *
 */
public class ShortestJobFirst {
	/**
	 * Executes SJF
	 */
	public static void execute() {
		int current = 0;
		
		//find process with highest priority
		if(!MLFQ.bool){
			current = findShortestJob(MLFQ.queue4);
			Collections.swap(MLFQ.queue4, current, 0);
			MLFQ.bool = true;
		}

		while(MLFQ.queue4.size() > 0){
			current = findShortestJob(MLFQ.queue4);
			MLFQ.details += "\nAt time = " + MLFQ.time + "... \n";
			MLFQ.details += "   In Queue 5...\n";
			MLFQ.details += "     Using Shortest Job First: \n";
			MLFQ.queue4.get(0).setDecBurstTime(MLFQ.queue4.get(0).getDecBurstTime() - 1);
			MLFQ.details += "     Process " + MLFQ.queue4.get(0).getId() + ": -1 Burst Time (" + MLFQ.queue4.get(0).getDecBurstTime() +" remaining.)\n";
			//add waiting time
			MLFQ.addWaitingTime();
			if(MLFQ.queue4.size() > 0)
				MLFQ.queue4.get(0).setWaitingTime(MLFQ.queue4.get(0).getWaitingTime() - 1);
			
			//if burst time is zero
			if(MLFQ.queue4.get(0).getDecBurstTime() == 0){
				MLFQ.details += "     Process " + MLFQ.queue4.get(0).getId() + ": Burst Time = 0... Finished! \n";
				MLFQ.finishedProcesses.add(MLFQ.queue4.get(0));
				MLFQ.queue4.remove(0);
				
				//find process with highest priority
				if(MLFQ.queue4.size() > 0){
					current = findShortestJob(MLFQ.queue4);
					Collections.swap(MLFQ.queue4, current, 0);
				}
				
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
