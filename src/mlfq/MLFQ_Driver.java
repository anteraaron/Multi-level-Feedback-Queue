package mlfq;
import java.util.ArrayList;
/**
 * Driver for the Multi-level feedback queue
 * @author Anter Aaron Custodio, Marquee Mae Enriquez, Cristine Grace Cañedo
 */
@SuppressWarnings("unused")
public class MLFQ_Driver
{
	/**
	 * Main
	 */
    public static void main(String[] args){
    	/*
    	Process p1 = new Process(1,0,5,1);
    	Process p2 = new Process(2,1,3,2);
    	Process p3 = new Process(3,2,8,3);
    	Process p4 = new Process(4,3,6,4);
    	Process p5 = new Process(5,20,3,5);
    	
    	int[] quantum = new int[5];
    	quantum[0] = i;
    	quantum[1] = i;
    	quantum[2] = i;
    	quantum[3] = i;
    	quantum[4] = i;
    	*/
    	/*
    	Process p1 = new Process(1,0,31,1);
    	Process p2 = new Process(2,0,11,2);
    	Process p3 = new Process(3,0,21,3);
    	Process p4 = new Process(4,0,4,4);
    	Process p5 = new Process(5,0,18,5);
    	
    	int[] quantum = new int[5];
    	quantum[0] = 1;
    	quantum[1] = 1;
    	quantum[2] = 1;
    	quantum[3] = 1;
    	quantum[4] = 1;
    	
    	ArrayList<Process> list= new ArrayList<Process>();
    	list.add(p1);
    	list.add(p2);
    	list.add(p3);
    	list.add(p4);
    	list.add(p5);
    	int i = 3;
    	*/
    	//MLFQ test = new MLFQ(4, 5, quantum, list);
    	//.execute();
    	
    	/*
    	ArrayList<Process> test = ReadFromFile.execute("d:/Users/anterson/Desktop/test.txt");
    	Functions.print(test, 0, 0);
    	*/
    	
    	//run gui
    	new GUI();
    	
    }
}