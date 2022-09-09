//This class creates students with id, name, duration and rating.
public class Student implements Comparable<Student> {
private int id, duration;
private String name;
private double rating;
private boolean house;
public Student(int id, String name, int duration, double rating) {
	this.id = id;
	this.duration = duration;
	this.name = name;
	this.rating = rating;
	this.house = false;
}
@Override
public int compareTo(Student o) {
	if (this.id > o.id) {
		return 1;
	}
	else if (o.id > this.id ) {
		return -1;
	}
	return 0;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getDuration() {
	return duration;
}
public void setDuration(int duration) {
	this.duration = duration;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public double getRating() {
	return rating;
}
public void setRating(double rating) {
	this.rating = rating;
}
public boolean getHouse() {
	return house;
}
public void setHouse() {
	this.house = true;
}


}
