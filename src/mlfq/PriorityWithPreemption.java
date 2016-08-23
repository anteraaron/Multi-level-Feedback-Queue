package mlfq;

import java.util.ArrayList;

/**
 * A Class that implements the Priority With Preemption Scheduling Algorithm.
 * @author Anter Aaron Custodio, Marquee Mae Enriquez, Cristine Grace Cañedo
 */
class PriorityWithPreemption {

	/**
	 * Executes PWP
	 */
	public static void execute() {
		int current = 0;
		
		//find process with highest priority
		current = findPriority(MLFQ.queue4);
		
		while(MLFQ.queue4.size() > 0){
			
			current = findPriority(MLFQ.queue4);
			MLFQ.details += "\nAt time = " + MLFQ.time + "... \n";
			MLFQ.details += "   In Queue 5...\n";
			MLFQ.details += "     Using Priority With Preemption: \n";
			MLFQ.queue4.get(current).setDecBurstTime(MLFQ.queue4.get(current).getDecBurstTime() - 1);
			MLFQ.details += "     Process " + MLFQ.queue4.get(current).getId() + ": -1 Burst Time (" + MLFQ.queue4.get(current).getDecBurstTime() +" remaining.)\n";
			//add waiting time
			MLFQ.addWaitingTime();
			if(MLFQ.queue4.size() > 0)
				MLFQ.queue4.get(current).setWaitingTime(MLFQ.queue4.get(current).getWaitingTime() - 1);
			
			if(MLFQ.queue4.get(current).getDecBurstTime() == 0){
				MLFQ.finishedProcesses.add(MLFQ.queue4.get(current));
				MLFQ.queue4.remove(current);
			}
			
			//if the arrival time in the process arraylist is equals to the current time, add it to queue0
			for(int i = 0; i < MLFQ.processes.size(); i++){
				if(MLFQ.processes.get(i).getArrivalTime() == MLFQ.time){
					
					return;
				}
			}
			MLFQ.time ++;
			
			MLFQ.details += "At time = " + MLFQ.time + "\n";
		}
		//end of while
	}
	
	/**
	 * Finds the index of the highest priority process
	 * @param processes
	 * @return int
	 */
	private static int findPriority(ArrayList<Process> processes){
		int current = processes.get(0).getPriorityNum();
		int currentIndex = 0;

		for(int i = 0; i < processes.size(); i++){	
			if(current > processes.get(i).getPriorityNum()){
				current = processes.get(i).getPriorityNum();
				currentIndex = i;
				i = 0;
			}
		}	
		return currentIndex;
	}
}