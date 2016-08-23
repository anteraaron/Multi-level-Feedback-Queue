package mlfq;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A Class that implements the Round-Robin Scheduling Algorithm.
 * @author Anter Aaron Custodio, Marquee Mae Enriquez, Cristine Grace Cañedo
 *
 */
public class RoundRobin {
	/**
	 * Execute RR
	 * @param queue the queue to be manipulated
	 * @param queueNo the current queue number
	 * @param origQuantum the original quantum per queue
	 * @param NumOfRR number of round robins
	 */
	public static void execute(ArrayList<Process> queue, int queueNo, int origQuantum, int NumOfRR){
		MLFQ.details += "\nAt time = " + MLFQ.time + "... \n";
		MLFQ.details += "   In Queue "+ (queueNo + 1) +"...\n";
		if(queue.size() != 0){
			if(queue.get(0).getDecBurstTime() != 0){
				queue.get(0).setDecBurstTime(queue.get(0).getDecBurstTime() - 1);
				MLFQ.details += "     Using Round-Robin: \n";
				MLFQ.details += "     Process " + queue.get(0).getId() + ": -1 Burst Time (" + queue.get(0).getDecBurstTime() +" remaining.)\n";
				MLFQ.quantum[queueNo] --;
				MLFQ.details += "     Queue " + (queueNo + 1) + ": Quantum - 1 (" + MLFQ.quantum[queueNo] + " remaining.)\n";
				
				MLFQ.addWaitingTime();
				//subtract 1 waiting time from the process which is currently being processed
				if(queueNo == 0 && MLFQ.queue0.size() > 0)
					MLFQ.queue0.get(0).setWaitingTime(MLFQ.queue0.get(0).getWaitingTime() - 1);
				else if(queueNo == 1 && MLFQ.queue1.size() > 0)
					MLFQ.queue1.get(0).setWaitingTime(MLFQ.queue1.get(0).getWaitingTime() - 1);
				else if(queueNo == 2 && MLFQ.queue2.size() > 0)
					MLFQ.queue2.get(0).setWaitingTime(MLFQ.queue2.get(0).getWaitingTime() - 1);
				else if(queueNo == 3 && MLFQ.queue3.size() > 0)
					MLFQ.queue3.get(0).setWaitingTime(MLFQ.queue3.get(0).getWaitingTime() - 1);
				else if(queueNo == 4 && MLFQ.queue4.size() > 0)
					MLFQ.queue4.get(0).setWaitingTime(MLFQ.queue4.get(0).getWaitingTime() - 1);
				
				//if burst time is 0
				if(queue.get(0).getDecBurstTime() == 0){
					MLFQ.details += "     Process " + queue.get(0).getId() + ": Burst Time = 0... Finished! \n";
					MLFQ.finishedProcesses.add(queue.get(0));
					queue.remove(0);
					MLFQ.quantum[queueNo] = origQuantum;
				}
				if(queue.size() != 0){
					//if quantum is 0, move the process to the next queue
					if (MLFQ.quantum[queueNo] == 0){
						MLFQ.details += "     Quantum = 0, switch process in same Queue...\n";
						switch(queueNo){
							case 0:
								if(NumOfRR > 1){
									MLFQ.queue1.add(queue.get(0));
									MLFQ.details += "   Move Process " + queue.get(0).getId() + " to Queue 2\n";
								}else{
									MLFQ.queue4.add(queue.get(0));
									MLFQ.details += "   Move Process " + queue.get(0).getId() + " to Queue 5\n";
								}
								break;
							case 1:
								if(NumOfRR > 2){
									MLFQ.queue2.add(queue.get(0));
									MLFQ.details += "   Move Process " + queue.get(0).getId() + " to Queue 3\n";
								}else{
									MLFQ.queue4.add(queue.get(0));
									MLFQ.details += "   Move Process " + queue.get(0).getId() + " to Queue 5\n";
								}
								break;
							case 2:
								if(NumOfRR > 3){
									MLFQ.queue3.add(queue.get(0));
									MLFQ.details += "   Move Process " + queue.get(0).getId() + " to Queue 4\n";
								}else{
									MLFQ.queue4.add(queue.get(0));
									MLFQ.details += "   Move Process " + queue.get(0).getId() + " to Queue 4\n";
								}
								break;
							case 3:
								MLFQ.queue4.add(queue.get(0));
								MLFQ.details += "   Move Process " + queue.get(0).getId() + " to Queue 5\n";
								break;
							case 4:
								Collections.rotate(queue, -1);
								break;
							default:
								break;
						}
						//remove it from current queue
						if(queueNo != 4)
							queue.remove(0);
						//return the original quantum
						MLFQ.quantum[queueNo] = origQuantum;
					}
				}
			}else{
				MLFQ.finishedProcesses.add(queue.get(0));
			}
		}else{
			return;
		}
	}
	
}


