import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClassConstructor {

	private JFrame frmSemesterConstructor;
	private JTextField textField;
	private JTable table;
	private ArrayList<Course> courses = new ArrayList<Course>();
	private int thisSemester = 0;
	private Semester[] semesters = new Semester[8];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClassConstructor window = new ClassConstructor();
					window.frmSemesterConstructor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClassConstructor() {

		File file = new File("Courses.txt");
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String name = line.substring(0, line.indexOf('|'));
			line = line.substring(line.indexOf('|') + 1);
			@SuppressWarnings("resource")
			Scanner scanner2 = new Scanner(line);
			int points = scanner2.nextInt();
			// scanner2.close();
			Course aCourse = new Course(name, points);
			courses.add(courses.size(), aCourse);

		}
		createUser();
		initialize();
	}

	private void createUser() {
		for(int x=0;x<8;x++){
			semesters[x] = new Semester("");
		}

		semesters[0].name = "Freshman Fall";
		semesters[1].name = "Freshman Spring";
		semesters[2].name = "Sophomore Fall";
		semesters[3].name = "Sophomore Spring";
		semesters[4].name = "Junior Fall";
		semesters[5].name = "Junior Spring";
		semesters[6].name = "Senior Fall";
		semesters[7].name = "Senior Spring";
			
	
		
		Scanner scanner = null;
		try {
			File file = new File("UserInfo.txt");
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int currentSemester = -1;
		String currentCourse = "";
		while(scanner.hasNextLine()){
			String line = scanner.nextLine();
			
			if(line.equals("**")){
				currentSemester++;
				if(scanner.hasNextLine()){
					line = scanner.nextLine();
					
					if (!line.equals("")) {
						String name = line.substring(0, line.indexOf('|'));
						line = line.substring(line.indexOf('|')+1);
						@SuppressWarnings("resource")
						Scanner scan2 = new Scanner(line);
						int num = scan2.nextInt();
						semesters[currentSemester].addCourse(name, num);
						currentCourse = name;
					}
				}

			}else if(line.equals("*")){
				line = scanner.nextLine();
				String name = line.substring(0,line.indexOf('|'));
				line = line.substring(line.indexOf('|')+1);
				@SuppressWarnings("resource")
				Scanner scan2 = new Scanner(line);
				int num = scan2.nextInt();
				semesters[currentSemester].addCourse(name,num);
				currentCourse = name;
			}else{
				String name = line.substring(0,line.indexOf('|'));
				line = line.substring(line.indexOf('|')+1);
				String line2 = line.substring(0,line.indexOf('|'));
				@SuppressWarnings("resource")
				Scanner scan2 = new Scanner(line2);
				int score = scan2.nextInt();
				line=line.substring(line.indexOf('|')+1);
				scan2 = new Scanner(line);
				int possible = scan2.nextInt();
				semesters[currentSemester].courses.get(semesters[currentSemester].findCourse(currentCourse)).add(name, score, possible);
							
			}
			
		}
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		frmSemesterConstructor = new JFrame();
		frmSemesterConstructor.setResizable(false);
		frmSemesterConstructor.setTitle("Semester Constructor");
		frmSemesterConstructor.setBounds(100, 100, 518, 294);
		frmSemesterConstructor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSemesterConstructor.getContentPane().setLayout(null);
		table = new JTable();

		final JComboBox comboBox = new JComboBox();
		comboBox.setEditable(true);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(courses.get(comboBox.getSelectedIndex())!=null)
				textField.setText(""+courses.get(comboBox.getSelectedIndex()).totalPoints);
			}
		});
		String[] aString = new String[this.courses.size()];
		for (int x = 0; x < this.courses.size(); x++) {
			aString[x] = this.courses.get(x).toString();
		}
		comboBox.setModel(new DefaultComboBoxModel(aString));
		comboBox.setBounds(80, 56, 184, 20);
		frmSemesterConstructor.getContentPane().add(comboBox);

		JLabel lblClass = new JLabel("Course:");
		lblClass.setBounds(24, 59, 46, 14);
		frmSemesterConstructor.getContentPane().add(lblClass);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				table.getRowCount();
				table.getSelectedRow();
				// table.getValueAt(arg0, arg1);
				Object[][] thing = new Object[table.getRowCount() + 1][2];
				for (int x = 0; x < table.getRowCount(); x++) {
					thing[x][0] = table.getValueAt(x, 0);
					thing[x][1] = table.getValueAt(x, 1);
				}

				thing[table.getRowCount()][0] = comboBox.getSelectedItem();

				String number = textField.getText();

				try {
					@SuppressWarnings("resource")
					Scanner scan = new Scanner(number);
					int toPass = 0;
					toPass = scan.nextInt();

					thing[table.getRowCount()][1] = toPass;
					table.setModel(new DefaultTableModel(thing, new String[] {
							"Course", "Points" }));
					table.getColumnModel().getColumn(0).setPreferredWidth(166);
					semesters[thisSemester].addCourse((String) comboBox.getSelectedItem(), toPass);
				} catch (Exception e) {
					JFileChooser chooser = new JFileChooser();
					JOptionPane
							.showMessageDialog(chooser,
									"ERROR: Please enter a number for \"Total points in course.\"");
				}

			}
		});
		btnAdd.setBounds(154, 118, 110, 23);
		frmSemesterConstructor.getContentPane().add(btnAdd);

		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedColumn() != -1) {
					semesters[thisSemester].courses.remove(table.getSelectedRow());
					Object[][] thing = new Object[table.getRowCount() - 1][2];
					boolean reached = false;
					for (int x = 0; x < table.getRowCount(); x++) {
						if (reached) {

							thing[x - 1][0] = table.getValueAt(x, 0);
							thing[x - 1][1] = table.getValueAt(x, 1);

						} else {

							if (x != table.getSelectedRow()) {
								thing[x][0] = table.getValueAt(x, 0);
								thing[x][1] = table.getValueAt(x, 1);
							} else {
								reached = true;
							}

						}

					}

					table.setModel(new DefaultTableModel(thing, new String[] {
							"Course", "Points" }));
					table.getColumnModel().getColumn(0).setPreferredWidth(166);
				} else {
					JFileChooser chooser = new JFileChooser();
					JOptionPane.showMessageDialog(chooser,
							"ERROR: Please choose a course to remove!");
				}
				

			}
		});
		btnRemove.setBounds(154, 152, 110, 23);
		frmSemesterConstructor.getContentPane().add(btnRemove);

		textField = new JTextField();
		textField.setText("1000");
		textField.setBounds(221, 87, 43, 20);
		frmSemesterConstructor.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblTotalPointsIn = new JLabel("Total points in course:");
		lblTotalPointsIn.setBounds(76, 90, 135, 14);
		frmSemesterConstructor.getContentPane().add(lblTotalPointsIn);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(274, 11, 218, 240);
		frmSemesterConstructor.getContentPane().add(scrollPane);

		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] {

		}, new String[] { "Course", "Points" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(166);

		JLabel lblSemester = new JLabel("Semester:");
		lblSemester.setBounds(10, 18, 60, 14);
		frmSemesterConstructor.getContentPane().add(lblSemester);

		final JComboBox comboBox_1 = new JComboBox();
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				thisSemester = comboBox_1.getSelectedIndex();
				
				@SuppressWarnings("unused")
				int length = semesters[thisSemester].courses.size();
				
				
				Object[][] thing = new Object[semesters[thisSemester].courses.size()][2];
				
				for(int x=0;x<semesters[thisSemester].courses.size(); x++){
					thing[x][0] = semesters[thisSemester].courses.get(x).name;
					thing[x][1] = semesters[thisSemester].courses.get(x).totalPoints;
				}
				
				table.setModel(new DefaultTableModel(thing, new String[] {
						"Course", "Points" }));
				table.getColumnModel().getColumn(0).setPreferredWidth(166);
			}
		});
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] { 
				"Freshman Fall", "Freshman Spring", "Sophomore Fall", "Sophomore Spring", "Junior Fall", "Junior Spring", "Senior Fall", "Senior Spring" }));
		comboBox_1.setBounds(80, 15, 184, 20);
		frmSemesterConstructor.getContentPane().add(comboBox_1);

		JButton btnRemoveAll = new JButton("Remove All");
		btnRemoveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				while(semesters[thisSemester].courses.size()>0){
					semesters[thisSemester].courses.remove(0);
				}
				table.setModel(new DefaultTableModel(new Object[][] {

				}, new String[] { "Course", "Points" }));
				table.getColumnModel().getColumn(0).setPreferredWidth(166);
				
				
				

			}
		});
		btnRemoveAll.setBounds(154, 186, 110, 23);
		frmSemesterConstructor.getContentPane().add(btnRemoveAll);

		JButton btnClassEditor = new JButton("Class Editor!");
		btnClassEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				@SuppressWarnings("unused")
				UserInterface face = new UserInterface(semesters);
				frmSemesterConstructor.dispose();
				
			}
		});
		btnClassEditor.setBounds(24, 115, 120, 91);
		frmSemesterConstructor.getContentPane().add(btnClassEditor);
		
		JButton btnHelpMe = new JButton("Help Me!");
		btnHelpMe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser chooser = new JFileChooser();
				JOptionPane
						.showMessageDialog(chooser,
								"Hello! This program is designed to help you keep track of your grades. "
								+ "Simply choose a course \nfrom the drop down menu and click \"Add\" to "
								+ "add it to the selected semester. You can also \nmanually type in the "
								+ "course you wish to add. Don't forget to type in the total number of "
								+ "points \nfor the course! To remove a course, select it from the table "
								+ "and click \"Remove\". \nOnce you have added all the classes you want, "
								+ "click \"Class Editor!\". Enjoy!");
			}
		});
		btnHelpMe.setBounds(96, 220, 89, 23);
		frmSemesterConstructor.getContentPane().add(btnHelpMe);
		
		comboBox_1.setSelectedIndex(0);
		
		thisSemester = comboBox_1.getSelectedIndex();
		
		@SuppressWarnings("unused")
		int length = semesters[thisSemester].courses.size();
		
		
		Object[][] thing = new Object[semesters[thisSemester].courses.size()][2];
		
		for(int x=0;x<semesters[thisSemester].courses.size(); x++){
			thing[x][0] = semesters[thisSemester].courses.get(x).name;
			thing[x][1] = semesters[thisSemester].courses.get(x).totalPoints;
		}
		
		table.setModel(new DefaultTableModel(thing, new String[] {
				"Course", "Points" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(166);

	}

}
