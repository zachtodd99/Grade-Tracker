import java.util.ArrayList;


public class Semester {
	protected String name;
	protected ArrayList<Course> courses = new ArrayList<Course>();
	
	public Semester(String myName){
		this.name = myName;
	}
	
	public void addCourse(String name, int totalPoints){
		Course aCourse = new Course(name,totalPoints);
		this.courses.add(aCourse);
	}
	
	public int findCourse(String name){
		for(int x = 0; x<courses.size();x++){
			if(name.equals(courses.get(x).name)){
				return x;
			}
		}
		return -1;
	}
}
