package mlfq;
/**
 * The Class of the Processes
 * @author Anter Aaron Custodio, Marquee Mae Enriquez, Cristine Grace Cañedo
 *
 */
public class Process
{
	private int id;
	private int arrivalTime;
	private int burstTime;
	private int priorityNum;
	private int waitingTime;
	private int turnaroundTime;
	private int endTime;
	private int DecBurstTime;
	/**
	 * A constructor when AT, BT, PN is given
	 * @param id processID
	 * @param arrivalTime arrivaltime of processes
	 * @param burstTime burst time of processes
	 * @param priorityNum priority of the process
	 */
	public Process(int id, int arrivalTime, int burstTime, int priorityNum)
	{
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.priorityNum = priorityNum;
	}
	/**
	 * A constructor when all properties are given
	 * @param id processID
	 * @param arrivalTime arrivaltime of processes
	 * @param burstTime burst time of processes
	 * @param priorityNum priority of the process
	 * @param waitingTime waiting time of the process
	 * @param turnaroundTime turn around time of the process
	 * @param endTime end time of the process
	 */
	public Process(int id, int arrivalTime, int burstTime, int priorityNum, int waitingTime, int turnaroundTime, int endTime)
	{
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.priorityNum = priorityNum;
		this.waitingTime = waitingTime;
		this.turnaroundTime = turnaroundTime;
		this.endTime = endTime;
	}
	
	/**
	 * Getters and setters
	 */
	public int getId(){ return id; }
	public void setId(int id){ this.id = id; }
	
	public int getArrivalTime(){ return arrivalTime; }
	public void setArrivalTime(int arrivalTime){ this.arrivalTime = arrivalTime; }
	
	public int getBurstTime(){ return burstTime; }
	public void setBurstTime(int burstTime){ this.burstTime = burstTime; }
	
	public int getDecBurstTime(){ return DecBurstTime; }
	public void setDecBurstTime(int DecBurstTime){ this.DecBurstTime = DecBurstTime; }
	
	public int getPriorityNum(){ return priorityNum; }
	public void setPriorityNum(int priorityNum){ this.priorityNum = priorityNum; }
	
	public int getWaitingTime(){ return waitingTime; }
	public void setWaitingTime(int waitingTime){ this.waitingTime = waitingTime; }
	
	public int getTurnaroundTime(){ return turnaroundTime; }
	public void setTurnaroundTime(int turnaroundTime){ this.turnaroundTime = turnaroundTime; }
	
	public int getEndTime(){ return endTime; }
	public void setEndTime(int endTime){ this.endTime = endTime; }
	
}
