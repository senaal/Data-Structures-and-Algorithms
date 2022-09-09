import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class project1main {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		PriorityQueue<House> houses = new PriorityQueue<>();
		ArrayList<House> loaded = new ArrayList<House>();
		ArrayList<Student> students = new ArrayList<Student>();		
//file reading
		while(in.hasNext()) {
			if (in.next().equals("h")){
				int id = in.nextInt();
				int duration = in.nextInt();
				String placeHolder = in.next();
				double rating = Double.parseDouble(placeHolder);
				House house = new House(id, duration, rating);	
				houses.add(house);
			}
			else {
				int id = in.nextInt();
				String name = in.next();
				int duration = in.nextInt();
				String placeHolder = in.next();
				double rating = Double.parseDouble(placeHolder);
				Student student = new Student(id, name, duration, rating);
				students.add(student);
			}
		}
//This part checks every student one by one for each house. If a house's and a student's rating are match, it regulates durations.
		Collections.sort(students);
		while(houses.isEmpty() == false) {
			House h = houses.poll();
			if(h.getDuration() == 0) {
				for(int s = 0; s < students.size(); s++) {
					if(students.get(s).getHouse() == false && students.get(s).getDuration() > 0) {
						if(students.get(s).getRating() <= h.getRating()) {
							h.setDuration(students.get(s).getDuration());
							students.get(s).setHouse();
							loaded.add(h);
							break;
						}
					}
				}
			}
			else {
				loaded.add(h);
			}
			if(houses.isEmpty() == true) {
				for(int i = 0; i < loaded.size(); i++) {
					loaded.get(i).setDuration(loaded.get(i).getDuration()-1);
				}
				while(loaded.isEmpty() == false) {
					houses.add(loaded.get(0));
					loaded.remove(0);
				}
				for(int j = 0; j < students.size(); j++) {
					students.get(j).setDuration((students.get(j).getDuration()-1));
				}
			}
		}
//file writing
		for(int t = 0; t < students.size(); t++) {
			if (students.get(t).getHouse() == false) {
				out.println(students.get(t).getName());
			}			
		}
	}
}
