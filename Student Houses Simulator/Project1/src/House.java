//This class creates houses with id, duration and rating.
public class House implements Comparable<House> {
private int id, duration;
private double rating;
public House(int id, int duration, double rating) {
	this.id = id;
	this.duration = duration;
	this.rating = rating;
}
@Override
public int compareTo(House o) {
	if (this.duration > o.duration) {
		return 1;
	}
	else if (o.duration > this.duration) {
		return -1;
	}
	else {
		if (this.id > o.id) {
			return 1;
		}
		else if (o.id > this.id) {
			return -1;
		}
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
public double getRating() {
	return rating;
}
public void setRating(double rating) {
	this.rating = rating;
}

}
