package mlfq;
/**
 * Class that simulates First Come First Serve Scheduling Algorithm
 * @author Anter Aaron Custodio, Marquee Mae Enriquez, Cristine Grace Cañedo
 *
 */
public class FirstComeFirstServe {
	/**
	 * Executes FCFS
	 */
	public static void execute(){
	
		while(MLFQ.queue4.size() > 0){
			//save details
			MLFQ.details += "\nAt time = " + MLFQ.time + "... \n";
			MLFQ.details += "   In Queue 5...\n";
			MLFQ.details += "     Using First Come First Serve: \n";
			MLFQ.queue4.get(0).setDecBurstTime(MLFQ.queue4.get(0).getDecBurstTime() - 1);
			MLFQ.details += "     Process " + MLFQ.queue4.get(0).getId() + ": -1 Burst Time (" + MLFQ.queue4.get(0).getDecBurstTime() +" remaining.)\n";
			//add waiting time
			MLFQ.addWaitingTime();
			if(MLFQ.queue4.size() > 0)
				MLFQ.queue4.get(0).setWaitingTime(MLFQ.queue4.get(0).getWaitingTime() - 1);
			
			//if burst time is zero, remove from queue
			if(MLFQ.queue4.get(0).getDecBurstTime() == 0){
				MLFQ.details += "     Process " + MLFQ.queue4.get(0).getId() + ": Burst Time = 0... Finished! \n";
				MLFQ.finishedProcesses.add(MLFQ.queue4.get(0));
				MLFQ.queue4.remove(0);
			}
			
			
			//if the arrival time in the process arraylist is equals to the current time, add it to queue0
			for(int i = 0; i < MLFQ.processes.size(); i++){
				if(MLFQ.processes.get(i).getArrivalTime() == MLFQ.time){
					MLFQ.details += "   Process " + MLFQ.processes.get(i).getId() + " arrived. \n";
					return;
				}
			}
			
			MLFQ.time++;
			MLFQ.details += "At time = " + MLFQ.time + "\n";
			
		}

	}
	
}
