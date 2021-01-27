import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import java.awt.Color;

import javax.swing.border.EtchedBorder;


import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;


public class UserInterface {

	private JFrame frame;
	private JTable table_1;
	private JTextField txtName;
	private JTextField txtScore;
	private JTextField txtPossible;
	private JLabel lblCurrentGrade;
	private JLabel lblPointsRemainingIn;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox_1;
	private int currentSemester = 0;
	private Semester[] semesters = new Semester[8];
	
	/**
	 * Create the application.
	 */
	public UserInterface(Semester[] semesters) {
		this.semesters = semesters;
		initialize();
		this.frame.setVisible(true);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void update() {
				// TODO Auto-generated method stub
		int courseNum = comboBox.getSelectedIndex();
		currentSemester = comboBox_1.getSelectedIndex();
		
		frame.setTitle("Grade Tracker");
		
		String[] aString = new String[semesters[currentSemester].courses.size()];
	
		for(int x=0; x<semesters[currentSemester].courses.size();x++){
			aString[x] = semesters[currentSemester].courses.get(x).name;
		}
		
		comboBox.setModel(new DefaultComboBoxModel(aString));
		if(semesters[currentSemester].courses.size()>0){
		if(courseNum>-1){
					comboBox.setSelectedIndex(courseNum);
		}else{
			courseNum = 0;
		}
		}else{
			lblPointsRemainingIn.setText("Points remaining in course: N/A");
		}

		

		
		if(semesters[currentSemester].courses.size()>0 && courseNum>-1){
			int length = semesters[currentSemester].
				courses.get(courseNum).
				assignments.size();
		Object[][] things = new Object[length][4];
		
		for(int x=0; x<length;x++){
			things[x][0] = semesters[currentSemester].courses.get(courseNum).assignments.get(x);
			things[x][1] = semesters[currentSemester].courses.get(courseNum).scores.get(x);
			things[x][2] = semesters[currentSemester].courses.get(courseNum).possibles.get(x);
			things[x][3] = 100*semesters[currentSemester].courses.get(courseNum).scores.get(x)/semesters[currentSemester].courses.get(courseNum).possibles.get(x);
		}
		
		table_1.setModel(new DefaultTableModel(
				things,
				new String[] {
					"Event", "Your Score", "Total Possible", "Percent"
				}
			) );
		}else{
			table_1.setModel(new DefaultTableModel(
					new Object[][] {

					},
					new String[] {
						"Event", "Your Score", "Total Possible", "Percent"
					}
				) );
			
		}
		
		table_1.getColumnModel().getColumn(0).setPreferredWidth(135);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(67);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(77);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(55);
		double accpoints = 0;
		double totalpoints= 0;
		if(semesters[currentSemester].courses.size()>0 && courseNum>-1){
		for(int x=0;x<semesters[currentSemester].courses.get(courseNum).scores.size();x++){
			accpoints+=semesters[currentSemester].courses.get(courseNum).scores.get(x);
			totalpoints += semesters[currentSemester].courses.get(courseNum).possibles.get(x);
		}
		
		double perc = 100*accpoints/totalpoints;
		
			String res = "Current Grade: " + perc;
			if (totalpoints == 0) {
				res = "Current Grade: N/A";
			}
			if (res.length() > 20) {
				res = res.substring(0, 20);
			}

			lblCurrentGrade.setText(res);
			int total = semesters[currentSemester].courses.get(courseNum).totalPoints;
		
		for(int x = 0; x<semesters[currentSemester].courses.get(courseNum).scores.size(); x++){
			total-=semesters[currentSemester].courses.get(courseNum).possibles.get(x);
		}
		
		lblPointsRemainingIn.setText("Points remaining in course: " + total);
		} else {
			lblCurrentGrade.setText("Current Grade: N/A");
		}
		
		
		
	}
	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 556, 331);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				update();
			}

			
		});
		comboBox.setBounds(51, 37, 192, 20);
		frame.getContentPane().add(comboBox);
		
		JLabel lblClass = new JLabel("Class: ");
		lblClass.setBounds(10, 40, 46, 14);
		frame.getContentPane().add(lblClass);
		
		lblCurrentGrade = new JLabel("Current Grade:");
		lblCurrentGrade.setBounds(326, 15, 122, 14);
		frame.getContentPane().add(lblCurrentGrade);
		
		lblPointsRemainingIn = new JLabel("Points remaining in course:");
		lblPointsRemainingIn.setBounds(253, 40, 222, 14);
		frame.getContentPane().add(lblPointsRemainingIn);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				File file = new File("UserInfo.txt");
				PrintStream stream = null;
				try {
					stream = new PrintStream(file);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				stream.println("**");
				
				for(int x=0; x<8;x++){
					
					if(semesters[x].courses.size()>0){
						for(int y =0; y<semesters[x].courses.size();y++){
							stream.println(semesters[x].courses.get(y).name + "|" + semesters[x].courses.get(y).totalPoints);
							if(semesters[x].courses.get(y).assignments.size()>0){
								for(int z = 0; z<semesters[x].courses.get(y).assignments.size();z++){
									String one = (semesters[x].courses.get(y).scores.get(z)).toString();
									one = one.substring(0,one.indexOf('.'));
									String two = (semesters[x].courses.get(y).possibles.get(z)).toString();
									two = two.substring(0,two.indexOf('.'));
									String str = semesters[x].courses.get(y).assignments.get(z)+ "|" + one + "|" + two;
									stream.println(str);
									if(z==semesters[x].courses.get(y).assignments.size()-1 && y !=semesters[x].courses.size()-1){
										stream.println("*");
									}
								}
							}else if(semesters[x].courses.size()>y+1){
								stream.println("*");
							}
						}
						
					}else{
						
							stream.println("");
						
						
					}
					
					if(x==7){
						stream.print("**");
					}else{
					stream.println("**");	
					}
					
				}
				
				
			}
		});
		btnSave.setBounds(448, 11, 69, 21);
		frame.getContentPane().add(btnSave);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 65, 497, 192);
		frame.getContentPane().add(scrollPane);
		
		table_1 = new JTable();
		scrollPane.setViewportView(table_1);
		table_1.setModel(new DefaultTableModel(
			new Object[][] {

			},
			new String[] {
				"Event", "Your Score", "Total Possible", "Percent"
			}
		) );
		table_1.getColumnModel().getColumn(0).setPreferredWidth(135);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(67);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(77);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(55);
		//
		table_1.setRowSelectionAllowed(true);
		table_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		table_1.setBackground(Color.WHITE);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String name = txtName.getText();
				String pointsString = txtScore.getText();
				String possibleString = txtPossible.getText();
				double points = 0;
				double possible = 0;
				@SuppressWarnings("resource")
				Scanner scanner = new Scanner(pointsString);
				if(scanner.hasNextDouble()){
					points = scanner.nextDouble();
				}else{
					JFileChooser chooser = new JFileChooser();
					JOptionPane
							.showMessageDialog(chooser,
									"ERROR: Please enter a number for \"Points.\"");
					return;
				}
				
				scanner = new Scanner(possibleString);
				
				if(scanner.hasNextDouble()){
					 possible = scanner.nextDouble();
				}else{
					JFileChooser chooser = new JFileChooser();
					JOptionPane
							.showMessageDialog(chooser,
									"ERROR: Please enter a number for \"Possible Points.\"");
					return;
				}
				
				int courseNum = comboBox.getSelectedIndex();
				if(courseNum == -1){
					courseNum = 0;
				}
				try{
					semesters[currentSemester].courses.get(courseNum).assignments.add(name);
					semesters[currentSemester].courses.get(courseNum).scores.add(points);
					semesters[currentSemester].courses.get(courseNum).possibles.add(possible);
				}catch (IndexOutOfBoundsException e){
					JFileChooser chooser = new JFileChooser();
					JOptionPane
							.showMessageDialog(chooser,
									"ERROR: You currently do not have any courses assigned for this semester.");
				}
				

				update();
				
				
			}
		});
		btnAdd.setBounds(339, 262, 89, 23);
		frame.getContentPane().add(btnAdd);
		
		txtName = new JTextField();
		txtName.setText("Name");
		txtName.setBounds(79, 263, 86, 20);
		frame.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		JLabel lblNew = new JLabel("New Event");
		lblNew.setBounds(10, 266, 74, 14);
		frame.getContentPane().add(lblNew);
		
		txtScore = new JTextField();
		txtScore.setText("Score");
		txtScore.setBounds(176, 263, 74, 20);
		frame.getContentPane().add(txtScore);
		txtScore.setColumns(10);
		
		txtPossible = new JTextField();
		txtPossible.setText("Possible");
		txtPossible.setBounds(260, 263, 69, 20);
		frame.getContentPane().add(txtPossible);
		txtPossible.setColumns(10);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (table_1.getSelectedColumn() != -1) {

					boolean reached = false;
					int theCourse = comboBox.getSelectedIndex();
					for (int x = 0; x < table_1.getRowCount(); x++) {
						if (reached) {


						} else {

							if (x != table_1.getSelectedRow()) {
								
							} else {
								reached = true;
								semesters[currentSemester].courses.get(theCourse).remove(x);
							}

						}

					}
					
					
					update();
				} else {
					JFileChooser chooser = new JFileChooser();
					JOptionPane.showMessageDialog(chooser,
							"ERROR: Please choose an assignment to remove!");
				}
			}
		});
		btnRemove.setBounds(438, 262, 89, 23);
		frame.getContentPane().add(btnRemove);
		
		comboBox_1 = new JComboBox();
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				update();
			}
		});
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "Freshman Fall", "Freshman Spring", "Sophomore Fall", "Sophomore Spring", "Junior Fall", "Junior Spring", "Senior Fall", "Senior Spring"}));
		comboBox_1.setBounds(78, 6, 165, 20);
		frame.getContentPane().add(comboBox_1);
		
		JLabel lblSemester = new JLabel("Semester:");
		lblSemester.setBounds(10, 9, 94, 14);
		frame.getContentPane().add(lblSemester);
		update();
	}
}
