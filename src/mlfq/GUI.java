package mlfq;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *Graphical User Interface of MultiLevel Feedback Queue
 */
public class GUI extends JFrame implements ActionListener {

	private JTabbedPane leftTabbedPane, rightTabbedPane;
	private JPanel mlfqTab, detailsTab, helpTab, panel;
	private JToolBar toolbar;
	private JButton save, saveAs, open, execute, addTab;
	private ImageIcon i;
	private JComboBox<Object> noOfQueues, schedulingAlgoLabel;
	private JFileChooser chooser;
	private JLabel noOfQueuesLabel;
	private JTextField[] queue;
	private JLabel[] queueLabel;
	private SpinnerModel[] spinnerModel;
	private JSpinner[] spinner;
	private FileTypeFilter filter;
	private MLFQ mlfq;
	
	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 */
	public GUI(){
		super("GUI");
		
		changeLookAndFeel(); //change the look and feel of the GUI
		createSideBarLeft(); //creates left side bar
		createSideBarRight(); //creates right side bar
		createToolBar(); //creates toolbar
		setLayout(null);
		
		setTitle("Multi-level Feedback Queue");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 550);
        getContentPane().setBackground(new Color(235, 235, 235));
        setLocationRelativeTo(null); // center on screen
        setVisible(true);
         
        //constructor for different variables
        chooser = new JFileChooser(System.getProperty("user.dir"));
	}
	
	/**
	 * Changes the look and feel of the whole GUI
	 */
	private void changeLookAndFeel(){
		 //Set the look and feel to users OS.
        try {
        	UIManager.setLookAndFeel(
        			"com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Creates the left panel of the GUI
	 */
	private void createSideBarLeft(){
		//create container for tabs
		leftTabbedPane = new JTabbedPane();
		
		//create the tabs
		mlfqTab = new JPanel();
		
		//Insert Queues here
		insertQueues();
    		
		//create the contents of the tab
		detailsTab = new JPanel();
		helpTab = new JPanel();
		
		leftTabbedPane.addTab("MLFQ", mlfqTab);
		leftTabbedPane.addTab("Process Details", detailsTab);
		leftTabbedPane.addTab("Help", helpTab);
		leftTabbedPane.setLocation(0, 28);
		leftTabbedPane.setSize(new Dimension(250, 496));
		this.add(leftTabbedPane);
		
		//insert help details here
		insertHelpTab();
	}
	
	/**
	 * Creates the queues on the left panel of the GUI
	 */
	private void insertQueues() {
		int count = 0, counter  = 1;
		Integer[] num = {2,3,4,5};
		
		panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		//label: Number of queues
		noOfQueuesLabel = new JLabel("Number of queues:");
		c.weightx = 0.3;
		c.gridx = 2;
		c.gridy = 0;
		panel.add(noOfQueuesLabel, c);
		
		//indicates number of queues
		noOfQueues = new JComboBox<Object>(num);
		noOfQueues.setSelectedIndex(0);
		noOfQueues.setPreferredSize(new Dimension(70,noOfQueues.getPreferredSize().height));
		noOfQueues.setMinimumSize(new Dimension(70,noOfQueues.getPreferredSize().height));
		noOfQueues.addActionListener(this);
		c = new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0,0,0,0), 0, 0);
		c.weightx = 0.5;
		c.gridx = 4;
		c.gridy = 0;
		panel.add(noOfQueues, c);
		
		//label: Queue
		JLabel queueHeader = new JLabel("               Queue");
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 1;
		panel.add(queueHeader, c);
		
		//label: time slice
		JLabel timeQuantumHeader = new JLabel("Time Slice");
		c.weightx = 0.5;
		c.gridx = 4;
		c.gridy = 1;
		panel.add(timeQuantumHeader, c);
		
		queue = new JTextField[5];
    	queueLabel = new JLabel[5];
    	spinnerModel = new SpinnerNumberModel[5];
    	spinner = new JSpinner[5];
    	
    	int grid = 2;
    	String[] schedulingAlgo = {"First Come First Serve","SJF(without preemption)",
    							   "SJF(with preemption)","Round Robin","Priority(without preemption)",
    							   "Priority(with preemption)"};
    	while(count != 5){
    		//label: Q1 - Q5
    		queueLabel[count] = new JLabel("Q" + counter);
        	queueLabel[count].setLabelFor(queue[count]);
        	c.gridx = 1;
    	    c.gridy = grid;
        	panel.add(queueLabel[count], c);
        	
    		if(count != 4){
	        	//scheduling algorithm for Q1 - Q(n-1)
	        	queue[count] = new JTextField("Round Robin");
	        	queue[count].setEditable(false);
	        	queue[count].setOpaque(false);
	        	queue[count].setColumns(12);
	        	queue[count].setPreferredSize(new Dimension(100, 25));
	        	c.gridx = 2;
	    	    c.gridy = grid;
	    	    panel.add(queue[count], c);
    		}
    		else{
    			//scheduling algorithm for Q1 - Qn
    			schedulingAlgoLabel = new JComboBox<Object>(schedulingAlgo);
    	    	schedulingAlgoLabel.setPreferredSize(new Dimension(100,25));
    	    	schedulingAlgoLabel.addActionListener(this);
    			c.fill = GridBagConstraints.HORIZONTAL;
    	    	c.gridx = 2;
    		    c.gridy = grid;
    	        panel.add(schedulingAlgoLabel, c);
    			
    		}
    		
    	    //time slice for Q1 - Qn
    	    spinnerModel[count] = new SpinnerNumberModel (1, /*initial value*/ 1, /*min*/100, /*max*/1);
    	    spinner[count] = new JSpinner(spinnerModel[count]);
    	    spinner[count].setPreferredSize(new Dimension(70,25));
    	    spinner[count].setMinimumSize(new Dimension(70,25));
    	    c = new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0,0,0,0), 0, 0);
    	    c.gridx = 4;
    	    c.gridy = grid;
    	    panel.add(spinner[count], c);
        	
        	count++;
        	counter++;
        	grid++;
        }

    	spinner[4].setEnabled(false);
    	
    	//add execute button
    	i = new ImageIcon(getClass().getResource("/images/execute.png"));
    	execute = new JButton(i);
    	i = new ImageIcon(getClass().getResource("/images/execute2.png"));
    	execute.setPressedIcon(i);
		execute.setOpaque(false);
		execute.setContentAreaFilled(false);
		execute.setBorderPainted(false);
		execute.setPreferredSize(new Dimension(65,65));
		execute.setFocusable(false);
		execute.setCursor(new Cursor(Cursor.HAND_CURSOR));
		execute.setToolTipText("Execute");
		execute.addActionListener(this);
    	c = new GridBagConstraints();
    	c.insets = new Insets(20, 55, 0, 0);
    	c.gridx = 2;
    	c.gridy = 8;
    	panel.add(execute, c);
    	
		mlfqTab.add(panel);
		enableDisableQueue(2);
		
	}
	
	/**
	 * Creates the help tab on the left panel of the GUI
	 */
	private void insertHelpTab() {
		
		String disclamer =  "Disclaimer:\n This program expects the user to have a\n"+
							"background knowledge on what a Multilevel Feedback Queue\n" +
							"as well as the various scheduling algorithms involved.\n" +
							"These knowledge are important for the user to appreciate\n" +
							"the functionalities of the program.\n\n";
		
		String overview = "Overview:\n This program implements a multilevel\n" +
					  	  "feedback queue to calculate the waiting time, turnaround\n" +
					  	  "time, end time and throughput of a set of processes given\n" +
					  	  "its priority number, arrival time and burst time. This\n" +
					  	  "program reads a file  to acquire required process details,\n" +
					  	  "generates a summarized table of results and enables a user\n" +
					  	  "to save the results to another file.\n\n";

		String what = "What?\n This program uses the following scheduling algorithms:\n" +
					  "Round Robin, First Come First Served, Shortest Job First\n" +
					  "(with and without preemption), and Priority Scheduling (with and\n" +
					  "without preemption).\n\n";

		String how = "How?\n " +
					 "1) Open your file by clicking on the folder button on the menu bar.\n " +
					 "2) Once your file is selected, the contents of the file will appear\n" +
					 "   in a tabular form.\n" +
					 "3) Select the scheduling algorithms you want to use \n" +
					 "   Note: Maximum of 5 scheduling algorithms only, the 1st 4 are Round\n" +
					 "   Robin scheduling algorithm and the last can either be FCFS, SJF,\n" +
					 "   SRT, PWOP, PWP or RR itself.\n" +
					 "4) Click on the blue wrench button to start the MLFQ calculation\n" +
					 "   Note: The button turns red indicating that the answers are being\n" +
					 "   calculated, it will turn blue again once the computations are done\n\n";

		String niceToKnow = "Nice to know!\n" +
							"- You can save (overwrite your original file) or save as\n" +
							"  (save to a new file) your MLFQ results by clicking on either of the\n" +
							"  diskette buttons on the menu bar.\n" +
							"  Note: Make sure that a process has just been executed if you want to \n" +
							"  use this function." +
							"- If you want to compare multiple results, you can add another tab\n" +
							"  for another MLF calculation by clicking on the add tab button on the\n" +
							"  menu bar\n" +
							"- You can click on the \"Details\" to see what happens in the whole\n" +
							"  process in a more detailed manner, this is very helpful for debugging\n" +
							"  your calculated work!\n\n";
		
				
		JPanel panel = new JPanel(); //create panel
		panel.setLayout(new BorderLayout()); //set layout
		
		//create a font
		Font font = new Font("Verdana", Font.PLAIN, 10);
		
		 //create a textArea that contains the details
		JTextArea help = new JTextArea(disclamer + overview + what + how + niceToKnow);
		help.setFont(font);
		help.setForeground(Color.BLUE);
		
		help.setBorder(new EmptyBorder(10,10,10,10)); //set padding of JTextArea
		help.setEditable(false); //make textArea non-editable
		
		//make it scrollable
		JScrollPane scrollPane = new JScrollPane(help,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		this.leftTabbedPane.setComponentAt(2, panel);
	}
	/**
	 * Creates Toolbar at the top of the GUI
	 */
	private void createToolBar(){
		toolbar = new JToolBar();
		toolbar.setFloatable(false);
		
		//open button
		i = new ImageIcon(getClass().getResource("/images/open.png"));
		open = new JButton(i);
		open.setOpaque(false);
		open.setBorderPainted(false);
		open.setToolTipText("Open a file");
		
		//save button
		i = new ImageIcon(getClass().getResource("/images/save.png"));
		save = new JButton(i);
		save.setOpaque(false);
		save.setBorderPainted(false);
		save.setToolTipText("Save");
	
		
		//save as button
		i = new ImageIcon(getClass().getResource("/images/saveas.png"));
		saveAs = new JButton(i);
		saveAs.setOpaque(false);
		saveAs.setBorderPainted(false);
		saveAs.setToolTipText("Save As");
		
		open.addActionListener(this);		
		save.addActionListener(this);
		saveAs.addActionListener(this);
		
		
		
		//add Tab
		i = new ImageIcon(getClass().getResource("/images/addtab.png"));
		addTab = new JButton(i);
		addTab.setOpaque(false);
		addTab.setBorderPainted(false);
		addTab.setToolTipText("Add new tab");
		addTab.addActionListener(this);
		
		
		//add toolbar to frame
		toolbar.setLocation(0, 0);
		toolbar.setSize(new Dimension(800, 30));
		//toolbar.add(newButton);
		toolbar.add(open);
		toolbar.add(save);
		toolbar.add(saveAs);
		toolbar.add(addTab);
		this.add(toolbar);
		
	}
	/**
	 * Creates the right panel of the GUI
	 */
	private void createSideBarRight(){
		rightTabbedPane = new JTabbedPane();
		
		//add initial tab
		rightTabbedPane.add("Open a file", new JPanel());
		new CloseTabButton(rightTabbedPane, 0); //create close button
		rightTabbedPane.setLocation(250, 18);
		rightTabbedPane.setSize(new Dimension(550,508));
		
		this.add(rightTabbedPane);
	}
	
	/**
	 * Method that converts the arraylist of processes to data
	 * @param data the elements of the table
	 * @return JPanel that will be used to display the data
	 */
	private JPanel createTable(ArrayList<Process> processes){
		
		Object[][] data = new Object [processes.size()][7];
		
		for(int i = 0; i < processes.size(); i++){
				data[i][0] = processes.get(i).getId();
				data[i][1] = processes.get(i).getArrivalTime();
				data[i][2] = processes.get(i).getBurstTime();
				data[i][3] = processes.get(i).getPriorityNum();
				data[i][4] = processes.get(i).getWaitingTime();
				data[i][5] = processes.get(i).getTurnaroundTime();
				data[i][6] = processes.get(i).getEndTime();
		}
		
		//column names
		String[] columnNames = {"PID",
                "Arrival Time",
                "Burst Time",
                "Priority",
                "Waiting Time",
                "Turn-around Time",
                "End Time"};
		
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		
		JTable table = new JTable(data, columnNames);
		
		//edit table looks
		table.setRowHeight(30);
		table.getTableHeader().setFont(new Font("Arial" ,Font.BOLD, 11 ));
		table.setFillsViewportHeight(true);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);	
		for(int k = 0; k < 7; k++){
			table.getColumnModel().getColumn(k).setCellRenderer(centerRenderer);
		}
		DefaultTableCellRenderer colorRenderer = new DefaultTableCellRenderer();
		colorRenderer.setBackground(Color.CYAN);
		colorRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(colorRenderer);
		
		//gets first arrival time and end time for throughput
		String content = ""; //avgWait time, turn around time, throughput container
		int first = processes.get(0).getArrivalTime();
	    int last = processes.get(0).getEndTime();	
	    double avgWaitTime = 0;
		double avgTurnAroundTime = 0;
		double throughput = 0;
	    
	    for(int i = 0; i < processes.size(); i++){
	    	if(processes.get(i).getArrivalTime() < first){
	    		first = processes.get(i).getArrivalTime();
	    		i = 0;
	    	}
	    	if(processes.get(i).getEndTime() > last){
	    		last = processes.get(i).getEndTime();
	    		i = 0;
	    	}
	    }
	    
	    for(Process process : processes){
	    	//get the total waiting time and turn around time
	    	avgWaitTime += process.getWaitingTime();
	    	avgTurnAroundTime += process.getTurnaroundTime();
	    }

	    avgWaitTime = (double)Math.round((avgWaitTime/processes.size())*1000)/1000;
	    avgTurnAroundTime =  (double)Math.round((avgTurnAroundTime/processes.size()) * 1000)/1000;
	    throughput = (double)processes.size()/(last - first);
	    
	    //print only after execute button
	    if(avgTurnAroundTime > 0){
		    content += "Average Wait Time: " + avgWaitTime + "\n";
		    content += "Average Turn Around Time: " + avgTurnAroundTime + "\n";
		    content += "Throughput: " + processes.size() + "/" + (last - first) + " (" + throughput + ")";
	    }
		//add to scroll pane
		JScrollPane scrollPane = new JScrollPane(table);
		JTextArea avg= new JTextArea(content);
		
		//edit avg text area
		avg.setBorder(new EmptyBorder(5,5,10,5));
		avg.setBackground(Color.CYAN);
		avg.setForeground(Color.BLUE);
		avg.setFont(new Font("Arial" ,Font.BOLD, 11 ));
		
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		tablePanel.add(avg, BorderLayout.SOUTH);
		return tablePanel;
	}
	
	/**
	 * Creates the panel which displays the process details
	 * @param text the content of the panel to be created
	 * @return JPanel
	 */
	private JPanel createDetails(String text){
		JPanel panel = new JPanel(); //create panel
		panel.setLayout(new BorderLayout()); //set layout
		
		//create a font
		Font font = new Font("Verdana", Font.PLAIN, 10);
		
		 //create a textArea that contains the details
		JTextArea details = new JTextArea(text);
		details.setFont(font);
		details.setForeground(Color.BLUE);
		
		details.setBorder(new EmptyBorder(10,10,10,10)); //set padding of JTextArea
		details.setEditable(false); //make textArea non-editable
		
		//make it scrollable
		JScrollPane scrollPane = new JScrollPane(details,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		return panel;
	}
	/**
	 * Method that simulates the active and inactive queue
	 * @param count
	 */
	private void enableDisableQueue(int count) {
		int i = 0;
		Color bgColor = UIManager.getColor("TextField.background");
        Color fgColor = UIManager.getColor("TextField.foreground");
       
		while(i != (count-1)){
			queueLabel[i].setEnabled(true);
			queueLabel[i].setBackground(bgColor);
			    
			queue[i].setEnabled(true);
			queue[i].setBackground(bgColor);
			queue[i].setBorder(BorderFactory.createEtchedBorder());
		    queue[i].setDisabledTextColor(fgColor);
			
			spinner[i].setEnabled(true);
			spinner[i].setBackground(bgColor);
			spinner[i].setBorder(BorderFactory.createEtchedBorder());
			i++;
		}
		while(count-1 != 4){
			queueLabel[count-1].setEnabled(false);
			queue[count-1].setEnabled(false);
			spinner[count-1].setEnabled(false);
			count++;
		}
	}
	/**
	 * Get the values of the table in the current tab and save it as a string
	 */
	public String getTableContent(){
		String content = "";
		
		JPanel panel = (JPanel)rightTabbedPane.getSelectedComponent(); //extract the JPanel in the current tab
		JScrollPane jsp = (JScrollPane)panel.getComponents()[0]; //extract the JScrollPane from the JPanel
		JTable table = (JTable)jsp.getViewport().getComponents()[0]; //extract the table from the JScrollPane
		
		//loop through table values and add it to content variable
		for(int i = 0; i < table.getModel().getRowCount(); i++){
			for(int j = 1; j < table.getModel().getColumnCount(); j++){
				if(j == 1)
					content += table.getModel().getValueAt(i, 3) + " ";
				if(j != 3)
					content += table.getModel().getValueAt(i, j) + " ";
			}
			content += "\n";
		}
		
		return content;
	}
	
	/**
	 * Execute MLFQ
	 */
	private void execute(){
		try {
			spinner[0].commitEdit();
			spinner[1].commitEdit();
			spinner[2].commitEdit();
			spinner[3].commitEdit();
			spinner[4].commitEdit();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		//if no tabs are open
		if(this.rightTabbedPane.getSelectedIndex() == -1){
			JOptionPane.showMessageDialog(new JFrame(), "No tab selected.","Run Error", JOptionPane.ERROR_MESSAGE);
		}else if(((Container) this.rightTabbedPane.getSelectedComponent()).getComponents().length == 0){ //if it's a blank tab
			JOptionPane.showMessageDialog(new JFrame(), "Empty Tab.","Run Error", JOptionPane.ERROR_MESSAGE);
		}else{
			String content = getTableContent(); //get the contents of the table
			String[] line = content.split("\n"); //split the content line by line
			String[] properties; // char by char splitted per line
			int numOfRR = this.noOfQueues.getSelectedIndex() + 1; //number of round robins
			int schedulingAlgorithm = this.schedulingAlgoLabel.getSelectedIndex(); // get what scheduling algo is selected
			int[] quantum = new int[5]; //quantum per queue
			ArrayList<Process> processes = new ArrayList<Process>(); //container of all the processes
			//get quantum per queue
			for(int i = 0; i < quantum.length; i++){
				quantum[i] = (int) this.spinner[i].getValue();
			}
			//add the values of the table to an arraylist
			for(int i = 0; i < line.length; i++){
				properties = line[i].split("\\s+");
				processes.add(new Process(i + 1,Integer.parseInt(properties[1]), Integer.parseInt(properties[2]), Integer.parseInt(properties[0])));
			}
			//instantiate mlfq object
			mlfq = new MLFQ(numOfRR, schedulingAlgorithm, quantum, processes);
			
			//set up details header
			MLFQ.details += "Multi-Level FeedBack Queue \n" +
					"No of RoundRobin/s: " + numOfRR +
					"\nQn Scheduling Algorithm: " + this.schedulingAlgoLabel.getSelectedItem() +
					"\nQuantum:" + 
					"\n\t Queue 1: "+ quantum[0] +
					"\n\t Queue 2: "+ quantum[1] + 
					"\n\t Queue 3: "+ quantum[2] +
					"\n\t Queue 4: "+ quantum[3] +
					"\n\t Queue 5: "+ quantum[4];
			//execute mlfq
			mlfq.execute();
			
			//set right table, left process details and save finishedProcesses on the tab.
			this.rightTabbedPane.setComponentAt(this.rightTabbedPane.getSelectedIndex(), createTable(MLFQ.finishedProcesses));
			((CloseTabButton) this.rightTabbedPane.getTabComponentAt(this.rightTabbedPane.getSelectedIndex())).setFinishedProcesses(MLFQ.finishedProcesses);
			this.leftTabbedPane.setComponentAt(1, createDetails(MLFQ.details));
			
		}
		
		
		
	}
	/**
	 * Converts System.out.println output to string variable
	 * @return
	 */
	private String convertSysOut(){
		String content = "";
		// Create a stream to hold the output
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PrintStream ps = new PrintStream(baos);
	    // IMPORTANT: Save the old System.out!
	    PrintStream old = System.out;
	    // Tell Java to use your special stream
	    System.setOut(ps);
	   
	    //get first and last processes for computing throughput.
	    ArrayList<Process> processes = ((CloseTabButton) this.rightTabbedPane.getTabComponentAt(this.rightTabbedPane.getSelectedIndex())).getFinishedProcesses();
	   
	    if(processes.size() == 0){
	    	setFinishedProcess();
	    	processes = ((CloseTabButton) this.rightTabbedPane.getTabComponentAt(this.rightTabbedPane.getSelectedIndex())).getFinishedProcesses();
	    }
	    
	    int first = processes.get(0).getArrivalTime();
	    int last = processes.get(0).getEndTime();
	    
	    for(int i = 0; i < processes.size(); i++){
	    	if(processes.get(i).getArrivalTime() < first){
	    		first = processes.get(i).getArrivalTime();
	    		i = 0;
	    	}
	    	if(processes.get(i).getEndTime() > last){
	    		last = processes.get(i).getEndTime();
	    		i = 0;
	    	}
	    }
	    
	    // Print some output: goes to your special stream
	    Functions.print(MLFQ.finishedProcesses, first, last);
	    // Put things back
	    System.out.flush();
	    System.setOut(old);
	    // Show what happened
	    content = baos.toString();
		System.out.flush();
		return content;
	}
	/**
	 * Set finished process of the hidden field per Tab
	 */
	private void setFinishedProcess(){
		//set finished process of the tab
    	String content = getTableContent(); //get the contents of the table
		String[] line = content.split("\n"); //split the content line by line
		String[] properties; // char by char splitted per line
		ArrayList<Process> processes = new ArrayList<Process>(); //container of all the processes
		
		//add the values of the table to an arraylist
		for(int i = 0; i < line.length; i++){
			properties = line[i].split("\\s+");
			if(properties.length > 3)
				processes.add(new Process(i + 1,Integer.parseInt(properties[1]), Integer.parseInt(properties[2]), Integer.parseInt(properties[0]), Integer.parseInt(properties[3]), Integer.parseInt(properties[4]), Integer.parseInt(properties[5])));
			else
				processes.add(new Process(i + 1,Integer.parseInt(properties[1]), Integer.parseInt(properties[2]), Integer.parseInt(properties[0]), 0, 0, 0));
		}
    	((CloseTabButton) this.rightTabbedPane.getTabComponentAt(this.rightTabbedPane.getSelectedIndex())).setFinishedProcesses(processes);
	}
	/**
	 * Action Listener
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		filter = new FileTypeFilter(".txt", "Text File");
		chooser.addChoosableFileFilter(filter); //add filter and accept only text file
		chooser.setFileFilter(filter); // set the file filter as the default option
		chooser.setAcceptAllFileFilterUsed(false); //disable all files in filter
		
		//File explorer for open button.
        if (e.getSource() == open) {
        	int value = chooser.showOpenDialog(GUI.this);
        	
        	//if 'open' is pressed in file picker
        	if (value == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                try{
                	chooser.setCurrentDirectory(file); //change current directory to previous file path
                	
                	//recreate the tab
                	if(this.rightTabbedPane.getSelectedIndex() == -1){
                		this.rightTabbedPane.add(file.getName(), createTable(ReadFromFile.execute(file.getAbsolutePath())));
                		new CloseTabButton(this.rightTabbedPane, this.rightTabbedPane.getTabCount() - 1);
                	}else if(((Container) this.rightTabbedPane.getSelectedComponent()).getComponents().length == 0){//if there is blank tab open
                		this.rightTabbedPane.setComponentAt(this.rightTabbedPane.getSelectedIndex(), createTable(ReadFromFile.execute(file.getAbsolutePath())));
                		this.rightTabbedPane.setTitleAt(this.rightTabbedPane.getSelectedIndex(), file.getName());
                		new CloseTabButton(this.rightTabbedPane, this.rightTabbedPane.getSelectedIndex());
                	}else{//if there is none, create a new tab
                		this.rightTabbedPane.add(file.getName(), createTable(ReadFromFile.execute(file.getAbsolutePath())));
                		new CloseTabButton(this.rightTabbedPane, this.rightTabbedPane.getTabCount() - 1);
                	}
                	
                	//set contents of hidden field
                	CloseTabButton ctb = ((CloseTabButton) this.rightTabbedPane.getTabComponentAt(this.rightTabbedPane.getSelectedIndex()));
                	ctb.setText(file + "");
                	
                }catch(Exception e1){ //an error has occured in reading the file
                	e1.printStackTrace();
                	JOptionPane.showMessageDialog(new JFrame(), "An Error has occured. Invalid File Format.","File Error", JOptionPane.ERROR_MESSAGE);
                }          
            }

        }
        //File explorer for save and save as button.
        else if (e.getSource() == save) {
        	
	        	//if no tabs are open
	    		if(this.rightTabbedPane.getSelectedIndex() == -1){
	    			JOptionPane.showMessageDialog(new JFrame(), "No tab selected.","Save Error", JOptionPane.ERROR_MESSAGE);
	    		}else if(((Container) this.rightTabbedPane.getSelectedComponent()).getComponents().length == 0){ //if it's a blank tab
	    			JOptionPane.showMessageDialog(new JFrame(), "Empty Tab.","Save Error", JOptionPane.ERROR_MESSAGE);
	    		}else{    
	        	    			
	        	//get the file path from the hidden field of the tab
	        	CloseTabButton ctb = ((CloseTabButton) this.rightTabbedPane.getTabComponentAt(this.rightTabbedPane.getSelectedIndex()));
	        	String filePath = ctb.getText().split("\n")[0];
	        	File file = new File(filePath);
	        	
	        	try {
					WriteToFile.execute(getTableContent() + "~~~\n" + convertSysOut(), file);
					JOptionPane.showMessageDialog(new JFrame(), "Save successful!");
					
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(new JFrame(), "An Error has occured. File could not be saved.","Save Error", JOptionPane.ERROR_MESSAGE);
				}
    		}
        }
        else if (e.getSource() == saveAs){
    		//if no tabs are open
    		if(this.rightTabbedPane.getSelectedIndex() == -1){
    			JOptionPane.showMessageDialog(new JFrame(), "No tab selected.","Save Error", JOptionPane.ERROR_MESSAGE);
    		}else if(((Container) this.rightTabbedPane.getSelectedComponent()).getComponents().length == 0){ //if it's a blank tab
    			JOptionPane.showMessageDialog(new JFrame(), "Empty Tab.","Save Error", JOptionPane.ERROR_MESSAGE);
    		}else{     	
    			int value = chooser.showSaveDialog(GUI.this);
	        	//when save is clicked
	        	if(value == JFileChooser.APPROVE_OPTION){
	        				
	        		try{
	        			//if the file exists, prompt the user whether to overwrite or not
	        			String filePath = chooser.getSelectedFile().getAbsolutePath();
	        			boolean bool = false;
	        			//will check that file and file.txt are the same
	        			if (filePath.indexOf(".") > 0){
	        			    filePath = filePath.substring(0, filePath.lastIndexOf("."));
	        			    bool = true;
	        			}
	        			if(new File(filePath + ".txt").exists() || (new File(filePath).exists() && bool)){
	        				int choice = JOptionPane.showConfirmDialog(new JFrame(), "File name exists! Overwrite existing file?", "Overwrite", JOptionPane.YES_NO_OPTION);
	        				//if yes, overwrite
	        				if(choice == 0){
	        					WriteToFile.execute(getTableContent() + "~~~\n" + convertSysOut(), chooser.getSelectedFile());
	        				}
	        			}else{ //if file name does not exist, save.
							
	        				WriteToFile.execute(getTableContent() + "~~~\n" + convertSysOut(), chooser.getSelectedFile());
	        				
	        				while(!new File(filePath + ".txt").exists()){
	        					System.out.println(chooser.getSelectedFile());
	        				}
							this.rightTabbedPane.setComponentAt(this.rightTabbedPane.getSelectedIndex(), createTable(ReadFromFile.execute(filePath  + ".txt")));
							this.rightTabbedPane.setTitleAt(this.rightTabbedPane.getSelectedIndex(), chooser.getSelectedFile().getName());
	                		new CloseTabButton(this.rightTabbedPane, this.rightTabbedPane.getSelectedIndex());
							
						}
	        		}catch(Exception e1){
	        			e1.printStackTrace();
	                	JOptionPane.showMessageDialog(new JFrame(), "An Error has occured. File could not be saved.","Save Error", JOptionPane.ERROR_MESSAGE);
	        		}
	        		
	        	}
    		}
        }
        else if(e.getSource() == noOfQueues){
        	int num = noOfQueues.getSelectedIndex();
        	
        	if(num == 0){
        		enableDisableQueue(2);
        	}
        	else if(num == 1){
        		enableDisableQueue(3);
        	}
        	else if(num == 2){
        		enableDisableQueue(4);
            }
        	else{
        		enableDisableQueue(5);
            }
        	
        }
        //enabl/disable time quantum for queue 5 if scheduling algorithm is not round robin
        else if(e.getSource() == schedulingAlgoLabel){
        	
        	if(schedulingAlgoLabel.getSelectedIndex() == 3){
        		spinner[4].setEnabled(true);
        	}
        	else{
        		spinner[4].setEnabled(false);
        	}
        			
        }
        //add tabs
        else if(e.getSource() == addTab){
            rightTabbedPane.add("New", new JPanel());
            new CloseTabButton(rightTabbedPane, rightTabbedPane.getTabCount() - 1);
            rightTabbedPane.setSelectedIndex(rightTabbedPane.getTabCount() - 1);
        }
        //Run MLFQ
        else if(e.getSource() == execute){
        	execute();
        }
	}
	
}

/**
 * Class that simulates close tab button
 */
class CloseTabButton extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTabbedPane pane;
	private ImageIcon i;
	private JTextArea text;
	private ArrayList<Process> finishedProcesses;
	  
	public CloseTabButton(JTabbedPane pane, int index) {
		this.finishedProcesses = new ArrayList<Process>();
		
		
		this.pane = pane;
	    this.setOpaque(false);
	    JLabel label = new JLabel(pane.getTitleAt(index),pane.getIconAt(index),JLabel.LEFT);
	    add(label);
	    i = new ImageIcon(getClass().getResource("/images/closetab.png"));
	    
	    //creating the close button in the tab
	    JButton close = new JButton(i);
	    close.setFocusable(false);
	    close.setContentAreaFilled(false);
	    close.setBorderPainted(false);
	    close.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    close.setPreferredSize(new Dimension(i.getIconWidth(), i.getIconHeight()));
	    
	    //add hidden fields
	    text = new JTextArea("");
	    text.setVisible(false);
	    
	    //add close to tab
	    add(text);
	    add(close);
	    close.addActionListener(this);
	    pane.setTabComponentAt(index, this);
	}
	
	public void setText(String text){
		this.text.setText(this.text.getText() + text + "\n");
	}
	
	public String getText(){
		return this.text.getText();
	}
	
	public void setFinishedProcesses(ArrayList<Process> finishedProcesses){
		this.finishedProcesses = finishedProcesses;
	}
	
	public ArrayList<Process> getFinishedProcesses(){
		return this.finishedProcesses;
	}
	  
	  //close when the button is clicked
	public void actionPerformed(ActionEvent e) {
		int i = pane.indexOfTabComponent(this);
		if (i != -1) {
			pane.remove(i);
	    }
	}
}

/**
 * Class that creates a filter for Filechooser
 *
 */
class FileTypeFilter extends FileFilter {
	 
    private String fileExtension;
    private String description;
     
    public FileTypeFilter(String extension, String description) {
        this.fileExtension = extension;
        this.description = description;
    }
     
    @Override
    public boolean accept(File file) {
    	//if a file is still a folder
        if (file.isDirectory()) {
            return true;
        }
        return file.getName().toLowerCase().endsWith(fileExtension);
    }
     
    public String getDescription() {
        return description + String.format(" (*%s)", fileExtension);
    }
}

