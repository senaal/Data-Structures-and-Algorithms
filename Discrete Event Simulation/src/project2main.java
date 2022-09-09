import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Scanner;

public class project2main {
	public static void main(String[] args) throws FileNotFoundException {
		
		Locale.setDefault(new Locale("en", "US"));
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		// created PQs with specific comparators
		ArrayList<Player> players = new ArrayList<>();
		ArrayList<Physiotherapist> physiotherapists = new ArrayList<>();
		PriorityQueue<Event> events = new PriorityQueue<>(Event::timeComp);
		PriorityQueue<Event> training = new PriorityQueue<>(Event::timeComp);
		PriorityQueue<Event> massage = new PriorityQueue<>(Event::skillLevComp);
		PriorityQueue<Event> physiotherapy = new PriorityQueue<>(Event::trTimeComp);

		int maxT = 0;
		int maxP = 0;
		int maxM = 0;
		// file reading
		int N = in.nextInt();
		while (N > 0) {
			int ID = in.nextInt();
			int skill = in.nextInt();
			Player p = new Player(ID, skill);
			players.add(p);
			N--;
		}

		int A = in.nextInt();
		while (A > 0) {
			String type = in.next();
			int ID = in.nextInt();
			double time = in.nextDouble();
			double duration = in.nextDouble();
			Event e = new Event(type, players.get(ID), time, duration);
			events.add(e);
			A--;
		}

		int S = in.nextInt();
		for (int t = 0; S > t; t++) {
			double duration = in.nextDouble();
			Physiotherapist p = new Physiotherapist(t, duration);
			physiotherapists.add(p);
		}

		int coach = in.nextInt();
		int masseur = in.nextInt();

		int cancelled = 0;
		int invalid = 0;
		double dur = 0;
		double totalTime = 0;
		double totalMassage = 0;
		int massageCount = 0;
		double totalTraining = 0;
		int trainingCount = 0;
		double totalTherapy = 0;
		double trainingQ = 0;
		double massageQ = 0;
		double therapyQ = 0;
		

		while (events.isEmpty() == false) { //while for every start and finish events.
			int maxLenPhysiotherapyQueue = physiotherapy.size();
			int maxLenMassageQueue = massage.size();
			int maxLenTrainingQueue = training.size();
			// checkers for maximum length of queues.
			if(maxT < maxLenTrainingQueue) {
				maxT = maxLenTrainingQueue;
			}
			if(maxP < maxLenPhysiotherapyQueue) {
				maxP = maxLenPhysiotherapyQueue;
			}
			if(maxM < maxLenMassageQueue) {
				maxM = maxLenMassageQueue;
			}
			Event e = events.poll();
			double time = e.getTime();
			totalTime = time;
					
			if (e.isStart() == true) { //checks start events' type and set their times and creates finish events.
				if (e.getType().equals("f")) {
					totalTherapy += e.getPhysiotherapist().getDuration();
					e.getPhysiotherapist().setStatus(true);
					e.setTime(time + e.getPhysiotherapist().getDuration());		
					e.getPlayer().setTherapyTime(e.getPhysiotherapist().getDuration());
					e.setStart(false);
					events.add(e);	
				} 
				else {
						if (e.getType().equals("t")) {
							if (e.getPlayer().isStatus() == false) {
								e.getPlayer().setStatus(true);
								e.getPlayer().setTrainingTime(e.getDuration());
								totalTraining += players.get(e.getPlayer().getID()).getTrainingTime();
								trainingCount++;
								if (coach > 0) {
									coach--;
									e.setTime(e.getDuration() + time);
									e.setStart(false);
									events.add(e);
								} 
								else {
									training.add(e);
									maxLenTrainingQueue++;
								}
							}
							else {
								cancelled++;
							}
						} 
						
						else if (e.getType().equals("m")) {
							if (e.getPlayer().getMassage() < 3) {
								if (e.getPlayer().isStatus() == false) {
									e.getPlayer().setStatus(true);
									e.getPlayer().setMassage(e.getPlayer().getMassage() + 1);
									e.getPlayer().setMassageTime(e.getDuration());
									totalMassage += players.get(e.getPlayer().getID()).getMassageTime();
									massageCount++;
									if (masseur > 0) {
										masseur--;
										e.setTime(e.getDuration() + time);
										e.setStart(false);
										events.add(e);
									}
									else {
										massage.add(e);
										maxLenMassageQueue++;
									}
								}
								else {
									cancelled++;
								}
							}
							else {
								invalid++;
							}
						}	
					}
				}
			else if (e.isStart() == false) { //checks finish events' type and if queue is not empty, create another finish event.
				e.getPlayer().setStatus(false);
				if (e.getType().equals("t")) {
					e.getPlayer().setStatus(true);
					Event phy = new Event("f", e.getPlayer(), time , dur, null);
					physiotherapy.add(phy);
					maxLenPhysiotherapyQueue++;
					if (training.isEmpty()) {
						coach++;
					}
					else {
						Event t = training.poll();
						t.getPlayer().setWaitTrainQ(time - t.getTime());
						trainingQ += t.getPlayer().getWaitTrainQ();
						maxLenTrainingQueue--;
						t.setTime(time + t.getDuration()); 
						t.setStart(false);
						events.add(t);
					}
					//checks availibility of physiotherapist.
					for (int i = 0; physiotherapists.size() > i; i++) {
						Event ph = physiotherapy.poll();
						maxLenPhysiotherapyQueue--;
						if (physiotherapists.get(i).isStatus() == false) {
							ph.setDuration(physiotherapists.get(i).getDuration());
							physiotherapists.get(i).setStatus(true);
							ph.setPhysiotherapist(physiotherapists.get(i));
							ph.setStart(true);
							events.add(ph);
							break;
						}
						else {
							physiotherapy.add(ph);
							maxLenPhysiotherapyQueue++;
						}
					}
				}

				else if (e.getType().equals("m")) {
					if (massage.isEmpty() == true) {
						masseur++;
					}
					else {
						Event m = massage.poll();
						m.getPlayer().increaseMWT(time - m.getTime());
						m.setTime(time + m.getDuration());
						m.setStart(false);
						events.add(m);
						maxLenMassageQueue--;

					}
				}

				else if (e.getType().equals("f")) {
					if (physiotherapy.isEmpty() == true) {
						e.getPhysiotherapist().setStatus(false);
					}
					else {
						Event p = physiotherapy.poll();
						p.setPhysiotherapist(e.getPhysiotherapist());
						p.getPlayer().increaseThWT(time - p.getTime());
						p.setDuration(p.getPhysiotherapist().getDuration());
						totalTherapy += p.getPhysiotherapist().getDuration();
						p.setTime(time + p.getDuration());
						p.setStart(false);
						events.add(p);
						maxLenPhysiotherapyQueue--;
					}
				}
			}
		}
		double totT = (double)totalTraining/trainingCount;
		double totTh = (double)totalTherapy/trainingCount;
		double totM = (double)totalMassage/massageCount;
		
		// checks all players and compare their waiting times in therapy queue.
		double waitingT = 0;
		int idOfQ = 0;
		for (int s = 0; players.size() > s; s++) {
			therapyQ += players.get(s).getWaitTherQ();
			double waiting = players.get(s).getWaitTherQ();
			if(waiting > waitingT) {
				waitingT = waiting;
				idOfQ = players.get(s).getID();
			}
			
		}
		// checks all players and compare their waiting times in massage queue.
		double waitingM = Double.POSITIVE_INFINITY;
		int idOfMQ = 0;
		for(int s = 0; players.size() > s; s++) {
			massageQ += players.get(s).getWaitMassQ();
			if(players.get(s).getMassage() == 3) {
				double waiting = players.get(s).getWaitMassQ();
				if(waiting < waitingM) {
					waitingM = waiting;
					idOfMQ = players.get(s).getID();
				}
			}
		}

		
		// average waiting time calculators
		double avgWaitTimeTraining = (double)trainingQ/trainingCount;
		double avgWaitTimeTherapy = (double)therapyQ/trainingCount;
		double avgWaitTimeMassage = (double)massageQ/massageCount;
		double avgTurnaroundTime = (totT + totTh + avgWaitTimeTraining + avgWaitTimeTherapy);
		// file writing
		out.println(maxT);
		out.println(maxP);
		out.println(maxM);
		out.println(String.format("%.3f",avgWaitTimeTraining));
		out.println(String.format("%.3f",avgWaitTimeTherapy));
		out.println(String.format("%.3f",avgWaitTimeMassage));
		out.println( String.format("%.3f",totT));
		out.println(String.format("%.3f",totTh));
		out.println(String.format("%.3f",totM));
		out.println(String.format("%.3f",avgTurnaroundTime));
		out.print(idOfQ + " ");
		out.println(String.format("%.3f",waitingT));
		out.print(idOfMQ + " ");
		out.println(String.format("%.3f",waitingM));
		out.println(invalid);
		out.println(cancelled);
		out.println(String.format("%.3f",totalTime));
	}
}

