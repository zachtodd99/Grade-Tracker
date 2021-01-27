import java.util.ArrayList;


public class Course {

	protected String name;
	protected int totalPoints;
	protected ArrayList<String> assignments = new ArrayList<String>();
	protected ArrayList<Double> scores = new ArrayList<Double>();
	protected ArrayList<Double> possibles = new ArrayList<Double>();
	public Course(String name, int points){
		this.name = name;
		this.totalPoints = points;
	}
	
	public String toString(){
		return this.name;
	}
	
	public void add(String name, double score, double possible){
		this.assignments.add(name);
		this.scores.add(score);
		this.possibles.add(possible);
	}
	
	public void remove(int spot){
		this.assignments.remove(spot);
		this.scores.remove(spot);
		this.possibles.remove(spot);
	}
	
	
}
