package mlfq;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
/**
 * Class that simulates Multi-Level Feedback Queue
 * @author Anter Aaron Custodio, Marquee Mae Enriquez, Cristine Grace Cañedo
 *
 */
public class MLFQ {

	private int numOfRR= 0; //Number of round robins
	private int schedulingAlgorithm = 0; //Indicates what sorting algorithm is selected
	public static int time = 0;
	public static boolean bool;
	public static String details; //processing details
	public static int[] quantum = new int[5]; //quantum for each scheduling algorithm quantum[0] is for queue0 , quantum[1] is for queue1 so on...
	public static ArrayList<Process> finishedProcesses= new ArrayList<Process>(); //finishedprocess are inserted here (Burst time == 0)
	public static ArrayList<Process> processes = new ArrayList<Process>(); //list of initial processes that were inserted by the user
	public static ArrayList<Process> queue0 = new ArrayList<Process>(); //first queue
	public static ArrayList<Process> queue1 = new ArrayList<Process>(); //second queue
	public static ArrayList<Process> queue2 = new ArrayList<Process>(); //third queue
	public static ArrayList<Process> queue3 = new ArrayList<Process>(); //fourth queue
	public static ArrayList<Process> queue4 = new ArrayList<Process>(); //queue, which the scheduling algorithm selected by the user, will use

	/**
	 * Constructor
	 * @param numOfRR # of scheduling algorithm
	 * @param schedulingAlgorithm determines which scheduling algorithm is used
	 * @param quantum time quantum
	 * @param processes processes inputted by the user
	 */
	@SuppressWarnings("static-access")
	public MLFQ(int numOfRR, int schedulingAlgorithm, int[] quantum, ArrayList<Process> processes){
		this.numOfRR= numOfRR;
		this.schedulingAlgorithm = schedulingAlgorithm;
		this.quantum = quantum;
		this.processes.clear();
		this.processes = processes;
		this.time = 0;
		this.finishedProcesses.clear();
		this.queue0.clear();
		this.queue1.clear();
		this.queue2.clear();
		this.queue3.clear();
		this.queue4.clear();
		this.details = "";
		this.bool = false;
	}
	
	@SuppressWarnings("static-access")
	public ArrayList<Process> getBurstTime(){
		return this.processes;
	}
	/**
	 * Perform MLFQ
	 */
	public void execute(){	
		time = 0; // time per iteration (+1 per iteration)
		
		int[] origQuantum = (int[])quantum.clone(); //make a copy of the original quantums so we can manipulate the original quantums
		
		
		//copy burst time for future use of decreasing burst time
		for(int i=0;i<processes.size();++i){
			processes.get(i).setDecBurstTime(processes.get(i).getBurstTime());
		}
		
		while(processes.size() != finishedProcesses.size()){
			if(queue0.size() != 0){//if there are processes in queue0 perform a roundrobin
				RoundRobin.execute(queue0, 0, origQuantum[0], numOfRR);
			}else if(queue1.size() != 0){//if there are processes in queue1 perform a roundrobin
				RoundRobin.execute(queue1, 1, origQuantum[1], numOfRR);
			}else if(queue2.size() != 0){//if there are processes in queue2 perform a roundrobin
				RoundRobin.execute(queue2, 2, origQuantum[2], numOfRR);
			}else if(queue3.size() != 0){//if there are processes in queue3 perform a roundrobin
				RoundRobin.execute(queue3, 3, origQuantum[3], numOfRR);
			}else if(queue4.size() != 0){ //if there are processes in queue4, perform the selected scheduling algorithm
				if(schedulingAlgorithm == 0){
					FirstComeFirstServe.execute();
				}else if(schedulingAlgorithm == 1){
					ShortestJobFirst.execute();
				}else if(schedulingAlgorithm == 2){
					ShortestRemainingTimeFirst.execute();
				}else if(schedulingAlgorithm == 3){
					RoundRobin.execute(queue4, 4, origQuantum[4], numOfRR);
				}else if(schedulingAlgorithm == 4){
					PriorityWithoutPreemption.execute();
				}else if(schedulingAlgorithm == 5){
					PriorityWithPreemption.execute();
				}
				
				//...
			}
			
			//if the arrival time in the process arraylist is equals to the current time, add it to queue0
			for(int i = 0; i < processes.size(); i++){
				if(processes.get(i).getArrivalTime() == time){
					queue0.add(processes.get(i));
					details += "   Process " + processes.get(i).getId() + " arrived. \n";
				}
			}
			
			//iterate time
			time++;
		}
		
		//set evaluated processes' end time and turn around time
		for(int i = 0; i < finishedProcesses.size(); i++){
			finishedProcesses.get(i).setTurnaroundTime(finishedProcesses.get(i).getBurstTime()+finishedProcesses.get(i).getWaitingTime());
			finishedProcesses.get(i).setEndTime(finishedProcesses.get(i).getArrivalTime() + finishedProcesses.get(i).getTurnaroundTime());
		}
		
		Collections.sort(finishedProcesses, new IdComparator());
		//print all the queues (for debugging purposes)
		/*
		System.out.println("Queue0:");
		Functions.print(queue0, 0, 0);
		
		System.out.println("Queue1:");
		Functions.print(queue1, 0, 0);
		
		System.out.println("Queue2:");
		Functions.print(queue2, 0, 0);
		
		System.out.println("Queue3:");
		Functions.print(queue3, 0, 0);
		
		System.out.println("Queue4:");
		Functions.print(queue4, 0, 0);
		
		System.out.println("Final:");
		Functions.print(this.finishedProcesses, 0, 0);
		*/
	}
	
	/**
	 * Adds waiting time to other queues
	 */
	public static void addWaitingTime(){
		MLFQ.details += "     +1 wait time to Processes in Queue. \n";
		for(int i = 0; i < MLFQ.queue0.size(); i++){
			if(MLFQ.queue0.get(i).getArrivalTime() <= MLFQ.time){
				MLFQ.queue0.get(i).setWaitingTime(MLFQ.queue0.get(i).getWaitingTime() + 1);
			}
		}
		
		for(int i = 0; i < MLFQ.queue1.size(); i++){
			if(MLFQ.queue1.get(i).getArrivalTime() <= MLFQ.time){
				MLFQ.queue1.get(i).setWaitingTime(MLFQ.queue1.get(i).getWaitingTime() + 1);
			}
		}
		
		for(int i = 0; i < MLFQ.queue2.size(); i++){
			if(MLFQ.queue2.get(i).getArrivalTime() <= MLFQ.time){
				MLFQ.queue2.get(i).setWaitingTime(MLFQ.queue2.get(i).getWaitingTime() + 1);
			}
		}
		
		for(int i = 0; i < MLFQ.queue3.size(); i++){
			if(MLFQ.queue3.get(i).getArrivalTime() <= MLFQ.time){
				MLFQ.queue3.get(i).setWaitingTime(MLFQ.queue3.get(i).getWaitingTime() + 1);
			}
		}
		
		for(int i = 0; i < MLFQ.queue4.size(); i++){
			if(MLFQ.queue4.get(i).getArrivalTime() <= MLFQ.time){
				MLFQ.queue4.get(i).setWaitingTime(MLFQ.queue4.get(i).getWaitingTime() + 1);
			}
		}
	}
	
}
/**
 * Comparator class that sorts processes by ID
 */
class IdComparator implements Comparator<Process> {
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
